package com.iws_manager.iws_manager_api.dtos.contractordercommission;

import com.iws_manager.iws_manager_api.dtos.shared.BasicReferenceDTO;
import java.math.BigDecimal;

public record ContractOrderCommissionInputDTO(
    BigDecimal commission,
    BigDecimal fromOrderValue, 
    BigDecimal minCommission,
    BasicReferenceDTO basicContract  
) {}