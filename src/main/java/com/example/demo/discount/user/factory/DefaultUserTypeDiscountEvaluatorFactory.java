package com.example.demo.discount.user.factory;

import com.example.demo.discount.user.UserTypeDiscountEvaluator;
import com.example.demo.transaction.model.TransactionType;
import com.example.demo.user.Customer;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DefaultUserTypeDiscountEvaluatorFactory implements UserTypeDiscountEvaluatorFactory {

    private final Set<UserTypeDiscountEvaluator> userTypeDiscountEvaluators;

    public DefaultUserTypeDiscountEvaluatorFactory(Set<UserTypeDiscountEvaluator> userTypeDiscountEvaluators) {
        this.userTypeDiscountEvaluators = userTypeDiscountEvaluators;
    }

    @Override
    public Set<UserTypeDiscountEvaluator> getUserDiscountEvaluators(TransactionType transactionType, Customer customer) {

        return userTypeDiscountEvaluators
                .stream()
                .filter(userTypeDiscountEvaluator ->
                        userTypeDiscountEvaluator.supportedUserType().contains(customer.getCustomerType())
                                && userTypeDiscountEvaluator.supportedTransactionType().contains(transactionType))
                .collect(Collectors.toSet());
    }


}
