package com.example.demo.transaction.transfer.controller;

import com.example.demo.customer.CustomerPrincipal;
import com.example.demo.transaction.transfer.TransferTransactionService;
import com.example.demo.transaction.transfer.model.TransferFinancialTransactionRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/transfer")
public class TransferTransactionController {

    private final TransferTransactionService transferTransactionService;

    public TransferTransactionController(@Qualifier("defaultTransferTransactionService") TransferTransactionService transactionService) {
        this.transferTransactionService = transactionService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/")
    public ResponseEntity<Object> receiveAirtimeTransactionRequest(@Valid @RequestBody TransferFinancialTransactionRequest transferFinancialTransactionRequest, @AuthenticationPrincipal CustomerPrincipal customerPrincipal) {

        return ResponseEntity.ok(transferTransactionService.doTransferTransaction(transferFinancialTransactionRequest, customerPrincipal.getUsername()));
    }
}
