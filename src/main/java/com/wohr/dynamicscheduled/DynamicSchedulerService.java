package com.wohr.dynamicscheduled;

import jakarta.annotation.PostConstruct;
import net.javacrumbs.shedlock.core.DefaultLockingTaskExecutor;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.core.LockingTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;

@Service
public class DynamicSchedulerService {

    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private ScheduledFuture<?> scheduledTask;
    private final GeneralSettingsService generalSettingsService;
    private final LockingTaskExecutor taskExecutor;


    String cronExpression = null;

    public DynamicSchedulerService(GeneralSettingsService generalSettingsService, LockProvider lockProvider) {
        this.threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        this.threadPoolTaskScheduler.initialize();
        this.generalSettingsService = generalSettingsService;
        this.taskExecutor = new DefaultLockingTaskExecutor(lockProvider);
    }

    @PostConstruct
    public void initialize() {
        startTask();
    }

    public void startTask() {
        this.cronExpression = generalSettingsService.getCronExpressionFromDB(1L);

        if (!CronExpression.isValidExpression(this.cronExpression)) {
            throw new IllegalArgumentException("Expressão cron inválida: " + this.cronExpression);
        }

        threadPoolTaskScheduler.schedule(() ->
                        taskExecutor.executeWithLock((Runnable) this::executeTask,
                                new LockConfiguration(Instant.now(), "myTaskLock",
                                        Duration.ofMinutes(2), // Tempo máximo do lock
                                        Duration.ofMinutes(1)  // Tempo mínimo antes de liberar o lock
                                )
                        ),
                new CronTrigger(cronExpression)
        );
    }

    public void stopTask() {
        if (scheduledTask != null) {
            scheduledTask.cancel(false);
        }
    }

    private void executeTask() {
        System.out.println("Executando tarefa em " + LocalDateTime.now() + " com a expressão cron: " + cronExpression);
    }
}