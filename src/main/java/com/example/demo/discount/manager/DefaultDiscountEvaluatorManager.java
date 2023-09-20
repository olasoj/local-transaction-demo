package com.example.demo.discount.manager;

import com.example.demo.discount.user.UserTypeDiscountEvaluator;
import com.example.demo.discount.user.factory.UserTypeDiscountEvaluatorFactory;
import com.example.demo.discount.year.DurationYearDiscountEvaluator;
import com.example.demo.transaction.model.TransactionType;
import com.example.demo.user.Customer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

@Component
public class DefaultDiscountEvaluatorManager implements DiscountEvaluatorManager {

    private final UserTypeDiscountEvaluatorFactory userTypeDiscountEvaluatorFactory;
    private final DurationYearDiscountEvaluator durationYearDiscountEvaluator;

    public DefaultDiscountEvaluatorManager(UserTypeDiscountEvaluatorFactory userTypeDiscountEvaluatorFactory, DurationYearDiscountEvaluator durationYearDiscountEvaluator) {
        this.userTypeDiscountEvaluatorFactory = userTypeDiscountEvaluatorFactory;
        this.durationYearDiscountEvaluator = durationYearDiscountEvaluator;
    }

    @Override
    public BigDecimal evaluateDiscount(TransactionType transactionType, Customer customer, String financialAccountNumber, BigDecimal amount) {
        Set<UserTypeDiscountEvaluator> userDiscountEvaluators = userTypeDiscountEvaluatorFactory.getUserDiscountEvaluators(transactionType, customer);

        BigDecimal totalUserDiscount = userDiscountEvaluators
                .stream()
                .reduce(BigDecimal.ZERO,
                        (bigDecimal, userTypeDiscountEvaluator) -> userTypeDiscountEvaluator.evaluateDiscount(transactionType, customer, financialAccountNumber, amount).add(bigDecimal),
                        BigDecimal::add
                )
                .add(durationYearDiscountEvaluator.evaluateDiscount(transactionType, customer, financialAccountNumber, amount));


        BigDecimal multiply = totalUserDiscount.multiply(amount);
        return multiply.divide(BigDecimal.valueOf(100), 2, RoundingMode.FLOOR);
    }
}
