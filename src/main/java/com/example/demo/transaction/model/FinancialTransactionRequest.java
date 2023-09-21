package com.example.demo.transaction.model;

import java.time.Instant;
import java.util.*;

public class FinancialTransactionRequest {
    private final Set<FinancialTransactionItem> financialTransactionItems;
    private final TransactionType transactionType;
    private final String financialTransactionReference;
    private final Instant financialTransactionDate;

    public FinancialTransactionRequest(TransactionType transactionType, String financialTransactionReference, Instant financialTransactionDate, Set<FinancialTransactionItem> financialTransactionItems) {

        this.transactionType = transactionType;
        this.financialTransactionReference = financialTransactionReference;
        this.financialTransactionDate = financialTransactionDate;
        this.financialTransactionItems = financialTransactionItems;
    }

    public Set<FinancialTransactionItem> getFinancialTransactionItems() {
        return financialTransactionItems;
    }

    public String getFinancialTransactionReference() {
        return financialTransactionReference;
    }

    public Instant getFinancialTransactionDate() {
        return financialTransactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FinancialTransactionRequest.class.getSimpleName() + "[", "]")
                .add("financialTransactionItems=" + financialTransactionItems)
                .add("transactionType=" + transactionType)
                .add("financialTransactionReference='" + financialTransactionReference + "'")
                .add("financialTransactionDate=" + financialTransactionDate)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FinancialTransactionRequest)) return false;
        FinancialTransactionRequest that = (FinancialTransactionRequest) obj;
        return Objects.equals(financialTransactionItems, that.financialTransactionItems) && transactionType == that.transactionType && Objects.equals(financialTransactionReference, that.financialTransactionReference) && Objects.equals(financialTransactionDate, that.financialTransactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(financialTransactionItems, transactionType, financialTransactionReference, financialTransactionDate);
    }

}
