package com.iws_manager.iws_manager_api.models;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ProjectCostTest {

    @Test
    void testProjectCostCreation() {
        // Arrange
        Byte approveOrPlan = 1; // 1: approved costs
        BigDecimal costs = new BigDecimal("15000.75");
        Integer projectId = 1001;
        Integer projectPeriodId = 2024;

        // Act
        ProjectCost projectCost = new ProjectCost();
        projectCost.setApproveOrPlan(approveOrPlan);
        projectCost.setCosts(costs);
        projectCost.setProjectId(projectId);
        projectCost.setProjectPeriodId(projectPeriodId);

        // Assert
        assertEquals(approveOrPlan, projectCost.getApproveOrPlan());
        assertEquals(costs, projectCost.getCosts());
        assertEquals(projectId, projectCost.getProjectId());
        assertEquals(projectPeriodId, projectCost.getProjectPeriodId());
    }

    @Test
    void testProjectCostWithAuditFields() {
        // Arrange
        ProjectCost projectCost = new ProjectCost();
        projectCost.setApproveOrPlan((byte) 2); // 2: planned costs
        LocalDateTime now = LocalDateTime.now();

        // Act
        projectCost.setCreatedAt(now);
        projectCost.setUpdatedAt(now);

        // Assert
        assertEquals(now, projectCost.getCreatedAt());
        assertEquals(now, projectCost.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        ProjectCost projectCost1 = new ProjectCost();
        projectCost1.setApproveOrPlan((byte) 1);
        projectCost1.setCosts(new BigDecimal("50000.00"));
        projectCost1.setId(1L);

        ProjectCost projectCost2 = new ProjectCost();
        projectCost2.setApproveOrPlan((byte) 1);
        projectCost2.setCosts(new BigDecimal("50000.00"));
        projectCost2.setId(1L);

        // Assert
        assertEquals(projectCost1, projectCost2);
        assertEquals(projectCost1.hashCode(), projectCost2.hashCode());
    }

    @Test
    void testProjectCostWithAllFields() {
        // Arrange & Act
        ProjectCost projectCost = new ProjectCost();
        projectCost.setId(1L);
        projectCost.setApproveOrPlan((byte) 2); // planned costs
        projectCost.setCosts(new BigDecimal("75000.50"));
        projectCost.setProjectId(123);
        projectCost.setProjectPeriodId(2024);

        // Assert
        assertAll("ProjectCost properties",
                () -> assertEquals(1L, projectCost.getId()),
                () -> assertEquals((byte) 2, projectCost.getApproveOrPlan()),
                () -> assertEquals(new BigDecimal("75000.50"), projectCost.getCosts()),
                () -> assertEquals(123, projectCost.getProjectId()),
                () -> assertEquals(2024, projectCost.getProjectPeriodId()));
    }

    @Test
    void testProjectCostWithNullValues() {
        // Arrange & Act
        ProjectCost projectCost = new ProjectCost();
        projectCost.setId(2L);
        projectCost.setApproveOrPlan((byte) 1);

        // Assert - Los campos costs, projectId y projectPeriodId pueden ser null según
        // el modelo
        assertAll("ProjectCost with null optional fields",
                () -> assertEquals(2L, projectCost.getId()),
                () -> assertEquals((byte) 1, projectCost.getApproveOrPlan()),
                () -> assertNull(projectCost.getCosts()),
                () -> assertNull(projectCost.getProjectId()),
                () -> assertNull(projectCost.getProjectPeriodId()));
    }

    @Test
    void testProjectCostWithProjectTotal() {
        // Arrange & Act
        ProjectCost projectCost = new ProjectCost();
        projectCost.setId(3L);
        projectCost.setApproveOrPlan((byte) 1);
        projectCost.setCosts(new BigDecimal("100000.00"));
        projectCost.setProjectId(456);
        projectCost.setProjectPeriodId(0); // 0 = project total según comentario en la tabla

        // Assert
        assertEquals(0, projectCost.getProjectPeriodId(),
                "projectPeriodId should be 0 for project total");
        assertEquals(new BigDecimal("100000.00"), projectCost.getCosts());
    }

    @Test
    void testProjectCostConstructorWithAllArgs() {
        // Arrange
        Byte approveOrPlan = 1;
        BigDecimal costs = new BigDecimal("25000.25");
        Integer projectId = 789;
        Integer projectPeriodId = 2025;

        // Act
        ProjectCost projectCost = new ProjectCost(approveOrPlan, costs, projectId, projectPeriodId);
        projectCost.setId(4L);

        // Assert
        assertAll("ProjectCost created with all-args constructor",
                () -> assertEquals(4L, projectCost.getId()),
                () -> assertEquals(approveOrPlan, projectCost.getApproveOrPlan()),
                () -> assertEquals(costs, projectCost.getCosts()),
                () -> assertEquals(projectId, projectCost.getProjectId()),
                () -> assertEquals(projectPeriodId, projectCost.getProjectPeriodId()));
    }

    @Test
    void testToString() {
        // Arrange
        ProjectCost projectCost = new ProjectCost();
        projectCost.setId(5L);
        projectCost.setApproveOrPlan((byte) 1);
        projectCost.setCosts(new BigDecimal("15000.75"));
        projectCost.setProjectId(999);
        projectCost.setProjectPeriodId(2024);

        // Act
        String toStringResult = projectCost.toString();

        // Assert
        assertTrue(toStringResult.contains("5"));
        assertTrue(toStringResult.contains("1"));
        assertTrue(toStringResult.contains("15000.75"));
        assertTrue(toStringResult.contains("999"));
        assertTrue(toStringResult.contains("2024"));
    }
}