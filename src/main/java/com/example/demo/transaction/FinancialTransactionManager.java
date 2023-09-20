package com.example.demo.transaction;

import com.example.demo.account.FinancialAccount;
import com.example.demo.account.FinancialAccountService;
import com.example.demo.discount.manager.DiscountEvaluatorManager;
import com.example.demo.transaction.model.FinancialTransactionItem;
import com.example.demo.transaction.model.FinancialTransactionRequest;
import com.example.demo.transaction.model.TransactionType;
import com.example.demo.user.Customer;
import com.example.demo.user.CustomerService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class FinancialTransactionManager {

    private final DiscountEvaluatorManager discountEvaluatorManager;
    private final CustomerService customerService;
    private final FinancialAccountService financialAccountService;
    private final TransactionService transactionService;

    public FinancialTransactionManager(DiscountEvaluatorManager discountEvaluatorManager, CustomerService customerService, FinancialAccountService financialAccountService, TransactionService transactionService) {
        this.discountEvaluatorManager = discountEvaluatorManager;
        this.customerService = customerService;
        this.financialAccountService = financialAccountService;
        this.transactionService = transactionService;
    }

    public void doTransaction(
            TransactionType transactionType
            , String sourceFinancialAccount
            , String destinationFinancialAccount
            , BigDecimal amount) {

        ReentrantLock accountLock = financialAccountService.getAccountLock(sourceFinancialAccount);

        accountLock.lock();
        try {

            FinancialAccount financialAccount = financialAccountService.getFinancialAccount(sourceFinancialAccount);
            Customer customer = customerService.getCustomer(financialAccount.getCustomerId());
            BigDecimal discount = discountEvaluatorManager.evaluateDiscount(transactionType, customer, sourceFinancialAccount, amount);

            BigDecimal chargeAmount = amount.subtract(discount);

            FinancialTransactionRequest financialTransactionRequest = new FinancialTransactionRequest(
                    FinancialTransactionGenerator.sessionId(),
                    Instant.now(),
                    new FinancialTransactionItem(sourceFinancialAccount, chargeAmount, FinancialTransactionFlow.DEBIT),
                    new FinancialTransactionItem(destinationFinancialAccount, chargeAmount, FinancialTransactionFlow.CREDIT)
            );

            transactionService.doTransaction(financialTransactionRequest);
        } finally {
            accountLock.unlock();
        }
    }
}

class FinancialTransactionGenerator {

    private static final SecureRandom secureRandom = new SecureRandom();

    private FinancialTransactionGenerator() {
    }

    private static String randomId() {
        var bytes = new byte[20];
        secureRandom.nextBytes(bytes);
        return Base64url.encode(bytes);
    }

    protected static String sessionId() {
        var tokenId = randomId();
        return hash(tokenId);
    }

    private static String hash(String tokenId) {
        assert tokenId != null;
        var hash = sha256(tokenId);
        return Base64url.encode(hash);
    }

    static byte[] sha256(String tokenId) {
        try {
            var sha256 = MessageDigest.getInstance("SHA-256");
            return sha256.digest(tokenId.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

}

class Base64url {
    private static final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder decoder = Base64.getUrlDecoder();

    private Base64url() {
    }

    public static String encode(byte[] data) {
        return encoder.encodeToString(data);
    }

    public static byte[] decode(String encoded) {
        return decoder.decode(encoded);
    }
}
