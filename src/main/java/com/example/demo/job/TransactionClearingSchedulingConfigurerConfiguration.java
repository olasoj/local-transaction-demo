package com.example.demo.job;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
@EnableScheduling
public class TransactionClearingSchedulingConfigurerConfiguration implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(2);
        taskScheduler.initialize();
        taskRegistrar.setTaskScheduler(taskScheduler);
    }

    @Bean(name = "transactionClearingTaskScheduler")
    public TaskScheduler transactionClearingTaskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(1);
        taskScheduler.setDaemon(true);
        taskScheduler.setErrorHandler(t -> {

        });
        taskScheduler.setThreadNamePrefix("transaction-clearing-pool-");
        taskScheduler.initialize();
        return taskScheduler;
    }

}
