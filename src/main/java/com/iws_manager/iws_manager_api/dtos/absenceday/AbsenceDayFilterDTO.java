package com.iws_manager.iws_manager_api.dtos.absenceday;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record AbsenceDayFilterDTO(
    Long employeeId,
    Long absenceTypeId,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate startDate,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate endDate,
    Integer year,
    Boolean includePublicHolidays
) {}