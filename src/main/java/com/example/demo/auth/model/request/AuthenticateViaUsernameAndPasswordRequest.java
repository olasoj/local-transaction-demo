package com.example.demo.auth.model.request;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class AuthenticateViaUsernameAndPasswordRequest {

    private final String username;
    private final String password;

    public AuthenticateViaUsernameAndPasswordRequest(String username, String password) {

        Assert.notNull(username, "Username cannot be null");
        this.username = username;

        Assert.notNull(password, "Password cannot be null");
        this.password = password;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.username)
                .append(this.password)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof final AuthenticateViaUsernameAndPasswordRequest otherAuthenticateViaUsernameAndPasswordRequest))
            return false;

        return new EqualsBuilder()
                .append(this.username, otherAuthenticateViaUsernameAndPasswordRequest.username)
                .append(this.password, otherAuthenticateViaUsernameAndPasswordRequest.password)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("username", this.username)
                .append("password", this.password)
                .toString();
    }
}
