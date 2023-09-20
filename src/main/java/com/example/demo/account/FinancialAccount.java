package com.example.demo.account;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

public class FinancialAccount {

    private final String accountNumber;
    private final BigDecimal accountBalance;
    private final  String customerId;

    public FinancialAccount(String accountNumber, BigDecimal accountBalance, String customerId) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.customerId = customerId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public String getCustomerId() {
        return customerId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof FinancialAccount that)) return false;
        return Objects.equals(accountNumber, that.accountNumber) && Objects.equals(accountBalance, that.accountBalance) && Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, accountBalance, customerId);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", FinancialAccount.class.getSimpleName() + "[", "]")
                .add("accountNumber='" + accountNumber + "'")
                .add("accountBalance='" + accountBalance + "'")
                .add("customerId='" + customerId + "'")
                .toString();
    }
}
