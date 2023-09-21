package com.example.demo.auth.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRestInboundResponse implements Serializable {
    @JsonProperty("sessionId")
    private String sessionId;

    public AuthRestInboundResponse(String sessionId) {
        this.sessionId = sessionId;
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

        if (!(obj instanceof final AuthRestInboundResponse otherAuthRestInboundResponse))
            return false;

        return new EqualsBuilder()
                .append(this.sessionId, otherAuthRestInboundResponse.sessionId)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("sessionId", this.sessionId)
                .toString();
    }

}
