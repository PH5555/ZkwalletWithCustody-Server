package com.zkrypto.zkwalletWithCustody.domain.transaction.domain.repository;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTransactionsBySender(Corporation sender);

    List<Transaction> findTransactionsByReceiver(Corporation receiver);
}
