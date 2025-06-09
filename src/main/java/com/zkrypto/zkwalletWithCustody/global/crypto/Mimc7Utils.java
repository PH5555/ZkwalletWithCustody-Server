package com.zkrypto.zkwalletWithCustody.global.crypto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.ing.dlt.zkkrypto.ecc.mimc.Mimc7Hash;
import org.springframework.stereotype.Service;

@Service
public class Mimc7Utils {
    private final Mimc7Hash mimc7;
    private final BigInteger prime = new BigInteger("21888242871839275222246405745257275088548364400416034343698204186575808495617");

    public Mimc7Utils() {
        mimc7 = new Mimc7Hash();
    }

    public BigInteger hash(BigInteger input) {
        BigInteger mod = input.mod(prime);
        byte[] hash = mimc7.hash(mod.toByteArray());
        return new BigInteger(hash).mod(prime);
    }

    public BigInteger hash(List<BigInteger> inputs) {
        List<BigInteger> modResult = new ArrayList<>();
        for (BigInteger input : inputs) {
            modResult.add(input.mod(prime));
        }
        byte[] hash = mimc7.hash(modResult);
        return new BigInteger(hash).mod(prime);
    }
}
