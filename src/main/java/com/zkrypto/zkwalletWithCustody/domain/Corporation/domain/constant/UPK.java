package com.zkrypto.zkwalletWithCustody.domain.Corporation.domain.constant;

import lombok.Getter;
import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

@Getter
public class UPK {
    private BigInteger ena;
    private BigInteger pkOwn;
    private AffinePoint pkEnc;

    @Getter
    static class AffinePoint {
        private BigInteger x;
        private BigInteger y;

        public AffinePoint(BigInteger x, BigInteger y) {
            this.x = x;
            this.y = y;
        }
    }

    public UPK(BigInteger ena, BigInteger pkOwn, ECPoint pkEnc) {
        this.ena = ena;
        this.pkOwn = pkOwn;
        this.pkEnc = new AffinePoint(pkEnc.getAffineXCoord().toBigInteger(), pkEnc.getAffineYCoord().toBigInteger());
    }
}