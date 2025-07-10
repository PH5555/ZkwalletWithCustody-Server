package com.zkrypto.zkwalletWithCustody.domain.transaction.domain.repository;

import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.SignedTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignedTransactionRepository extends JpaRepository<SignedTransaction, Long> {
}
