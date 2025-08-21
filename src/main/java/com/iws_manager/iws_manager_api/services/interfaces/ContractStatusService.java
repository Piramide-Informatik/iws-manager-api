package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

import com.iws_manager.iws_manager_api.models.ContractStatus;

public interface ContractStatusService {
    ContractStatus create(ContractStatus contractStatus);
    Optional<ContractStatus> findById(Long id);
    List<ContractStatus> findAll();
    ContractStatus update(Long id, ContractStatus contractStatusDetails);
    void delete(Long id);

    List<ContractStatus> getByChanceGreaterThanEqual(BigDecimal chance);
    List<ContractStatus> getByChanceLessThanEqual(BigDecimal chance);
    List<ContractStatus> getByChanceBetween(BigDecimal minChance, BigDecimal maxChance);
}
