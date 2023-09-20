package com.example.demo.transaction.airtime.controller;

import com.example.demo.transaction.airtime.AirtimeTransactionService;
import com.example.demo.transaction.airtime.model.AirtimeFinancialTransactionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/airtime")
public class AirtimeTransactionController {

    private final AirtimeTransactionService airtimeTransactionService;


    public AirtimeTransactionController(AirtimeTransactionService airtimeTransactionService) {
        this.airtimeTransactionService = airtimeTransactionService;
    }

    public ResponseEntity<Object> receiveAirtimeTransactionRequest(@RequestBody AirtimeFinancialTransactionRequest airtimeFinancialTransactionRequest){

        return null;
    }
}
