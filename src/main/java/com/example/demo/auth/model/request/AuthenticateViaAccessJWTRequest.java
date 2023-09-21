package com.example.demo.auth.model.request;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public record AuthenticateViaAccessJWTRequest(String token) {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (!(obj instanceof AuthenticateViaAccessJWTRequest otherAuthenticateViaAccessJWTRequest)) return false;

        return new EqualsBuilder()
                .append(token, otherAuthenticateViaAccessJWTRequest.token)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(token)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("token", token)
                .toString();
    }
}
