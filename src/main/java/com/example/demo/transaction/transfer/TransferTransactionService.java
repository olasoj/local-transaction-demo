package com.example.demo.transaction.transfer;

import com.example.demo.transaction.transfer.model.TransferFinancialTransactionRequest;

public interface TransferTransactionService {
    Object doTransferTransaction(TransferFinancialTransactionRequest transferFinancialTransactionRequest, String username);
}
