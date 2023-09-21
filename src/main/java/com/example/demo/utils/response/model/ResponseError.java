package com.example.demo.utils.response.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"message", "error"})
public class ResponseError implements Serializable {

    @JsonProperty("message")
    private String message;

    @JsonProperty("error")
    private String error;

    @JsonProperty("errors")
    private Map<String, Object> errors;

    public ResponseError(ResponseErrorBuilder responseErrorBuilder) {
        Assert.notNull(responseErrorBuilder, "Response error builder cannot be null");

        this.message = responseErrorBuilder.message;

        Assert.notNull(responseErrorBuilder.error, "Response error cannot be null");
        this.error = responseErrorBuilder.error;

        this.errors = responseErrorBuilder.errors;
    }

    public static ResponseErrorBuilder builder() {
        return new ResponseErrorBuilder();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.message)
                .append(this.error)
                .append(this.errors)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof final ResponseError responseError))
            return false;

        return new EqualsBuilder()
                .append(this.message, responseError.message)
                .append(this.error, responseError.error)
                .append(this.errors, responseError.errors)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("message", this.message)
                .append("error", this.error)
                .append("errors", this.errors)
                .toString();
    }

    public static class ResponseErrorBuilder {
        private String message;
        private String error;
        private Map<String, Object> errors = null;

        public ResponseErrorBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ResponseErrorBuilder error(String error) {
            this.error = error;
            return this;
        }

        public ResponseErrorBuilder errors(Map<String, Object> errors) {
            this.errors = errors;
            return this;
        }

        public ResponseError build() {
            return new ResponseError(this);
        }
    }
}
