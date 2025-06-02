package com.zkrypto.zkwalletWithCustody.global.crypto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.ing.dlt.zkkrypto.ecc.mimc.Mimc7Hash;
import org.springframework.stereotype.Service;

@Service
public class Mimc7Utils {
    private final Mimc7Hash mimc7;

    public Mimc7Utils() {
        mimc7 = new Mimc7Hash();
    }

    public BigInteger hash(BigInteger input) {
        BigInteger mod = input.mod(mimc7.getR());
        byte[] hash = mimc7.hash(mod.toByteArray());
        return new BigInteger(hash);
    }

    public BigInteger hash(List<BigInteger> inputs) {
        List<BigInteger> modResult = new ArrayList<>();
        for (BigInteger input : inputs) {
            modResult.add(input.mod(mimc7.getR()));
        }
        byte[] hash = mimc7.hash(modResult);
        return new BigInteger(hash);
    }
}
