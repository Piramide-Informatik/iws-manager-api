package com.iws_manager.iws_manager_api.dtos.publicholiday;

import java.time.LocalDate;

public record PublicHolidayDTO(
    Long id,
    LocalDate date,
    String name,
    Boolean isFixedDate,
    Integer sequenceNo,
    Integer version
) {}