package com.zkrypto.zkwalletWithCustody.global.crypto.constant;

import java.math.BigInteger;

public class MiMC7 extends MiMC7Base {

    public MiMC7(String seedStr, BigInteger prime, Integer numRounds) {
        super(
                seedStr,
                prime,
                numRounds != null ? numRounds : 91,
                (msg, key, rc) -> {
                    BigInteger xored = msg.add(key).add(rc).mod(BN256_FIELD_PRIME);
                    return xored.modPow(BigInteger.valueOf(7), BN256_FIELD_PRIME);
                }
        );
    }

    // 오버로드: 인자 없이 생성 가능
    public MiMC7() {
        this(null, null, null);
    }
}