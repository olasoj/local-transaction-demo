package com.example.demo.transaction;

import com.example.demo.account.FinancialAccountService;
import com.example.demo.transaction.model.FinancialTransactionItem;
import com.example.demo.transaction.model.FinancialTransactionRequest;
import com.example.demo.utils.TimeUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class DefaultTransactionService implements TransactionService {

    private static final ConcurrentMap<String, TreeSet<FinancialTransactionRequest>> transactionHistory = new ConcurrentHashMap<>();

    private final FinancialAccountService financialAccountService;

    public DefaultTransactionService(FinancialAccountService financialAccountService) {
        this.financialAccountService = financialAccountService;
    }

    private static Month getMonth() {
        LocalDate now = LocalDate.now();
        return now.getMonth();
    }

    @Override
    public Object doTransaction(FinancialTransactionRequest financialTransactionRequest) {

        //Financial transactions
        List<FinancialTransactionItem> financialTransactionItems = financialTransactionRequest.getFinancialTransactionItems();
        BigDecimal totalCredit = financialTransactionItems
                .stream()
                .filter(financialTransactionItem -> FinancialTransactionFlow.CREDIT.equals(financialTransactionItem.getFinancialTransactionFlow()))
                .reduce(BigDecimal.ZERO, (bigDecimal, financialTransactionItem) -> bigDecimal.add(financialTransactionItem.getAmount()), BigDecimal::add);

        BigDecimal totalDebit = financialTransactionItems
                .stream()
                .filter(financialTransactionItem -> FinancialTransactionFlow.DEBIT.equals(financialTransactionItem.getFinancialTransactionFlow()))
                .reduce(BigDecimal.ZERO, (bigDecimal, financialTransactionItem) -> bigDecimal.add(financialTransactionItem.getAmount()), BigDecimal::add);

        //Balance transaction
        if (totalCredit.subtract(totalDebit).compareTo(BigDecimal.ZERO) != 0) throw new IllegalStateException("Imbalanced transaction detected");

        try {

            //Perform credit and debit transactions
            for (FinancialTransactionItem financialTransactionItem : financialTransactionItems) {
                if (FinancialTransactionFlow.DEBIT.equals(financialTransactionItem.getFinancialTransactionFlow()))
                    financialAccountService.creditAccount(financialTransactionItem.getAccountNumber(), financialTransactionItem.getAmount());

                else if (FinancialTransactionFlow.CREDIT.equals(financialTransactionItem.getFinancialTransactionFlow()))
                    financialAccountService.debitAccount(financialTransactionItem.getAccountNumber(), financialTransactionItem.getAmount());
            }

            //Register transaction items
            financialTransactionItems
                    .forEach(financialTransactionItem ->
                            transactionHistory.compute(
                                    financialTransactionItem.getAccountNumber(),
                                    (s, financialTransactionRequests) -> {

                                        if (financialTransactionRequests == null) {
                                            TreeSet<FinancialTransactionRequest> financialTransactionRequestsLocal = new TreeSet<>(Comparator.comparing(FinancialTransactionRequest::getFinancialTransactionDate).reversed());
                                            financialTransactionRequestsLocal.add(financialTransactionRequest);
                                            return financialTransactionRequestsLocal;
                                        } else {
                                            financialTransactionRequests.add(financialTransactionRequest);
                                            return financialTransactionRequests;
                                        }
                                    }


                            ));

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            //Rollback
            throw new FinancialTransactionException("Transaction failed");
        }

    }

    @Override
    public boolean hasAccountDoneMoreThanInXMonth(String accountNumber, int noOfMonths) {

        TreeSet<FinancialTransactionRequest> financialTransactionRequests = transactionHistory.get(accountNumber);
        if (financialTransactionRequests == null || financialTransactionRequests.size() < noOfMonths) return false;
        return getAccumulator(noOfMonths, financialTransactionRequests) > noOfMonths;
    }

    @Override
    public boolean hasAccountDoneLessXTxThanInMonth(String accountNumber, int noOfMonths) {

        TreeSet<FinancialTransactionRequest> financialTransactionRequests = transactionHistory.get(accountNumber);
        if (financialTransactionRequests == null) return true;
        return getAccumulator(noOfMonths, financialTransactionRequests) < noOfMonths;
    }

    private int getAccumulator(int noOfMonths, TreeSet<FinancialTransactionRequest> financialTransactionRequests) {
        Month month = getMonth();

        int accumulator = 0;

        for (FinancialTransactionRequest financialTransactionRequest : financialTransactionRequests) {
            ZonedDateTime zonedDateTime = TimeUtils.toZonedDateTime(financialTransactionRequest.getFinancialTransactionDate(), ZoneId.systemDefault().getId());
            boolean isTransactionDoneInTheMonth = zonedDateTime.getMonth().equals(month);
            if (isTransactionDoneInTheMonth) accumulator++;
            if (!isTransactionDoneInTheMonth || accumulator >= noOfMonths) break;
        }
        return accumulator;
    }

    @Override
    public TreeSet<FinancialTransactionRequest> getTransactionHistory(String accountNumber) {
        return transactionHistory.get(accountNumber);
    }
}

class FinancialTransactionException extends RuntimeException {

    public FinancialTransactionException(String message) {
        super(message);
    }
}
