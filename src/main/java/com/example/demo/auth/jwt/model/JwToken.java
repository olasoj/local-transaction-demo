package com.example.demo.auth.jwt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.Duration;
import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JwToken {

    private final Duration expiration;
    private final Instant validity;
    private final String subject;
    private final Instant issuedAt;

    public JwToken(JwTokenBuilder jwTokenBuilder) {
        this.expiration = jwTokenBuilder.expiration;
        this.validity = jwTokenBuilder.validity;
        this.subject = jwTokenBuilder.subject;
        this.issuedAt = jwTokenBuilder.issuedAt;
    }

    public static JwTokenBuilder builder() {
        return new JwTokenBuilder();
    }

    public Duration expiration() {
        return expiration;
    }

    public Instant validity() {
        return validity;
    }

    public String subject() {
        return subject;
    }

    public Instant issuedAt() {
        return issuedAt;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.expiration)
                .append(this.validity)
                .append(this.subject)
                .append(this.issuedAt)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof final JwToken jwToken))
            return false;

        return new EqualsBuilder()
                .append(this.expiration, jwToken.expiration)
                .append(this.validity, jwToken.validity)
                .append(this.subject, jwToken.subject)
                .append(this.issuedAt, jwToken.issuedAt)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("expiration", this.expiration)
                .append("validity", this.validity)
                .append("subject", this.subject)
                .append("issuedAt", this.issuedAt)
                .toString();
    }

    public static class JwTokenBuilder {
        private Duration expiration;
        private Instant validity;
        private Instant issuedAt;
        private String subject;

        public JwTokenBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public JwTokenBuilder issuedAt(Instant issuedAt) {
            this.issuedAt = issuedAt;
            return this;
        }

        public JwTokenBuilder expiration(Duration expiration) {
            this.expiration = expiration;
            return this;
        }

        public JwTokenBuilder validity(Instant validity) {
            this.validity = validity;
            return this;
        }

        public JwToken build() {
            return new JwToken(this);
        }
    }

}
