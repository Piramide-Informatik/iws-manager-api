package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectPeriodTest {
    Integer periodNo = 1;
    ProjectPeriod projectPeriod = new ProjectPeriod();
    @Test
    void testPublicHolidayCreation() {
        projectPeriod.setPeriodNo(periodNo);

        assertEquals(periodNo, projectPeriod.getPeriodNo());
    }

    @Test
    void testPublicHolidayWithAuditFields(){
        // Arrange
        projectPeriod.setPeriodNo(periodNo);
        LocalDateTime now = LocalDateTime.now();

        // Act
        projectPeriod.setCreatedAt(now);
        projectPeriod.setUpdatedAt(now);

        // Assert
        assertEquals(now, projectPeriod.getCreatedAt());
        assertEquals(now, projectPeriod.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode(){
        // Arrange
        projectPeriod.setPeriodNo(periodNo);
        projectPeriod.setId(1L);

        ProjectPeriod projectPeriod2 = new ProjectPeriod();
        projectPeriod2.setPeriodNo(periodNo);
        projectPeriod2.setId(1L);

        // Assert
        assertEquals(projectPeriod, projectPeriod2);
        assertEquals(projectPeriod.hashCode(), projectPeriod2.hashCode());
    }
}