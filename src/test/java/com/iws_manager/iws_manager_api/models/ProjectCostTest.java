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
        Project project = createProject(1001L);
        ProjectPeriod projectPeriod = createProjectPeriod(2024L);

        // Act
        ProjectCost projectCost = new ProjectCost();
        projectCost.setApproveOrPlan(approveOrPlan);
        projectCost.setCosts(costs);
        projectCost.setProject(project);
        projectCost.setProjectPeriod(projectPeriod);

        // Assert
        assertEquals(approveOrPlan, projectCost.getApproveOrPlan());
        assertEquals(costs, projectCost.getCosts());
        assertEquals(project, projectCost.getProject());
        assertEquals(projectPeriod, projectCost.getProjectPeriod());
    }

    @Test
    void testProjectCostWithAuditFields() {
        // Arrange
        ProjectCost projectCost = new ProjectCost();
        projectCost.setApproveOrPlan(PLANNED_COST_TYPE);
        projectCost.setProject(createProject(2001L));
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
        Project project = createProject(3001L);

        ProjectCost projectCost1 = new ProjectCost();
        projectCost1.setApproveOrPlan(APPROVED_COST_TYPE);
        projectCost1.setCosts(COST_50000_00);
        projectCost1.setProject(project);
        projectCost1.setId(ENTITY_ID_1);

        ProjectCost projectCost2 = new ProjectCost();
        projectCost2.setApproveOrPlan(APPROVED_COST_TYPE);
        projectCost2.setCosts(COST_50000_00);
        projectCost2.setProject(project);
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
        projectCost.setProject(createProject(123L));
        projectCost.setProjectPeriod(createProjectPeriod(2024L));

        // Assert
        assertAll("ProjectCost properties",
                () -> assertEquals(ENTITY_ID_1, projectCost.getId()),
                () -> assertEquals(PLANNED_COST_TYPE, projectCost.getApproveOrPlan()),
                () -> assertEquals(COST_75000_50, projectCost.getCosts()),
                () -> assertNotNull(projectCost.getProject()),
                () -> assertNotNull(projectCost.getProjectPeriod()));
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
                () -> assertNull(projectCost.getProject()),
                () -> assertNull(projectCost.getProjectPeriod()));
    }

    @Test
    void testProjectCostWithProjectTotal() {
        // Arrange & Act
        ProjectCost projectCost = new ProjectCost();
        projectCost.setId(ENTITY_ID_3);
        projectCost.setApproveOrPlan(APPROVED_COST_TYPE);
        projectCost.setCosts(COST_100000_00);
        projectCost.setProject(createProject(456L));

        projectCost.setProjectPeriod(null);

        // Assert
        assertNull(projectCost.getProjectPeriod(),
                "projectPeriod should be null for project total");
        assertEquals(COST_100000_00, projectCost.getCosts());
    }

    @Test
    void testProjectCostConstructorWithAllArgs() {
        // Arrange
        Byte approveOrPlan = APPROVED_COST_TYPE;
        BigDecimal costs = COST_25000_25;
        Project project = createProject(789L);
        ProjectPeriod projectPeriod = createProjectPeriod(2025L);

        // Act
        ProjectCost projectCost = new ProjectCost();
        projectCost.setApproveOrPlan(approveOrPlan);
        projectCost.setCosts(costs);
        projectCost.setProject(project);
        projectCost.setProjectPeriod(projectPeriod);
        projectCost.setId(ENTITY_ID_4);

        // Assert
        assertAll("ProjectCost with all fields set",
                () -> assertEquals(ENTITY_ID_4, projectCost.getId()),
                () -> assertEquals(approveOrPlan, projectCost.getApproveOrPlan()),
                () -> assertEquals(costs, projectCost.getCosts()),
                () -> assertEquals(project, projectCost.getProject()),
                () -> assertEquals(projectPeriod, projectCost.getProjectPeriod()));
    }

    @Test
    void testToString() {
        // Arrange
        ProjectCost projectCost = new ProjectCost();
        projectCost.setId(ENTITY_ID_5);
        projectCost.setApproveOrPlan(APPROVED_COST_TYPE);
        projectCost.setCosts(COST_15000_75);
        projectCost.setProject(createProject(999L));
        projectCost.setProjectPeriod(createProjectPeriod(2024L));

        // Act
        String toStringResult = projectCost.toString();

        // Assert
        assertTrue(toStringResult.contains(ENTITY_ID_5.toString()));
        assertTrue(toStringResult.contains(APPROVED_COST_TYPE.toString()));
        assertTrue(toStringResult.contains(COST_15000_75.toString()));
        assertTrue(toStringResult.contains("Project") || toStringResult.contains("999"));
        assertTrue(toStringResult.contains("ProjectPeriod") || toStringResult.contains("2024"));
    }

    private Project createProject(Long id) {
        Project project = new Project();
        project.setId(id);
        return project;
    }

    private ProjectPeriod createProjectPeriod(Long id) {
        ProjectPeriod projectPeriod = new ProjectPeriod();
        projectPeriod.setId(id);
        return projectPeriod;
    }
}