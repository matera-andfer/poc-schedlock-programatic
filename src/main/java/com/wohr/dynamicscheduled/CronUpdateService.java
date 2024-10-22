package com.wohr.dynamicscheduled;

import org.springframework.stereotype.Service;

@Service
public class CronUpdateService {
    private final DynamicSchedulerService schedulerService;

    public CronUpdateService(DynamicSchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    public void updateCron() {
        schedulerService.stopTask();  // Para a tarefa atual
        schedulerService.startTask(); // Inicia com a nova expressão do banco
    }
}