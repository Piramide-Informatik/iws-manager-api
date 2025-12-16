package com.iws_manager.iws_manager_api.dtos.shared;

import java.math.BigDecimal;

public record AbsenceTypeInfoDTO(
    Long id,
    String name,
    String label,
    Byte hours,
    Byte isHoliday,
    BigDecimal shareOfDay,
    Integer version
) {}