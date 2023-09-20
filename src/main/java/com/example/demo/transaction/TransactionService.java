package com.example.demo.transaction;

import com.example.demo.transaction.model.FinancialTransactionRequest;

import java.util.TreeSet;

public interface TransactionService {

    Object doTransaction(FinancialTransactionRequest financialTransactionRequest);

    boolean hasAccountDoneMoreThanInXMonth(String accountNumber, int noOfMonths);

    boolean hasAccountDoneLessXTxThanInMonth(String accountNumber, int noOfMonths);

    TreeSet<FinancialTransactionRequest> getTransactionHistory(String accountNumber);
}
