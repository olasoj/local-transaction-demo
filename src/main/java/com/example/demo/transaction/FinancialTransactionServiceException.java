package com.example.demo.transaction;

public class FinancialTransactionServiceException extends RuntimeException {

    public FinancialTransactionServiceException(String message) {
        super(message);
    }

    public FinancialTransactionServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
