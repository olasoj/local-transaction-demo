package com.example.demo.account;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class FinancialAccountService {

    private static final ReentrantStripedLock accountLock = new ReentrantStripedLock();
    private static final ConcurrentMap<String, FinancialAccount> financialAccountConcurrentMap = new ConcurrentHashMap<>();

    static {
        financialAccountConcurrentMap.put("234455990", new FinancialAccount("234455990", BigDecimal.valueOf(1000).setScale(2, RoundingMode.FLOOR), "2344559"));
        financialAccountConcurrentMap.put("134445990", new FinancialAccount("134445990", BigDecimal.valueOf(1000).setScale(2, RoundingMode.FLOOR), "1344459"));
        financialAccountConcurrentMap.put("334465990", new FinancialAccount("334465990", BigDecimal.valueOf(1000).setScale(2, RoundingMode.FLOOR), "3344659"));
        financialAccountConcurrentMap.put("231115990", new FinancialAccount("231115990", BigDecimal.valueOf(1000).setScale(2, RoundingMode.FLOOR), "2311159"));
    }

    public void creditAccount(String financialAccountNumber, BigDecimal amount) {

        ReentrantLock lock = getAccountLock(financialAccountNumber);
        lock.lock();

        try {
            //Get Financial account
            FinancialAccount financialAccount = getFinancialAccount(financialAccountNumber);
            //Credit Financial Account
            BigDecimal newBalance = financialAccount.getAccountBalance().add(amount);
            //Save new financial account
            financialAccountConcurrentMap.put(financialAccountNumber, new FinancialAccount(financialAccountNumber, newBalance, financialAccount.getCustomerId()));
        } finally {
            lock.unlock();
        }
    }

    public ReentrantLock getAccountLock(String financialAccountNumber) {
        return accountLock.getLock(financialAccountNumber);
    }

    public FinancialAccount getFinancialAccount(String financialAccountNumber) {
        return Optional.ofNullable(financialAccountConcurrentMap.get(financialAccountNumber))
                .orElseThrow(() -> new FinancialAccountException("Financial  Account " + financialAccountNumber + " not found"));
    }

    public void debitAccount(String financialAccountNumber, BigDecimal amount) {

        ReentrantLock lock = getAccountLock(financialAccountNumber);
        lock.lock();

        try {
            //Get Financial account
            FinancialAccount financialAccount = getFinancialAccount(financialAccountNumber);

            //Debit Financial Account
            BigDecimal newBalance = financialAccount.getAccountBalance().subtract(amount);
            if (BigDecimal.ZERO.compareTo(newBalance) > 0) throw new FinancialAccountException("Insufficient balance");

            //Save new financial account
            financialAccountConcurrentMap.put(financialAccountNumber, new FinancialAccount(financialAccountNumber, newBalance, financialAccount.getCustomerId()));
        } finally {
            lock.unlock();
        }
    }
}

class ReentrantStripedLock {

    private final ReentrantLock[] locks;


    public ReentrantStripedLock() {
        this(128);
    }

    public ReentrantStripedLock(int lockCount) {
        locks = new ReentrantLock[ConcurrencyUtil.findNextHighestPowerOfTwo(lockCount)];
        for (int i = 0; i < locks.length; ++i) {
            locks[i] = new ReentrantLock();
        }
    }

    public ReentrantLock getLock(Object key) {

        int lockNumber = ConcurrencyUtil.selectLock(key, locks.length);
        return locks[lockNumber];
    }

}

class ConcurrencyUtil {

    private ConcurrencyUtil() {
    }

    public static int hash(Object object) {
        int h = object.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    public static int selectLock(final Object key, int numberOfLocks) throws IllegalArgumentException {
        int number = numberOfLocks & (numberOfLocks - 1);
        if (number != 0) {
            throw new IllegalArgumentException("Lock number must be a power of two: " + numberOfLocks);
        }
        if (key == null) {
            return 0;
        } else {
            return hash(key) & (numberOfLocks - 1);
        }
    }

    public static int findNextHighestPowerOfTwo(int num) {
        if (num <= 1) {
            return 1;
        } else if (num >= 0x40000000) {
            return 0x40000000;
        }
        int highestBit = Integer.highestOneBit(num);
        return num <= highestBit ? highestBit : highestBit << 1;
    }
}

class FinancialAccountException extends RuntimeException {

    public FinancialAccountException(String message) {
        super(message);
    }
}
