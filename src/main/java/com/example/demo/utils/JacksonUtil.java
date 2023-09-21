package com.example.demo.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import static com.example.demo.config.jackson.TransactionSenderApplicationInstantJacksonConfig.JACKSON_OBJECT_MAPPER;


public class JacksonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonUtil.class);

    private JacksonUtil() {
    }

    public static <T> String toString(T reqResBody) {
        try {
            return JACKSON_OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(reqResBody);
        } catch (JsonProcessingException jsonProcessingException) {
            LOGGER.error(jsonProcessingException.getMessage(), jsonProcessingException);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to log request/response body");
        }
    }

    public static <T> Map<String, Object> toMap(T reqResBody) {
        try {
            return JACKSON_OBJECT_MAPPER.convertValue(reqResBody, new TypeReference<>() {
            });
        } catch (IllegalArgumentException illegalArgumentException) {
            LOGGER.error(illegalArgumentException.getMessage(), illegalArgumentException);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to log request/response body");
        }
    }

    public static <T> void logReqRes(T reqResBody) {

//        if (LOGGER.isDebugEnabled()) {
            String prettyString = toString(reqResBody);
            LOGGER.info(prettyString);
//        }
    }
}
