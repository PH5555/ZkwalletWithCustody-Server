package com.zkrypto.zkwalletWithCustody.global.crypto.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SaltUtils {
    public static String generateSalt() {
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstanceStrong();
            byte[] saltBytes = new byte[16];
            sr.nextBytes(saltBytes);
            return Base64.getEncoder().encodeToString(saltBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
