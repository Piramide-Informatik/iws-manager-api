package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.dtos.absenceday.AbsenceDayFilterDTO;
import com.iws_manager.iws_manager_api.dtos.absenceday.AbsenceDayRequestDTO;
import com.iws_manager.iws_manager_api.mappers.AbsenceDayMapper;
import com.iws_manager.iws_manager_api.models.AbsenceDay;
import com.iws_manager.iws_manager_api.models.Employee;
import com.iws_manager.iws_manager_api.models.AbsenceType;
import com.iws_manager.iws_manager_api.models.PublicHoliday;
import com.iws_manager.iws_manager_api.repositories.PublicHolidayRepository;
import com.iws_manager.iws_manager_api.repositories.AbsenceDayRepository;
import com.iws_manager.iws_manager_api.repositories.EmployeeRepository;
import com.iws_manager.iws_manager_api.repositories.AbsenceTypeRepository;
import com.iws_manager.iws_manager_api.services.interfaces.AbsenceDayServiceV2;
import com.iws_manager.iws_manager_api.exception.exceptions.DuplicateResourceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AbsenceDayServiceV2Impl implements AbsenceDayServiceV2 {
    private static final String EMPLOYEE = "Employee not found with id: ";
    private static final String ABSENCE_TYPE = "AbsenceType not found with id: ";

    private final AbsenceDayRepository absenceDayRepository;
    private final EmployeeRepository employeeRepository;
    private final AbsenceTypeRepository absenceTypeRepository;
    private final PublicHolidayRepository publicHolidayRepository;

    @Autowired
    public AbsenceDayServiceV2Impl(
            AbsenceDayRepository absenceDayRepository,
            EmployeeRepository employeeRepository,
            AbsenceTypeRepository absenceTypeRepository,
            PublicHolidayRepository publicHolidayRepository) {
        this.absenceDayRepository = absenceDayRepository;
        this.employeeRepository = employeeRepository;
        this.absenceTypeRepository = absenceTypeRepository;
        this.publicHolidayRepository = publicHolidayRepository;
    }

    @Override
    public AbsenceDay createFromDTO(AbsenceDayRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("AbsenceDayRequestDTO cannot be null");
        }

        if (requestDTO.absenceDate() == null) {
            throw new IllegalArgumentException("Absence date must be specified");
        }
        
        if (requestDTO.employee() == null || requestDTO.employee().id() == null) {
            throw new IllegalArgumentException("Employee must be specified");
        }
        
        if (requestDTO.absenceType() == null || requestDTO.absenceType().id() == null) {
            throw new IllegalArgumentException("AbsenceType must be specified");
        }

        validateNotPublicHoliday(requestDTO.absenceDate());

        if (absenceDayRepository.existsByEmployeeIdAndAbsenceDate(
                requestDTO.employee().id(), requestDTO.absenceDate())) {
            throw new DuplicateResourceException(
                "Absence already exists for employee ID " + requestDTO.employee().id() + 
                " on date " + requestDTO.absenceDate());
        }

        // Load related entities
        Employee employee = employeeRepository.findById(requestDTO.employee().id())
                .orElseThrow(() -> new EntityNotFoundException(
                        EMPLOYEE + requestDTO.employee().id()));
        
        AbsenceType absenceType = absenceTypeRepository.findById(requestDTO.absenceType().id())
                .orElseThrow(() -> new EntityNotFoundException(
                        ABSENCE_TYPE + requestDTO.absenceType().id()));

        // Create entity
        AbsenceDay absenceDay = new AbsenceDay();
        absenceDay.setAbsenceDate(requestDTO.absenceDate());
        absenceDay.setEmployee(employee);
        absenceDay.setAbsenceType(absenceType);

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
    public AbsenceDay updateFromDTO(Long id, AbsenceDayRequestDTO requestDTO) {
        if (id == null || requestDTO == null) {
            throw new IllegalArgumentException("ID and AbsenceDayRequestDTO cannot be null");
        }

        return absenceDayRepository.findById(id)
                .map(existingAbsenceDay -> {
                    // Validate changes
                    validateUpdateFromDTO(existingAbsenceDay, requestDTO, id);
                    
                    // Update basic fields
                    if (requestDTO.absenceDate() != null) {
                        existingAbsenceDay.setAbsenceDate(requestDTO.absenceDate());
                    }
                    
                    // Update relationships if provided
                    if (requestDTO.employee() != null && requestDTO.employee().id() != null) {
                        Employee employee = employeeRepository.findById(requestDTO.employee().id())
                                .orElseThrow(() -> new EntityNotFoundException(
                                        EMPLOYEE + requestDTO.employee().id()));
                        existingAbsenceDay.setEmployee(employee);
                    }
                    
                    if (requestDTO.absenceType() != null && requestDTO.absenceType().id() != null) {
                        AbsenceType absenceType = absenceTypeRepository.findById(requestDTO.absenceType().id())
                                .orElseThrow(() -> new EntityNotFoundException(
                                        ABSENCE_TYPE + requestDTO.absenceType().id()));
                        existingAbsenceDay.setAbsenceType(absenceType);
                    }

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

    @Override
    @Transactional(readOnly = true)
    public List<AbsenceDay> getByEmployeeIdAndYear(Long employeeId, int year) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        
        if (year <= 0) {
            throw new IllegalArgumentException("Year must be a positive number");
        }
        
        return absenceDayRepository.findByEmployeeIdAndYear(employeeId, year);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByEmployeeIdAndAbsenceTypeIdAndYear(Long employeeId, Long absenceTypeId, int year) {
        if (employeeId == null || absenceTypeId == null) {
            throw new IllegalArgumentException("Employee ID and AbsenceType ID cannot be null");
        }
        
        if (year <= 0) {
            throw new IllegalArgumentException("Year must be a positive number");
        }
        
        return absenceDayRepository.countByEmployeeIdAndAbsenceTypeIdAndYear(employeeId, absenceTypeId, year);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> countAbsenceDaysByTypeForEmployee(Long employeeId) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        return absenceDayRepository.countAbsenceDaysByTypeForEmployee(employeeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbsenceDay> filter(AbsenceDayFilterDTO filterDTO) {
        if (filterDTO == null) {
            throw new IllegalArgumentException("FilterDTO cannot be null");
        }

        // Validate that at least employeeId is present
        if (filterDTO.employeeId() == null) {
            throw new IllegalArgumentException("At least employeeId must be specified for filtering");
        }

        // Filtering logic based on parameters
        if (filterDTO.startDate() != null && filterDTO.endDate() != null) {
            return getByEmployeeIdAndDateRange(filterDTO.employeeId(), filterDTO.startDate(), filterDTO.endDate());
        } else if (filterDTO.year() != null) {
            return getByEmployeeIdAndYear(filterDTO.employeeId(), filterDTO.year());
        } else if (filterDTO.absenceTypeId() != null) {
            return getByEmployeeIdAndAbsenceTypeId(filterDTO.employeeId(), filterDTO.absenceTypeId());
        } else {
            return getByEmployeeId(filterDTO.employeeId());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> countAbsenceDaysByTypeForEmployeeAndYear(Long employeeId, int year) {
        if (employeeId == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        
        if (year <= 0) {
            throw new IllegalArgumentException("Year must be a positive number");
        }
        
        return absenceDayRepository.countAbsenceDaysByTypeForEmployeeAndYear(employeeId, year);
    }

    @Override
    @Transactional
    public List<AbsenceDay> createBulkFromDTO(List<AbsenceDayRequestDTO> requestDTOs) {
        if (requestDTOs == null || requestDTOs.isEmpty()) {
            throw new IllegalArgumentException("Absence list cannot be null or empty");
        }

        List<AbsenceDay> absencesToSave = new ArrayList<>();

        for (AbsenceDayRequestDTO dto : requestDTOs) {

            if (dto.absenceDate() == null) {
                throw new IllegalArgumentException("Absence date must be specified");
            }

            if (dto.employee() == null || dto.employee().id() == null) {
                throw new IllegalArgumentException("Employee must be specified");
            }

            if (dto.absenceType() == null || dto.absenceType().id() == null) {
                throw new IllegalArgumentException("AbsenceType must be specified");
            }

            // Validate holiday
            validateNotPublicHoliday(dto.absenceDate());

            // Validate duplicates
            if (absenceDayRepository.existsByEmployeeIdAndAbsenceDate(
                    dto.employee().id(), dto.absenceDate())) {
                throw new DuplicateResourceException(
                        "Absence already exists for employee ID " + dto.employee().id() +
                                " on date " + dto.absenceDate());
            }

            Employee employee = employeeRepository.findById(dto.employee().id())
                    .orElseThrow(() -> new EntityNotFoundException(
                            EMPLOYEE + dto.employee().id()));

            AbsenceType absenceType = absenceTypeRepository.findById(dto.absenceType().id())
                    .orElseThrow(() -> new EntityNotFoundException(
                            ABSENCE_TYPE + dto.absenceType().id()));

            AbsenceDay absenceDay = new AbsenceDay();
            absenceDay.setAbsenceDate(dto.absenceDate());
            absenceDay.setEmployee(employee);
            absenceDay.setAbsenceType(absenceType);

            absencesToSave.add(absenceDay);
        }

        // Mass saving
        return absenceDayRepository.saveAll(absencesToSave);
    }

    // ========== Private methods ==========
    
    private void validateNotPublicHoliday(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        
        if (publicHolidayRepository.existsByDate(date)) {
            Optional<PublicHoliday> holiday = publicHolidayRepository.findByDate(date);
            String holidayName = holiday.map(PublicHoliday::getName).orElse("Public Holiday");
            
            throw new IllegalArgumentException(
                String.format("Cannot create absence on public holiday: %s (%s)", 
                    date, holidayName));
        }
    }

    private void validateUpdateFromDTO(AbsenceDay existingAbsenceDay, AbsenceDayRequestDTO requestDTO, Long id) {
        // Determine the employeeId to use
        Long employeeId = requestDTO.employee() != null && requestDTO.employee().id() != null
                ? requestDTO.employee().id()
                : existingAbsenceDay.getEmployee().getId();
        
        // Determine the date to use
        LocalDate absenceDate = requestDTO.absenceDate() != null
                ? requestDTO.absenceDate()
                : existingAbsenceDay.getAbsenceDate();
        
        // Verify if the date changed
        boolean dateChanged = requestDTO.absenceDate() != null && 
                !existingAbsenceDay.getAbsenceDate().equals(requestDTO.absenceDate());
        
        // Verify if the employee changed
        boolean employeeChanged = requestDTO.employee() != null && 
                requestDTO.employee().id() != null &&
                !existingAbsenceDay.getEmployee().getId().equals(requestDTO.employee().id());

        // If the date changed, validate that it's not a public holiday
        if (dateChanged) {
            validateNotPublicHoliday(absenceDate);
        }

        // Verify duplicates if date or employee changed
        if (dateChanged || employeeChanged) {
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