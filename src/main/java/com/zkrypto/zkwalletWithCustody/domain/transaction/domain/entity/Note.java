package com.zkrypto.zkwalletWithCustody.domain.transaction.domain.entity;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;
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

    private Note(String open, String tokenAddress, String tokenId, String amount, String addr, String commitment, Corporation corporation) {
        this.open = open;
        this.tokenAddress = tokenAddress;
        this.tokenId = tokenId;
        this.amount = amount;
        this.addr = addr;
        this.commitment = commitment;
        this.corporation = corporation;
    }

    public static Note from(List<BigInteger> ret, BigInteger commitment, Corporation corporation) {
        return new Note(ret.get(0).toString(), ret.get(1).toString(), ret.get(2).toString(), ret.get(3).toString(), ret.get(4).toString(), commitment.toString(), corporation);
    }
}
