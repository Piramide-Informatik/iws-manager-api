package com.iws_manager.iws_manager_api.dtos.contractordercommission;

import java.math.BigDecimal;

public record ContractOrderCommissionDTO(
    Long id,
    BigDecimal commission,
    BigDecimal fromOrderValue,
    BigDecimal minCommission, 
    Long basicContractId,
    String contractLabel,
    Integer contractNo,
    Integer version
) {}