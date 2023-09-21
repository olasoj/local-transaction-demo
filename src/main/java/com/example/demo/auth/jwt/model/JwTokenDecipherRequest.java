package com.example.demo.auth.jwt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JwTokenDecipherRequest implements Serializable {
    private final String token;

    public JwTokenDecipherRequest(String token) {
        this.token = token;
    }

    public JwTokenDecipherRequest(JwTokenVerificationBuilder jwTokenVerificationBuilder) {
        this.token = jwTokenVerificationBuilder.token;
    }

    public static JwTokenVerificationBuilder builder() {
        return new JwTokenVerificationBuilder();
    }

    public String token() {
        return token;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.token)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof final JwTokenDecipherRequest jwTokenDecipherRequest))
            return false;

        return new EqualsBuilder()
                .append(this.token, jwTokenDecipherRequest.token)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("token", this.token)
                .toString();
    }

    public static class JwTokenVerificationBuilder {
        private String token;

        public JwTokenVerificationBuilder token(String accessToken) {
            this.token = accessToken;
            return this;
        }

        public JwTokenDecipherRequest build() {
            return new JwTokenDecipherRequest(this);
        }
    }

}
