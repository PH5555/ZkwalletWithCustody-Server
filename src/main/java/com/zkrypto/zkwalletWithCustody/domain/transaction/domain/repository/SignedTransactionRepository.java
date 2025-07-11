package com.zkrypto.zkwalletWithCustody.domain.transaction.domain.repository;

import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.SignedTransaction;
import com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SignedTransactionRepository extends JpaRepository<SignedTransaction, Long> {
    @Query("select count(t) from SignedTransaction t where t.transaction = :transaction")
    int findSignedTransactionCountByTransaction(@Param("transaction") Transaction transaction);

    boolean existsByTransactionAndMember(Transaction transaction, Member member);
}
