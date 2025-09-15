package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.ReminderLevel;

public interface ReminderLevelService {
    ReminderLevel create(ReminderLevel reminderLevel);
    Optional<ReminderLevel> findById(Long id);
    List<ReminderLevel> findAll();
    ReminderLevel update(Long id, ReminderLevel reminderLevelDetails);
    void delete(Long id);
}