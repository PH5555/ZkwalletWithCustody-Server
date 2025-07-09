package com.zkrypto.zkwalletWithCustody.transaction;

import com.zkrypto.zkwalletWithCustody.domain.corporation.application.dto.request.CorporationCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.corporation.application.service.CorporationService;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.repository.CorporationRepository;
import com.zkrypto.zkwalletWithCustody.domain.transaction.application.dto.request.TransactionCreationCommand;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.constant.Status;
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
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CorporationService corporationService;
    @Autowired
    private CorporationRepository corporationRepository;

//
//    @Test
//    void 블럭_조회() {
//        Optional<BigInteger> maxBlockNumber = transactionRepository.findMaxBlockNumber();
//        Assertions.assertThat(maxBlockNumber.get().toString()).isEqualTo("1");
//    }
//
//    @Transactional
//    @Test
//    void 트랜잭션_조회() {
//
//        Corporation corporation = corporationService.createCorporation(new CorporationCreationCommand("지크립토토"));
//        Corporation corporation2 = corporationService.createCorporation(new CorporationCreationCommand("지크립토토토"));
//
//        Transaction transaction1 = new Transaction();
//        transaction1.setSender(corporation);
//        transaction1.setReceiver(corporation2);
//        transaction1.setStatus(Status.DONE);
//        transactionRepository.save(transaction1);
//
//        Transaction transaction2 = new Transaction();
//        transaction2.setSender(corporation2);
//        transaction2.setReceiver(corporation);
//        transaction2.setStatus(Status.DONE);
//        transactionRepository.save(transaction2);
//
//        log.info(corporation.getCorporationId());
//        List<Transaction> result1 = transactionRepository.findTransactionsByCorporation(corporation, Status.DONE);
//        List<Transaction> result2 = transactionRepository.findTransactionsByReceiver(corporation, Status.DONE);
//
//        Assertions.assertThat(result1.size()).isEqualTo(2);
//        Assertions.assertThat(result2.size()).isEqualTo(1);
//    }
}
