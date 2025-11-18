package com.iws_manager.iws_manager_api.dtos.contractordercommission;

import java.math.BigDecimal;

public record ContractOrderCommissionDetailDTO(
    Long id,
    BigDecimal commission,
    BigDecimal fromOrderValue, 
    BigDecimal minCommission,
    BasicContractInfoDTO basicContract
) {}