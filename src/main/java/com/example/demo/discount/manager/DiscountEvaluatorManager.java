package com.example.demo.discount.manager;

import com.example.demo.transaction.model.TransactionType;
import com.example.demo.user.Customer;

import java.math.BigDecimal;

public interface DiscountEvaluatorManager {

    BigDecimal evaluateDiscount(
            TransactionType transactionType,
            Customer customer,
            String financialAccountNumber,
            BigDecimal amount
    );

}
