package com.example.demo.auth.jwt.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateJwTokenResult {

    private final String jwtToken;

    public CreateJwTokenResult(CreateJwTokenResultBuilder createJwTokenResultBuilder) {
        this.jwtToken = createJwTokenResultBuilder.jwtToken;
    }

    public static CreateJwTokenResultBuilder builder() {
        return new CreateJwTokenResultBuilder();
    }

    public String jwtToken() {
        return jwtToken;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.jwtToken)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof final CreateJwTokenResult jwToken))
            return false;

        return new EqualsBuilder()
                .append(this.jwtToken, jwToken.jwtToken)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("jwtToken", this.jwtToken)
                .toString();
    }

    public static class CreateJwTokenResultBuilder {
        private String jwtToken;

        public CreateJwTokenResultBuilder jwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
            return this;
        }

        public CreateJwTokenResult build() {
            return new CreateJwTokenResult(this);
        }
    }

}
