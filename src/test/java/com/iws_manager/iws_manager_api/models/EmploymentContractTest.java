package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

class EmploymentContractTest {

    @Test
    void testEmploymentContractCreation() {
        // Arrange
        Customer customer = new Customer();
        customer.setId(1L);
        
        Employee employee = new Employee();
        employee.setId(1L);

        LocalDate startDate = LocalDate.now();

        // Act
        EmploymentContract contract = new EmploymentContract();
        contract.setCustomer(customer);
        contract.setEmployee(employee);
        contract.setHourlyRate(25.50);
        contract.setHourlyRealRate(27.30);
        contract.setHoursPerWeek(40.0);
        contract.setMaxHoursPerDay(8.0);
        contract.setMaxHoursPerMonth(160.0);
        contract.setSalaryPerMonth(4000.0);
        contract.setSpecialPayment(500.0);
        contract.setStartDate(startDate);
        contract.setWorkShortTime(0.0);

        // Assert
        assertNotNull(contract);
        assertEquals(customer, contract.getCustomer());
        assertEquals(employee, contract.getEmployee());
        assertEquals(25.50, contract.getHourlyRate());
        assertEquals(27.30, contract.getHourlyRealRate());
        assertEquals(40.0, contract.getHoursPerWeek());
        assertEquals(8.0, contract.getMaxHoursPerDay());
        assertEquals(160.0, contract.getMaxHoursPerMonth());
        assertEquals(4000.0, contract.getSalaryPerMonth());
        assertEquals(500.0, contract.getSpecialPayment());
        assertEquals(startDate, contract.getStartDate());
        assertEquals(0.0, contract.getWorkShortTime());
    }

    @Test
    void testEmploymentContractWithAuditFields() {
        // Arrange
        EmploymentContract contract = new EmploymentContract();
        
        LocalDateTime now = LocalDateTime.now();
        contract.setCreatedAt(now);
        contract.setUpdatedAt(now);

        // Assert
        assertEquals(now, contract.getCreatedAt());
        assertEquals(now, contract.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        EmploymentContract contract1 = new EmploymentContract();
        contract1.setId(1L);
        
        EmploymentContract contract2 = new EmploymentContract();
        contract2.setId(1L);

        // Assert equals()
        assertEquals(contract1, contract2);
        
        // Assert hashCode()
        assertEquals(contract1.hashCode(), contract2.hashCode());
    }
}