package com.example.demo.auth.model.response;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class AuthenticateViaUsernameAndPasswordResult {

    private final String sessionId;

    public AuthenticateViaUsernameAndPasswordResult(String sessionId) {
        Assert.notNull(sessionId, "SessionId cannot be null");

        this.sessionId = sessionId;
    }

    public String sessionId() {
        return sessionId;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.sessionId)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof final AuthenticateViaUsernameAndPasswordResult otherAuthenticateViaUsernameAndPasswordResult))
            return false;

        return new EqualsBuilder()
                .append(this.sessionId, otherAuthenticateViaUsernameAndPasswordResult.sessionId)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("sessionId", this.sessionId)
                .toString();
    }
}
