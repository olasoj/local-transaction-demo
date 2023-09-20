package com.example.demo.transaction.airtime.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

public class AirtimeFinancialTransactionRequest {

    private final String sourceAccount;
    private final String networkProvider;
    private final BigDecimal amount;
    private final String phoneNumber;

    public AirtimeFinancialTransactionRequest(String sourceAccount, String networkProvider, BigDecimal amount, String phoneNumber) {
        this.sourceAccount = sourceAccount;
        this.networkProvider = networkProvider;
        this.amount = amount;
        this.phoneNumber = phoneNumber;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public String getNetworkProvider() {
        return networkProvider;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AirtimeFinancialTransactionRequest that)) return false;
        return Objects.equals(sourceAccount, that.sourceAccount) && Objects.equals(networkProvider, that.networkProvider) && Objects.equals(amount, that.amount) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceAccount, networkProvider, amount, phoneNumber);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AirtimeFinancialTransactionRequest.class.getSimpleName() + "[", "]")
                .add("sourceAccount='" + sourceAccount + "'")
                .add("networkProvider='" + networkProvider + "'")
                .add("amount=" + amount)
                .add("phoneNumber='" + phoneNumber + "'")
                .toString();
    }
}
