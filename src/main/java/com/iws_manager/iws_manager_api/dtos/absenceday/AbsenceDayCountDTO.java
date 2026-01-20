package com.iws_manager.iws_manager_api.dtos.absenceday;

import java.math.BigDecimal;

import com.iws_manager.iws_manager_api.dtos.shared.AbsenceTypeInfoDTO;

public record AbsenceDayCountDTO(
        AbsenceTypeInfoDTO absenceType,
        Long count,
        BigDecimal calculatedCount) {
}