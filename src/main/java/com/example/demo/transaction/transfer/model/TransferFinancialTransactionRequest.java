package com.example.demo.transaction.transfer.model;

import com.example.demo.transaction.model.TransactionType;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

public class TransferFinancialTransactionRequest {

    private final String sourceAccount;

    @NotBlank
    private final String narration;

    @NonNull
    private final BigDecimal amount;

    @NonNull
    private final TransactionType transactionType;

    public TransferFinancialTransactionRequest(String sourceAccount, String narration, BigDecimal amount, TransactionType transactionType) {
        this.sourceAccount = sourceAccount;
        this.narration = narration;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public String getNarration() {
        return narration;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof TransferFinancialTransactionRequest that)) return false;
        return Objects.equals(sourceAccount, that.sourceAccount) && Objects.equals(narration, that.narration) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceAccount, narration, amount);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TransferFinancialTransactionRequest.class.getSimpleName() + "[", "]")
                .add("sourceAccount='" + sourceAccount + "'")
                .add("destinationAccount='" + narration + "'")
                .add("amount=" + amount)
                .toString();
    }
}
