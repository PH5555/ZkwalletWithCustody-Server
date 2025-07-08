package com.zkrypto.zkwalletWithCustody.domain.transaction.domain.repository;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.constant.Status;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select transaction from Transaction transaction left join fetch transaction.sender left join fetch transaction.receiver where transaction.sender = :sender and transaction.status = :status")
    List<Transaction> findTransactionsBySender(@Param(value = "sender") Corporation sender, @Param(value = "status") Status status);

    @Query("select transaction from Transaction transaction left join fetch transaction.sender left join fetch transaction.receiver where transaction.receiver = :receiver and transaction.status = :status")
    List<Transaction> findTransactionsByReceiver(@Param(value = "receiver") Corporation receiver, @Param(value = "status") Status status);

    @Query("select transaction from Transaction transaction left join fetch transaction.sender left join fetch transaction.receiver")
    List<Transaction> findAllWithCorporation();

    @Query("select MAX(transaction.blockNumber) from Transaction transaction")
    Optional<BigInteger> findMaxBlockNumber();

    @Query("select transaction from Transaction transaction left join fetch transaction.sender left join fetch transaction.receiver where transaction.id = :transactionId")
    Optional<Transaction> findTransactionByIdWithCorporation(@Param(value = "transactionId") Long transactionId);

    Optional<Transaction> findTransactionById(Long id);
}
