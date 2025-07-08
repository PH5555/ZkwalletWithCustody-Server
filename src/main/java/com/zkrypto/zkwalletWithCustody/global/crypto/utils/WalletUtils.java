package com.zkrypto.zkwalletWithCustody.global.crypto.utils;

import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.constant.UPK;
import com.zkrypto.zkwalletWithCustody.domain.corporation.domain.entity.Corporation;
import com.zkrypto.zkwalletWithCustody.global.crypto.constant.AffinePoint;
import com.zkrypto.zkwalletWithCustody.global.crypto.constant.MiMC7;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class WalletUtils {
    public static BigInteger generateWallet(Corporation corporation) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        ECKeyPair keyPair = Keys.createEcKeyPair();
        BigInteger privateKey = keyPair.getPrivateKey();
        String address = "0x" + Keys.getAddress(keyPair);

        corporation.setAddress(address);
        return privateKey;
    }

    public static UPK recoverFromUserSk(BigInteger sk) {
        MiMC7 mimc = new MiMC7();
        BigInteger pkOwn = mimc.hash(sk);
        AffinePoint pkEnc = EcUtils.basePointMul(sk);
        BigInteger ena = mimc.hash(pkOwn, pkEnc.getX(), pkEnc.getY());
        return new UPK(ena, pkOwn, pkEnc);
    }

    public static BigInteger deriveUskFromPrivateKey(BigInteger privateKey) {
        MiMC7 mimc = new MiMC7();
        return mimc.hash(privateKey);
    }
}
