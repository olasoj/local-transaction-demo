package com.example.demo.utils.response.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.io.Serial;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"status", "timestamp", "statusCode", "data"})
//@Generated("jsonschema2pojo")
public class Response<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -4886351106258078331L;

    @JsonProperty("status")
    private ResponseStatus responseStatus;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("statusCode")
    private Integer statusCode;

    @JsonProperty("data")
    private T data;

    public Response(ResponseBuilder<T> responseBuilder) {
        Assert.notNull(responseBuilder, "Response builder cannot be null");

        Assert.notNull(responseBuilder.responseStatus, "Response status cannot be null");
        this.responseStatus = responseBuilder.responseStatus;

        Assert.notNull(responseBuilder.timestamp, "Response timestamp cannot be null");
        this.timestamp = responseBuilder.timestamp;

        Assert.notNull(responseBuilder.statusCode, "Response statusCode cannot be null");
        this.statusCode = responseBuilder.statusCode;

        Assert.notNull(responseBuilder.data, "Response data cannot be null");
        this.data = responseBuilder.data;
    }

    public static <T> ResponseBuilder<T> builder() {
        return new ResponseBuilder<>();
    }

    public ResponseStatus responseStatus() {
        return responseStatus;
    }

    public String timestamp() {
        return timestamp;
    }

    public Integer statusCode() {
        return statusCode;
    }

    public T data() {
        return data;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.responseStatus)
                .append(this.timestamp)
                .append(this.statusCode)
                .append(this.data)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof final Response<?> response))
            return false;

        return new EqualsBuilder()
                .append(this.responseStatus, response.responseStatus)
                .append(this.timestamp, response.timestamp)
                .append(this.statusCode, response.statusCode)
                .append(this.data, response.data)
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("responseStatus", this.responseStatus)
                .append("timestamp", this.timestamp)
                .append("statusCode", this.statusCode)
                .append("data", this.data)
                .toString();
    }

    public static class ResponseBuilder<B> {
        private ResponseStatus responseStatus;
        private String timestamp;
        private Integer statusCode;
        private B data;

        public ResponseBuilder<B> withResponseStatus(ResponseStatus responseStatus) {
            this.responseStatus = responseStatus;
            return this;
        }

        public ResponseBuilder<B> withTimestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ResponseBuilder<B> withStatusCode(Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }


        public ResponseBuilder<B> withData(B data) {
            this.data = data;
            return this;
        }

        public Response<B> build() {
            return new Response<>(this);
        }
    }
}