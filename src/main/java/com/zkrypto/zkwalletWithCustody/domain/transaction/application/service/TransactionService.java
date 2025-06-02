package com.zkrypto.zkwalletWithCustody.domain.transaction.application.service;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.repository.CorporationRepository;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.repository.MemberRepository;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Transaction;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final MemberRepository memberRepository;
    private final CorporationRepository corporationRepository;
    private final TransactionRepository transactionRepository;

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
}
