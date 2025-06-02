package com.zkrypto.zkwalletWithCustody.domain.transaction.domain.repository;

import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
