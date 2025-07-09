package com.zkrypto.zkwalletWithCustody.domain.audit.domain.constant;

import com.zkrypto.zkwalletWithCustody.global.crypto.constant.AffinePoint;
import com.zkrypto.zkwalletWithCustody.global.crypto.utils.EcUtils;
import com.zkrypto.zkwalletWithCustody.global.crypto.utils.FieldUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

@Slf4j
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

    public static AuditKey keyGen() {
        BigInteger sk = FieldUtils.randomFieldElement();
        log.info("audit secret key: " + sk);
        AffinePoint point = EcUtils.basePointMul(sk);
        return new AuditKey(point.getX(), point.getY(), sk);
    }
}
