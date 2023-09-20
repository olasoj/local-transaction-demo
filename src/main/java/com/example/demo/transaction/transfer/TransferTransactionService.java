package com.example.demo.transaction.transfer;

import com.example.demo.account.FinancialAccountService;
import com.example.demo.transaction.FinancialTransactionManager;
import com.example.demo.transaction.TransactionService;
import com.example.demo.transaction.model.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TransferTransactionService implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferTransactionService.class);

    private final FinancialTransactionManager financialTransactionManager;
    private final FinancialAccountService financialAccountService;
    private final TransactionService transactionService;

    public TransferTransactionService(FinancialTransactionManager financialTransactionManager, FinancialAccountService financialAccountService, TransactionService transactionService) {
        this.financialTransactionManager = financialTransactionManager;
        this.financialAccountService = financialAccountService;
        this.transactionService = transactionService;
    }

    public Object doAirtimeTransaction() {

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


        List<Callable<String>> runnableList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Callable<String> runnable = () -> {
                financialTransactionManager.doTransaction(TransactionType.TRANSFER, "234455990"
                        , "334465990"
                        , BigDecimal.TEN);
                        return "";
            };
            runnableList.add(runnable);
        }

        try {
            executorService.invokeAll(runnableList);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        LOGGER.info(financialAccountService.getFinancialAccount("234455990").toString());
        LOGGER.info(financialAccountService.getFinancialAccount("334465990").toString());
        LOGGER.info(transactionService.getTransactionHistory("334465990").toString());
        return null;

    }

    @Override
    public void run(String... args) throws Exception {
        doAirtimeTransaction();
    }
}
