package com.wohr.dynamicscheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/scheduler")
@RequiredArgsConstructor
public class SchedulerController {

    private final GeneralSettingsService generalSettingsService;
    private final DynamicSchedulerService dynamicSchedulerService;

    // Atualiza a expressão cron para o id específico
    @PutMapping("/{id}/cron")
    public ResponseEntity<String> updateCronExpression(@PathVariable Long id, @RequestParam String newCron) {
        try {
            generalSettingsService.updateCronExpression(newCron, id);
            return ResponseEntity.ok("Cron expression updated successfully for ID " + id + "!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating cron expression for ID " + id + ": " + e.getMessage());
        }
    }

    // Atualiza o fuso horário para o id específico
    @PutMapping("/{id}/timezone")
    public ResponseEntity<String> updateTimeZone(@PathVariable Long id, @RequestParam String newTimeZone) {
        try {
            generalSettingsService.updateTimeZone(newTimeZone, id);
            return ResponseEntity.ok("Time zone updated successfully for ID " + id + "!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating time zone for ID " + id + ": " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<GeneralSettings>> getAllSettings() {
        List<GeneralSettings> settings = generalSettingsService.getAllSettings();
        return ResponseEntity.ok(settings);
    }

    @PostMapping("/start")
    public ResponseEntity<String> startScheduler() {
        dynamicSchedulerService.startTask();
        return ResponseEntity.ok("Scheduler started.");
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopScheduler() {
        dynamicSchedulerService.stopTask();
        return ResponseEntity.ok("Scheduler stopped.");
    }
}