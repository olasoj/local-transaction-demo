package com.example.demo.account;

import com.example.demo.utils.concurrency.ReentrantStripedLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class DefaultFinancialAccountService implements FinancialAccountService {

    public static final String DISCOUNT_ACCOUNT_NUMBER = "231115290";
    public static final String POOL_ACCOUNT_NUMBER = "231115291";
    public static final ConcurrentMap<String, FinancialAccount> financialAccountConcurrentMap = new ConcurrentHashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultFinancialAccountService.class);
    private static final ReentrantStripedLock accountLock = new ReentrantStripedLock();

    static {
        financialAccountConcurrentMap.put("234455990", new FinancialAccount("234455990", BigDecimal.valueOf(100_000_000).setScale(2, RoundingMode.FLOOR), "2344559", FinancialAccountType.SAVING));
        financialAccountConcurrentMap.put("134445990", new FinancialAccount("134445990", BigDecimal.valueOf(100_000_000).setScale(2, RoundingMode.FLOOR), "1344459", FinancialAccountType.SAVING));
        financialAccountConcurrentMap.put("334465990", new FinancialAccount("334465990", BigDecimal.valueOf(1000).setScale(2, RoundingMode.FLOOR), "3344659", FinancialAccountType.SAVING));
        financialAccountConcurrentMap.put("231115990", new FinancialAccount("231115990", BigDecimal.valueOf(100_000_000).setScale(2, RoundingMode.FLOOR), "2311159", FinancialAccountType.SAVING));
        financialAccountConcurrentMap.put(DISCOUNT_ACCOUNT_NUMBER, new FinancialAccount(DISCOUNT_ACCOUNT_NUMBER, BigDecimal.valueOf(100_000).setScale(2, RoundingMode.FLOOR), "2311152", FinancialAccountType.SAVING)); //Discount Account
        financialAccountConcurrentMap.put(POOL_ACCOUNT_NUMBER, new FinancialAccount(POOL_ACCOUNT_NUMBER, BigDecimal.valueOf(1000).setScale(2, RoundingMode.FLOOR), "2311152", FinancialAccountType.SAVING)); //PoolAccount
    }

    @Override
    public void creditAccount(String financialAccountNumber, BigDecimal amount) {

        ReentrantLock lock = getAccountLock(financialAccountNumber);
        lock.lock();

        try {
            //Get Financial account
            FinancialAccount financialAccount = getFinancialAccount(financialAccountNumber);
            //Credit Financial Account
            BigDecimal newBalance = financialAccount.getAccountBalance().add(amount);
            //Save new financial account
            financialAccountConcurrentMap.put(financialAccountNumber, new FinancialAccount(financialAccountNumber, newBalance, financialAccount.getCustomerId(), financialAccount.getFinancialAccountType()));
            if (LOGGER.isTraceEnabled()) LOGGER.trace("Successfully credit account {} with {} amount ", financialAccountNumber, amount);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public ReentrantLock getAccountLock(String financialAccountNumber) {
        return accountLock.getLock(financialAccountNumber);
    }

    @Override
    public Set<ReentrantLock> getAccountLocks(String... financialAccountNumbers) {

        return Arrays.stream(financialAccountNumbers)
                .sorted(Comparator.naturalOrder())
                .map(accountLock::getLock)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public FinancialAccount getFinancialAccount(String financialAccountNumber) {
        return Optional.ofNullable(financialAccountConcurrentMap.get(financialAccountNumber))
                .orElseThrow(() -> new FinancialAccountServiceException("Financial  Account " + financialAccountNumber + " not found"));
    }

    @Override
    public FinancialAccount getFinancialAccountByCustomerId(String customerID) {
        return financialAccountConcurrentMap.values()
                .stream()
                .filter(financialAccount -> financialAccount.getCustomerId().equals(customerID))
                .findFirst()
                .orElseThrow(() -> new FinancialAccountServiceException("Financial  Account " + customerID + " not found"));
    }

    @Override
    public void debitAccount(String financialAccountNumber, BigDecimal amount) {

        ReentrantLock lock = getAccountLock(financialAccountNumber);
        lock.lock();

        try {
            //Get Financial account
            FinancialAccount financialAccount = getFinancialAccount(financialAccountNumber);

            //Debit Financial Account
            BigDecimal newBalance = financialAccount.getAccountBalance().subtract(amount);
            if (BigDecimal.ZERO.compareTo(newBalance) > 0) throw new FinancialAccountServiceException("Insufficient balance");

            //Save new financial account
            financialAccountConcurrentMap.put(financialAccountNumber, new FinancialAccount(financialAccountNumber, newBalance, financialAccount.getCustomerId(), financialAccount.getFinancialAccountType()));
        } finally {
            lock.unlock();
        }
    }
}

