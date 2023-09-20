package com.example.demo.transaction.model;

import java.time.Instant;
import java.util.*;

public class FinancialTransactionRequest {
    private final List<FinancialTransactionItem> financialTransactionItems;
    private final String financialTransactionReference;
    private final Instant financialTransactionDate;

    public FinancialTransactionRequest(String financialTransactionReference, Instant financialTransactionDate, FinancialTransactionItem... financialTransactionItems) {
        this.financialTransactionReference = financialTransactionReference;
        this.financialTransactionDate = financialTransactionDate;
        List<FinancialTransactionItem> list = new ArrayList<>();
        Collections.addAll(list, financialTransactionItems);
        this.financialTransactionItems = list;
    }

    public List<FinancialTransactionItem> getFinancialTransactionItems() {
        return financialTransactionItems;
    }

    public String getFinancialTransactionReference() {
        return financialTransactionReference;
    }

    public Instant getFinancialTransactionDate() {
        return financialTransactionDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FinancialTransactionRequest)) return false;
        FinancialTransactionRequest that = (FinancialTransactionRequest) obj;
        return Objects.equals(financialTransactionItems, that.financialTransactionItems)
                && Objects.equals(financialTransactionReference, that.financialTransactionReference)
                && Objects.equals(financialTransactionDate, that.financialTransactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(financialTransactionItems, financialTransactionReference, financialTransactionDate);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FinancialTransactionRequest.class.getSimpleName() + "[", "]")
                .add("financialTransactions=" + financialTransactionItems)
                .add("financialTransactionReference=" + financialTransactionReference)
                .add("financialTransactionDate=" + financialTransactionDate)
                .toString();
    }
}
