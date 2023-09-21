package com.example.demo.transaction.util;

import com.example.demo.utils.security.Base64url;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class FinancialTransactionReferenceGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();

    private FinancialTransactionReferenceGenerator() {
    }

    private static String randomId() {
        var bytes = new byte[20];
        secureRandom.nextBytes(bytes);
        return Base64url.encode(bytes);
    }

    public static String sessionId() {
        var tokenId = randomId();
        return hash(tokenId);
    }

    private static String hash(String tokenId) {
        assert tokenId != null;
        var hash = sha256(tokenId);
        return Base64url.encode(hash);
    }

    static byte[] sha256(String tokenId) {
        try {
            var sha256 = MessageDigest.getInstance("SHA-256");
            return sha256.digest(tokenId.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

}
