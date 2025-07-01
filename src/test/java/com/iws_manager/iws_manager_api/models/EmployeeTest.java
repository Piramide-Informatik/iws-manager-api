package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {
    private static final String FIRSTNAME = "Joe";
    private static final String LASTNAME = "Doe";
    private static final String EMAIL = "joe.doe@example.com";
    private static final String PHONE = "123456789";
    private static final Integer NO = 1001;
    private static final String LABEL = "Senior Dev";
    private static final LocalDate COENTREPRENEURSINCE = LocalDate.of(2020, 1, 1);

    @Test
    void testEmployeeCreation() {
        Employee employee = new Employee();
        employee.setFirstname(FIRSTNAME);
        employee.setLastname(LASTNAME);
        employee.setEmail(EMAIL);
        employee.setPhone(PHONE);
        employee.setEmployeeno(NO);
        employee.setLabel(LABEL);
        employee.setCoentrepreneursince(COENTREPRENEURSINCE);

        assertEquals(FIRSTNAME, employee.getFirstname());
        assertEquals(LASTNAME, employee.getLastname());
        assertEquals(EMAIL, employee.getEmail());
        assertEquals(PHONE, employee.getPhone());
        assertEquals(NO, employee.getEmployeeno());
        assertEquals(LABEL, employee.getLabel());
        assertEquals(COENTREPRENEURSINCE, employee.getCoentrepreneursince());
    }

    @Test
    void testEqualsAndHashCode() {
        Employee emp1 = new Employee();
        emp1.setId(1L);
        emp1.setFirstname(FIRSTNAME);
        emp1.setLastname(LASTNAME);

        Employee emp2 = new Employee();
        emp2.setId(1L);
        emp2.setFirstname(FIRSTNAME);
        emp2.setLastname(LASTNAME);

        assertEquals(emp1, emp2);
        assertEquals(emp1.hashCode(), emp2.hashCode());
    }

    @Test
    void testEmployeeWithAuditFields() {
        Employee employee = new Employee();
        employee.setFirstname(FIRSTNAME);
        employee.setLastname(LASTNAME);
        LocalDateTime now = LocalDateTime.now();
        
        employee.setCreatedAt(now);
        employee.setUpdatedAt(now);

        assertEquals(FIRSTNAME, employee.getFirstname());
        assertEquals(LASTNAME, employee.getLastname());
        assertEquals(now, employee.getCreatedAt());
        assertEquals(now, employee.getUpdatedAt());
    }
}