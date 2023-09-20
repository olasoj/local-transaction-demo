package com.example.demo.discount.user.factory;

import com.example.demo.discount.DiscountEvaluatorFactory;
import com.example.demo.discount.user.UserTypeDiscountEvaluator;
import com.example.demo.transaction.model.TransactionType;
import com.example.demo.user.Customer;

import java.util.Set;

public interface UserTypeDiscountEvaluatorFactory extends DiscountEvaluatorFactory {

    Set<UserTypeDiscountEvaluator> getUserDiscountEvaluators(TransactionType transactionType, Customer customer);
}
