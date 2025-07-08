package com.zkrypto.zkwalletWithCustody.domain.transaction.application.service;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.constant.UPK;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.repository.CorporationRepository;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.constant.Role;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.repository.MemberRepository;
import com.zkrypto.zkwalletWithCustody.domain.note.application.dto.event.NoteEventDto;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionUpdateCommand;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.response.TransactionResponse;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.constant.Status;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.constant.Type;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Transaction;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.repository.TransactionRepository;
import com.zkrypto.zkwalletWithCustody.global.crypto.utils.AESUtils;
import com.zkrypto.zkwalletWithCustody.global.crypto.utils.WalletUtils;
import com.zkrypto.zkwalletWithCustody.global.web3.Groth16AltBN128Mixer;
import com.zkrypto.zkwalletWithCustody.global.web3.Web3Service;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
    private final MemberRepository memberRepository;
    private final CorporationRepository corporationRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;
    private final Web3Service web3Service;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${contract.mixer.address}")
    private String contractAddress;

    @Value("${ethereum.privateKey}")
    private String privateKey;
    /***
     *  트랜잭션 생성 메서드
     */
    @Transactional
    public Transaction createTransaction(UUID memberId, TransactionCreationCommand transactionCreationCommand) {
        // 멤버 확인
        Member sender = memberRepository.findMemberByMemberIdWithCorporation(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));

        // 비밀번호 확인
        if(!passwordEncoder.matches(transactionCreationCommand.getPassword(), sender.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 불일치합니다.");
        }

        // receiver 확인
        Corporation receiver = corporationRepository.findCorporationByAddress(transactionCreationCommand.getReceiverAddress())
                .orElseThrow(() -> new IllegalArgumentException("해당 주소를 가진 법인이 없습니다."));

        // 트랜잭션 생성
        Transaction transaction = Transaction.create(transactionCreationCommand, sender.getCorporation(), receiver);
        transactionRepository.save(transaction);
        return transaction;
    }

    /***
     *  트랜잭션 가져오기 메서드
     */
    @Transactional
    public List<TransactionResponse> getTransactions(UUID memberId, Status status, Type type) {
        // 어드민일 경우 status 상관 없이 다 가져오기
        if(memberId == null) {
            return transactionRepository.findAllWithCorporation().stream().map(TransactionResponse::from).toList();
        }

        // 멤버 확인
        Member member = memberRepository.findMemberByMemberIdWithCorporation(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));

        if(member.getRole() == Role.ROLE_USER && status == Status.NONE) {
            return transactionRepository.findTransactionsBySender(member.getCorporation(), Status.NONE).stream().map(TransactionResponse::from).toList();
        }
        else if (member.getRole() == Role.ROLE_USER && status == Status.DONE) {
            if(type == Type.SEND) {
                return transactionRepository.findTransactionsBySender(member.getCorporation(), Status.DONE).stream().map(TransactionResponse::from).toList();
            }
            else if(type == Type.RECEIVE) {
                return transactionRepository.findTransactionsByReceiver(member.getCorporation(), Status.DONE).stream().map(TransactionResponse::from).toList();
            }
        }

        return null;
    }

    @Transactional
    public void updateTransaction(Transaction transaction, BigInteger blockNumber) {
        transaction.update(blockNumber);
    }

    /***
     *  트랜잭션 조회 이벤트 리스너
     */
    @Transactional
    public void monitorTransaction(TransactionUpdateCommand transactionUpdateCommand) {
        // 트랜잭션 조회
        Transaction transaction = transactionRepository.findById(transactionUpdateCommand.getTransactionId())
                .orElseThrow(() -> new IllegalArgumentException("해당 트랜잭션을 찾을 수 없습니다."));

        // 블럭넘버 조회
        Optional<BigInteger> blockNumber = transactionRepository.findMaxBlockNumber();
        DefaultBlockParameter startBlock = blockNumber.map(DefaultBlockParameter::valueOf).orElse(DefaultBlockParameterName.EARLIEST);

        // 블록 조회 이벤트 생성
        Groth16AltBN128Mixer smartContract = web3Service.loadContract(privateKey, contractAddress);
        Flowable<Long> timeoutFlowable = Flowable.timer(2, TimeUnit.MINUTES);
        Flowable<Groth16AltBN128Mixer.LogZkTransferEventResponse> eventFlowable = smartContract.logZkTransferEventFlowable(startBlock, DefaultBlockParameterName.LATEST);
        AtomicReference<Disposable> subscriptionRef = new AtomicReference<>();
        Disposable subscription = eventFlowable
                .takeUntil(timeoutFlowable)
                .subscribe(event -> {
                    if (valid(event, transaction.getSender())) {
                        // 트랜잭션 업데이트
                        updateTransaction(transaction, event.log.getBlockNumber());

                        // 노트 생성 이벤트 생성
                        eventPublisher.publishEvent(new NoteEventDto(event.ct, event.com, transaction.getReceiver(), event.numLeaves));
                        subscriptionRef.get().dispose();
                    }
                });
        subscriptionRef.set(subscription);
    }

    /***
     *  블럭 유효성 검사
     */
    private Boolean valid(Groth16AltBN128Mixer.LogZkTransferEventResponse event, Corporation sender) throws Exception {
        // ena 복원
        String usk = AESUtils.decrypt(sender.getSecretKey(), sender.getSalt());
        UPK upk = WalletUtils.recoverFromUserSk(new BigInteger(usk));
        // event의 ena와 sender의 ena 일치하면 true
        return event.ena.getFirst().toString().equals(upk.getEna().toString());
    }
}
