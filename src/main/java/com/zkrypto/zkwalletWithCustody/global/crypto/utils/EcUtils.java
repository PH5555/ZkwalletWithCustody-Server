package com.zkrypto.zkwalletWithCustody.global.crypto.utils;

import com.zkrypto.zkwalletWithCustody.global.crypto.constant.AffinePoint;
import com.zkrypto.zkwalletWithCustody.global.crypto.constant.TwistedEdwardsCurve;
import org.springframework.stereotype.Component;
import java.math.BigInteger;

@Component
public class EcUtils {
    public static AffinePoint basePointMul(BigInteger exp) {
        TwistedEdwardsCurve curve = new TwistedEdwardsCurve();
        AffinePoint bp = new AffinePoint(curve.getG().getX(), curve.getG().getY());
        if(!curve.checkPointOnCurve(bp)) {
            throw new IllegalArgumentException("시작점이 커브 위에 점이 있지 않습니다.");
        }

        AffinePoint result = curve.computeScalarMul(bp, exp);
        if(!curve.checkPointOnCurve(bp)) {
            throw new IllegalArgumentException("새로 생성한 점이 커브 위에 점이 있지 않습니다.");
        }
        return result;
    }
}
