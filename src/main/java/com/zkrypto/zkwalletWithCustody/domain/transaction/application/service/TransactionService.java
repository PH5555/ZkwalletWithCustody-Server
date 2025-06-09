package com.zkrypto.zkwalletWithCustody.domain.transaction.application.service;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.repository.CorporationRepository;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.constant.Role;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.repository.MemberRepository;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionUpdateCommand;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.response.TransactionResponse;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.constant.Status;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.constant.Type;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Transaction;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {
    private final MemberRepository memberRepository;
    private final CorporationRepository corporationRepository;
    private final TransactionRepository transactionRepository;
    private final PasswordEncoder passwordEncoder;
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
        Corporation receiver = corporationRepository.findCorporationsByAddress(transactionCreationCommand.getReceiverAddress())
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
    public void updateTransaction(TransactionUpdateCommand transactionUpdateCommand) {
        Transaction transaction = transactionRepository.findById(transactionUpdateCommand.getTransactionId())
                .orElseThrow(() -> new IllegalArgumentException("해당 트랜잭션을 찾을 수 없습니다."));
        transaction.setStatus(Status.DONE);
    }
}
