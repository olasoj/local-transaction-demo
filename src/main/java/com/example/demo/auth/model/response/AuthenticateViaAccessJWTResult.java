package com.example.demo.auth.model.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

public class AuthenticateViaAccessJWTResult {

    private final Authentication authentication;

    public AuthenticateViaAccessJWTResult(Authentication authentication) {
        Assert.notNull(authentication, "Authentication cannot be null");
        this.authentication = authentication;
    }

    public Authentication authentication() {
        return authentication;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof AuthenticateViaAccessJWTResult otherAuthenticateViaAccessJWTResult)) return false;

        return new EqualsBuilder()
                .append(authentication, otherAuthenticateViaAccessJWTResult.authentication)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(authentication)
                .toHashCode();
    }
}
