package com.iws_manager.iws_manager_api.dtos.absenceday;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iws_manager.iws_manager_api.dtos.shared.BasicReferenceDTO;
import java.time.LocalDate;

public record AbsenceDayRequestDTO(
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate absenceDate,
    BasicReferenceDTO absenceType,
    BasicReferenceDTO employee
) {}