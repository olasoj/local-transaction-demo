package com.example.demo.transaction.airtime;

import com.example.demo.transaction.FinancialTransactionManager;
import com.example.demo.transaction.model.TransactionType;
import org.springframework.stereotype.Service;

@Service
public class AirtimeTransactionService {

    private final FinancialTransactionManager financialTransactionManager;

    public AirtimeTransactionService(FinancialTransactionManager financialTransactionManager) {
        this.financialTransactionManager = financialTransactionManager;
    }

    public Object doAirtimeTransaction() {


        financialTransactionManager.doTransaction(TransactionType.AIRTIME, null
                , null
                , null);

        return null;

    }
}
