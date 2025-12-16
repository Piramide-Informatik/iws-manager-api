package com.iws_manager.iws_manager_api.dtos.absenceday;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iws_manager.iws_manager_api.dtos.shared.AbsenceTypeInfoDTO;
import com.iws_manager.iws_manager_api.dtos.shared.EmployeeInfoDTO;
import java.time.LocalDateTime;
import java.time.LocalDate;

public record AbsenceDayDetailDTO(
    Long id,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate absenceDate,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime updatedAt,
    Integer version,

    AbsenceTypeInfoDTO absenceType,
    EmployeeInfoDTO employee
) {}