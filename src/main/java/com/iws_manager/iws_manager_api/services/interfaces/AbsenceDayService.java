package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.AbsenceDay;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AbsenceDayService {
    
    AbsenceDay create(AbsenceDay absenceDay);
    Optional<AbsenceDay> findById(Long id);
    List<AbsenceDay> findAll();
    AbsenceDay update(Long id, AbsenceDay absenceDayDetails);
    void delete(Long id);

    List<AbsenceDay> getByEmployeeId(Long employeeId);
    List<AbsenceDay> getByEmployeeIdAndDateRange(Long employeeId, LocalDate startDate, LocalDate endDate);
    List<AbsenceDay> getByEmployeeIdAndAbsenceTypeId(Long employeeId, Long absenceTypeId);
    boolean existsByEmployeeIdAndAbsenceDate(Long employeeId, LocalDate absenceDate);
    List<AbsenceDay> getByEmployeeIdAndYear(Long employeeId, int year);
    long countByEmployeeIdAndAbsenceTypeIdAndYear(Long employeeId, Long absenceTypeId, int year);
}