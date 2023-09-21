package com.example.demo.account;

import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public interface FinancialAccountService {
    void creditAccount(String financialAccountNumber, BigDecimal amount);

    ReentrantLock getAccountLock(String financialAccountNumber);

    Set<ReentrantLock> getAccountLocks(String... financialAccountNumbers);

    FinancialAccount getFinancialAccount(String financialAccountNumber);

    FinancialAccount getFinancialAccountByCustomerId(String financialAccountNumber);

    void debitAccount(String financialAccountNumber, BigDecimal amount);
}
