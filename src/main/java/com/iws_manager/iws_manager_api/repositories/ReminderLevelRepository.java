package com.iws_manager.iws_manager_api.repositories;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.ReminderLevel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReminderLevelRepository extends JpaRepository<ReminderLevel, Long> {
    
    List<ReminderLevel> findAllByOrderByLevelNoAsc();
}