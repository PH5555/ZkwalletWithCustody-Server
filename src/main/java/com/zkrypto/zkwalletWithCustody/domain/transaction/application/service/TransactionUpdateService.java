package com.zkrypto.zkwalletWithCustody.domain.transaction.application.service;

import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Transaction;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.repository.TransactionRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionUpdateService {
    private final TransactionRepository transactionRepository;

    @Transactional
    public void updateTransaction(Long transactionId, BigInteger blockNumber, String transactionHash) {
        Transaction transaction = transactionRepository.findById(transactionId)
                        .orElseThrow(()-> new IllegalArgumentException("잘못된 트랜잭션입니다."));
        transaction.update(blockNumber, transactionHash);
    }
}
