package com.iws_manager.iws_manager_api.dtos.contractordercommission;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContractOrderCommissionDetailDTO(
    Long id,
    BigDecimal commission,
    BigDecimal fromOrderValue, 
    BigDecimal minCommission,
    BasicContractInfoDTO basicContract
) {}

record BasicContractInfoDTO(
    Long id,
    String contractLabel,
    Integer contractNo,
    String contractTitle,
    LocalDate confirmationDate
) {}