package com.example.demo.auth.jwt.config;

import com.example.demo.utils.security.JwtUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.security.Key;
import java.security.PublicKey;

@Component
public class JwtKey {
    private final JwtConfig jwtConfig;

    private PublicKey publicKeyForVerifying;
    private Key privateKeyForSigning;

    public JwtKey(@Qualifier("jwtConfig") JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @PostConstruct
    public void init() {
        String publicKeyString = jwtConfig.secretPublicKey();
        String privateKeyString = jwtConfig.secretPrivateKey();

        publicKeyForVerifying = JwtUtils.generatePublicKeyForVerifying(publicKeyString);
        privateKeyForSigning = JwtUtils.secretPrivateKeyForSigning(privateKeyString);

        Assert.notNull(privateKeyForSigning, "PrivateKey for signing cannot be null");
        Assert.notNull(publicKeyForVerifying, "PublicKey for signing cannot be null");
    }

    public PublicKey publicKeyForVerifying() {
        return publicKeyForVerifying;
    }

    public Key privateKeyForSigning() {
        return privateKeyForSigning;
    }
}
