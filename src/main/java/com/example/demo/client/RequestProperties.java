package com.example.demo.client;

import org.springframework.http.HttpMethod;

import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpHeaders;
import java.util.Objects;
import java.util.StringJoiner;

public class RequestProperties<T extends Serializable> {
    private final URI url;
    private final HttpMethod httpMethod;
    private final HttpHeaders httpHeaders;
    private final T body;

    public RequestProperties(RequestPropertiesBuilder<T> requestPropertiesBuilder) {
        if (requestPropertiesBuilder == null) throw new IllegalStateException("RequestPropertiesBuilder cannot be null");

        this.url = requestPropertiesBuilder.url;
        this.httpMethod = requestPropertiesBuilder.httpMethod;
        this.httpHeaders = requestPropertiesBuilder.httpHeaders;
        this.body = requestPropertiesBuilder.body;
    }

    public static <T extends Serializable> RequestPropertiesBuilder<T> builder() {
        return new RequestPropertiesBuilder<>();
    }

    public URI getUrl() {
        return url;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
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
        if (!(obj instanceof RequestProperties<?> that)) return false;
        return
                Objects.equals(url, that.url) &&
                        httpMethod == that.httpMethod &&
                        Objects.equals(httpHeaders, that.httpHeaders) &&
                        Objects.equals(body, that.body);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RequestProperties.class.getSimpleName() + "[", "]")
                .add("url=" + url)
                .add("httpMethod=" + httpMethod)
                .add("httpHeaders=" + httpHeaders)
                .add("body=" + body)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, httpMethod, httpHeaders, body);
    }

    public static class RequestPropertiesBuilder<B extends Serializable> {
        private URI url;
        private HttpMethod httpMethod;
        private HttpHeaders httpHeaders;
        private B body;

        public RequestPropertiesBuilder<B> uri(URI url) {
            this.url = url;
            return this;
        }

        public RequestPropertiesBuilder<B> httpHeaders(HttpHeaders httpHeaders) {
            this.httpHeaders = httpHeaders;
            return this;
        }

        public RequestPropertiesBuilder<B> httpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public RequestPropertiesBuilder<B> body(B body) {
            this.body = body;
            return this;
        }

        public RequestProperties<B> build() {
            return new RequestProperties<>(this);
        }
    }
}