package com.example.demo.auth.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRestInboundRequest implements Serializable {
    @NotBlank(message = "Enter username")
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    @NotNull(message = "Enter Password")
    private String password;

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

        if (!(obj instanceof final AuthRestInboundRequest otherAuthRestInboundRequest))
            return false;

        return new EqualsBuilder()
                .append(this.username, otherAuthRestInboundRequest.username)
                .append(this.password, otherAuthRestInboundRequest.password)
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
