package com.example.demo.transaction;

import com.example.demo.account.FinancialAccount;
import com.example.demo.account.FinancialAccountService;
import com.example.demo.customer.Customer;
import com.example.demo.customer.CustomerService;
import com.example.demo.transaction.model.FinancialTransactionItem;
import com.example.demo.transaction.model.FinancialTransactionRequest;
import com.example.demo.transaction.model.TransactionType;
import com.example.demo.transaction.util.FinancialTransactionReferenceGenerator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class FinancialTransactionManager {

    private final CustomerService customerService;
    private final FinancialAccountService financialAccountService;
    private final FinancialTransactionService financialTransactionService;

    public FinancialTransactionManager(CustomerService customerService, FinancialAccountService financialAccountService, FinancialTransactionService financialTransactionService) {
        this.customerService = customerService;
        this.financialAccountService = financialAccountService;
        this.financialTransactionService = financialTransactionService;
    }

    public void doTransaction(
            TransactionType transactionType
            , String sourceFinancialAccount
            , String destinationFinancialAccountNumber
            , BigDecimal amount
    ) {

        Set<ReentrantLock> accountLocks = financialAccountService.getAccountLocks(sourceFinancialAccount, destinationFinancialAccountNumber);

        accountLocks
                .forEach(ReentrantLock::lock);

        try {

            FinancialAccount financialAccount = financialAccountService.getFinancialAccount(sourceFinancialAccount);

            FinancialAccount destinationFinancialAccount = financialAccountService.getFinancialAccount(destinationFinancialAccountNumber);
            if (amount.compareTo(financialAccount.getAccountBalance()) > 0) throw new FinancialTransactionServiceException("Insufficient balance");
            if (destinationFinancialAccount.equals(financialAccount)) throw new FinancialTransactionServiceException("Cant transfer to the same account");

            Customer customer = customerService.getCustomer(financialAccount.getCustomerId());

            Set<FinancialTransactionItem> transactions = new LinkedHashSet<>();
            transactions.add(new FinancialTransactionItem(sourceFinancialAccount, amount, FinancialTransactionFlow.DEBIT, "Source account is debited"));
            transactions.add(new FinancialTransactionItem(destinationFinancialAccountNumber, amount, FinancialTransactionFlow.CREDIT, "Destination account is credited"));

            FinancialTransactionRequest financialTransactionRequest = new FinancialTransactionRequest(
                    transactionType,
                    FinancialTransactionReferenceGenerator.sessionId(),
                    Instant.now(),
                    transactions
            );

            financialTransactionService.doTransaction(financialTransactionRequest);
            //Send alert
        } finally {
            accountLocks
                    .forEach(ReentrantLock::unlock);
        }
    }
}

