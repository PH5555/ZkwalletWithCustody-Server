package com.zkrypto.zkwalletWithCustody.global.crypto;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
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

    public static ECPoint basePointMul(BigInteger exp) {
        String curveName = "secp256k1";

        // 1. Named Curve 파라미터 로드
        ECParameterSpec spec = ECNamedCurveTable.getParameterSpec(curveName);
        if (spec == null) {
            throw new IllegalArgumentException("곡선 파라미터를 찾을 수 없습니다: " + curveName);
        }

        // 2. 곡선 객체와 기본 생성점 G 가져오기
        //    spec.getCurve() → ECCurve (곡선 위 연산에 사용)
        //    spec.getG()    → G (기본 생성점, ECPoint)
        ECPoint G = spec.getG();

        // 3. 스칼라(exp)를 곱해 새로운 점 R = exp * G 계산
        //    multiply() 내부에서 fast-double-and-add 또는 windowed NAF 알고리즘을 사용함
        ECPoint R = G.multiply(exp);

        // 4. 결과점 R을 Affine 좌표로 정규화(normalize)하고 반환
        //    normalize() 호출 후에는 getAffineXCoord(), getAffineYCoord()를 바로 얻을 수 있음
        return R.normalize();
    }
}
