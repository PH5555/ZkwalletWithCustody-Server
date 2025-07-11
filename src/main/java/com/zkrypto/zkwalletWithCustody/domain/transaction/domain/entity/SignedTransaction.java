package com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity;

import com.zkrypto.zkwalletWithCustody.domain.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class SignedTransaction {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public SignedTransaction(Transaction transaction, Member member) {
        this.transaction = transaction;
        this.member = member;
    }
}
