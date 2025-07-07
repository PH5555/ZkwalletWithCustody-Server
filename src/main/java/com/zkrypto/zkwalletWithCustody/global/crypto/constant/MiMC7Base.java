package com.zkrypto.zkwalletWithCustody.global.crypto.constant;

import org.bouncycastle.jcajce.provider.digest.Keccak;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class MiMC7Base {

    public static final String SEED = "mimc7_seed";
    public static final BigInteger BN256_FIELD_PRIME = new BigInteger("21888242871839275222246405745257275088548364400416034343698204186575808495617");

    protected String seed;
    protected BigInteger prime;
    protected int numRounds;

    // 라운드 함수 인터페이스
    @FunctionalInterface
    public interface MiMCRound {
        BigInteger round(BigInteger msg, BigInteger key, BigInteger rc);
    }

    protected MiMCRound mimcRound;

    public MiMC7Base(String seedStr, BigInteger prime, int numRounds, MiMCRound mimcRound) {
        // seedStr = SEED, prime = BN256_FIELD_PRIME, numRounds
        if (seedStr == null) seedStr = SEED;
        if (prime == null) prime = BN256_FIELD_PRIME;

        // Keccak256 해시 (bytes → hex)
        this.seed = keccak256(seedStr.getBytes(StandardCharsets.UTF_8));
        this.prime = prime;
        this.numRounds = numRounds;
        this.mimcRound = mimcRound;
    }

    // Keccak256 해시 결과를 hex string으로 반환
    public static String keccak256(byte[] input) {
        Keccak.Digest256 keccak = new Keccak.Digest256();
        keccak.update(input, 0, input.length);
        byte[] hash = keccak.digest();
        // hex string으로 변환
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    // hex string을 BigInteger로
    public static BigInteger hexToInt(String hex) {
        return new BigInteger(hex, 16);
    }

    // MiMC 암호화
    public BigInteger encrypt(BigInteger msg, BigInteger ek) {
        msg = msg.mod(prime);
        ek = ek.mod(prime);
        String roundConstant = seed;
        BigInteger result = mimcRound.round(msg, ek, BigInteger.ZERO);

        for (int i = 0; i < numRounds - 1; i++) {
            roundConstant = keccak256(hexStringToByteArray(roundConstant));
            result = mimcRound.round(result, ek, hexToInt(roundConstant));
        }
        return result.add(ek).mod(prime);
    }

    // 해시 함수
    protected BigInteger _hash(BigInteger left, BigInteger right) {
        BigInteger x = left.mod(prime);
        BigInteger y = right.mod(prime);
        return encrypt(x, y).add(x).add(y).mod(prime);
    }

    // 여러개의 인자에 대한 해시
    public BigInteger hash(BigInteger... args) {
        if (args.length == 1) {
            return _hash(args[0], args[0]);
        } else {
            BigInteger result = _hash(args[0], args[1]);
            for (int i = 2; i < args.length; i++) {
                result = _hash(result, args[i]);
            }
            return result;
        }
    }

    // hex string → byte array
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}