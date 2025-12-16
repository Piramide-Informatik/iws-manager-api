package com.iws_manager.iws_manager_api.dtos.absenceday;

import com.iws_manager.iws_manager_api.dtos.shared.AbsenceTypeInfoDTO;

public record AbsenceDayCountDTO(
    AbsenceTypeInfoDTO absenceType,
    Long count
) {}