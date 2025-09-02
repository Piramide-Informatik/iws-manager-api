package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HolidayYearTest {

    Integer weekday = 1;
    private HolidayYear holidayYear = new HolidayYear();

    @Test
    void testHolidayYearCreation() {
        holidayYear.setWeekday(weekday);

        assertEquals(weekday, holidayYear.getWeekday());
    }

    @Test
    void testHolidayYearWithAuditFields(){
        holidayYear.setWeekday(weekday);
        LocalDateTime now = LocalDateTime.now();

        holidayYear.setCreatedAt(now);
        holidayYear.setUpdatedAt(now);

        assertEquals(now, holidayYear.getCreatedAt());
        assertEquals(now, holidayYear.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        holidayYear.setWeekday(weekday);
        holidayYear.setId(1L);

        HolidayYear holidayYear2 = new HolidayYear();
        holidayYear2.setWeekday(weekday);
        holidayYear2.setId(1L);

        assertEquals(holidayYear, holidayYear2);
        assertEquals(holidayYear.hashCode(), holidayYear2.hashCode());
    }
}