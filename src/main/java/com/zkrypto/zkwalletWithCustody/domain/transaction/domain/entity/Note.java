package com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
public class Note {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID noteId;

    private String open;
    private String tokenAddress;
    private String tokenId;
    private String amount;
    private String addr;
    private String commitment;
    private String index;
    private String isSpent;

    @ManyToOne
    @JoinColumn(name = "corporation_id")
    private Corporation corporation;
}
