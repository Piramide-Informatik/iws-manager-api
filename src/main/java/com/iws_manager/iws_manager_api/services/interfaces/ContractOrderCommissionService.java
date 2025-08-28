package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.ContractOrderCommission;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing ContractOrderCommission entities.
 * Provides methods for CRUD operations and various query operations.
 */
public interface ContractOrderCommissionService {
    
    // Basic CRUD operations
    ContractOrderCommission create(ContractOrderCommission contractOrderCommission);
    Optional<ContractOrderCommission> findById(Long id);
    List<ContractOrderCommission> findAll();
    ContractOrderCommission update(Long id, ContractOrderCommission contractOrderCommissionDetails);
    void delete(Long id);
    
    // Get all ContractOrderCommission by each property
    List<ContractOrderCommission> getByCommission(BigDecimal commission);
    List<ContractOrderCommission> getByFromOrderValue(BigDecimal fromOrderValue);
    List<ContractOrderCommission> getByMinCommission(BigDecimal minCommission);
    List<ContractOrderCommission> getByEmploymentContractId(Long employmentContractId);
    
    // Commission - Greater than or equal/Less than or equal
    List<ContractOrderCommission> getByCommissionGreaterThanEqual(BigDecimal commission);
    List<ContractOrderCommission> getByCommissionLessThanEqual(BigDecimal commission);
    
    // FromOrderValue - Greater than or equal/Less than or equal
    List<ContractOrderCommission> getByFromOrderValueGreaterThanEqual(BigDecimal fromOrderValue);
    List<ContractOrderCommission> getByFromOrderValueLessThanEqual(BigDecimal fromOrderValue);
    
    // MinCommission - Greater than or equal/Less than or equal
    List<ContractOrderCommission> getByMinCommissionGreaterThanEqual(BigDecimal minCommission);
    List<ContractOrderCommission> getByMinCommissionLessThanEqual(BigDecimal minCommission);
    
    // Commission by employmentContract - Greater than or equal/Less than or equal
    List<ContractOrderCommission> getByEmploymentContractIdAndCommissionGreaterThanEqual(
        Long employmentContractId, BigDecimal commission);
    List<ContractOrderCommission> getByEmploymentContractIdAndCommissionLessThanEqual(
        Long employmentContractId, BigDecimal commission);
    
    // FromOrderValue by employmentContract - Greater than or equal/Less than or equal
    List<ContractOrderCommission> getByEmploymentContractIdAndFromOrderValueGreaterThanEqual(
        Long employmentContractId, BigDecimal fromOrderValue);
    List<ContractOrderCommission> getByEmploymentContractIdAndFromOrderValueLessThanEqual(
        Long employmentContractId, BigDecimal fromOrderValue);
    
    // MinCommission by employmentContract - Greater than or equal/Less than or equal
    List<ContractOrderCommission> getByEmploymentContractIdAndMinCommissionGreaterThanEqual(
        Long employmentContractId, BigDecimal minCommission);
    List<ContractOrderCommission> getByEmploymentContractIdAndMinCommissionLessThanEqual(
        Long employmentContractId, BigDecimal minCommission);
    
    // Additional useful methods with ranges
    List<ContractOrderCommission> getByCommissionBetween(BigDecimal minCommission, BigDecimal maxCommission);
    List<ContractOrderCommission> getByFromOrderValueBetween(BigDecimal minFromOrderValue, BigDecimal maxFromOrderValue);
    List<ContractOrderCommission> getByMinCommissionBetween(BigDecimal minMinCommission, BigDecimal maxMinCommission);
    
    // Methods with employmentContract and ranges
    List<ContractOrderCommission> getByEmploymentContractIdAndCommissionBetween(
        Long employmentContractId, BigDecimal minCommission, BigDecimal maxCommission);
    List<ContractOrderCommission> getByEmploymentContractIdAndFromOrderValueBetween(
        Long employmentContractId, BigDecimal minFromOrderValue, BigDecimal maxFromOrderValue);
    List<ContractOrderCommission> getByEmploymentContractIdAndMinCommissionBetween(
        Long employmentContractId, BigDecimal minMinCommission, BigDecimal maxMinCommission);
}