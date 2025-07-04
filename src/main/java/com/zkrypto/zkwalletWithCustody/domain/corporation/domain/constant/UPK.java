package com.zkrypto.zkwalletWithCustody.domain.corporation.domain.constant;

import com.zkrypto.zkwalletWithCustody.global.crypto.constant.AffinePoint;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public class UPK {
    private BigInteger ena;
    private BigInteger pkOwn;
    private AffinePoint pkEnc;

    public UPK(BigInteger ena, BigInteger pkOwn, AffinePoint pkEnc) {
        this.ena = ena;
        this.pkOwn = pkOwn;
        this.pkEnc = pkEnc;
    }
}