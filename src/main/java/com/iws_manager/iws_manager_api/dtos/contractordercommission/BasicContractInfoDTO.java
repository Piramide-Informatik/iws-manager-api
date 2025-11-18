package com.iws_manager.iws_manager_api.dtos.contractordercommission;

import java.time.LocalDate;

public record BasicContractInfoDTO(
    Long id,
    String contractLabel,
    Integer contractNo,
    String contractTitle,
    LocalDate confirmationDate
) {}