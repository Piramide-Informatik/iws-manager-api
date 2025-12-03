package com.iws_manager.iws_manager_api.models;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ProjectCostTest {

    private static final Byte APPROVED_COST_TYPE = 1;
    private static final Byte PLANNED_COST_TYPE = 2;
    private static final BigDecimal COST_15000_75 = new BigDecimal("15000.75");
    private static final BigDecimal COST_50000_00 = new BigDecimal("50000.00");
    private static final BigDecimal COST_75000_50 = new BigDecimal("75000.50");
    private static final BigDecimal COST_100000_00 = new BigDecimal("100000.00");
    private static final BigDecimal COST_25000_25 = new BigDecimal("25000.25");

    private static final Integer PROJECT_ID_1001 = 1001;
    private static final Integer PROJECT_ID_123 = 123;
    private static final Integer PROJECT_ID_456 = 456;
    private static final Integer PROJECT_ID_789 = 789;
    private static final Integer PROJECT_ID_999 = 999;

    private static final Integer PROJECT_PERIOD_2024 = 2024;
    private static final Integer PROJECT_PERIOD_2025 = 2025;
    private static final Integer PROJECT_PERIOD_TOTAL = 0; // 0 = project total

    private static final Long ENTITY_ID_1 = 1L;
    private static final Long ENTITY_ID_2 = 2L;
    private static final Long ENTITY_ID_3 = 3L;
    private static final Long ENTITY_ID_4 = 4L;
    private static final Long ENTITY_ID_5 = 5L;

    @Test
    void testProjectCostCreation() {
        // Arrange
        Byte approveOrPlan = APPROVED_COST_TYPE;
        BigDecimal costs = COST_15000_75;
        Integer projectId = PROJECT_ID_1001;
        Integer projectPeriodId = PROJECT_PERIOD_2024;

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
        projectCost.setApproveOrPlan(PLANNED_COST_TYPE);
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
        projectCost1.setApproveOrPlan(APPROVED_COST_TYPE);
        projectCost1.setCosts(COST_50000_00);
        projectCost1.setId(ENTITY_ID_1);

        ProjectCost projectCost2 = new ProjectCost();
        projectCost2.setApproveOrPlan(APPROVED_COST_TYPE);
        projectCost2.setCosts(COST_50000_00);
        projectCost2.setId(ENTITY_ID_1);

        // Assert
        assertEquals(projectCost1, projectCost2);
        assertEquals(projectCost1.hashCode(), projectCost2.hashCode());
    }

    @Test
    void testProjectCostWithAllFields() {
        // Arrange & Act
        ProjectCost projectCost = new ProjectCost();
        projectCost.setId(ENTITY_ID_1);
        projectCost.setApproveOrPlan(PLANNED_COST_TYPE);
        projectCost.setCosts(COST_75000_50);
        projectCost.setProjectId(PROJECT_ID_123);
        projectCost.setProjectPeriodId(PROJECT_PERIOD_2024);

        // Assert
        assertAll("ProjectCost properties",
                () -> assertEquals(ENTITY_ID_1, projectCost.getId()),
                () -> assertEquals(PLANNED_COST_TYPE, projectCost.getApproveOrPlan()),
                () -> assertEquals(COST_75000_50, projectCost.getCosts()),
                () -> assertEquals(PROJECT_ID_123, projectCost.getProjectId()),
                () -> assertEquals(PROJECT_PERIOD_2024, projectCost.getProjectPeriodId()));
    }

    @Test
    void testProjectCostWithNullValues() {
        // Arrange & Act
        ProjectCost projectCost = new ProjectCost();
        projectCost.setId(ENTITY_ID_2);
        projectCost.setApproveOrPlan(APPROVED_COST_TYPE);

        // Assert
        assertAll("ProjectCost with null optional fields",
                () -> assertEquals(ENTITY_ID_2, projectCost.getId()),
                () -> assertEquals(APPROVED_COST_TYPE, projectCost.getApproveOrPlan()),
                () -> assertNull(projectCost.getCosts()),
                () -> assertNull(projectCost.getProjectId()),
                () -> assertNull(projectCost.getProjectPeriodId()));
    }

    @Test
    void testProjectCostWithProjectTotal() {
        // Arrange & Act
        ProjectCost projectCost = new ProjectCost();
        projectCost.setId(ENTITY_ID_3);
        projectCost.setApproveOrPlan(APPROVED_COST_TYPE);
        projectCost.setCosts(COST_100000_00);
        projectCost.setProjectId(PROJECT_ID_456);
        projectCost.setProjectPeriodId(PROJECT_PERIOD_TOTAL);

        // Assert
        assertEquals(PROJECT_PERIOD_TOTAL, projectCost.getProjectPeriodId(),
                "projectPeriodId should be 0 for project total");
        assertEquals(COST_100000_00, projectCost.getCosts());
    }

    @Test
    void testProjectCostConstructorWithAllArgs() {
        // Arrange
        Byte approveOrPlan = APPROVED_COST_TYPE;
        BigDecimal costs = COST_25000_25;
        Integer projectId = PROJECT_ID_789;
        Integer projectPeriodId = PROJECT_PERIOD_2025;

        // Act
        ProjectCost projectCost = new ProjectCost(approveOrPlan, costs, projectId, projectPeriodId);
        projectCost.setId(ENTITY_ID_4);

        // Assert
        assertAll("ProjectCost created with all-args constructor",
                () -> assertEquals(ENTITY_ID_4, projectCost.getId()),
                () -> assertEquals(approveOrPlan, projectCost.getApproveOrPlan()),
                () -> assertEquals(costs, projectCost.getCosts()),
                () -> assertEquals(projectId, projectCost.getProjectId()),
                () -> assertEquals(projectPeriodId, projectCost.getProjectPeriodId()));
    }

    @Test
    void testToString() {
        // Arrange
        ProjectCost projectCost = new ProjectCost();
        projectCost.setId(ENTITY_ID_5);
        projectCost.setApproveOrPlan(APPROVED_COST_TYPE);
        projectCost.setCosts(COST_15000_75);
        projectCost.setProjectId(PROJECT_ID_999);
        projectCost.setProjectPeriodId(PROJECT_PERIOD_2024);

        // Act
        String toStringResult = projectCost.toString();

        // Assert
        assertTrue(toStringResult.contains(ENTITY_ID_5.toString()));
        assertTrue(toStringResult.contains(APPROVED_COST_TYPE.toString()));
        assertTrue(toStringResult.contains(COST_15000_75.toString()));
        assertTrue(toStringResult.contains(PROJECT_ID_999.toString()));
        assertTrue(toStringResult.contains(PROJECT_PERIOD_2024.toString()));
    }
}