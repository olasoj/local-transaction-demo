package com.example.demo.utils.security;

import io.jsonwebtoken.io.Decoders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class JwtUtils {

    private JwtUtils() {
    }

    public static PublicKey generatePublicKeyForVerifying(String keyString) {
        try {
            keyString = keyString.replaceAll("\\s+", "");

            return publicKeyGenerator(KeyFactory.getInstance("RSA"), publicKeySpec(keyString));
        } catch (NoSuchAlgorithmException e) {
            //TODO: Change this
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "e");
        }
    }

    private static EncodedKeySpec publicKeySpec(String data) {
        return new X509EncodedKeySpec(Decoders.BASE64.decode(data));
    }

    private static PublicKey publicKeyGenerator(KeyFactory keyFactory, EncodedKeySpec encodedKeySpec) {
        try {
            return keyFactory.generatePublic(encodedKeySpec);
        } catch (InvalidKeySpecException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "e");
        }
    }

    public static Key secretPrivateKeyForSigning(String keyString) {

        try {
            keyString = keyString.replaceAll("\\s+", "");

            return privateKeyGenerator(KeyFactory.getInstance("RSA"), privateKeySpec(keyString));
        } catch (NoSuchAlgorithmException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private static EncodedKeySpec privateKeySpec(String data) {
        return new PKCS8EncodedKeySpec(Decoders.BASE64.decode(data));
    }

    private static PrivateKey privateKeyGenerator(KeyFactory keyFactory, EncodedKeySpec encodedKeySpec) {
        try {
            return keyFactory.generatePrivate(encodedKeySpec);
        } catch (InvalidKeySpecException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
