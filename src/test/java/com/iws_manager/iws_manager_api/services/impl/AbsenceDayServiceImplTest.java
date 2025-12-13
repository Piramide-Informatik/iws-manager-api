package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.AbsenceDay;
import com.iws_manager.iws_manager_api.models.Employee;
import com.iws_manager.iws_manager_api.models.AbsenceType;
import com.iws_manager.iws_manager_api.repositories.AbsenceDayRepository;
import com.iws_manager.iws_manager_api.repositories.EmployeeRepository;
import com.iws_manager.iws_manager_api.repositories.AbsenceTypeRepository;
import com.iws_manager.iws_manager_api.exception.exceptions.DuplicateResourceException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbsenceDayServiceImplTest {

    @Mock
    private AbsenceDayRepository absenceDayRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private AbsenceTypeRepository absenceTypeRepository;

    @InjectMocks
    private AbsenceDayServiceImpl absenceDayService;

    private AbsenceDay absenceDay;
    private Employee employee;
    private AbsenceType absenceType;
    private LocalDate testDate;

    @BeforeEach
    void setUp() {
        testDate = LocalDate.of(2024, 1, 15);
        
        employee = new Employee();
        employee.setId(1L);
        
        absenceType = new AbsenceType();
        absenceType.setId(1L);
        
        absenceDay = new AbsenceDay();
        absenceDay.setId(1L);
        absenceDay.setAbsenceDate(testDate);
        absenceDay.setEmployee(employee);
        absenceDay.setAbsenceType(absenceType);
    }

    @Test
    void createShouldSaveAbsenceDay() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(absenceTypeRepository.findById(1L)).thenReturn(Optional.of(absenceType));
        when(absenceDayRepository.existsByEmployeeIdAndAbsenceDate(1L, testDate)).thenReturn(false);
        when(absenceDayRepository.save(any(AbsenceDay.class))).thenReturn(absenceDay);

        AbsenceDay result = absenceDayService.create(absenceDay);

        assertNotNull(result);
        assertEquals(absenceDay, result);
        verify(absenceDayRepository).save(any(AbsenceDay.class));
    }

    @Test
    void createShouldThrowWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> absenceDayService.create(null));
    }

    @Test
    void createShouldThrowWhenEmployeeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> absenceDayService.create(absenceDay));
    }

    @Test
    void createShouldThrowWhenAbsenceTypeNotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(absenceTypeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> absenceDayService.create(absenceDay));
    }

    @Test
    void createShouldThrowWhenDuplicateAbsence() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(absenceTypeRepository.findById(1L)).thenReturn(Optional.of(absenceType));
        when(absenceDayRepository.existsByEmployeeIdAndAbsenceDate(1L, testDate)).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> absenceDayService.create(absenceDay));
    }

    @Test
    void findByIdShouldReturnAbsenceDay() {
        when(absenceDayRepository.findById(1L)).thenReturn(Optional.of(absenceDay));

        Optional<AbsenceDay> result = absenceDayService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(absenceDay, result.get());
    }

    @Test
    void findByIdShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> absenceDayService.findById(null));
    }

    @Test
    void findAllShouldReturnList() {
        when(absenceDayRepository.findAll()).thenReturn(List.of(absenceDay));

        List<AbsenceDay> result = absenceDayService.findAll();

        assertEquals(1, result.size());
        assertEquals(absenceDay, result.get(0));
    }

    @Test
    void findAllShouldReturnEmptyList() {
        when(absenceDayRepository.findAll()).thenReturn(Collections.emptyList());

        List<AbsenceDay> result = absenceDayService.findAll();

        assertTrue(result.isEmpty());
    }

    @Test
    void updateShouldModifyAndSaveAbsenceDay() {
        // Arrange
        Long absenceDayId = 1L;
        Long employeeId = 1L;
        LocalDate originalDate = LocalDate.of(2024, 1, 15);
        LocalDate updatedDate = LocalDate.of(2024, 1, 16);
        
        // Existing absence day
        AbsenceDay existingAbsenceDay = new AbsenceDay();
        existingAbsenceDay.setId(absenceDayId);
        existingAbsenceDay.setAbsenceDate(originalDate);
        existingAbsenceDay.setEmployee(employee);
        existingAbsenceDay.setAbsenceType(absenceType);
        
        // Updated details
        AbsenceDay updatedDetails = new AbsenceDay();
        updatedDetails.setAbsenceDate(updatedDate);
        updatedDetails.setEmployee(employee);
        updatedDetails.setAbsenceType(absenceType);
        
        // Stubbing
        when(absenceDayRepository.findById(absenceDayId)).thenReturn(Optional.of(existingAbsenceDay));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(absenceTypeRepository.findById(1L)).thenReturn(Optional.of(absenceType));
        
        // IMPORTANTE: Usar el nuevo método en lugar de findAll()
        when(absenceDayRepository.existsByEmployeeIdAndAbsenceDateExcludingId(
            employeeId, updatedDate, absenceDayId)).thenReturn(false);
        
        when(absenceDayRepository.save(any(AbsenceDay.class))).thenReturn(existingAbsenceDay);

        // Act
        AbsenceDay result = absenceDayService.update(absenceDayId, updatedDetails);

        // Assert
        assertNotNull(result);
        assertEquals(updatedDate, result.getAbsenceDate());
        
        // Verificar interacciones
        verify(absenceDayRepository).findById(absenceDayId);
        verify(employeeRepository).findById(employeeId);
        verify(absenceTypeRepository).findById(1L);
        verify(absenceDayRepository).existsByEmployeeIdAndAbsenceDateExcludingId(
            employeeId, updatedDate, absenceDayId);
        verify(absenceDayRepository).save(any(AbsenceDay.class));
    }

    @Test
    void updateShouldModifyAndSaveAbsenceDayWhenOnlyTypeChanges() {
        // Arrange
        Long absenceDayId = 1L;
        Long employeeId = 1L;
        LocalDate sameDate = LocalDate.of(2024, 1, 15);
        
        // Existing absence day
        AbsenceDay existingAbsenceDay = new AbsenceDay();
        existingAbsenceDay.setId(absenceDayId);
        existingAbsenceDay.setAbsenceDate(sameDate);
        existingAbsenceDay.setEmployee(employee);
        existingAbsenceDay.setAbsenceType(absenceType);
        
        // New absence type
        AbsenceType newAbsenceType = new AbsenceType();
        newAbsenceType.setId(2L);
        
        // Updated details 
        AbsenceDay updatedDetails = new AbsenceDay();
        updatedDetails.setAbsenceDate(sameDate); // Same date
        updatedDetails.setEmployee(employee); // Same employee
        updatedDetails.setAbsenceType(newAbsenceType); // Only type changes
        
        AbsenceDay savedAbsenceDay = new AbsenceDay();
        savedAbsenceDay.setId(absenceDayId);
        savedAbsenceDay.setAbsenceDate(sameDate);
        savedAbsenceDay.setEmployee(employee);
        savedAbsenceDay.setAbsenceType(newAbsenceType);
        
        // Stubbing
        when(absenceDayRepository.findById(absenceDayId)).thenReturn(Optional.of(existingAbsenceDay));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(absenceTypeRepository.findById(2L)).thenReturn(Optional.of(newAbsenceType));
        when(absenceDayRepository.save(any(AbsenceDay.class))).thenReturn(savedAbsenceDay);

        // Act
        AbsenceDay result = absenceDayService.update(absenceDayId, updatedDetails);

        // Assert
        assertNotNull(result);
        assertEquals(sameDate, result.getAbsenceDate());
        assertEquals(newAbsenceType.getId(), result.getAbsenceType().getId());
        
        verify(absenceDayRepository).findById(absenceDayId);
        verify(employeeRepository).findById(employeeId);
        verify(absenceTypeRepository).findById(2L);
        verify(absenceDayRepository, never()).existsByEmployeeIdAndAbsenceDateExcludingId(
            anyLong(), any(LocalDate.class), anyLong());
        verify(absenceDayRepository).save(any(AbsenceDay.class));
    }

    @Test
    void updateShouldNotThrowWhenNoDuplicateAbsence() {
        // Arrange
        Long absenceDayId = 1L;
        Long employeeId = 1L;
        LocalDate newDate = LocalDate.of(2024, 1, 17);
        
        // Existing absence day
        AbsenceDay existingAbsenceDay = new AbsenceDay();
        existingAbsenceDay.setId(absenceDayId);
        existingAbsenceDay.setAbsenceDate(LocalDate.of(2024, 1, 15));
        existingAbsenceDay.setEmployee(employee);
        existingAbsenceDay.setAbsenceType(absenceType);
        
        // Updated details
        AbsenceDay updatedDetails = new AbsenceDay();
        updatedDetails.setAbsenceDate(newDate);
        updatedDetails.setEmployee(employee);
        updatedDetails.setAbsenceType(absenceType);
        
        AbsenceDay savedAbsenceDay = new AbsenceDay();
        savedAbsenceDay.setId(absenceDayId);
        savedAbsenceDay.setAbsenceDate(newDate);
        savedAbsenceDay.setEmployee(employee);
        savedAbsenceDay.setAbsenceType(absenceType);
        
        // Stubbing
        when(absenceDayRepository.findById(absenceDayId)).thenReturn(Optional.of(existingAbsenceDay));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(absenceTypeRepository.findById(1L)).thenReturn(Optional.of(absenceType));
        when(absenceDayRepository.existsByEmployeeIdAndAbsenceDateExcludingId(
            employeeId, newDate, absenceDayId)).thenReturn(false);
        when(absenceDayRepository.save(any(AbsenceDay.class))).thenReturn(savedAbsenceDay);

        // Act
        AbsenceDay result = absenceDayService.update(absenceDayId, updatedDetails);

        // Assert
        assertNotNull(result);
        assertEquals(newDate, result.getAbsenceDate());
        
        // Verificar interacciones
        verify(absenceDayRepository).findById(absenceDayId);
        verify(employeeRepository).findById(employeeId);
        verify(absenceTypeRepository).findById(1L);
        verify(absenceDayRepository).existsByEmployeeIdAndAbsenceDateExcludingId(
            employeeId, newDate, absenceDayId);
        verify(absenceDayRepository).save(any(AbsenceDay.class));
    }

    @Test
    void updateShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> absenceDayService.update(null, absenceDay));
    }

    @Test
    void updateShouldThrowWhenDetailsNull() {
        assertThrows(IllegalArgumentException.class, () -> absenceDayService.update(1L, null));
    }

    @Test
    void updateShouldThrowWhenNotFound() {
        when(absenceDayRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> absenceDayService.update(99L, absenceDay));
    }

   @Test
    void updateShouldThrowWhenDuplicateAbsence() {
        // Arrange
        Long absenceDayId = 1L;
        Long duplicateEmployeeId = 1L;
        LocalDate duplicateDate = LocalDate.of(2024, 1, 16);
        
        // Existing absence day to update
        AbsenceDay existingAbsenceDay = new AbsenceDay();
        existingAbsenceDay.setId(absenceDayId);
        existingAbsenceDay.setAbsenceDate(LocalDate.of(2024, 1, 15)); // Fecha original
        existingAbsenceDay.setEmployee(employee);
        existingAbsenceDay.setAbsenceType(absenceType);
        
        // Updated details with date that already exists for another absence
        AbsenceDay updatedDetails = new AbsenceDay();
        updatedDetails.setAbsenceDate(duplicateDate); // Cambia a fecha que ya existe
        updatedDetails.setEmployee(employee); // Mismo empleado
        updatedDetails.setAbsenceType(absenceType);
        
        // Stubbing
        when(absenceDayRepository.findById(absenceDayId)).thenReturn(Optional.of(existingAbsenceDay));
        when(employeeRepository.findById(duplicateEmployeeId)).thenReturn(Optional.of(employee));
        when(absenceTypeRepository.findById(1L)).thenReturn(Optional.of(absenceType));
        
        // Simula que existe otra ausencia con la misma fecha para el mismo empleado
        when(absenceDayRepository.existsByEmployeeIdAndAbsenceDateExcludingId(
            duplicateEmployeeId, duplicateDate, absenceDayId)).thenReturn(true);

        // Act & Assert
        DuplicateResourceException exception = assertThrows(
            DuplicateResourceException.class, 
            () -> absenceDayService.update(absenceDayId, updatedDetails)
        );
        
        // Verificar el mensaje de la excepción
        assertTrue(exception.getMessage().contains("Absence already exists for employee ID"));
        assertTrue(exception.getMessage().contains("on date " + duplicateDate));
        
        // Verificar interacciones con los mocks
        verify(absenceDayRepository).findById(absenceDayId);
        verify(employeeRepository).findById(duplicateEmployeeId);
        verify(absenceTypeRepository).findById(1L);
        verify(absenceDayRepository).existsByEmployeeIdAndAbsenceDateExcludingId(
            duplicateEmployeeId, duplicateDate, absenceDayId);
        
        // Verificar que save NO fue llamado
        verify(absenceDayRepository, never()).save(any(AbsenceDay.class));
    }

    @Test
    void deleteShouldRemoveAbsenceDay() {
        when(absenceDayRepository.existsById(1L)).thenReturn(true);
        
        absenceDayService.delete(1L);
        
        verify(absenceDayRepository).deleteById(1L);
    }

    @Test
    void deleteShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> absenceDayService.delete(null));
    }

    @Test
    void deleteShouldThrowWhenNotFound() {
        when(absenceDayRepository.existsById(99L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> absenceDayService.delete(99L));
    }

    @Test
    void getByEmployeeIdShouldReturnList() {
        when(absenceDayRepository.findByEmployeeId(1L)).thenReturn(List.of(absenceDay));

        List<AbsenceDay> result = absenceDayService.getByEmployeeId(1L);

        assertEquals(1, result.size());
        assertEquals(absenceDay, result.get(0));
    }

    @Test
    void getByEmployeeIdShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> absenceDayService.getByEmployeeId(null));
    }

    @Test
    void getByEmployeeIdAndDateRangeShouldReturnList() {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 31);
        
        when(absenceDayRepository.findByEmployeeIdAndAbsenceDateBetween(1L, startDate, endDate))
                .thenReturn(List.of(absenceDay));

        List<AbsenceDay> result = absenceDayService.getByEmployeeIdAndDateRange(1L, startDate, endDate);

        assertEquals(1, result.size());
        assertEquals(absenceDay, result.get(0));
    }

    @Test
    void getByEmployeeIdAndDateRangeShouldThrowWhenEmployeeIdNull() {
        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayService.getByEmployeeIdAndDateRange(null, LocalDate.now(), LocalDate.now()));
    }

    @Test
    void getByEmployeeIdAndDateRangeShouldThrowWhenStartDateNull() {
        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayService.getByEmployeeIdAndDateRange(1L, null, LocalDate.now()));
    }

    @Test
    void getByEmployeeIdAndDateRangeShouldThrowWhenEndDateNull() {
        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayService.getByEmployeeIdAndDateRange(1L, LocalDate.now(), null));
    }

    @Test
    void getByEmployeeIdAndDateRangeShouldThrowWhenStartDateAfterEndDate() {
        LocalDate startDate = LocalDate.of(2024, 1, 31);
        LocalDate endDate = LocalDate.of(2024, 1, 1);
        
        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayService.getByEmployeeIdAndDateRange(1L, startDate, endDate));
    }

    @Test
    void getByEmployeeIdAndAbsenceTypeIdShouldReturnList() {
        when(absenceDayRepository.findByEmployeeIdAndAbsenceTypeId(1L, 1L))
                .thenReturn(List.of(absenceDay));

        List<AbsenceDay> result = absenceDayService.getByEmployeeIdAndAbsenceTypeId(1L, 1L);

        assertEquals(1, result.size());
        assertEquals(absenceDay, result.get(0));
    }

    @Test
    void getByEmployeeIdAndAbsenceTypeIdShouldThrowWhenEmployeeIdNull() {
        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayService.getByEmployeeIdAndAbsenceTypeId(null, 1L));
    }

    @Test
    void getByEmployeeIdAndAbsenceTypeIdShouldThrowWhenAbsenceTypeIdNull() {
        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayService.getByEmployeeIdAndAbsenceTypeId(1L, null));
    }

    @Test
    void existsByEmployeeIdAndAbsenceDateShouldReturnTrue() {
        when(absenceDayRepository.existsByEmployeeIdAndAbsenceDate(1L, testDate)).thenReturn(true);

        boolean result = absenceDayService.existsByEmployeeIdAndAbsenceDate(1L, testDate);

        assertTrue(result);
    }

    @Test
    void existsByEmployeeIdAndAbsenceDateShouldReturnFalse() {
        when(absenceDayRepository.existsByEmployeeIdAndAbsenceDate(1L, testDate)).thenReturn(false);

        boolean result = absenceDayService.existsByEmployeeIdAndAbsenceDate(1L, testDate);

        assertFalse(result);
    }

    @Test
    void existsByEmployeeIdAndAbsenceDateShouldThrowWhenEmployeeIdNull() {
        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayService.existsByEmployeeIdAndAbsenceDate(null, testDate));
    }

    @Test
    void existsByEmployeeIdAndAbsenceDateShouldThrowWhenAbsenceDateNull() {
        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayService.existsByEmployeeIdAndAbsenceDate(1L, null));
    }
}