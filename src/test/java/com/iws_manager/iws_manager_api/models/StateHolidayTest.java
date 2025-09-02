package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StateHolidayTest {
    Boolean isholiday = true;
    private StateHoliday stateHoliday;

    @BeforeEach
    void setUp() {
        stateHoliday = new StateHoliday();
    }

    @Test
    void testStateHolidayCreation() {
        stateHoliday.setIsholiday(isholiday);
        assertEquals(true, stateHoliday.getIsholiday());
    }

    @Test
    void testStateHolidayWithAuditFields(){
        stateHoliday.setIsholiday(isholiday);
        LocalDateTime now = LocalDateTime.now();

        stateHoliday.setCreatedAt(now);
        stateHoliday.setUpdatedAt(now);

        assertEquals(now, stateHoliday.getCreatedAt());
        assertEquals(now, stateHoliday.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        stateHoliday.setIsholiday(isholiday);
        stateHoliday.setId(1L);

        StateHoliday stateHoliday2 = new StateHoliday();
        stateHoliday2.setIsholiday(isholiday);
        stateHoliday2.setId(1L);

        assertEquals(stateHoliday, stateHoliday2);
        assertEquals(stateHoliday.hashCode(), stateHoliday2.hashCode());
    }
}