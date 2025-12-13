package com.iws_manager.iws_manager_api.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iws_manager.iws_manager_api.models.AbsenceDay;

/**
 * Repository interface for managing absence day records in the database.
 * Provides CRUD operations and custom queries for absence day tracking.
 */
@Repository
public interface AbsenceDayRepository extends JpaRepository<AbsenceDay, Long> {
    
    /**
     * Finds all absence days for a specific employee.
     * 
     * @param employeeId the ID of the employee to search for
     * @return list of absence days associated with the employee
     */
    List<AbsenceDay> findByEmployeeId(Long employeeId);
    
    /**
     * Finds absence days for an employee within a specific date range.
     * 
     * @param employeeId the ID of the employee
     * @param startDate the start date of the range (inclusive)
     * @param endDate the end date of the range (inclusive)
     * @return list of absence days matching the criteria
     */
    List<AbsenceDay> findByEmployeeIdAndAbsenceDateBetween(
        Long employeeId, 
        LocalDate startDate, 
        LocalDate endDate);
    
    /**
     * Finds absence days for an employee with a specific absence type.
     * 
     * @param employeeId the ID of the employee
     * @param absenceTypeId the ID of the absence type to filter by
     * @return list of matching absence days
     */
    List<AbsenceDay> findByEmployeeIdAndAbsenceTypeId(
        Long employeeId, 
        Long absenceTypeId);
    
    /**
     * Custom query to count absence days by type for a specific employee.
     * Returns an array of objects where each contains:
     * - [0] AbsenceType entity
     * - [1] Count of days for that type
     * 
     * @param employeeId the ID of the employee
     * @return list of Object arrays containing absence type and count pairs
     */
    @Query("SELECT a.absenceType, COUNT(a) FROM AbsenceDay a WHERE a.employee.id = :employeeId GROUP BY a.absenceType")
    List<Object[]> countAbsenceDaysByTypeForEmployee(Long employeeId);
    
    /**
     * Checks if an employee has an absence recorded on a specific date.
     * 
     * @param employeeId the ID of the employee to check
     * @param absenceDate the date to check for absences
     * @return true if an absence exists, false otherwise
     */
    boolean existsByEmployeeIdAndAbsenceDate(Long employeeId, LocalDate absenceDate);

    /**
     * Checks if an absence exists for an employee on a specific date, excluding a specific absence ID.
     * 
     * @param employeeId the ID of the employee
     * @param absenceDate the date to check
     * @param excludeAbsenceDayId the absence day ID to exclude from the check
     * @return true if another absence exists, false otherwise
     */
    @Query("SELECT COUNT(a) > 0 FROM AbsenceDay a WHERE a.employee.id = :employeeId " +
        "AND a.absenceDate = :absenceDate AND a.id != :excludeAbsenceDayId")
    boolean existsByEmployeeIdAndAbsenceDateExcludingId(
        Long employeeId, 
        LocalDate absenceDate, 
        Long excludeAbsenceDayId);
}