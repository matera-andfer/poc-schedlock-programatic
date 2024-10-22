package com.wohr.dynamicscheduled;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneralSettingsRepository extends JpaRepository<GeneralSettings, Long> {
    Optional<GeneralSettings> findById(Long id);
}