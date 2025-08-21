package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iws_manager.iws_manager_api.models.EmployeeIws;
import com.iws_manager.iws_manager_api.models.TeamIws;
import com.iws_manager.iws_manager_api.models.User;
import com.iws_manager.iws_manager_api.repositories.EmployeeIwsRepository;

@ExtendWith(MockitoExtension.class)
class EmployeeIwsServiceImplTest {

    @Mock
    private EmployeeIwsRepository employeeIwsRepository;

    @InjectMocks
    private EmployeeIwsServiceImpl employeeIwsService;

    private EmployeeIws employeeIws;
    private EmployeeIws employeeIws2;
    private final Long EMPLOYEE_ID = 1L;
    private final Long TEAM_IWS_ID = 2L;
    private final Long USER_ID = 3L;

    @BeforeEach
    void setUp() {
        employeeIws = new EmployeeIws();
        employeeIws.setId(EMPLOYEE_ID);
        employeeIws.setActive(1);
        employeeIws.setEmployeeLabel("DEV001");
        employeeIws.setEmployeeNo(1001);
        employeeIws.setFirstname("John");
        employeeIws.setLastname("Doe");
        employeeIws.setMail("john.doe@example.com");
        employeeIws.setStartDate(LocalDate.of(2023, 1, 1));
        employeeIws.setEndDate(LocalDate.of(2025, 12, 31));

        TeamIws teamIws = new TeamIws();
        teamIws.setId(TEAM_IWS_ID);
        employeeIws.setTeamIws(teamIws);

        User user = new User();
        user.setId(USER_ID);
        employeeIws.setUser(user);

        employeeIws2 = new EmployeeIws();
        employeeIws2.setId(4L);
        employeeIws2.setFirstname("Jane");
        employeeIws2.setLastname("Smith");
        employeeIws2.setActive(1);
    }

    // CRUD Tests
    @Test
    void createShouldSaveAndReturnEmployeeIws() {
        when(employeeIwsRepository.save(any(EmployeeIws.class))).thenReturn(employeeIws);

        EmployeeIws result = employeeIwsService.create(employeeIws);

        assertNotNull(result);
        assertEquals(EMPLOYEE_ID, result.getId());
        verify(employeeIwsRepository).save(employeeIws);
    }

