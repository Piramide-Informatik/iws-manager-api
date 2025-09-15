package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class ReminderLevelTest {

    private static final String TEST_TITLE = "1. Mahnung";

    @Test
    void testReminderLevelCreation() {
        // Arrange
        BigDecimal fee = new BigDecimal("15.50");
        BigDecimal interestRate = new BigDecimal("2.50");
        Short levelNo = 1;
        
        // Act
        ReminderLevel reminderLevel = new ReminderLevel();
        reminderLevel.setFee(fee);
        reminderLevel.setInterestRate(interestRate);
        reminderLevel.setLevelNo(levelNo);
        
        // Assert
        assertEquals(fee, reminderLevel.getFee());
        assertEquals(interestRate, reminderLevel.getInterestRate());
        assertEquals(levelNo, reminderLevel.getLevelNo());
    }

    @Test
    void testReminderLevelWithAllFields() {
        // Arrange
        BigDecimal fee = new BigDecimal("25.75");
        BigDecimal interestRate = new BigDecimal("3.25");
        Short levelNo = 2;
        Short payPeriod = 14;
        String reminderText = "This is a reminder text for level 2";
        String reminderTitle = "2. Mahnung";
        
        // Act
        ReminderLevel reminderLevel = new ReminderLevel();
        reminderLevel.setId(1L);
        reminderLevel.setFee(fee);
        reminderLevel.setInterestRate(interestRate);
        reminderLevel.setLevelNo(levelNo);
        reminderLevel.setPayPeriod(payPeriod);
        reminderLevel.setReminderText(reminderText);
        reminderLevel.setReminderTitle(reminderTitle);
        
        // Assert
        assertAll("ReminderLevel properties",
            () -> assertEquals(1L, reminderLevel.getId()),
            () -> assertEquals(fee, reminderLevel.getFee()),
            () -> assertEquals(interestRate, reminderLevel.getInterestRate()),
            () -> assertEquals(levelNo, reminderLevel.getLevelNo()),
            () -> assertEquals(payPeriod, reminderLevel.getPayPeriod()),
            () -> assertEquals(reminderText, reminderLevel.getReminderText()),
            () -> assertEquals(reminderTitle, reminderLevel.getReminderTitle())
        );
    }

    @Test
    void testReminderLevelWithAuditFields() {
        // Arrange
        ReminderLevel reminderLevel = new ReminderLevel();
        reminderLevel.setReminderTitle(TEST_TITLE);
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        reminderLevel.setCreatedAt(now);
        reminderLevel.setUpdatedAt(now);
        
        // Assert
        assertEquals(now, reminderLevel.getCreatedAt());
        assertEquals(now, reminderLevel.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        ReminderLevel reminderLevel1 = new ReminderLevel();
        reminderLevel1.setReminderTitle(TEST_TITLE);
        reminderLevel1.setId(1L);
        
        ReminderLevel reminderLevel2 = new ReminderLevel();
        reminderLevel2.setReminderTitle(TEST_TITLE);
        reminderLevel2.setId(1L);
        
        // Assert
        assertEquals(reminderLevel1, reminderLevel2);
        assertEquals(reminderLevel1.hashCode(), reminderLevel2.hashCode());
    }

    @Test
    void testReminderLevelWithNullValues() {
        // Arrange & Act
        ReminderLevel reminderLevel = new ReminderLevel();
        reminderLevel.setId(1L);
        reminderLevel.setFee(null);
        reminderLevel.setInterestRate(null);
        reminderLevel.setLevelNo(null);
        reminderLevel.setPayPeriod(null);
        reminderLevel.setReminderText(null);
        reminderLevel.setReminderTitle(null);
        
        // Assert
        assertAll("ReminderLevel with null values",
            () -> assertEquals(1L, reminderLevel.getId()),
            () -> assertNull(reminderLevel.getFee()),
            () -> assertNull(reminderLevel.getInterestRate()),
            () -> assertNull(reminderLevel.getLevelNo()),
            () -> assertNull(reminderLevel.getPayPeriod()),
            () -> assertNull(reminderLevel.getReminderText()),
            () -> assertNull(reminderLevel.getReminderTitle())
        );
    }
}