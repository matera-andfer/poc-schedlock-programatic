package com.wohr.dynamicscheduled;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "general_settings")
@Getter
@Setter
public class GeneralSettings {

    @Id
    private Long id;
    private String cronExpression;  // Campo para a expressão cron
    private String timeZone;        // Campo para o fuso horário
}