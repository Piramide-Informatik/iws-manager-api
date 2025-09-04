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

    private static final String FIRSTNAME = "Joe";
    private static final String LASTNAME = "Doe";
    private static final String EMAIL = "joe.doe@example.com";
    private static final String PHONE = "123456789";
    private static final Integer NO = 1001;
    private static final String LABEL = "Senior Dev";
    private static final LocalDate COENTREPRENEURSINCE = LocalDate.of(2020, 1, 1);

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
        sampleEmployee.setFirstname(FIRSTNAME);
        sampleEmployee.setLastname(LASTNAME);
        sampleEmployee.setEmail(EMAIL);
        sampleEmployee.setPhone(PHONE);
        sampleEmployee.setEmployeeno(NO);
        sampleEmployee.setLabel(LABEL);
        sampleEmployee.setCoentrepreneursince(COENTREPRENEURSINCE);
    }

    @Test
    void testCreate() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(sampleEmployee);
        Employee created = employeeService.create(sampleEmployee);
        assertNotNull(created);
        assertEquals(FIRSTNAME, created.getFirstname());
        verify(employeeRepository, times(1)).save(sampleEmployee);
    }

    @Test
    void testFindById() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(sampleEmployee));
        Optional<Employee> result = employeeService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(LASTNAME, result.get().getLastname());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        List<Employee> employees = List.of(sampleEmployee);
        
        when(employeeRepository.findAllByOrderByEmployeenoAsc()).thenReturn(employees);
        
        List<Employee> result = employeeService.findAll();
        
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findAllByOrderByEmployeenoAsc();
        verify(employeeRepository, never()).findAll();
    }

    @Test
    void testFindAllReturnsEmployeesInOrder() {
        Employee emp1 = new Employee();
        emp1.setEmployeeno(2);
        Employee emp2 = new Employee();
        emp2.setEmployeeno(1);
        
        List<Employee> expectedSorted = List.of(emp2, emp1);
        
        when(employeeRepository.findAllByOrderByEmployeenoAsc()).thenReturn(expectedSorted);
        
        List<Employee> result = employeeService.findAll();
        
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getEmployeeno());
        assertEquals(2, result.get(1).getEmployeeno());
    }

    @Test
    void testUpdate() {
        Employee updated = new Employee();
        updated.setFirstname(FIRSTNAME);
        updated.setLastname(LASTNAME);
        updated.setEmail(EMAIL);
        updated.setPhone(PHONE);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(sampleEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updated);

        Employee result = employeeService.update(1L, updated);
        assertNotNull(result);
        assertEquals(FIRSTNAME, result.getFirstname());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testDelete() {
        when(employeeRepository.existsById(1L)).thenReturn(true);
        employeeService.delete(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindByLastname() {
        when(employeeRepository.findByLastname(LASTNAME)).thenReturn(List.of(sampleEmployee));
        List<Employee> result = employeeService.findByLastname(LASTNAME);
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findByLastname(LASTNAME);
    }

    @Test
    void testFindByEmail() {
        when(employeeRepository.findByEmail(EMAIL)).thenReturn(sampleEmployee);
        Optional<Employee> result = employeeService.findByEmail(EMAIL);
        assertTrue(result.isPresent());
        assertEquals(LASTNAME, result.get().getLastname());
        verify(employeeRepository, times(1)).findByEmail(EMAIL);
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
        when(employeeRepository.findByCustomerIdOrderByEmployeenoAsc(1L)).thenReturn(List.of(sampleEmployee));
        List<Employee> result = employeeService.findByCustomerId(1L);
        assertEquals(1, result.size());
        verify(employeeRepository, times(1)).findByCustomerIdOrderByEmployeenoAsc(1L);
    }
}
