package com.iws_manager.iws_manager_api.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeIwsTest {

    @Test
    void testEmployeeIwsCreation() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        LocalDate startDate = LocalDate.now();
        Integer active = 1;
        
        // Act
        EmployeeIws employee = new EmployeeIws();
        employee.setFirstname(firstName);
        employee.setLastname(lastName);
        employee.setMail(email);
        employee.setStartdate(startDate);
        employee.setActive(active);
        
        // Assert
        assertAll(
            () -> assertEquals(firstName, employee.getFirstname()),
            () -> assertEquals(lastName, employee.getLastname()),
            () -> assertEquals(email, employee.getMail()),
            () -> assertEquals(startDate, employee.getStartdate()),
            () -> assertEquals(active, employee.getActive())
        );
    }

    @Test
    void testEmployeeIwsWithAuditFields() {
        // Arrange
        EmployeeIws employee = new EmployeeIws();
        employee.setFirstname("Jane");
        employee.setLastname("Smith");
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        employee.setCreatedAt(now);
        employee.setUpdatedAt(now);
        
        // Assert
        assertAll(
            () -> assertEquals(now, employee.getCreatedAt()),
            () -> assertEquals(now, employee.getUpdatedAt())
        );
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        EmployeeIws employee1 = new EmployeeIws();
        employee1.setFirstname("Michael");
        employee1.setLastname("Johnson");
        employee1.setId(1L);
        
        EmployeeIws employee2 = new EmployeeIws();
        employee2.setFirstname("Michael");
        employee2.setLastname("Johnson");
        employee2.setId(1L);
        
        EmployeeIws employee3 = new EmployeeIws();
        employee3.setFirstname("Different");
        employee3.setLastname("Employee");
        employee3.setId(2L);
        
        // Assert
        assertAll(
            () -> assertEquals(employee1, employee2),
            () -> assertEquals(employee1.hashCode(), employee2.hashCode()),
            () -> assertNotEquals(employee1, employee3),
            () -> assertNotEquals(employee1.hashCode(), employee3.hashCode())
        );
    }
}