package com.iws_manager.iws_manager_api.dtos.publicholiday;

import java.time.LocalDate;

/**
 * DTO for the endpoint V2 that returns holidays + weekends
 * Only contains date and name as you requested
 */
public record SimpleHolidayDTO(
    LocalDate date,
    String name
) {
    
    // Factory method for holidays from the database
    public static SimpleHolidayDTO fromHoliday(String name, LocalDate date) {
        return new SimpleHolidayDTO(date, name);
    }
    
    // Factory method for Saturday
    public static SimpleHolidayDTO forSaturday(LocalDate date) {
        return new SimpleHolidayDTO(date, "Saturday");
    }
    
    // Factory method for Sunday
    public static SimpleHolidayDTO forSunday(LocalDate date) {
        return new SimpleHolidayDTO(date, "Sunday");
    }
}