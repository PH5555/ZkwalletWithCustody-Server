package com.zkrypto.zkwalletWithCustody.domain.transaction.application.service;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.repository.CorporationRepository;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.constant.Role;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.repository.MemberRepository;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.response.TransactionResponse;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.constant.Status;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.constant.Type;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Transaction;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final MemberRepository memberRepository;
    private final CorporationRepository corporationRepository;
    private final TransactionRepository transactionRepository;

    /***
     *  트랜잭션 생성 메서드
     */
    @Transactional
    public void createTransaction(UUID memberId, TransactionCreationCommand transactionCreationCommand) {
        // 멤버 확인
        Member sender = memberRepository.findMemberByMemberIdWithCorporation(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));

        // receiver 확인
        Corporation receiver = corporationRepository.findCorporationsByAddress(transactionCreationCommand.getReceiverAddress())
                .orElseThrow(() -> new IllegalArgumentException("해당 주소를 가진 법인이 없습니다."));

        // 트랜잭션 생성
        Transaction transaction = Transaction.create(transactionCreationCommand, sender.getCorporation(), receiver);
        transactionRepository.save(transaction);
    }

    /***
     *  트랜잭션 가져오기 메서드
     */
    @Transactional
    public List<TransactionResponse> getTransactions(UUID memberId, Status status, Type type) {
        // 멤버 확인
        Member member = memberRepository.findMemberByMemberIdWithCorporation(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));

        List<Transaction> transactions = new ArrayList<>();
        // 어드민, 일반 유저 분기
        if(member.getRole().equals(Role.ROLE_ADMIN)) { // 어드민일 경우 status 상관 없이 다 가져오기
            transactions = transactionRepository.findAll();
        }
        else if(member.getRole().equals(Role.ROLE_USER)) { // 일반 유저일 경우 status none 인거는 send만, status done 인거는 send, receive 요청한거에 따라
            if(status.equals(Status.NONE)) {
                // send
                transactions = transactionRepository.findTransactionsBySender(member.getCorporation());
            }
            else if(status.equals(Status.DONE)) {
                if(type.equals(Type.SEND)) {
                    transactions = transactionRepository.findTransactionsBySender(member.getCorporation());
                }
                else if(type.equals(Type.RECEIVE)) {
                    transactions = transactionRepository.findTransactionsByReceiver(member.getCorporation());
                }
            }
        }

        return transactions.stream().map(TransactionResponse::from).toList();
    }
}
