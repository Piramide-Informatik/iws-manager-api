package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PublicHolidayTest {

    String name = "holiday";
    PublicHoliday publicHoliday = new PublicHoliday();

    @Test
    void testPublicHolidayCreation() {
        publicHoliday.setName(name);

        assertEquals(name, publicHoliday.getName());
    }

    @Test
    void testPublicHolidayWithAuditFields(){
        // Arrange
        publicHoliday.setName(name);
        LocalDateTime now = LocalDateTime.now();

        // Act
        publicHoliday.setCreatedAt(now);
        publicHoliday.setUpdatedAt(now);

        // Assert
        assertEquals(now, publicHoliday.getCreatedAt());
        assertEquals(now, publicHoliday.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode(){
        // Arrange
        publicHoliday.setName(name);
        publicHoliday.setId(1L);

        PublicHoliday publicHoliday2 = new PublicHoliday();
        publicHoliday2.setName(name);
        publicHoliday2.setId(1L);

        // Assert
        assertEquals(publicHoliday, publicHoliday2);
        assertEquals(publicHoliday.hashCode(), publicHoliday2.hashCode());
    }
}
