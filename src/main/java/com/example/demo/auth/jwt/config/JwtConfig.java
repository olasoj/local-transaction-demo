package com.example.demo.auth.jwt.config;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {

    private final int expireLength;
    private final String secretPublicKey;
    private final String secretPrivateKey;

    public JwtConfig(
            @Value("${app.jwt.secret-public-key}") String secretPublicKey,
            @Value("${app.jwt.secret-private-key}") String secretPrivateKey,
            @Value("${app.jwt.expire-length}") int expireLength) {

        this.secretPublicKey = secretPublicKey;
        this.secretPrivateKey = secretPrivateKey;
        this.expireLength = expireLength;
    }

    public String secretPublicKey() {
        return secretPublicKey;
    }

    public String secretPrivateKey() {
        return secretPrivateKey;
    }

    public int expireLength() {
        return expireLength;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof JwtConfig otherJwtConfig)) return false;

        return new EqualsBuilder()
                .append(secretPublicKey, otherJwtConfig.secretPublicKey)
                .append(secretPrivateKey, otherJwtConfig.secretPrivateKey)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(secretPublicKey)
                .append(secretPrivateKey)
                .toHashCode();
    }
}
