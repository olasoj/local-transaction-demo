package com.example.demo.discount.user;

import com.example.demo.transaction.TransactionService;
import com.example.demo.transaction.model.TransactionType;
import com.example.demo.user.Customer;
import com.example.demo.user.UserType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
public class BusinessUserTypeDiscountEvaluator implements UserTypeDiscountEvaluator {
    private static final BigDecimal thresholdAmount = BigDecimal.valueOf(150_000);
    private static final BigDecimal percentage = BigDecimal.valueOf(27);


    private final TransactionService transactionService;

    public BusinessUserTypeDiscountEvaluator(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public BigDecimal evaluateDiscount(TransactionType transactionType
            , Customer customer
            , String financialAccountNumber
            , BigDecimal amount) {

        if (thresholdAmount.compareTo(amount) < 0 && transactionService.hasAccountDoneMoreThanInXMonth(financialAccountNumber, 3))
            return percentage;
        return BigDecimal.ZERO;
    }

    @Override
    public Set<TransactionType> supportedTransactionType() {
        return Set.of(TransactionType.TRANSFER);
    }

    @Override
    public Set<UserType> supportedUserType() {
        return Set.of(UserType.BUSINESS);
    }
}
