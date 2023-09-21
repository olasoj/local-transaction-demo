package com.example.demo.job;


import com.example.demo.account.DefaultFinancialAccountService;
import com.example.demo.transaction.FinancialTransactionService;
import com.example.demo.transaction.model.FinancialTransactionRequest;
import com.example.demo.utils.TimeUtils;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class TransactionClearingJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionClearingJob.class);
    private static final ExecutorService fixedThreadPool;
    private static final Runtime runtime = Runtime.getRuntime();
    private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    static {
        fixedThreadPool = Executors.newFixedThreadPool(runtime.availableProcessors());
    }

    private final TaskScheduler transactionClearingTaskScheduler;
    private final FinancialTransactionService financialTransactionService;
    private volatile boolean isDailyClearingJobActive = false;

    private TransactionClearingJob(TaskScheduler transactionClearingTaskScheduler, FinancialTransactionService financialTransactionService) {
        this.transactionClearingTaskScheduler = transactionClearingTaskScheduler;
        this.financialTransactionService = financialTransactionService;
    }

    @PostConstruct
    public void init() {

        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        Runnable runnable = this::initInternal;
        scheduledExecutorService.scheduleAtFixedRate(runnable, 2L, 1, TimeUnit.HOURS);
    }

    public boolean isDailyJobCommencing() {
        return isDailyClearingJobActive;
    }

    public void initInternal() {

        isDailyClearingJobActive = isDailyClearingJobActive();

        if (isDailyClearingJobActive) {
            LOGGER.debug("Started TransactionClearingJob");
            computeDailyJob();
            isDailyClearingJobActive = false;
        }

        Runnable shutdownTask = () -> {
            LOGGER.debug("Shutting down TransactionClearingJob");
            fixedThreadPool.shutdown();
            scheduledExecutorService.shutdown();
        };

        runtime.addShutdownHook(new Thread(shutdownTask));
    }

    private boolean isDailyClearingJobActive() {
        LocalDate now = LocalDate.now();
        Instant nowInstant = Instant.now();
        LocalDateTime localDateTime = now.atStartOfDay();
        Instant instant = TimeUtils.toInstant(localDateTime);
        Instant endOfDay = instant.plus(Duration.ofHours(24));
        Instant hourBeforeEndOfDay = instant.minus(Duration.ofHours(1));

        return nowInstant.isAfter(hourBeforeEndOfDay) && nowInstant.isBefore(endOfDay);
    }

    private void computeDailyJob() {

        DefaultFinancialAccountService.financialAccountConcurrentMap
                .values()
                .stream().forEach(
                        financialAccount -> {
                            TreeSet<FinancialTransactionRequest> transactionHistory = financialTransactionService.getTransactionHistory(financialAccount.getAccountNumber());
                            //getThatHappenToday
                            //Compute total daily fee

                        }
                );
    }

}

