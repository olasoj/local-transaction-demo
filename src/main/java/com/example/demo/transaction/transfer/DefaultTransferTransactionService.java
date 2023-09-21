package com.example.demo.transaction.transfer;

import com.example.demo.account.FinancialAccount;
import com.example.demo.account.FinancialAccountService;
import com.example.demo.transaction.FinancialTransactionManager;
import com.example.demo.transaction.model.TransactionType;
import com.example.demo.transaction.transfer.model.TransferFinancialTransactionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.example.demo.account.DefaultFinancialAccountService.POOL_ACCOUNT_NUMBER;

@Service
public class DefaultTransferTransactionService implements TransferTransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTransferTransactionService.class);

    private final FinancialTransactionManager financialTransactionManager;
    private final FinancialAccountService financialAccountService;

    public DefaultTransferTransactionService(FinancialTransactionManager financialTransactionManager, FinancialAccountService financialAccountService) {
        this.financialTransactionManager = financialTransactionManager;
        this.financialAccountService = financialAccountService;
    }

    @Override
    public Object doTransferTransaction(TransferFinancialTransactionRequest transferFinancialTransactionRequest, String username) {


        try {

            FinancialAccount financialAccountByCustomerId = financialAccountService.getFinancialAccountByCustomerId(username);

            switch (transferFinancialTransactionRequest.getTransactionType()) {
                case WITHDRAW -> financialTransactionManager.doTransaction(TransactionType.WITHDRAW, financialAccountByCustomerId.getAccountNumber()
                        , POOL_ACCOUNT_NUMBER
                        , transferFinancialTransactionRequest.getAmount());
                case DEPOSIT -> financialTransactionManager.doTransaction(TransactionType.DEPOSIT, POOL_ACCOUNT_NUMBER
                        , financialAccountByCustomerId.getAccountNumber()
                        , transferFinancialTransactionRequest.getAmount());
                default -> throw new IllegalStateException("Unexpected value: " + transferFinancialTransactionRequest.getTransactionType());
            }

            return "Success";
        } catch (Exception e) {
            return "Failed: " + e.getMessage();
        }
    }

}
