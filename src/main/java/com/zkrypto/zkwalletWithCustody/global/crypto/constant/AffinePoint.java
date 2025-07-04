package com.zkrypto.zkwalletWithCustody.global.crypto.constant;

import lombok.Getter;

import java.math.BigInteger;

@Getter
public class AffinePoint {
    private BigInteger x;
    private BigInteger y;

    public AffinePoint(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
    }
}
