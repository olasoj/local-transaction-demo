package com.example.demo.discount.year;

import com.example.demo.transaction.TransactionService;
import com.example.demo.transaction.model.TransactionType;
import com.example.demo.user.Customer;
import com.example.demo.user.UserType;
import com.example.demo.utils.TimeUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Component
public class DefaultDurationDiscountEvaluator implements DurationYearDiscountEvaluator {

    private static final BigDecimal percentage = BigDecimal.valueOf(10);

    private final TransactionService transactionService;

    public DefaultDurationDiscountEvaluator(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public BigDecimal evaluateDiscount(
            TransactionType transactionType
            , Customer customer
            , String financialAccountNumber
            , BigDecimal amount) {

        long between = ChronoUnit.YEARS.between(TimeUtils.toZonedDateTime(customer.getDateCreated()), TimeUtils.toZonedDateTime(Instant.now()));
        if (between > 4 && transactionService.hasAccountDoneLessXTxThanInMonth(financialAccountNumber, 3))
            return percentage;
        return BigDecimal.ZERO;
    }

    @Override
    public Set<TransactionType> supportedTransactionType() {
        return Set.of(TransactionType.TRANSFER);
    }

    @Override
    public Set<UserType> supportedUserType() {
        return Set.of(UserType.ALL);
    }
}
