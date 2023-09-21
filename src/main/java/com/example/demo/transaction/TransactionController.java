package com.example.demo.transaction;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/transaction")
public class TransactionController {

    private final FinancialTransactionService financialTransactionService;

    public TransactionController(@Qualifier("defaultFinancialTransactionService") FinancialTransactionService financialTransactionService) {
        this.financialTransactionService = financialTransactionService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/withdraw")
    public ResponseEntity<Object> withdraw(@PathVariable(value = "accountNumber") String accountNumber) {
        return ResponseEntity.ok(financialTransactionService.getTransactionHistory(accountNumber));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, path = "/")
    public ResponseEntity<Object> receiveAirtimeTransactionRequest(@PathVariable(value = "accountNumber") String accountNumber) {
        return ResponseEntity.ok(financialTransactionService.getTransactionHistory(accountNumber));
    }
}
