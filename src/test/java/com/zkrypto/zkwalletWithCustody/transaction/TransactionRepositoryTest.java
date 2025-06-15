package com.zkrypto.zkwalletWithCustody.transaction;

import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Transaction;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.repository.TransactionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

@SpringBootTest
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void 트랜잭션_생성() {
        Transaction transaction1 = new Transaction();
        transaction1.setBlockNumber(BigInteger.ZERO);
        transactionRepository.save(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setBlockNumber(BigInteger.ONE);
        transactionRepository.save(transaction2);
    }

    @Test
    void 블럭_조회() {
        BigInteger maxBlockNumber = transactionRepository.findMaxBlockNumber();
        Assertions.assertThat(maxBlockNumber.toString()).isEqualTo("1");
    }
}
