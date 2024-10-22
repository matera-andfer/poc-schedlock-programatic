package com.wohr.dynamicscheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneralSettingsService {

    private final GeneralSettingsRepository generalSettingsRepository;

    public String getCronExpressionFromDB(Long id) {
        // Supondo que o ID do cron seja 1, altere conforme sua necessidade
        GeneralSettings settings = findById(id);
        return settings != null ? settings.getCronExpression() : null;
    }

    public String getTimeZoneFromDB(Long id) {
        GeneralSettings settings = findById(id);
        return settings != null ? settings.getTimeZone() : null;
    }

    public void updateCronExpression(String newCron, Long id) {

        expressionCronIsValid(newCron);

        GeneralSettings settings = findById(id);
        if (settings != null) {
            settings.setCronExpression(newCron);
            generalSettingsRepository.save(settings);
        }
    }

    private void expressionCronIsValid(String cron){
        CronExpression.parse(cron);
    }

    public void updateTimeZone(String newTimeZone, Long id) {
        GeneralSettings settings = findById(id);
        if (settings != null) {
            settings.setTimeZone(newTimeZone);
            generalSettingsRepository.save(settings);
        }
    }

    public GeneralSettings findById(Long id) {
        return generalSettingsRepository.findById(id).orElse(null);
    }

    public List<GeneralSettings> getAllSettings() {
        return generalSettingsRepository.findAll();
    }
}