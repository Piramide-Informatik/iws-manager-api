package com.iws_manager.iws_manager_api.services.v2.interfaces;

import com.iws_manager.iws_manager_api.dtos.absenceday.AbsenceDayFilterDTO;
import com.iws_manager.iws_manager_api.dtos.absenceday.AbsenceDayRequestDTO;
import com.iws_manager.iws_manager_api.models.AbsenceDay;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AbsenceDayServiceV2 {
    
    AbsenceDay createFromDTO(AbsenceDayRequestDTO requestDTO);
    Optional<AbsenceDay> findById(Long id);
    List<AbsenceDay> findAll();
    AbsenceDay updateFromDTO(Long id, AbsenceDayRequestDTO requestDTO);
    void delete(Long id);
    List<AbsenceDay> getByEmployeeId(Long employeeId);
    List<AbsenceDay> getByEmployeeIdAndDateRange(Long employeeId, LocalDate startDate, LocalDate endDate);
    List<AbsenceDay> getByEmployeeIdAndAbsenceTypeId(Long employeeId, Long absenceTypeId);
    boolean existsByEmployeeIdAndAbsenceDate(Long employeeId, LocalDate absenceDate);
    List<AbsenceDay> getByEmployeeIdAndYear(Long employeeId, int year);
    long countByEmployeeIdAndAbsenceTypeIdAndYear(Long employeeId, Long absenceTypeId, int year);
    List<Object[]> countAbsenceDaysByTypeForEmployee(Long employeeId);
    List<AbsenceDay> filter(AbsenceDayFilterDTO filterDTO);
    Optional<AbsenceDay> findByIdWithRelations(Long id);
    List<Object[]> countAbsenceDaysByTypeForEmployeeAndYear(Long employeeId, int year);
}