package com.example.demo.utils.response.transformer;

import com.example.demo.utils.response.model.ResponseError;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class ResponseErrorAssembler {

    private ResponseErrorAssembler() {
    }

    public static ResponseError toResponseError(Map<String, Object> errors, String message, HttpStatus status) {
        return ResponseError.builder()
                .error(status.getReasonPhrase())
                .message(message)
                .errors(errors)
                .build();
    }

    public static ResponseError toResponseError(String message, HttpStatus status) {
        return ResponseError.builder()
                .error(status.getReasonPhrase())
                .message(message)
                .build();

    }
}
