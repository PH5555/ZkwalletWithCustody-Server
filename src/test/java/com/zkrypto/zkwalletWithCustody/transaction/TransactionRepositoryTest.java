package com.zkrypto.zkwalletWithCustody.transaction;

import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.request.CorporationCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.service.CorporationService;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Transaction;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Optional;

@Slf4j
@SpringBootTest
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CorporationService corporationService;

    @Transactional
    @BeforeEach
    void 트랜잭션_생성() {
        Corporation corporation = corporationService.createCorporation(new CorporationCreationCommand("지크립토토"));
        Corporation corporation2 = corporationService.createCorporation(new CorporationCreationCommand("지크립토토토"));

        Transaction transaction1 = new Transaction();
        transaction1.setBlockNumber(BigInteger.ZERO);
        transaction1.setSender(corporation);
        transaction1.setReceiver(corporation2);
        transactionRepository.save(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setBlockNumber(BigInteger.ONE);
        transactionRepository.save(transaction2);
        transaction2.setSender(corporation);
        transaction2.setReceiver(corporation2);
        transactionRepository.save(transaction2);
    }

    @Test
    void 블럭_조회() {
        Optional<BigInteger> maxBlockNumber = transactionRepository.findMaxBlockNumber();
        Assertions.assertThat(maxBlockNumber.get().toString()).isEqualTo("1");
    }

    @Test
    void 트랜잭션_조회() {
        Optional<Transaction> transactionByIdWithCorporation = transactionRepository.findTransactionByIdWithCorporation(1L);
        BigInteger blockNumber = transactionByIdWithCorporation.get().getBlockNumber();
        log.info(blockNumber.toString());
        Assertions.assertThat(transactionByIdWithCorporation.get().getSender().getName()).isEqualTo("지크립토토");
        Assertions.assertThat(transactionByIdWithCorporation.get().getReceiver().getName()).isEqualTo("지크립토토토");
    }
}
