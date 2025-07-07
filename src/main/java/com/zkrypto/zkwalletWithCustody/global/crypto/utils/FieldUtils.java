package com.zkrypto.zkwalletWithCustody.global.crypto.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class FieldUtils {
    private static final SecureRandom random = new SecureRandom();

    public static BigInteger randomFieldElement() {
        BigInteger prime = new BigInteger("21888242871839275222246405745257275088548364400416034343698204186575808495617");

        // prime의 비트 길이를 바이트 단위로 변환
        int bitLength = prime.bitLength();
        int byteLength = (int) Math.ceil(bitLength / 8.0);

        // 무작위 바이트 배열 생성
        byte[] randomBytes = new byte[byteLength];
        random.nextBytes(randomBytes);

        // 바이트 배열을 BigInteger로 변환 (양수로 처리)
        BigInteger randomValue = new BigInteger(1, randomBytes);

        // prime보다 작은 값으로 만들기 위해 mod
        return randomValue.mod(prime);
    }
}
