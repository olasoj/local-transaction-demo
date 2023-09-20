package com.example.demo.transaction.transfer.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

public class TransferFinancialTransactionRequest {

    private final String sourceAccount;
    private final String destinationAccount;
    private final BigDecimal amount;

    public TransferFinancialTransactionRequest(String sourceAccount, String destinationAccount, BigDecimal amount) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public String getNetworkProvider() {
        return destinationAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TransferFinancialTransactionRequest that)) return false;
        return Objects.equals(sourceAccount, that.sourceAccount) && Objects.equals(destinationAccount, that.destinationAccount) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceAccount, destinationAccount, amount);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TransferFinancialTransactionRequest.class.getSimpleName() + "[", "]")
                .add("sourceAccount='" + sourceAccount + "'")
                .add("destinationAccount='" + destinationAccount + "'")
                .add("amount=" + amount)
                .toString();
    }
}
