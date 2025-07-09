package com.zkrypto.zkwalletWithCustody.domain.note.domain.entity;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
@Setter
public class Note {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID noteId;

    private String open;
    private String tokenAddress;
    private String tokenId;
    private String amount;
    private String addr;
    private String commitment;

    @Column(name = "note_index")
    private String index;
    private Boolean isSpent;

    @ManyToOne
    @JoinColumn(name = "corporation_id")
    private Corporation corporation;

    private Note(String open, String tokenAddress, String tokenId, String amount, String addr, String commitment, Corporation corporation, String index) {
        this.open = open;
        this.tokenAddress = tokenAddress;
        this.tokenId = tokenId;
        this.amount = amount;
        this.addr = addr;
        this.commitment = commitment;
        this.corporation = corporation;
        this.isSpent = false;
        this.index = index;
    }

    private Note(String open, String tokenAddress, String tokenId, String amount, String addr, String commitment, String index) {
        this.open = open;
        this.tokenAddress = tokenAddress;
        this.tokenId = tokenId;
        this.amount = amount;
        this.addr = addr;
        this.commitment = commitment;
        this.index = index;
    }

    public static Note from(List<BigInteger> ret, BigInteger commitment, Corporation corporation, BigInteger numLeaves) {
        return new Note(ret.get(0).toString(), ret.get(1).toString(), ret.get(2).toString(), ret.get(3).toString(), ret.get(4).toString(), commitment.toString(), corporation, numLeaves.subtract(BigInteger.ONE).toString());
    }

    public static Note from(List<BigInteger> ret, String commitment, BigInteger numLeaves) {
        return new Note(ret.get(0).toString(), ret.get(1).toString(), ret.get(2).toString(), ret.get(3).toString(), ret.get(4).toString(), commitment, numLeaves.subtract(BigInteger.ONE).toString());
    }

    public void setNoteSpend() {
        this.isSpent = true;
    }
}
