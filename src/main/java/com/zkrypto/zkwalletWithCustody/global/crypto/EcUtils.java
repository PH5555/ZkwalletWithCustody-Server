package com.zkrypto.zkwalletWithCustody.global.crypto;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.Security;

@Component
public class EcUtils {
    static {
        // Bouncy Castle 프로바이더 등록
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public static ECPoint basePointMulCustom(BigInteger exp) {
        BigInteger p = new BigInteger("21888242871839275222246405745257275088548364400416034343698204186575808495617");
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.valueOf(3);
        BigInteger r = new BigInteger("21888242871839275222246405745257275088548364400416034343698204186575808495617");
        BigInteger h = BigInteger.ONE;
        BigInteger Gx = BigInteger.ONE;
        BigInteger Gy = BigInteger.valueOf(2);

        ECCurve curve = new ECCurve.Fp(p, a, b, r, h);

        ECPoint G = curve.createPoint(Gx, Gy);

        if (!G.isValid()) {
            throw new IllegalStateException("G = (1,2)가 y^2 = x^3 + 3 (mod p) 위에 있지 않습니다.");
        }

        ECPoint R = G.multiply(exp);
        return R.normalize();
    }
}
