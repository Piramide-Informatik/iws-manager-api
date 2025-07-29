package com.iws_manager.iws_manager_api.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class SubcontractYearTest {

    @Test
    void testSubcontractYearCreation() {
        // Arrange
        int months = 5;
        LocalDate year = LocalDate.of(2025, 1, 1);
        Subcontract subcontract = new Subcontract();

        // Act
        SubcontractYear subcontractYear = new SubcontractYear();
        subcontractYear.setMonths(months);
        subcontractYear.setYear(year);
        subcontractYear.setSubcontract(subcontract);

        // Assert
        assertEquals(months, subcontractYear.getMonths());
        assertEquals(year, subcontractYear.getYear());
        assertEquals(subcontract, subcontractYear.getSubcontract());
    }

    @Test
    void testSubcontractYearWithAuditFields() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        SubcontractYear subcontractYear = new SubcontractYear();

        // Act
        subcontractYear.setCreatedAt(now);
        subcontractYear.setUpdatedAt(now);

        // Assert
        assertEquals(now, subcontractYear.getCreatedAt());
        assertEquals(now, subcontractYear.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        SubcontractYear year1 = new SubcontractYear();
        year1.setId(1L);
        year1.setMonths(3);

        SubcontractYear year2 = new SubcontractYear();
        year2.setId(1L);
        year2.setMonths(3);

        // Assert
        assertEquals(year1, year2);
        assertEquals(year1.hashCode(), year2.hashCode());
    }
}
