package com.iws_manager.iws_manager_api.dtos.absenceday;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iws_manager.iws_manager_api.dtos.shared.AbsenceTypeInfoDTO;
import com.iws_manager.iws_manager_api.dtos.shared.EmployeeBasicDTO;
import java.time.LocalDate;

public record AbsenceDayInfoDTO(
    Long id,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate absenceDate,
    AbsenceTypeInfoDTO absenceType,
    EmployeeBasicDTO employee,
    Integer version
) {}