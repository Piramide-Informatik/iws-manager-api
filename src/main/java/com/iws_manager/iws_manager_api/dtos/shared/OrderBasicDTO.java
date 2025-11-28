package com.iws_manager.iws_manager_api.dtos.shared;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderBasicDTO(
        Long id,
        String acronym,
        LocalDate approvalDate,
        String orderLabel,
        Integer orderNo,
        String orderTitle,
        BigDecimal orderValue,
        BigDecimal iwsProvision,
        LocalDate orderDate,
        LocalDate signatureDate) {
}