package com.iws_manager.iws_manager_api.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DebtTest {

    private static final Integer DEBT_NO = 1001;
    private static final Integer ALTERNATIVE_DEBT_NO = 1002;
    private static final String COMMENT = "Test comment";
    private static final String DEBT_TITLE = "Project Funding";
    private static final BigDecimal DONATION = new BigDecimal("1500.75");
    private static final BigDecimal GROSS_AMOUNT = new BigDecimal("50000.00");
    private static final BigDecimal IWS_PERCENT = new BigDecimal("15.50");
    private static final BigDecimal KMUI_0838 = new BigDecimal("100.00");
    private static final BigDecimal KMUI_0850 = new BigDecimal("200.50");
    private static final LocalDate BILLING_END = LocalDate.of(2023, 12, 31);
    private static final LocalDate BILLING_START = LocalDate.of(2023, 1, 1);
    private static final LocalDate CONF_DATE_LEVEL1 = LocalDate.of(2023, 6, 15);
    private static final LocalDate PROJECT_START = LocalDate.of(2023, 1, 1);
    private static final Integer BILLING_MONTHS = 6;
    private static final Long ENTITY_ID = 1L;
    private static final Long ALTERNATIVE_ID = 2L;
    private static final Long VERSION = 1L;

    @Test
    void testBasicDebtProperties() {
        // Act
        Debt debt = new Debt();
        debt.setBillingEnd(BILLING_END);
        debt.setBillingMonths(BILLING_MONTHS);
        debt.setComment(COMMENT);
        debt.setDebtNo(DEBT_NO);
        debt.setDebtTitle(DEBT_TITLE);
        
        // Assert
        assertEquals(BILLING_END, debt.getBillingEnd());
        assertEquals(BILLING_MONTHS, debt.getBillingMonths());
        assertEquals(COMMENT, debt.getComment());
        assertEquals(DEBT_NO, debt.getDebtNo());
        assertEquals(DEBT_TITLE, debt.getDebtTitle());
    }

    @Test
    void testDateFields() {
        // Act
        Debt debt = new Debt();
        debt.setBillingStart(BILLING_START);
        debt.setConfDateLevel1(CONF_DATE_LEVEL1);
        debt.setProjectStart(PROJECT_START);
        
        // Assert
        assertEquals(BILLING_START, debt.getBillingStart());
        assertEquals(CONF_DATE_LEVEL1, debt.getConfDateLevel1());
        assertEquals(PROJECT_START, debt.getProjectStart());
    }

    @Test
    void testDecimalFields() {
        // Act
        Debt debt = new Debt();
        debt.setDonation(DONATION);
        debt.setGrossAmount(GROSS_AMOUNT);
        debt.setIwsPercent(IWS_PERCENT);
        
        // Assert
        assertEquals(0, DONATION.compareTo(debt.getDonation()));
        assertEquals(0, GROSS_AMOUNT.compareTo(debt.getGrossAmount()));
        assertEquals(0, IWS_PERCENT.compareTo(debt.getIwsPercent()));
        assertEquals(2, debt.getDonation().scale());
    }

    @Test
    void testAuditFields() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        Debt debt = new Debt();
        debt.setCreatedAt(now);
        debt.setUpdatedAt(now);
        debt.setVersion(VERSION);
        
        // Assert
        assertEquals(now, debt.getCreatedAt());
        assertEquals(now, debt.getUpdatedAt());
        assertEquals(VERSION, debt.getVersion());
    }

    @Test
    void testEqualsAndHashCodeBasedOnId() {
        // Arrange
        Debt debt1 = createBasicDebt(ENTITY_ID, DEBT_NO);
        Debt debt2 = createBasicDebt(ENTITY_ID, ALTERNATIVE_DEBT_NO);
        Debt debt3 = createBasicDebt(ALTERNATIVE_ID, DEBT_NO);
        
        // Assert
        assertEquals(debt1, debt2);
        assertNotEquals(debt1, debt3);
        assertEquals(debt1.hashCode(), debt2.hashCode());
        assertNotEquals(debt1.hashCode(), debt3.hashCode());
    }

    @Test
    void testKMUIFields() {
        // Act
        Debt debt = new Debt();
        debt.setKmui0838(KMUI_0838);
        debt.setKmui0850(KMUI_0850);
        
        // Assert
        assertEquals(0, KMUI_0838.compareTo(debt.getKmui0838()));
        assertEquals(0, KMUI_0850.compareTo(debt.getKmui0850()));
    }

    private Debt createBasicDebt(Long id, Integer debtNo) {
        Debt debt = new Debt();
        debt.setId(id);
        debt.setDebtNo(debtNo);
        return debt;
    }
}