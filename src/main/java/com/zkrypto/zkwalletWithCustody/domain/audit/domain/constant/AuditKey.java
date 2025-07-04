package com.zkrypto.zkwalletWithCustody.domain.audit.domain.constant;

import lombok.Getter;

import java.math.BigInteger;

@Getter
public class AuditKey {
    private Point pk;
    private BigInteger sk;

    @Getter
    public static class Point {
        private BigInteger x;
        private BigInteger y;

        public Point(BigInteger x, BigInteger y) {
            this.x = x;
            this.y = y;
        }
    }

    private AuditKey(BigInteger x, BigInteger y, BigInteger sk) {
        this.pk = new Point(x, y);
        this.sk = sk;
    }

//    public static AuditKey keyGen() {
//        BigInteger sk = FieldUtils.randomFieldElement();
//        ECPoint ecPoint = EcUtils.basePointMulCustom(sk);
//        return new AuditKey(ecPoint.getAffineXCoord().toBigInteger(), ecPoint.getAffineYCoord().toBigInteger(), sk);
//    }
}
