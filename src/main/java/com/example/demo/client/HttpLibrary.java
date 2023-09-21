package com.example.demo.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.http.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;

import static java.net.http.HttpClient.Redirect.NEVER;
import static java.net.http.HttpClient.Version.HTTP_2;
import static java.util.Objects.isNull;

public class HttpLibrary {

    public static final HttpLibrary httpLibrary = new HttpLibrary();
    protected static final String RESPONSE_MESSAGE = "Service is currently unavailable, please try later";
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpLibrary.class);
    private final HttpClient httpClient = HttpClient.newBuilder()
            .followRedirects(NEVER)
            .version(HTTP_2)
            .connectTimeout(Duration.ofMillis(100))
            .build();

    private HttpLibrary() {
    }

    public ResponseProperties<String> exchange(RequestProperties<String> requestProperties) {

        HttpMethod httpMethod = requestProperties.getHttpMethod();
        LOGGER.debug("Sending request");

        //Build HTTP request
        HttpRequest.BodyPublisher bodyPublisher = isNull(requestProperties.getBody())
                ? HttpRequest.BodyPublishers.noBody()
                :HttpRequest.BodyPublishers.ofString(requestProperties.getBody());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(requestProperties.getUrl())
                .header("Content-Type", "application/json")
                .timeout(Duration.ofMillis(600))
                .method(httpMethod.name(), bodyPublisher)
                .build();

        Instant start = Instant.now();
        try {
            //Build HTTP request
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            ResponseProperties.ResponsePropertiesBuilder<String> builder = ResponseProperties.builder();

            logEndTime(start);

            return builder
                    .httpHeaders(response.headers())
                    .httpStatus(HttpStatus.valueOf(response.statusCode()))
                    .body(response.body())
                    .build();
        } catch (HttpTimeoutException e) {
            logEndTime(start);
            return timeResponse();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            logEndTime(start);
            return timeResponse();
        } catch (IOException e) {
            logEndTime(start);
            throw new RuntimeException(e);
        }

    }

    private void logEndTime(Instant start) {
        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        LOGGER.debug("Request took {} millis", duration.toMillis());
    }


    private ResponseProperties<String> timeResponse() {
        ResponseProperties.ResponsePropertiesBuilder<String> builder = ResponseProperties.builder();
        return builder
                .httpHeaders(HttpHeaders.of(Collections.emptyMap(), (s, s2) -> false))
                .httpStatus(HttpStatus.GATEWAY_TIMEOUT)
                .body("")
                .build();
    }

}