    @Test
    void createWithNullEmployeeIwsShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> employeeIwsService.create(null));

        assertEquals("EmployeeIws cannot be null", exception.getMessage());
        verify(employeeIwsRepository, never()).save(any());
    }

    @Test
    void findByIdShouldReturnEmployeeIws() {
        when(employeeIwsRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(employeeIws));

        Optional<EmployeeIws> result = employeeIwsService.findById(EMPLOYEE_ID);

        assertTrue(result.isPresent());
        assertEquals(EMPLOYEE_ID, result.get().getId());
        verify(employeeIwsRepository).findById(EMPLOYEE_ID);
    }

    @Test
    void findByIdWithNullIdShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> employeeIwsService.findById(null));

        assertEquals("ID cannot be null", exception.getMessage());
        verify(employeeIwsRepository, never()).findById(any());
    }

    @Test
    void findByIdWithNonExistingIdShouldReturnEmpty() {
        when(employeeIwsRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<EmployeeIws> result = employeeIwsService.findById(999L);

        assertFalse(result.isPresent());
        verify(employeeIwsRepository).findById(999L);
    }

    @Test
    void findAllShouldReturnAllEmployees() {
        List<EmployeeIws> employees = Arrays.asList(employeeIws, employeeIws2);
        when(employeeIwsRepository.findAll()).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.findAll();

        assertEquals(2, result.size());
        verify(employeeIwsRepository).findAll();
    }

    @Test
    void updateShouldUpdateAndReturnEmployeeIws() {
        EmployeeIws updatedDetails = new EmployeeIws();
        updatedDetails.setActive(0);
        updatedDetails.setEmployeeLabel("DEV002");
        updatedDetails.setEmployeeNo(1002);
        updatedDetails.setFirstname("JohnUpdated");
        updatedDetails.setLastname("DoeUpdated");
        updatedDetails.setMail("john.updated@example.com");
        updatedDetails.setStartDate(LocalDate.of(2024, 1, 1));
        updatedDetails.setEndDate(LocalDate.of(2026, 12, 31));

        when(employeeIwsRepository.findById(EMPLOYEE_ID)).thenReturn(Optional.of(employeeIws));
        when(employeeIwsRepository.save(any(EmployeeIws.class))).thenReturn(employeeIws);

        EmployeeIws result = employeeIwsService.update(EMPLOYEE_ID, updatedDetails);

        assertNotNull(result);
        assertEquals(0, result.getActive());
        assertEquals("DEV002", result.getEmployeeLabel());
        assertEquals("JohnUpdated", result.getFirstname());
        verify(employeeIwsRepository).findById(EMPLOYEE_ID);
        verify(employeeIwsRepository).save(employeeIws);
    }

    @Test
    void updateWithNullIdShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> employeeIwsService.update(null, employeeIws));

        assertEquals("ID and employeeIws details cannot be null", exception.getMessage());
        verify(employeeIwsRepository, never()).findById(any());
    }

    @Test
    void updateWithNullDetailsShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> employeeIwsService.update(EMPLOYEE_ID, null));

        assertEquals("ID and employeeIws details cannot be null", exception.getMessage());
        verify(employeeIwsRepository, never()).findById(any());
    }

    @Test
    void updateWithNonExistingIdShouldThrowException() {
        when(employeeIwsRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> employeeIwsService.update(999L, employeeIws));

        assertEquals("EmployeeIws not found with id: 999", exception.getMessage());
        verify(employeeIwsRepository).findById(999L);
        verify(employeeIwsRepository, never()).save(any());
    }

    @Test
    void deleteShouldDeleteEmployeeIws() {
        doNothing().when(employeeIwsRepository).deleteById(EMPLOYEE_ID);

        employeeIwsService.delete(EMPLOYEE_ID);

        verify(employeeIwsRepository).deleteById(EMPLOYEE_ID);
    }

    @Test
    void deleteWithNullIdShouldThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> employeeIwsService.delete(null));

        assertEquals("ID cannot be null", exception.getMessage());
        verify(employeeIwsRepository, never()).deleteById(any());
    }

    // FIND ALL - ORDER Tests
    @Test
    void getAllByOrderByLastnameAscShouldReturnOrderedList() {
        List<EmployeeIws> employees = Arrays.asList(employeeIws, employeeIws2);
        when(employeeIwsRepository.findAllByOrderByLastnameAsc()).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getAllByOrderByLastnameAsc();

        assertEquals(2, result.size());
        verify(employeeIwsRepository).findAllByOrderByLastnameAsc();
    }

    @Test
    void getAllByOrderByFirstnameAscShouldReturnOrderedList() {
        List<EmployeeIws> employees = Arrays.asList(employeeIws, employeeIws2);
        when(employeeIwsRepository.findAllByOrderByFirstnameAsc()).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getAllByOrderByFirstnameAsc();

        assertEquals(2, result.size());
        verify(employeeIwsRepository).findAllByOrderByFirstnameAsc();
    }

    // PROPERTIES Tests
    @Test
    void getByActiveShouldReturnEmployees() {
        List<EmployeeIws> employees = Arrays.asList(employeeIws, employeeIws2);
        when(employeeIwsRepository.findByActive(1)).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getByActive(1);

        assertEquals(2, result.size());
        verify(employeeIwsRepository).findByActive(1);
    }

    @Test
    void getByEmployeeLabelShouldReturnEmployees() {
        List<EmployeeIws> employees = List.of(employeeIws);
        when(employeeIwsRepository.findByEmployeeLabel("DEV001")).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getByEmployeeLabel("DEV001");

        assertEquals(1, result.size());
        verify(employeeIwsRepository).findByEmployeeLabel("DEV001");
    }

    @Test
    void getByEmployeeNoShouldReturnEmployees() {
        List<EmployeeIws> employees = List.of(employeeIws);
        when(employeeIwsRepository.findByEmployeeNo(1001)).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getByEmployeeNo(1001);

        assertEquals(1, result.size());
        verify(employeeIwsRepository).findByEmployeeNo(1001);
    }

    @Test
    void getByEndDateShouldReturnEmployees() {
        LocalDate endDate = LocalDate.of(2025, 12, 31);
        List<EmployeeIws> employees = List.of(employeeIws);
        when(employeeIwsRepository.findByEndDate(endDate)).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getByEndDate(endDate);

        assertEquals(1, result.size());
        verify(employeeIwsRepository).findByEndDate(endDate);
    }

    @Test
    void getByFirstnameShouldReturnEmployees() {
        List<EmployeeIws> employees = List.of(employeeIws);
        when(employeeIwsRepository.findByFirstname("John")).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getByFirstname("John");

        assertEquals(1, result.size());
        verify(employeeIwsRepository).findByFirstname("John");
    }

    @Test
    void getByLastnameShouldReturnEmployees() {
        List<EmployeeIws> employees = List.of(employeeIws);
        when(employeeIwsRepository.findByLastname("Doe")).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getByLastname("Doe");

        assertEquals(1, result.size());
        verify(employeeIwsRepository).findByLastname("Doe");
    }

    @Test
    void getByMailShouldReturnEmployees() {
        List<EmployeeIws> employees = List.of(employeeIws);
        when(employeeIwsRepository.findByMail("john.doe@example.com")).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getByMail("john.doe@example.com");

        assertEquals(1, result.size());
        verify(employeeIwsRepository).findByMail("john.doe@example.com");
    }

    @Test
    void getByStartDateShouldReturnEmployees() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        List<EmployeeIws> employees = List.of(employeeIws);
        when(employeeIwsRepository.findByStartDate(startDate)).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getByStartDate(startDate);

        assertEquals(1, result.size());
        verify(employeeIwsRepository).findByStartDate(startDate);
    }

    @Test
    void getByTeamIwsIdShouldReturnEmployees() {
        List<EmployeeIws> employees = List.of(employeeIws);
        when(employeeIwsRepository.findByTeamIwsId(TEAM_IWS_ID)).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getByTeamIwsId(TEAM_IWS_ID);

        assertEquals(1, result.size());
        verify(employeeIwsRepository).findByTeamIwsId(TEAM_IWS_ID);
    }

    @Test
    void getByUserIdShouldReturnEmployees() {
        List<EmployeeIws> employees = List.of(employeeIws);
        when(employeeIwsRepository.findByUserId(USER_ID)).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getByUserId(USER_ID);

        assertEquals(1, result.size());
        verify(employeeIwsRepository).findByUserId(USER_ID);
    }

    // HELPERS Tests
    @Test
    void getByStartDateAfterShouldReturnEmployees() {
        LocalDate date = LocalDate.of(2022, 12, 31);
        List<EmployeeIws> employees = List.of(employeeIws);
        when(employeeIwsRepository.findByStartDateAfter(date)).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getByStartDateAfter(date);

        assertEquals(1, result.size());
        verify(employeeIwsRepository).findByStartDateAfter(date);
    }

    @Test
    void getByStartDateBeforeShouldReturnEmployees() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        List<EmployeeIws> employees = List.of(employeeIws);
        when(employeeIwsRepository.findByStartDateBefore(date)).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getByStartDateBefore(date);

        assertEquals(1, result.size());
        verify(employeeIwsRepository).findByStartDateBefore(date);
    }

    @Test
    void getByStartDateBetweenShouldReturnEmployees() {
        LocalDate start = LocalDate.of(2022, 12, 31);
        LocalDate end = LocalDate.of(2024, 1, 1);
        List<EmployeeIws> employees = List.of(employeeIws);
        when(employeeIwsRepository.findByStartDateBetween(start, end)).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getByStartDateBetween(start, end);

        assertEquals(1, result.size());
        verify(employeeIwsRepository).findByStartDateBetween(start, end);
    }

    @Test
    void getByEndDateAfterShouldReturnEmployees() {
        LocalDate date = LocalDate.of(2024, 12, 31);
        List<EmployeeIws> employees = List.of(employeeIws);
        when(employeeIwsRepository.findByEndDateAfter(date)).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getByEndDateAfter(date);

        assertEquals(1, result.size());
        verify(employeeIwsRepository).findByEndDateAfter(date);
    }

    @Test
    void getByEndDateBeforeShouldReturnEmployees() {
        LocalDate date = LocalDate.of(2026, 1, 1);
        List<EmployeeIws> employees = List.of(employeeIws);
        when(employeeIwsRepository.findByEndDateBefore(date)).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getByEndDateBefore(date);

        assertEquals(1, result.size());
        verify(employeeIwsRepository).findByEndDateBefore(date);
    }

    @Test
    void getByEndDateBetweenShouldReturnEmployees() {
        LocalDate start = LocalDate.of(2024, 12, 31);
        LocalDate end = LocalDate.of(2026, 1, 1);
        List<EmployeeIws> employees = List.of(employeeIws);
        when(employeeIwsRepository.findByEndDateBetween(start, end)).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getByEndDateBetween(start, end);

        assertEquals(1, result.size());
        verify(employeeIwsRepository).findByEndDateBetween(start, end);
    }

    // ACTIVE - ORDER Tests
    @Test
    void getByActiveOrderByFirstnameAscShouldReturnOrderedEmployees() {
        List<EmployeeIws> employees = Arrays.asList(employeeIws, employeeIws2);
        when(employeeIwsRepository.findByActiveOrderByFirstnameAsc(1)).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getByActiveOrderByFirstnameAsc(1);

        assertEquals(2, result.size());
        verify(employeeIwsRepository).findByActiveOrderByFirstnameAsc(1);
    }

    @Test
    void getByActiveOrderByLastnameAscShouldReturnOrderedEmployees() {
        List<EmployeeIws> employees = Arrays.asList(employeeIws, employeeIws2);
        when(employeeIwsRepository.findByActiveOrderByLastnameAsc(1)).thenReturn(employees);

        List<EmployeeIws> result = employeeIwsService.getByActiveOrderByLastnameAsc(1);

        assertEquals(2, result.size());
        verify(employeeIwsRepository).findByActiveOrderByLastnameAsc(1);
    }
}