package com.example.demo.discount;

import com.example.demo.transaction.model.TransactionType;
import com.example.demo.user.Customer;
import com.example.demo.user.UserType;

import java.math.BigDecimal;
import java.util.Set;

public interface DiscountEvaluator {

    //The discount evaluation method
    //sourceAccountType, transactionAmount, transactionDate
    BigDecimal evaluateDiscount(
            TransactionType transactionType,
            Customer customer,
            String financialAccountNumber,
            BigDecimal amount
    );

    //The support transaction types
    Set<TransactionType> supportedTransactionType();

    //The support user types
    Set<UserType> supportedUserType();
}
