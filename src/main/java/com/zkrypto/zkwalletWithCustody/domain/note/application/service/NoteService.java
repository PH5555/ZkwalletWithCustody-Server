package com.zkrypto.zkwalletWithCustody.domain.note.application.service;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.domain.note.domain.entity.Note;
import com.zkrypto.zkwalletWithCustody.domain.note.domain.repository.NoteRepository;
import com.zkrypto.zkwalletWithCustody.global.crypto.constant.AffinePoint;
import com.zkrypto.zkwalletWithCustody.global.crypto.constant.MiMC7;
import com.zkrypto.zkwalletWithCustody.global.crypto.constant.TwistedEdwardsCurve;
import com.zkrypto.zkwalletWithCustody.global.crypto.utils.AESUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public void saveNote(Note note) {
        noteRepository.save(note);
    }

    public Note getNote(List<BigInteger> ct, BigInteger commitment, BigInteger numLeaves, Corporation corporation) throws Exception {
        // usk 복호화
        String usk = AESUtils.decrypt(corporation.getSecretKey(), corporation.getSalt());

        // note 생성
        AffinePoint c0 = new AffinePoint(ct.get(0), ct.get(1));
        AffinePoint c1 = new AffinePoint(ct.get(2), ct.get(3));
        TwistedEdwardsCurve curve = new TwistedEdwardsCurve();
        AffinePoint curveV0 = curve.computeScalarMul(c0, new BigInteger(usk));
        AffinePoint curveK = curve.subAffinePoint(c1, curveV0);

        MiMC7 mimc7 = new MiMC7();
        List<BigInteger> ret = new ArrayList<>();
        AtomicInteger counter = new AtomicInteger(0);
        ct.stream().skip(6).forEach((e) -> {
            BigInteger hash = mimc7.hash(curveK.getX(), BigInteger.valueOf(counter.getAndIncrement()));
            ret.add(mod(e.subtract(hash), new BigInteger("21888242871839275222246405745257275088548364400416034343698204186575808495617")));
        });

        return Note.from(ret, commitment, corporation, numLeaves);
    }

    public Boolean isOwner(Note note) {
        MiMC7 mimc7 = new MiMC7();
        BigInteger hash = mimc7.hash(new BigInteger(note.getOpen()), new BigInteger(note.getTokenAddress()), new BigInteger(note.getTokenId()), new BigInteger(note.getAmount()), new BigInteger(note.getAddr()));
        return new BigInteger(note.getCommitment()).compareTo(hash) == 0;
    }

    private BigInteger mod(BigInteger value, BigInteger mod) {
        if(value.compareTo(BigInteger.ZERO) > 0) {
            return value.mod(mod);
        } else {
            return ((value.mod(mod)).add(mod)).mod(mod);
        }
    }
}
