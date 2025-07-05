package com.iws_manager.iws_manager_api.repositories;

import java.util.List;

import com.iws_manager.iws_manager_api.models.AbsenceDay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbsenceDayRepository extends JpaRepository<AbsenceDay, Long> {
    List<AbsenceDay> findByEmployeeId(Long employeeId);
}