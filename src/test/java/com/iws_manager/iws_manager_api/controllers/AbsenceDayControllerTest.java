package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.AbsenceDay;
import com.iws_manager.iws_manager_api.models.Employee;
import com.iws_manager.iws_manager_api.models.AbsenceType;
import com.iws_manager.iws_manager_api.services.interfaces.AbsenceDayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import jakarta.persistence.EntityNotFoundException;

class AbsenceDayControllerTest {

    private static final Long ABSENCE_DAY_ID = 1L;
    private static final Long EMPLOYEE_ID = 10L;
    private static final Long ABSENCE_TYPE_ID = 20L;
    private static final LocalDate TEST_DATE = LocalDate.of(2024, 1, 15);
    private static final LocalDate START_DATE = LocalDate.of(2024, 1, 1);
    private static final LocalDate END_DATE = LocalDate.of(2024, 1, 31);

    @Mock
    private AbsenceDayService absenceDayService;

    @InjectMocks
    private AbsenceDayController absenceDayController;

    private AbsenceDay absenceDay;
    private Employee employee = new Employee();
    private AbsenceType absenceType = new AbsenceType();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        employee.setId(EMPLOYEE_ID);        
        absenceType.setId(ABSENCE_TYPE_ID);
        
        absenceDay = new AbsenceDay();
        absenceDay.setId(ABSENCE_DAY_ID);
        absenceDay.setAbsenceDate(TEST_DATE);
        absenceDay.setEmployee(employee);
        absenceDay.setAbsenceType(absenceType);
    }

    @Test
    void createReturnsCreatedAbsenceDay() {
        when(absenceDayService.create(absenceDay)).thenReturn(absenceDay);

        ResponseEntity<AbsenceDay> response = absenceDayController.create(absenceDay);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(absenceDay, response.getBody());
        verify(absenceDayService, times(1)).create(absenceDay);
    }

    @Test
    void getByIdFound() {
        when(absenceDayService.findById(ABSENCE_DAY_ID)).thenReturn(Optional.of(absenceDay));

        ResponseEntity<AbsenceDay> response = absenceDayController.getById(ABSENCE_DAY_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(absenceDay, response.getBody());
    }

    @Test
    void getByIdNotFound() {
        when(absenceDayService.findById(ABSENCE_DAY_ID)).thenReturn(Optional.empty());

        ResponseEntity<AbsenceDay> response = absenceDayController.getById(ABSENCE_DAY_ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllReturnsList() {
        List<AbsenceDay> list = Arrays.asList(absenceDay);
        when(absenceDayService.findAll()).thenReturn(list);

        ResponseEntity<List<AbsenceDay>> response = absenceDayController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getAllReturnsEmptyList() {
        when(absenceDayService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<AbsenceDay>> response = absenceDayController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void updateFound() {
        when(absenceDayService.update(ABSENCE_DAY_ID, absenceDay)).thenReturn(absenceDay);

        ResponseEntity<AbsenceDay> response = absenceDayController.update(ABSENCE_DAY_ID, absenceDay);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(absenceDay, response.getBody());
        verify(absenceDayService, times(1)).update(ABSENCE_DAY_ID, absenceDay);
    }

    @Test
    void updateNotFound() {
        when(absenceDayService.update(ABSENCE_DAY_ID, absenceDay))
            .thenThrow(new EntityNotFoundException("AbsenceDay not found with id: " + ABSENCE_DAY_ID));

        assertThrows(EntityNotFoundException.class, () -> 
            absenceDayController.update(ABSENCE_DAY_ID, absenceDay));
        
        verify(absenceDayService, times(1)).update(ABSENCE_DAY_ID, absenceDay);
    }

    @Test
    void deleteSuccess() {
        ResponseEntity<Void> response = absenceDayController.delete(ABSENCE_DAY_ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(absenceDayService, times(1)).delete(ABSENCE_DAY_ID);
    }

    @Test
    void deleteNotFound() {
        doThrow(new EntityNotFoundException("AbsenceDay not found with id: " + ABSENCE_DAY_ID))
            .when(absenceDayService).delete(ABSENCE_DAY_ID);

        assertThrows(EntityNotFoundException.class, () -> 
            absenceDayController.delete(ABSENCE_DAY_ID));
        
        verify(absenceDayService, times(1)).delete(ABSENCE_DAY_ID);
    }

    @Test
    void getByEmployeeIdReturnsList() {
        List<AbsenceDay> list = Arrays.asList(absenceDay);
        when(absenceDayService.getByEmployeeId(EMPLOYEE_ID)).thenReturn(list);

        ResponseEntity<List<AbsenceDay>> response = absenceDayController.getByEmployeeId(EMPLOYEE_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
        verify(absenceDayService, times(1)).getByEmployeeId(EMPLOYEE_ID);
    }

    @Test
    void getByEmployeeIdReturnsEmptyList() {
        when(absenceDayService.getByEmployeeId(EMPLOYEE_ID)).thenReturn(Collections.emptyList());

        ResponseEntity<List<AbsenceDay>> response = absenceDayController.getByEmployeeId(EMPLOYEE_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void getByEmployeeIdAndDateRangeReturnsList() {
        List<AbsenceDay> list = Arrays.asList(absenceDay);
        when(absenceDayService.getByEmployeeIdAndDateRange(EMPLOYEE_ID, START_DATE, END_DATE))
            .thenReturn(list);

        ResponseEntity<List<AbsenceDay>> response = absenceDayController
            .getByEmployeeIdAndDateRange(EMPLOYEE_ID, START_DATE, END_DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
        verify(absenceDayService, times(1))
            .getByEmployeeIdAndDateRange(EMPLOYEE_ID, START_DATE, END_DATE);
    }

    @Test
    void getByEmployeeIdAndDateRangeReturnsEmptyList() {
        when(absenceDayService.getByEmployeeIdAndDateRange(EMPLOYEE_ID, START_DATE, END_DATE))
            .thenReturn(Collections.emptyList());

        ResponseEntity<List<AbsenceDay>> response = absenceDayController
            .getByEmployeeIdAndDateRange(EMPLOYEE_ID, START_DATE, END_DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void getByEmployeeIdAndAbsenceTypeIdReturnsList() {
        List<AbsenceDay> list = Arrays.asList(absenceDay);
        when(absenceDayService.getByEmployeeIdAndAbsenceTypeId(EMPLOYEE_ID, ABSENCE_TYPE_ID))
            .thenReturn(list);

        ResponseEntity<List<AbsenceDay>> response = absenceDayController
            .getByEmployeeIdAndAbsenceTypeId(EMPLOYEE_ID, ABSENCE_TYPE_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
        verify(absenceDayService, times(1))
            .getByEmployeeIdAndAbsenceTypeId(EMPLOYEE_ID, ABSENCE_TYPE_ID);
    }

    @Test
    void getByEmployeeIdAndAbsenceTypeIdReturnsEmptyList() {
        when(absenceDayService.getByEmployeeIdAndAbsenceTypeId(EMPLOYEE_ID, ABSENCE_TYPE_ID))
            .thenReturn(Collections.emptyList());

        ResponseEntity<List<AbsenceDay>> response = absenceDayController
            .getByEmployeeIdAndAbsenceTypeId(EMPLOYEE_ID, ABSENCE_TYPE_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void existsByEmployeeIdAndAbsenceDateReturnsTrue() {
        when(absenceDayService.existsByEmployeeIdAndAbsenceDate(EMPLOYEE_ID, TEST_DATE))
            .thenReturn(true);

        ResponseEntity<Boolean> response = absenceDayController
            .existsByEmployeeIdAndAbsenceDate(EMPLOYEE_ID, TEST_DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
        verify(absenceDayService, times(1))
            .existsByEmployeeIdAndAbsenceDate(EMPLOYEE_ID, TEST_DATE);
    }

    @Test
    void existsByEmployeeIdAndAbsenceDateReturnsFalse() {
        when(absenceDayService.existsByEmployeeIdAndAbsenceDate(EMPLOYEE_ID, TEST_DATE))
            .thenReturn(false);

        ResponseEntity<Boolean> response = absenceDayController
            .existsByEmployeeIdAndAbsenceDate(EMPLOYEE_ID, TEST_DATE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody());
        verify(absenceDayService, times(1))
            .existsByEmployeeIdAndAbsenceDate(EMPLOYEE_ID, TEST_DATE);
    }

    @Test
    void createThrowsIllegalArgumentException() {
        when(absenceDayService.create(absenceDay))
            .thenThrow(new IllegalArgumentException("AbsenceDay cannot be null"));

        assertThrows(IllegalArgumentException.class, () -> 
            absenceDayController.create(absenceDay));
        
        verify(absenceDayService, times(1)).create(absenceDay);
    }

    @Test
    void createThrowsEntityNotFoundException() {
        when(absenceDayService.create(absenceDay))
            .thenThrow(new EntityNotFoundException("Employee not found"));

        assertThrows(EntityNotFoundException.class, () -> 
            absenceDayController.create(absenceDay));
        
        verify(absenceDayService, times(1)).create(absenceDay);
    }
}