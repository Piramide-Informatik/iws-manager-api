package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.Employee;
import com.iws_manager.iws_manager_api.repositories.EmployeeRepository;
import com.iws_manager.iws_manager_api.services.interfaces.EmployeeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee sampleEmployee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleEmployee = new Employee();
        sampleEmployee.setId(1L);
        sampleEmployee.setFirstname("John");
        sampleEmployee.setLastname("Doe");
        sampleEmployee.setEmail("john.doe@example.com");
        sampleEmployee.setPhone("123456789");
        sampleEmployee.setEmployeeno(1001);
        sampleEmployee.setLabel("Dev");
        sampleEmployee.setCoentrepreneursince(LocalDate.of(2020, 1, 1));
    }

    @Test
    void testCreate() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(sampleEmployee);
        Employee created = employeeService.create(sampleEmployee);
        assertNotNull(created);
        assertEquals("John", created.getFirstname());
        verify(employeeRepository, times(1)).save(sampleEmployee);
    }

    @Test
    void testFindById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(sampleEmployee));
        Optional<Employee> result = employeeService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("Doe", result.get().getLastname());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        List<Employee> employees = List.of(sampleEmployee);
        when(employeeRepository.findAll()).thenReturn(employees);
        List<Employee> result = employeeService.findAll();
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        Employee updated = new Employee();
        updated.setFirstname("Jane");
        updated.setLastname("Smith");
        updated.setEmail("jane.smith@example.com");
        updated.setPhone("987654321");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(sampleEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updated);

        Employee result = employeeService.update(1L, updated);
        assertNotNull(result);
        assertEquals("Jane", result.getFirstname());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testDelete() {
        doNothing().when(employeeRepository).deleteById(1L);
        employeeService.delete(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByLastname() {
        when(employeeRepository.findByLastname("Doe")).thenReturn(List.of(sampleEmployee));
        List<Employee> result = employeeService.findByLastname("Doe");
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findByLastname("Doe");
    }

    @Test
    void testFindByEmail() {
        when(employeeRepository.findByEmail("john.doe@example.com")).thenReturn(sampleEmployee);
        Optional<Employee> result = employeeService.findByEmail("john.doe@example.com");
        assertTrue(result.isPresent());
        assertEquals("Doe", result.get().getLastname());
        verify(employeeRepository, times(1)).findByEmail("john.doe@example.com");
    }

    @Test
    void testFindByTitleId() {
        when(employeeRepository.findByTitleId(1L)).thenReturn(List.of(sampleEmployee));
        List<Employee> result = employeeService.findByTitleId(1L);
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findByTitleId(1L);
    }

    @Test
    void testFindBySalutationId() {
        when(employeeRepository.findBySalutationId(1L)).thenReturn(List.of(sampleEmployee));
        List<Employee> result = employeeService.findBySalutationId(1L);
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findBySalutationId(1L);
    }

    @Test
    void testFindByQualificationFZId() {
        when(employeeRepository.findByQualificationFZId(1L)).thenReturn(List.of(sampleEmployee));
        List<Employee> result = employeeService.findByQualificationFZId(1L);
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findByQualificationFZId(1L);
    }

    @Test
    void testFindByCustomerId() {
        when(employeeRepository.findByCustomerId(1L)).thenReturn(List.of(sampleEmployee));
        List<Employee> result = employeeService.findByCustomerId(1L);
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findByCustomerId(1L);
    }
}
