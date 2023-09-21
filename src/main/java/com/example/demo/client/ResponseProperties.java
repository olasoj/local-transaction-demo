package com.example.demo.client;

import org.springframework.http.HttpStatus;

import java.net.http.HttpHeaders;
import java.util.Objects;
import java.util.StringJoiner;

public class ResponseProperties<T> {
    private HttpStatus httpStatus;
    private HttpHeaders httpHeaders;
    private T body;

    public ResponseProperties(ResponsePropertiesBuilder<T> responsePropertiesBuilder) {
        if (responsePropertiesBuilder == null) throw new IllegalStateException("ResponsePropertiesBuilder cannot be null");

        this.httpStatus = responsePropertiesBuilder.httpStatus;
        this.httpHeaders = responsePropertiesBuilder.httpHeaders;
        this.body = responsePropertiesBuilder.body;
    }

    public ResponseProperties() {
    }

    public static <T> ResponsePropertiesBuilder<T> builder() {
        return new ResponsePropertiesBuilder<>();
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public T getBody() {
        return body;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ResponseProperties<?> that)) return false;
        return httpStatus == that.httpStatus && Objects.equals(httpHeaders, that.httpHeaders) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpStatus, httpHeaders, body);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ResponseProperties.class.getSimpleName() + "[", "]")
                .add("httpStatus=" + httpStatus)
                .add("httpHeaders=" + httpHeaders)
                .add("body=" + body)
                .toString();
    }

    public static class ResponsePropertiesBuilder<B> {
        private HttpStatus httpStatus;
        private HttpHeaders httpHeaders;
        private B body;

        public ResponsePropertiesBuilder<B> httpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public ResponsePropertiesBuilder<B> httpHeaders(HttpHeaders httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }

        public ResponsePropertiesBuilder<B> body(B body) {
            this.body = body;
            return this;
        }

        public ResponseProperties<B> build() {
            return new ResponseProperties<>(this);
        }
    }
}
