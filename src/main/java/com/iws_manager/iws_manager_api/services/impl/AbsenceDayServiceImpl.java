package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.AbsenceDay;
import com.iws_manager.iws_manager_api.models.Employee;
import com.iws_manager.iws_manager_api.models.AbsenceType;
import com.iws_manager.iws_manager_api.repositories.AbsenceDayRepository;
import com.iws_manager.iws_manager_api.repositories.EmployeeRepository;
import com.iws_manager.iws_manager_api.repositories.AbsenceTypeRepository;
import com.iws_manager.iws_manager_api.services.interfaces.AbsenceDayService;
import com.iws_manager.iws_manager_api.exception.exceptions.DuplicateResourceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AbsenceDayServiceImpl implements AbsenceDayService {

    private final AbsenceDayRepository absenceDayRepository;
    private final EmployeeRepository employeeRepository;
    private final AbsenceTypeRepository absenceTypeRepository;

    @Autowired
    public AbsenceDayServiceImpl(
            AbsenceDayRepository absenceDayRepository,
            EmployeeRepository employeeRepository,
            AbsenceTypeRepository absenceTypeRepository) {
        this.absenceDayRepository = absenceDayRepository;
        this.employeeRepository = employeeRepository;
        this.absenceTypeRepository = absenceTypeRepository;
    }

    @Override
    public AbsenceDay create(AbsenceDay absenceDay) {
        if (absenceDay == null) {
            throw new IllegalArgumentException("AbsenceDay cannot be null");
        }

        validateAbsenceDayForCreation(absenceDay);

        return absenceDayRepository.save(absenceDay);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AbsenceDay> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return absenceDayRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbsenceDay> findAll() {
        return absenceDayRepository.findAll();
    }

    @Override
    public AbsenceDay update(Long id, AbsenceDay absenceDayDetails) {
        if (id == null || absenceDayDetails == null) {
            throw new IllegalArgumentException("ID and AbsenceDay details cannot be null");
        }

        return absenceDayRepository.findById(id)
                .map(existingAbsenceDay -> {
                    validateAbsenceDayForUpdate(existingAbsenceDay, absenceDayDetails, id);

                    existingAbsenceDay.setAbsenceDate(absenceDayDetails.getAbsenceDate());
                    existingAbsenceDay.setAbsenceType(absenceDayDetails.getAbsenceType());
                    existingAbsenceDay.setEmployee(absenceDayDetails.getEmployee());

                    return absenceDayRepository.save(existingAbsenceDay);
                })
                .orElseThrow(() -> new EntityNotFoundException("AbsenceDay not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!absenceDayRepository.existsById(id)) {
            throw new EntityNotFoundException("AbsenceDay not found with id: " + id);
        }
        absenceDayRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbsenceDay> getByEmployeeId(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        return absenceDayRepository.findByEmployeeId(employeeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbsenceDay> getByEmployeeIdAndDateRange(Long employeeId, LocalDate startDate, LocalDate endDate) {
        if (employeeId == null || startDate == null || endDate == null) {
            throw new IllegalArgumentException("Employee ID, start date, and end date cannot be null");
        }
        
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        
        return absenceDayRepository.findByEmployeeIdAndAbsenceDateBetween(employeeId, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbsenceDay> getByEmployeeIdAndAbsenceTypeId(Long employeeId, Long absenceTypeId) {
        if (employeeId == null || absenceTypeId == null) {
            throw new IllegalArgumentException("Employee ID and AbsenceType ID cannot be null");
        }
        return absenceDayRepository.findByEmployeeIdAndAbsenceTypeId(employeeId, absenceTypeId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmployeeIdAndAbsenceDate(Long employeeId, LocalDate absenceDate) {
        if (employeeId == null || absenceDate == null) {
            throw new IllegalArgumentException("Employee ID and absence date cannot be null");
        }
        return absenceDayRepository.existsByEmployeeIdAndAbsenceDate(employeeId, absenceDate);
    }

    /**
     * Validates the absence day for creation.
     * Checks for duplicate absence on the same date for the same employee.
     * Validates that related entities (Employee, AbsenceType) exist.
     */
    private void validateAbsenceDayForCreation(AbsenceDay absenceDay) {
        // Check if employee exists
        if (absenceDay.getEmployee() == null || absenceDay.getEmployee().getId() == null) {
            throw new IllegalArgumentException("Employee must be specified");
        }
        
        Employee employee = employeeRepository.findById(absenceDay.getEmployee().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                    "Employee not found with id: " + absenceDay.getEmployee().getId()));
        absenceDay.setEmployee(employee);

        // Check if absence type exists
        if (absenceDay.getAbsenceType() == null || absenceDay.getAbsenceType().getId() == null) {
            throw new IllegalArgumentException("AbsenceType must be specified");
        }
        
        AbsenceType absenceType = absenceTypeRepository.findById(absenceDay.getAbsenceType().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                    "AbsenceType not found with id: " + absenceDay.getAbsenceType().getId()));
        absenceDay.setAbsenceType(absenceType);

        // Check for duplicate absence on the same date for the same employee
        if (absenceDay.getAbsenceDate() != null) {
            if (absenceDayRepository.existsByEmployeeIdAndAbsenceDate(
                    absenceDay.getEmployee().getId(), absenceDay.getAbsenceDate())) {
                throw new DuplicateResourceException(
                    "Absence already exists for employee ID " + absenceDay.getEmployee().getId() + 
                    " on date " + absenceDay.getAbsenceDate());
            }
        }
    }

    /**
     * Validates the absence day for update.
     * Checks for duplicate absence on the same date for the same employee (excluding current record).
     * Validates that related entities exist.
     */
    private void validateAbsenceDayForUpdate(AbsenceDay existingAbsenceDay, AbsenceDay newAbsenceDay, Long id) {
        // Determine the employee ID to use
        Long employeeId = newAbsenceDay.getEmployee() != null && newAbsenceDay.getEmployee().getId() != null
                ? newAbsenceDay.getEmployee().getId()
                : existingAbsenceDay.getEmployee().getId();
        
        // Determine the absence date to use
        LocalDate absenceDate = newAbsenceDay.getAbsenceDate() != null
                ? newAbsenceDay.getAbsenceDate()
                : existingAbsenceDay.getAbsenceDate();
        
        // Check if date changed
        boolean dateChanged = newAbsenceDay.getAbsenceDate() != null && 
                !existingAbsenceDay.getAbsenceDate().equals(newAbsenceDay.getAbsenceDate());
        
        // Check if employee changed
        boolean employeeChanged = newAbsenceDay.getEmployee() != null && 
                newAbsenceDay.getEmployee().getId() != null &&
                !existingAbsenceDay.getEmployee().getId().equals(newAbsenceDay.getEmployee().getId());

        // Validate employee if provided
        if (newAbsenceDay.getEmployee() != null && newAbsenceDay.getEmployee().getId() != null) {
            Employee emp = employeeRepository.findById(newAbsenceDay.getEmployee().getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                        "Employee not found with id: " + newAbsenceDay.getEmployee().getId()));
            existingAbsenceDay.setEmployee(emp);
        }

        // Validate absence type if provided
        if (newAbsenceDay.getAbsenceType() != null && newAbsenceDay.getAbsenceType().getId() != null) {
            AbsenceType at = absenceTypeRepository.findById(newAbsenceDay.getAbsenceType().getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                        "AbsenceType not found with id: " + newAbsenceDay.getAbsenceType().getId()));
            existingAbsenceDay.setAbsenceType(at);
        }

        // Check for duplicate absence if date or employee changed
        if (dateChanged || employeeChanged) {
            // Use the repository method to check for duplicates excluding the current record
            boolean duplicateExists = absenceDayRepository.existsByEmployeeIdAndAbsenceDateExcludingId(
                employeeId, absenceDate, id);
            
            if (duplicateExists) {
                throw new DuplicateResourceException(
                    "Absence already exists for employee ID " + employeeId + 
                    " on date " + absenceDate);
            }
        }
    }
}