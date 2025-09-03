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
    List<ContractOrderCommission> getByBasicContractId(Long basicContractId);
    
    // Commission - Greater than or equal/Less than or equal
    List<ContractOrderCommission> getByCommissionGreaterThanEqual(BigDecimal commission);
    List<ContractOrderCommission> getByCommissionLessThanEqual(BigDecimal commission);
    
    // FromOrderValue - Greater than or equal/Less than or equal
    List<ContractOrderCommission> getByFromOrderValueGreaterThanEqual(BigDecimal fromOrderValue);
    List<ContractOrderCommission> getByFromOrderValueLessThanEqual(BigDecimal fromOrderValue);
    
    // MinCommission - Greater than or equal/Less than or equal
    List<ContractOrderCommission> getByMinCommissionGreaterThanEqual(BigDecimal minCommission);
    List<ContractOrderCommission> getByMinCommissionLessThanEqual(BigDecimal minCommission);
    
    // Commission by basicContract - Greater than or equal/Less than or equal
    List<ContractOrderCommission> getByBasicContractIdAndCommissionGreaterThanEqual(
        Long basicContractId, BigDecimal commission);
    List<ContractOrderCommission> getByBasicContractIdAndCommissionLessThanEqual(
        Long basicContractId, BigDecimal commission);
    
    // FromOrderValue by basicContract - Greater than or equal/Less than or equal
    List<ContractOrderCommission> getByBasicContractIdAndFromOrderValueGreaterThanEqual(
        Long basicContractId, BigDecimal fromOrderValue);
    List<ContractOrderCommission> getByBasicContractIdAndFromOrderValueLessThanEqual(
        Long basicContractId, BigDecimal fromOrderValue);
    
    // MinCommission by basicContract - Greater than or equal/Less than or equal
    List<ContractOrderCommission> getByBasicContractIdAndMinCommissionGreaterThanEqual(
        Long basicContractId, BigDecimal minCommission);
    List<ContractOrderCommission> getByBasicContractIdAndMinCommissionLessThanEqual(
        Long basicContractId, BigDecimal minCommission);
    
    // Additional useful methods with ranges
    List<ContractOrderCommission> getByCommissionBetween(BigDecimal minCommission, BigDecimal maxCommission);
    List<ContractOrderCommission> getByFromOrderValueBetween(BigDecimal minFromOrderValue, BigDecimal maxFromOrderValue);
    List<ContractOrderCommission> getByMinCommissionBetween(BigDecimal minMinCommission, BigDecimal maxMinCommission);
    
    // Methods with basicContract and ranges
    List<ContractOrderCommission> getByBasicContractIdAndCommissionBetween(
        Long basicContractId, BigDecimal minCommission, BigDecimal maxCommission);
    List<ContractOrderCommission> getByBasicContractIdAndFromOrderValueBetween(
        Long basicContractId, BigDecimal minFromOrderValue, BigDecimal maxFromOrderValue);
    List<ContractOrderCommission> getByBasicContractIdAndMinCommissionBetween(
        Long basicContractId, BigDecimal minMinCommission, BigDecimal maxMinCommission);

    //SORTING
    List<ContractOrderCommission> getByBasicContractIdOrderByFromOrderValueAsc(Long basicContractId);
}