package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class OrderEmployeeTest {
    private static final String QUALIFICATION_K_MUI = "Senior Developer";
    private static final String TITLE = "FZ-Kurzbezeichnung FuE-Tätigkeit";
    private static final BigDecimal HOURLY_RATE = new BigDecimal("45.50");
    private static final BigDecimal PLANNED_HOURS = new BigDecimal("160.00");

    @Test
    void testOrderEmployeeCreation() {
        OrderEmployee orderEmployee = new OrderEmployee();
        orderEmployee.setQualificationkmui(QUALIFICATION_K_MUI);
        orderEmployee.setTitle(TITLE);
        orderEmployee.setHourlyrate(HOURLY_RATE);
        orderEmployee.setPlannedhours(PLANNED_HOURS);

        assertEquals(QUALIFICATION_K_MUI, orderEmployee.getQualificationkmui());
        assertEquals(TITLE, orderEmployee.getTitle());
        assertEquals(HOURLY_RATE, orderEmployee.getHourlyrate());
        assertEquals(PLANNED_HOURS, orderEmployee.getPlannedhours());
    }

    @Test
    void testHourlyRatePrecision() {
        OrderEmployee orderEmployee = new OrderEmployee();

        BigDecimal rate1 = new BigDecimal("25.00");
        BigDecimal rate2 = new BigDecimal("35.75");
        BigDecimal rate3 = new BigDecimal("99.99");

        orderEmployee.setHourlyrate(rate1);
        assertEquals(rate1, orderEmployee.getHourlyrate());

        orderEmployee.setHourlyrate(rate2);
        assertEquals(rate2, orderEmployee.getHourlyrate());

        orderEmployee.setHourlyrate(rate3);
        assertEquals(rate3, orderEmployee.getHourlyrate());
    }

    @Test
    void testPlannedHoursPrecision() {
        OrderEmployee orderEmployee = new OrderEmployee();

        BigDecimal hours1 = new BigDecimal("80.00");
        BigDecimal hours2 = new BigDecimal("120.50");
        BigDecimal hours3 = new BigDecimal("200.25");

        orderEmployee.setPlannedhours(hours1);
        assertEquals(hours1, orderEmployee.getPlannedhours());

        orderEmployee.setPlannedhours(hours2);
        assertEquals(hours2, orderEmployee.getPlannedhours());

        orderEmployee.setPlannedhours(hours3);
        assertEquals(hours3, orderEmployee.getPlannedhours());
    }

    @Test
    void testQualificationkmuiLength() {
        OrderEmployee orderEmployee = new OrderEmployee();

        String shortQualification = "Dev";
        String mediumQualification = "Senior Software Engineer";
        String longQualification = "Principal Software Engineer with AI/ML Specialization";

        orderEmployee.setQualificationkmui(shortQualification);
        assertEquals(shortQualification, orderEmployee.getQualificationkmui());

        orderEmployee.setQualificationkmui(mediumQualification);
        assertEquals(mediumQualification, orderEmployee.getQualificationkmui());

        orderEmployee.setQualificationkmui(longQualification);
        assertEquals(longQualification, orderEmployee.getQualificationkmui());
    }

    @Test
    void testTitleLength() {
        OrderEmployee orderEmployee = new OrderEmployee();

        String shortTitle = "Dev";
        String mediumTitle = "Software Development";
        String longTitle = "Research and Development in Artificial Intelligence Systems";

        orderEmployee.setTitle(shortTitle);
        assertEquals(shortTitle, orderEmployee.getTitle());

        orderEmployee.setTitle(mediumTitle);
        assertEquals(mediumTitle, orderEmployee.getTitle());

        orderEmployee.setTitle(longTitle);
        assertEquals(longTitle, orderEmployee.getTitle());
    }

    @Test
    void testNullValues() {
        OrderEmployee orderEmployee = new OrderEmployee();

        assertNull(orderEmployee.getQualificationkmui());
        assertNull(orderEmployee.getTitle());
        assertNull(orderEmployee.getHourlyrate());
        assertNull(orderEmployee.getPlannedhours());
        assertNull(orderEmployee.getEmployee());
        assertNull(orderEmployee.getOrder());
        assertNull(orderEmployee.getQualificationFZ());

        assertNull(orderEmployee.getVersion());
    }

    @Test
    void testEqualsAndHashCode() {
        OrderEmployee orderEmp1 = new OrderEmployee();
        orderEmp1.setId(1L);
        orderEmp1.setQualificationkmui(QUALIFICATION_K_MUI);
        orderEmp1.setTitle(TITLE);

        OrderEmployee orderEmp2 = new OrderEmployee();
        orderEmp2.setId(1L);
        orderEmp2.setQualificationkmui(QUALIFICATION_K_MUI);
        orderEmp2.setTitle(TITLE);

        assertEquals(orderEmp1, orderEmp2);
        assertEquals(orderEmp1.hashCode(), orderEmp2.hashCode());
    }

    @Test
    void testNotEquals() {
        OrderEmployee orderEmp1 = new OrderEmployee();
        orderEmp1.setId(1L);
        orderEmp1.setQualificationkmui("Developer");

        OrderEmployee orderEmp2 = new OrderEmployee();
        orderEmp2.setId(2L);
        orderEmp2.setQualificationkmui("Developer");

        assertNotEquals(orderEmp1, orderEmp2);
        assertNotEquals(orderEmp1.hashCode(), orderEmp2.hashCode());
    }

    @Test
    void testOrderEmployeeWithAuditFields() {
        OrderEmployee orderEmployee = new OrderEmployee();
        orderEmployee.setQualificationkmui(QUALIFICATION_K_MUI);
        orderEmployee.setTitle(TITLE);

        LocalDateTime now = LocalDateTime.now();
        orderEmployee.setCreatedAt(now);
        orderEmployee.setUpdatedAt(now);
        orderEmployee.setVersion(0);

        assertEquals(QUALIFICATION_K_MUI, orderEmployee.getQualificationkmui());
        assertEquals(TITLE, orderEmployee.getTitle());
        assertEquals(now, orderEmployee.getCreatedAt());
        assertEquals(now, orderEmployee.getUpdatedAt());
        assertEquals(0, orderEmployee.getVersion());
    }

    @Test
    void testToString() {
        OrderEmployee orderEmployee = new OrderEmployee();
        orderEmployee.setId(1L);
        orderEmployee.setQualificationkmui(QUALIFICATION_K_MUI);
        orderEmployee.setTitle(TITLE);
        orderEmployee.setHourlyrate(HOURLY_RATE);
        orderEmployee.setPlannedhours(PLANNED_HOURS);

        String toStringResult = orderEmployee.toString();

        assertNotNull(toStringResult);
        assertTrue(toStringResult.contains("1"));
        assertTrue(toStringResult.contains(QUALIFICATION_K_MUI));
        assertTrue(toStringResult.contains(TITLE));
    }

    @Test
    void testAllArgsConstructor() {
        OrderEmployee orderEmployee = new OrderEmployee(
                null, // employee
                HOURLY_RATE, // hourlyrate
                PLANNED_HOURS, // plannedhours
                null, // order
                null, // qualificationFZ
                QUALIFICATION_K_MUI, // qualificationkmui
                TITLE // title
        );

        assertNull(orderEmployee.getId());
        assertNull(orderEmployee.getCreatedAt());
        assertNull(orderEmployee.getUpdatedAt());
        assertNull(orderEmployee.getVersion());

        assertEquals(HOURLY_RATE, orderEmployee.getHourlyrate());
        assertEquals(PLANNED_HOURS, orderEmployee.getPlannedhours());
        assertEquals(QUALIFICATION_K_MUI, orderEmployee.getQualificationkmui());
        assertEquals(TITLE, orderEmployee.getTitle());
    }

    @Test
    void testNoArgsConstructor() {
        OrderEmployee orderEmployee = new OrderEmployee();

        assertNotNull(orderEmployee);
        assertNull(orderEmployee.getId());
        assertNull(orderEmployee.getQualificationkmui());
        assertNull(orderEmployee.getTitle());
        assertNull(orderEmployee.getHourlyrate());
        assertNull(orderEmployee.getPlannedhours());
        assertNull(orderEmployee.getVersion());
    }

    @Test
    void testSettersAndGettersConsistency() {
        OrderEmployee orderEmployee = new OrderEmployee();

        orderEmployee.setQualificationkmui("Initial Value");
        assertEquals("Initial Value", orderEmployee.getQualificationkmui());

        orderEmployee.setQualificationkmui("Updated Value");
        assertEquals("Updated Value", orderEmployee.getQualificationkmui());

        orderEmployee.setTitle("Initial Title");
        assertEquals("Initial Title", orderEmployee.getTitle());

        orderEmployee.setTitle("Updated Title");
        assertEquals("Updated Title", orderEmployee.getTitle());

        BigDecimal rate1 = new BigDecimal("30.00");
        BigDecimal rate2 = new BigDecimal("40.00");

        orderEmployee.setHourlyrate(rate1);
        assertEquals(rate1, orderEmployee.getHourlyrate());

        orderEmployee.setHourlyrate(rate2);
        assertEquals(rate2, orderEmployee.getHourlyrate());

        BigDecimal hours1 = new BigDecimal("100.00");
        BigDecimal hours2 = new BigDecimal("150.00");

        orderEmployee.setPlannedhours(hours1);
        assertEquals(hours1, orderEmployee.getPlannedhours());

        orderEmployee.setPlannedhours(hours2);
        assertEquals(hours2, orderEmployee.getPlannedhours());

        orderEmployee.setVersion(0);
        assertEquals(0, orderEmployee.getVersion());

        orderEmployee.setVersion(1);
        assertEquals(1, orderEmployee.getVersion());

        orderEmployee.setVersion(10);
        assertEquals(10, orderEmployee.getVersion());
    }

    @Test
    void testVersionFieldType() {
        OrderEmployee orderEmployee = new OrderEmployee();

        orderEmployee.setVersion(0);
        assertTrue(orderEmployee.getVersion() instanceof Integer);

        orderEmployee.setVersion(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, orderEmployee.getVersion());

        orderEmployee.setVersion(100);
        assertEquals(100, orderEmployee.getVersion());
    }

    @Test
    void testDefaultVersionValue() {
        OrderEmployee orderEmployee = new OrderEmployee();
        assertNull(orderEmployee.getVersion());

        orderEmployee.setVersion(0);
        assertEquals(0, orderEmployee.getVersion());
    }

    @Test
    void testBuilderPattern() {
        OrderEmployee orderEmployee = new OrderEmployee();
        orderEmployee.setId(1L);
        orderEmployee.setQualificationkmui(QUALIFICATION_K_MUI);
        orderEmployee.setTitle(TITLE);
        orderEmployee.setHourlyrate(HOURLY_RATE);
        orderEmployee.setPlannedhours(PLANNED_HOURS);
        orderEmployee.setVersion(0);

        assertEquals(1L, orderEmployee.getId());
        assertEquals(QUALIFICATION_K_MUI, orderEmployee.getQualificationkmui());
        assertEquals(TITLE, orderEmployee.getTitle());
        assertEquals(HOURLY_RATE, orderEmployee.getHourlyrate());
        assertEquals(PLANNED_HOURS, orderEmployee.getPlannedhours());
        assertEquals(0, orderEmployee.getVersion());
    }
}