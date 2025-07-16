package com.zkrypto.zkwalletWithCustody.domain.note.domain.entity;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.member.domain.constant.Role;
import com.zkrypto.zkwalletWithCustody.global.crypto.constant.AffinePoint;
import com.zkrypto.zkwalletWithCustody.global.crypto.constant.MiMC7;
import com.zkrypto.zkwalletWithCustody.global.crypto.constant.TwistedEdwardsCurve;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Setter
    @ManyToOne
    @JoinColumn(name = "corporation_id")
    private Corporation corporation;

    private Note(String open, String tokenAddress, String tokenId, String amount, String addr, String commitment, String index) {
        this.open = open;
        this.tokenAddress = tokenAddress;
        this.tokenId = tokenId;
        this.amount = amount;
        this.addr = addr;
        this.commitment = commitment;
        this.index = index;
        this.isSpent = false;
    }

    public static Note from(List<BigInteger> ret, BigInteger commitment, BigInteger numLeaves) {
        return new Note(ret.get(0).toString(), ret.get(1).toString(), ret.get(2).toString(), ret.get(3).toString(), ret.get(4).toString(), commitment.toString(), numLeaves.subtract(BigInteger.ONE).toString());
    }

    public static Note recoverNote(List<BigInteger> ct, BigInteger com, BigInteger numLeaves, String sk, Role role) {
        AffinePoint c0 = new AffinePoint(ct.get(0), ct.get(1));
        AffinePoint c1 = new AffinePoint(ct.get(2), ct.get(3));
        AffinePoint c2 = new AffinePoint(ct.get(4), ct.get(5));

        List<AffinePoint> recoverPoint = List.of(c1, c2);

        TwistedEdwardsCurve curve = new TwistedEdwardsCurve();
        AffinePoint curveV0 = curve.computeScalarMul(c0, new BigInteger(sk));

        // user면 c1, audit면 c2
        AffinePoint curveK = curve.subAffinePoint(recoverPoint.get(role.ordinal()), curveV0);

        MiMC7 mimc7 = new MiMC7();
        List<BigInteger> ret = new ArrayList<>();
        AtomicInteger counter = new AtomicInteger(0);
        ct.stream().skip(6).forEach((e) -> {
            BigInteger hash = mimc7.hash(curveK.getX(), BigInteger.valueOf(counter.getAndIncrement()));
            ret.add(mod(e.subtract(hash), new BigInteger("21888242871839275222246405745257275088548364400416034343698204186575808495617")));
        });

        return Note.from(ret, com, numLeaves);
    }

    private static BigInteger mod(BigInteger value, BigInteger mod) {
        if(value.compareTo(BigInteger.ZERO) > 0) {
            return value.mod(mod);
        } else {
            return ((value.mod(mod)).add(mod)).mod(mod);
        }
    }

    public Boolean isOwner() {
        MiMC7 mimc7 = new MiMC7();
        BigInteger hash = mimc7.hash(new BigInteger(this.open), new BigInteger(this.tokenAddress), new BigInteger(this.tokenId), new BigInteger(this.amount), new BigInteger(this.addr));
        return new BigInteger(this.commitment).compareTo(hash) == 0;
    }

    public void setNoteSpend() {
        this.isSpent = true;
    }
}
