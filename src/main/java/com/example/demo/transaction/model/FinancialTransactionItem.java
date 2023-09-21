package com.example.demo.transaction.model;

import com.example.demo.transaction.FinancialTransactionFlow;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

public class FinancialTransactionItem {
    private final String accountNumber;
    private final BigDecimal amount;
    private final FinancialTransactionFlow financialTransactionFlow;
    private final String description;

    public FinancialTransactionItem(String accountNumber, BigDecimal amount, FinancialTransactionFlow financialTransactionFlow, String description) {
        this.accountNumber = accountNumber;
        this.financialTransactionFlow = financialTransactionFlow;
        this.amount = amount;
        this.description = description;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public FinancialTransactionFlow getFinancialTransactionFlow() {
        return financialTransactionFlow;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FinancialTransactionItem.class.getSimpleName() + "[", "]")
                .add("accountNumber='" + accountNumber + "'")
                .add("amount=" + amount)
                .add("financialTransactionFlow=" + financialTransactionFlow)
                .add("description=" + description)
                .toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FinancialTransactionItem that)) return false;
        return Objects.equals(accountNumber, that.accountNumber)
                && Objects.equals(amount, that.amount)
                && Objects.equals(description, that.description)
                && financialTransactionFlow == that.financialTransactionFlow;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, amount, financialTransactionFlow, description);
    }

}
