package com.example.demo.account;

public class FinancialAccountServiceException extends RuntimeException {

    public FinancialAccountServiceException(String message) {
        super(message);
    }

    public FinancialAccountServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
