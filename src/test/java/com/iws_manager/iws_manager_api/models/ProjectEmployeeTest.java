package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProjectEmployeeTest {

    private static final String QUALIFICATION_K_MUI = "Senior Developer";
    private static final BigDecimal HOURLY_RATE = new BigDecimal("45.50");
    private static final BigDecimal PLANNED_HOURS = new BigDecimal("160.00");

    @Test
    void testProjectEmployeeCreation() {
        ProjectEmployee projectEmployee = new ProjectEmployee();
        projectEmployee.setQualificationkmui(QUALIFICATION_K_MUI);
        projectEmployee.setHourlyrate(HOURLY_RATE);
        projectEmployee.setPlannedhours(PLANNED_HOURS);

        assertEquals(QUALIFICATION_K_MUI, projectEmployee.getQualificationkmui());
        assertEquals(HOURLY_RATE, projectEmployee.getHourlyrate());
        assertEquals(PLANNED_HOURS, projectEmployee.getPlannedhours());
    }

    @Test
    void testHourlyRatePrecision() {
        ProjectEmployee projectEmployee = new ProjectEmployee();

        BigDecimal rate1 = new BigDecimal("25.00");
        BigDecimal rate2 = new BigDecimal("35.75");
        BigDecimal rate3 = new BigDecimal("99.99");

        projectEmployee.setHourlyrate(rate1);
        assertEquals(rate1, projectEmployee.getHourlyrate());

        projectEmployee.setHourlyrate(rate2);
        assertEquals(rate2, projectEmployee.getHourlyrate());

        projectEmployee.setHourlyrate(rate3);
        assertEquals(rate3, projectEmployee.getHourlyrate());
    }

    @Test
    void testPlannedHoursPrecision() {
        ProjectEmployee projectEmployee = new ProjectEmployee();

        BigDecimal hours1 = new BigDecimal("80.00");
        BigDecimal hours2 = new BigDecimal("120.50");
        BigDecimal hours3 = new BigDecimal("200.25");

        projectEmployee.setPlannedhours(hours1);
        assertEquals(hours1, projectEmployee.getPlannedhours());

        projectEmployee.setPlannedhours(hours2);
        assertEquals(hours2, projectEmployee.getPlannedhours());

        projectEmployee.setPlannedhours(hours3);
        assertEquals(hours3, projectEmployee.getPlannedhours());
    }

    @Test
    void testQualificationkmuiLength() {
        ProjectEmployee projectEmployee = new ProjectEmployee();

        String shortQualification = "Dev";
        String mediumQualification = "Senior Software Engineer";
        String longQualification = "Principal Software Engineer with AI/ML Specialization";

        projectEmployee.setQualificationkmui(shortQualification);
        assertEquals(shortQualification, projectEmployee.getQualificationkmui());

        projectEmployee.setQualificationkmui(mediumQualification);
        assertEquals(mediumQualification, projectEmployee.getQualificationkmui());

        projectEmployee.setQualificationkmui(longQualification);
        assertEquals(longQualification, projectEmployee.getQualificationkmui());
    }

    @Test
    void testNullValues() {
        ProjectEmployee projectEmployee = new ProjectEmployee();

        assertNull(projectEmployee.getQualificationkmui());
        assertNull(projectEmployee.getHourlyrate());
        assertNull(projectEmployee.getPlannedhours());
        assertNull(projectEmployee.getEmployee());
        assertNull(projectEmployee.getProject());
        assertNull(projectEmployee.getVersion());
    }

    @Test
    void testEqualsAndHashCode() {
        ProjectEmployee projectEmp1 = new ProjectEmployee();
        projectEmp1.setId(1L);
        projectEmp1.setQualificationkmui(QUALIFICATION_K_MUI);
        projectEmp1.setHourlyrate(HOURLY_RATE);

        ProjectEmployee projectEmp2 = new ProjectEmployee();
        projectEmp2.setId(1L);
        projectEmp2.setQualificationkmui(QUALIFICATION_K_MUI);
        projectEmp2.setHourlyrate(HOURLY_RATE);

        assertEquals(projectEmp1, projectEmp2);
        assertEquals(projectEmp1.hashCode(), projectEmp2.hashCode());
    }

    @Test
    void testNotEquals() {
        ProjectEmployee projectEmp1 = new ProjectEmployee();
        projectEmp1.setId(1L);
        projectEmp1.setQualificationkmui("Developer");

        ProjectEmployee projectEmp2 = new ProjectEmployee();
        projectEmp2.setId(2L);
        projectEmp2.setQualificationkmui("Developer");

        assertNotEquals(projectEmp1, projectEmp2);
        assertNotEquals(projectEmp1.hashCode(), projectEmp2.hashCode());
    }

    @Test
    void testAllArgsConstructor() {
        ProjectEmployee projectEmployee = new ProjectEmployee(
                null, // employee
                HOURLY_RATE, // hourlyrate
                PLANNED_HOURS, // plannedhours
                null, // project
                QUALIFICATION_K_MUI // qualificationkmui
        );

        assertNull(projectEmployee.getId());
        assertNull(projectEmployee.getCreatedAt());
        assertNull(projectEmployee.getUpdatedAt());
        assertNull(projectEmployee.getVersion());

        assertEquals(HOURLY_RATE, projectEmployee.getHourlyrate());
        assertEquals(PLANNED_HOURS, projectEmployee.getPlannedhours());
        assertEquals(QUALIFICATION_K_MUI, projectEmployee.getQualificationkmui());
    }

    @Test
    void testNoArgsConstructor() {
        ProjectEmployee projectEmployee = new ProjectEmployee();

        assertNotNull(projectEmployee);
        assertNull(projectEmployee.getId());
        assertNull(projectEmployee.getQualificationkmui());
        assertNull(projectEmployee.getHourlyrate());
        assertNull(projectEmployee.getPlannedhours());
        assertNull(projectEmployee.getVersion());
    }

    @Test
    void testSettersAndGettersConsistency() {
        ProjectEmployee projectEmployee = new ProjectEmployee();

        projectEmployee.setQualificationkmui("Initial Value");
        assertEquals("Initial Value", projectEmployee.getQualificationkmui());

        projectEmployee.setQualificationkmui("Updated Value");
        assertEquals("Updated Value", projectEmployee.getQualificationkmui());

        BigDecimal rate1 = new BigDecimal("30.00");
        BigDecimal rate2 = new BigDecimal("40.00");

        projectEmployee.setHourlyrate(rate1);
        assertEquals(rate1, projectEmployee.getHourlyrate());

        projectEmployee.setHourlyrate(rate2);
        assertEquals(rate2, projectEmployee.getHourlyrate());

        BigDecimal hours1 = new BigDecimal("100.00");
        BigDecimal hours2 = new BigDecimal("150.00");

        projectEmployee.setPlannedhours(hours1);
        assertEquals(hours1, projectEmployee.getPlannedhours());

        projectEmployee.setPlannedhours(hours2);
        assertEquals(hours2, projectEmployee.getPlannedhours());
    }

    @Test
    void testToString() {
        ProjectEmployee projectEmployee = new ProjectEmployee();
        projectEmployee.setId(1L);
        projectEmployee.setQualificationkmui(QUALIFICATION_K_MUI);
        projectEmployee.setHourlyrate(HOURLY_RATE);
        projectEmployee.setPlannedhours(PLANNED_HOURS);

        String toStringResult = projectEmployee.toString();

        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("1"));
        assertTrue(toStringResult.contains(QUALIFICATION_K_MUI));
        assertTrue(toStringResult.contains(HOURLY_RATE.toString()));
        assertTrue(toStringResult.contains(PLANNED_HOURS.toString()));
    }

    @Test
    void testVersionField() {
        ProjectEmployee projectEmployee = new ProjectEmployee();

        projectEmployee.setVersion(0);
        assertEquals(0, projectEmployee.getVersion());

        projectEmployee.setVersion(1);
        assertEquals(1, projectEmployee.getVersion());

        projectEmployee.setVersion(10);
        assertEquals(10, projectEmployee.getVersion());
    }

    @Test
    void testDefaultValues() {
        ProjectEmployee projectEmployee = new ProjectEmployee();

        assertNull(projectEmployee.getHourlyrate());
        assertNull(projectEmployee.getPlannedhours());
        assertNull(projectEmployee.getQualificationkmui());
        assertNull(projectEmployee.getVersion());
    }

    @Test
    void testEdgeCaseHourlyRates() {
        ProjectEmployee projectEmployee = new ProjectEmployee();

        BigDecimal minRate = new BigDecimal("0.01");
        BigDecimal maxRate = new BigDecimal("99.99");
        BigDecimal zeroRate = new BigDecimal("0.00");
        BigDecimal negativeRate = new BigDecimal("-10.00");

        projectEmployee.setHourlyrate(minRate);
        assertEquals(minRate, projectEmployee.getHourlyrate());

        projectEmployee.setHourlyrate(maxRate);
        assertEquals(maxRate, projectEmployee.getHourlyrate());

        projectEmployee.setHourlyrate(zeroRate);
        assertEquals(zeroRate, projectEmployee.getHourlyrate());

        projectEmployee.setHourlyrate(negativeRate);
        assertEquals(negativeRate, projectEmployee.getHourlyrate());
    }

    @Test
    void testEdgeCasePlannedHours() {
        ProjectEmployee projectEmployee = new ProjectEmployee();

        BigDecimal minHours = new BigDecimal("0.01");
        BigDecimal largeHours = new BigDecimal("999999.99");
        BigDecimal zeroHours = new BigDecimal("0.00");
        BigDecimal negativeHours = new BigDecimal("-100.00");

        projectEmployee.setPlannedhours(minHours);
        assertEquals(minHours, projectEmployee.getPlannedhours());

        projectEmployee.setPlannedhours(largeHours);
        assertEquals(largeHours, projectEmployee.getPlannedhours());

        projectEmployee.setPlannedhours(zeroHours);
        assertEquals(zeroHours, projectEmployee.getPlannedhours());

        projectEmployee.setPlannedhours(negativeHours);
        assertEquals(negativeHours, projectEmployee.getPlannedhours());
    }

    @Test
    void testEmptyQualificationkmui() {
        ProjectEmployee projectEmployee = new ProjectEmployee();

        projectEmployee.setQualificationkmui("");
        assertEquals("", projectEmployee.getQualificationkmui());

        projectEmployee.setQualificationkmui("   ");
        assertEquals("   ", projectEmployee.getQualificationkmui());

        projectEmployee.setQualificationkmui(null);
        assertNull(projectEmployee.getQualificationkmui());
    }

    @Test
    void testEqualsWithNullValues() {
        ProjectEmployee projectEmp1 = new ProjectEmployee();
        projectEmp1.setId(1L);

        ProjectEmployee projectEmp2 = new ProjectEmployee();
        projectEmp2.setId(1L);

        assertEquals(projectEmp1, projectEmp2);
        assertEquals(projectEmp1.hashCode(), projectEmp2.hashCode());
    }
}