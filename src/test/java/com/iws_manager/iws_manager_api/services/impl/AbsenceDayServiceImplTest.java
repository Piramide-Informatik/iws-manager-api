package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.dtos.absenceday.AbsenceDayFilterDTO;
import com.iws_manager.iws_manager_api.dtos.absenceday.AbsenceDayRequestDTO;
import com.iws_manager.iws_manager_api.dtos.shared.BasicReferenceDTO;
import com.iws_manager.iws_manager_api.models.AbsenceDay;
import com.iws_manager.iws_manager_api.models.Employee;
import com.iws_manager.iws_manager_api.models.AbsenceType;
import com.iws_manager.iws_manager_api.models.PublicHoliday;
import com.iws_manager.iws_manager_api.repositories.AbsenceDayRepository;
import com.iws_manager.iws_manager_api.repositories.EmployeeRepository;
import com.iws_manager.iws_manager_api.repositories.AbsenceTypeRepository;
import com.iws_manager.iws_manager_api.repositories.PublicHolidayRepository;
import com.iws_manager.iws_manager_api.exception.exceptions.DuplicateResourceException;
import  com.iws_manager.iws_manager_api.services.v2.impl.AbsenceDayServiceV2Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbsenceDayServiceImplTest {

    @Mock
    private AbsenceDayRepository absenceDayRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AbsenceTypeRepository absenceTypeRepository;

    @Mock
    private PublicHolidayRepository publicHolidayRepository;

    @InjectMocks
    private AbsenceDayServiceV2Impl absenceDayServiceV2;

    private AbsenceDay absenceDay;
    private Employee employee;
    private AbsenceType absenceType;
    private LocalDate testDate;
    private AbsenceDayRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        testDate = LocalDate.of(2024, 1, 15);
        
        employee = new Employee();
        employee.setId(1L);
        employee.setVersion(1);
        employee.setEmployeeno(1001);
        employee.setFirstname("Ana");
        employee.setLastname("Müller");
        employee.setLabel("Desarrolladora Senior");
        
        absenceType = new AbsenceType();
        absenceType.setId(1L);
        absenceType.setVersion(0);
        absenceType.setName("Vacaciones");
        absenceType.setLabel("VAC");
        absenceType.setHours((byte) 8);
        absenceType.setIsHoliday((byte) 0);
        absenceType.setShareOfDay(new BigDecimal("1.0"));
        
        absenceDay = new AbsenceDay();
        absenceDay.setId(1L);
        absenceDay.setAbsenceDate(testDate);
        absenceDay.setEmployee(employee);
        absenceDay.setAbsenceType(absenceType);
        
        // Crear RequestDTO para tests
        requestDTO = new AbsenceDayRequestDTO(
            testDate,
            new BasicReferenceDTO(1L, 0),
            new BasicReferenceDTO(1L, 1)
        );
    }

    @Test
    void createFromDTO_ShouldSaveAbsenceDay_WhenValidInput() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(absenceTypeRepository.findById(1L)).thenReturn(Optional.of(absenceType));
        when(publicHolidayRepository.existsByDate(testDate)).thenReturn(false);
        when(absenceDayRepository.existsByEmployeeIdAndAbsenceDate(1L, testDate)).thenReturn(false);
        when(absenceDayRepository.save(any(AbsenceDay.class))).thenReturn(absenceDay);

        // Act
        AbsenceDay result = absenceDayServiceV2.createFromDTO(requestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testDate, result.getAbsenceDate());
        assertEquals(employee.getId(), result.getEmployee().getId());
        assertEquals(absenceType.getId(), result.getAbsenceType().getId());
        
        verify(employeeRepository).findById(1L);
        verify(absenceTypeRepository).findById(1L);
        verify(publicHolidayRepository).existsByDate(testDate);
        verify(absenceDayRepository).existsByEmployeeIdAndAbsenceDate(1L, testDate);
        verify(absenceDayRepository).save(any(AbsenceDay.class));
    }

    @Test
    void createFromDTO_ShouldThrow_WhenRequestDTONull() {
        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayServiceV2.createFromDTO(null));
    }

    @Test
    void createFromDTO_ShouldThrow_WhenAbsenceDateNull() {
        AbsenceDayRequestDTO invalidDTO = new AbsenceDayRequestDTO(
            null,
            new BasicReferenceDTO(1L, 0),
            new BasicReferenceDTO(1L, 1)
        );
        
        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayServiceV2.createFromDTO(invalidDTO));
    }

    @Test
    void createFromDTO_ShouldThrow_WhenEmployeeNotSpecified() {
        AbsenceDayRequestDTO invalidDTO = new AbsenceDayRequestDTO(
            testDate,
            new BasicReferenceDTO(1L, 0),
            null // Employee null
        );
        
        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayServiceV2.createFromDTO(invalidDTO));
    }

    @Test
    void createFromDTO_ShouldThrow_WhenAbsenceTypeNotSpecified() {
        AbsenceDayRequestDTO invalidDTO = new AbsenceDayRequestDTO(
            testDate,
            null, // AbsenceType null
            new BasicReferenceDTO(1L, 1)
        );
        
        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayServiceV2.createFromDTO(invalidDTO));
    }

    @Test
    void createFromDTO_ShouldThrow_WhenEmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> 
            absenceDayServiceV2.createFromDTO(requestDTO));
    }

    @Test
    void createFromDTO_ShouldThrow_WhenAbsenceTypeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(absenceTypeRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> 
            absenceDayServiceV2.createFromDTO(requestDTO));
    }

    @Test
    void createFromDTO_ShouldThrow_WhenDateIsPublicHoliday() {
        // Arrange
        lenient().when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        lenient().when(absenceTypeRepository.findById(1L)).thenReturn(Optional.of(absenceType));
        
        when(publicHolidayRepository.existsByDate(testDate)).thenReturn(true);
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> absenceDayServiceV2.createFromDTO(requestDTO)
        );
        
        assertTrue(exception.getMessage().contains("public holiday"));
        verify(publicHolidayRepository).existsByDate(testDate);
    }

    @Test
    void createFromDTO_ShouldThrow_WhenDuplicateAbsence() {
        // Arrange
        lenient().when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        lenient().when(absenceTypeRepository.findById(1L)).thenReturn(Optional.of(absenceType));
        lenient().when(publicHolidayRepository.existsByDate(testDate)).thenReturn(false);
        
        when(absenceDayRepository.existsByEmployeeIdAndAbsenceDate(1L, testDate)).thenReturn(true);
        
        assertThrows(DuplicateResourceException.class, () -> 
            absenceDayServiceV2.createFromDTO(requestDTO));
        
        verify(absenceDayRepository).existsByEmployeeIdAndAbsenceDate(1L, testDate);
    }
    
    @Test
    void updateFromDTO_ShouldUpdateAbsenceDay_WhenValidInput() {
        // Arrange
        Long id = 1L;
        LocalDate newDate = LocalDate.of(2024, 1, 16);
        AbsenceDayRequestDTO updateDTO = new AbsenceDayRequestDTO(
            newDate,
            new BasicReferenceDTO(1L, 0),
            new BasicReferenceDTO(1L, 1)
        );
        
        when(absenceDayRepository.findById(id)).thenReturn(Optional.of(absenceDay));
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(absenceTypeRepository.findById(1L)).thenReturn(Optional.of(absenceType));
        when(publicHolidayRepository.existsByDate(newDate)).thenReturn(false);
        when(absenceDayRepository.existsByEmployeeIdAndAbsenceDateExcludingId(1L, newDate, id))
            .thenReturn(false);
        when(absenceDayRepository.save(any(AbsenceDay.class))).thenReturn(absenceDay);

        // Act
        AbsenceDay result = absenceDayServiceV2.updateFromDTO(id, updateDTO);

        // Assert
        assertNotNull(result);
        verify(absenceDayRepository).findById(id);
        verify(employeeRepository).findById(1L);
        verify(absenceTypeRepository).findById(1L);
        verify(publicHolidayRepository).existsByDate(newDate);
        verify(absenceDayRepository).save(any(AbsenceDay.class));
    }

    @Test
    void updateFromDTO_ShouldThrow_WhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayServiceV2.updateFromDTO(null, requestDTO));
    }

    @Test
    void updateFromDTO_ShouldThrow_WhenRequestDTONull() {
        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayServiceV2.updateFromDTO(1L, null));
    }

    @Test
    void updateFromDTO_ShouldThrow_WhenNotFound() {
        when(absenceDayRepository.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(EntityNotFoundException.class, () -> 
            absenceDayServiceV2.updateFromDTO(99L, requestDTO));
    }

    @Test
    void updateFromDTO_ShouldUpdateOnlyDate_WhenOtherFieldsNull() {
        // Arrange
        Long id = 1L;
        LocalDate newDate = LocalDate.of(2024, 1, 16);
        AbsenceDayRequestDTO updateDTO = new AbsenceDayRequestDTO(
            newDate,
            null, // No change to absenceType
            null  // No change to employee
        );
        
        when(absenceDayRepository.findById(id)).thenReturn(Optional.of(absenceDay));
        when(publicHolidayRepository.existsByDate(newDate)).thenReturn(false);
        when(absenceDayRepository.existsByEmployeeIdAndAbsenceDateExcludingId(1L, newDate, id))
            .thenReturn(false);
        when(absenceDayRepository.save(any(AbsenceDay.class))).thenReturn(absenceDay);

        // Act
        AbsenceDay result = absenceDayServiceV2.updateFromDTO(id, updateDTO);

        // Assert
        assertNotNull(result);
        verify(absenceDayRepository).findById(id);
        verify(employeeRepository, never()).findById(anyLong()); // Should not be called
        verify(absenceTypeRepository, never()).findById(anyLong()); // Should not be called
        verify(publicHolidayRepository).existsByDate(newDate);
        verify(absenceDayRepository).save(any(AbsenceDay.class));
    }

    @Test
    void filter_ShouldReturnFilteredList_WhenDateRangeProvided() {
        // Arrange
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        AbsenceDayFilterDTO filterDTO = new AbsenceDayFilterDTO(
            1L,  // employeeId
            null, // absenceTypeId
            startDate,
            endDate,
            2024, // year
            false // includePublicHolidays
        );
        
        when(absenceDayRepository.findByEmployeeIdAndAbsenceDateBetween(1L, startDate, endDate))
            .thenReturn(List.of(absenceDay));

        // Act
        List<AbsenceDay> result = absenceDayServiceV2.filter(filterDTO);

        // Assert
        assertEquals(1, result.size());
        assertEquals(absenceDay, result.get(0));
        verify(absenceDayRepository).findByEmployeeIdAndAbsenceDateBetween(1L, startDate, endDate);
    }

    @Test
    void filter_ShouldReturnFilteredList_WhenYearProvided() {
        // Arrange
        AbsenceDayFilterDTO filterDTO = new AbsenceDayFilterDTO(
            1L,  // employeeId
            null, // absenceTypeId
            null, // startDate
            null, // endDate
            2024, // year
            false // includePublicHolidays
        );
        
        when(absenceDayRepository.findByEmployeeIdAndYear(1L, 2024))
            .thenReturn(List.of(absenceDay));

        // Act
        List<AbsenceDay> result = absenceDayServiceV2.filter(filterDTO);

        // Assert
        assertEquals(1, result.size());
        assertEquals(absenceDay, result.get(0));
        verify(absenceDayRepository).findByEmployeeIdAndYear(1L, 2024);
    }

    @Test
    void filter_ShouldReturnFilteredList_WhenAbsenceTypeIdProvided() {
        // Arrange
        AbsenceDayFilterDTO filterDTO = new AbsenceDayFilterDTO(
            1L,     // employeeId
            1L,     // absenceTypeId
            null,   // startDate
            null,   // endDate
            null,   // year
            false   // includePublicHolidays
        );
        
        when(absenceDayRepository.findByEmployeeIdAndAbsenceTypeId(1L, 1L))
            .thenReturn(List.of(absenceDay));

        // Act
        List<AbsenceDay> result = absenceDayServiceV2.filter(filterDTO);

        // Assert
        assertEquals(1, result.size());
        assertEquals(absenceDay, result.get(0));
        verify(absenceDayRepository).findByEmployeeIdAndAbsenceTypeId(1L, 1L);
    }

    @Test
    void filter_ShouldReturnFilteredList_WhenOnlyEmployeeIdProvided() {
        // Arrange
        AbsenceDayFilterDTO filterDTO = new AbsenceDayFilterDTO(
            1L,     // employeeId
            null,   // absenceTypeId
            null,   // startDate
            null,   // endDate
            null,   // year
            false   // includePublicHolidays
        );
        
        when(absenceDayRepository.findByEmployeeId(1L))
            .thenReturn(List.of(absenceDay));

        // Act
        List<AbsenceDay> result = absenceDayServiceV2.filter(filterDTO);

        // Assert
        assertEquals(1, result.size());
        assertEquals(absenceDay, result.get(0));
        verify(absenceDayRepository).findByEmployeeId(1L);
    }

    @Test
    void filter_ShouldThrow_WhenFilterDTONull() {
        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayServiceV2.filter(null));
    }

    @Test
    void filter_ShouldThrow_WhenEmployeeIdNull() {
        AbsenceDayFilterDTO filterDTO = new AbsenceDayFilterDTO(
            null,   // employeeId (null)
            1L,     // absenceTypeId
            null,   // startDate
            null,   // endDate
            2024,   // year
            false   // includePublicHolidays
        );
        
        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayServiceV2.filter(filterDTO));
    }   
    
    @Test
    void countAbsenceDaysByTypeForEmployeeAndYear_ShouldReturnCounts() {
        // Arrange
        Long employeeId = 1L;
        int year = 2024;
        Object[] result1 = new Object[]{absenceType, 2L};
        Object[] result2 = new Object[]{absenceType, 3L};
        
        when(absenceDayRepository.countAbsenceDaysByTypeForEmployeeAndYear(employeeId, year))
            .thenReturn(List.of(result1, result2));

        // Act
        List<Object[]> results = absenceDayServiceV2.countAbsenceDaysByTypeForEmployeeAndYear(employeeId, year);

        // Assert
        assertEquals(2, results.size());
        verify(absenceDayRepository).countAbsenceDaysByTypeForEmployeeAndYear(employeeId, year);
    }

    @Test
    void countAbsenceDaysByTypeForEmployeeAndYear_ShouldThrow_WhenEmployeeIdNull() {
        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayServiceV2.countAbsenceDaysByTypeForEmployeeAndYear(null, 2024));
    }

    @Test
    void countAbsenceDaysByTypeForEmployeeAndYear_ShouldThrow_WhenYearInvalid() {
        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayServiceV2.countAbsenceDaysByTypeForEmployeeAndYear(1L, 0));
        
        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayServiceV2.countAbsenceDaysByTypeForEmployeeAndYear(1L, -1));
    }

    @Test
    void findById_ShouldReturnAbsenceDay() {
        when(absenceDayRepository.findById(1L)).thenReturn(Optional.of(absenceDay));

        Optional<AbsenceDay> result = absenceDayServiceV2.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(absenceDay, result.get());
    }

    @Test
    void findAll_ShouldReturnList() {
        when(absenceDayRepository.findAll()).thenReturn(List.of(absenceDay));

        List<AbsenceDay> result = absenceDayServiceV2.findAll();

        assertEquals(1, result.size());
        assertEquals(absenceDay, result.get(0));
    }

    @Test
    void getByEmployeeId_ShouldReturnList() {
        when(absenceDayRepository.findByEmployeeId(1L)).thenReturn(List.of(absenceDay));

        List<AbsenceDay> result = absenceDayServiceV2.getByEmployeeId(1L);

        assertEquals(1, result.size());
        assertEquals(absenceDay, result.get(0));
    }

    @Test
    void existsByEmployeeIdAndAbsenceDate_ShouldReturnTrue() {
        when(absenceDayRepository.existsByEmployeeIdAndAbsenceDate(1L, testDate)).thenReturn(true);

        boolean result = absenceDayServiceV2.existsByEmployeeIdAndAbsenceDate(1L, testDate);

        assertTrue(result);
    }

    @Test
    void countByEmployeeIdAndAbsenceTypeIdAndYear_ShouldReturnCount() {
        when(absenceDayRepository.countByEmployeeIdAndAbsenceTypeIdAndYear(1L, 1L, 2024))
            .thenReturn(5L);

        long result = absenceDayServiceV2.countByEmployeeIdAndAbsenceTypeIdAndYear(1L, 1L, 2024);

        assertEquals(5L, result);
    }

    @Test
    void delete_ShouldDeleteAbsenceDay() {
        when(absenceDayRepository.existsById(1L)).thenReturn(true);
        
        absenceDayServiceV2.delete(1L);
        
        verify(absenceDayRepository).deleteById(1L);
    }
}