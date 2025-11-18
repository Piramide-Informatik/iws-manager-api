package com.iws_manager.iws_manager_api.dtos.contractordercommission;

import java.math.BigDecimal;

public record ContractOrderCommissionInputDTO(
    BigDecimal commission,
    BigDecimal fromOrderValue, 
    BigDecimal minCommission,
    BasicContractReferenceDTO basicContract
) {}