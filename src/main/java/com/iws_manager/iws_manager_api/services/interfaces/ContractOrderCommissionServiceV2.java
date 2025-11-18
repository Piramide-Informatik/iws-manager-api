package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.dtos.contractordercommission.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing ContractOrderCommission entities.
 * Provides methods for CRUD operations and various query operations.
 */
public interface ContractOrderCommissionServiceV2  {
    
    // Basic CRUD operations - AHORA CON DTOs
    ContractOrderCommissionDTO create(ContractOrderCommissionInputDTO dto);
    Optional<ContractOrderCommissionDetailDTO> findById(Long id);
    List<ContractOrderCommissionDTO> findAll();
    ContractOrderCommissionDTO update(Long id, ContractOrderCommissionInputDTO dto);
    void delete(Long id);
    
    // Get all ContractOrderCommission by each property - AHORA CON DTOs
    List<ContractOrderCommissionDTO> getByCommission(BigDecimal commission);
    List<ContractOrderCommissionDTO> getByFromOrderValue(BigDecimal fromOrderValue);
    List<ContractOrderCommissionDTO> getByMinCommission(BigDecimal minCommission);
    List<ContractOrderCommissionDTO> getByBasicContractId(Long basicContractId);
    
    // Commission - Greater than or equal/Less than or equal - AHORA CON DTOs
    List<ContractOrderCommissionDTO> getByCommissionGreaterThanEqual(BigDecimal commission);
    List<ContractOrderCommissionDTO> getByCommissionLessThanEqual(BigDecimal commission);
    
    // FromOrderValue - Greater than or equal/Less than or equal - AHORA CON DTOs
    List<ContractOrderCommissionDTO> getByFromOrderValueGreaterThanEqual(BigDecimal fromOrderValue);
    List<ContractOrderCommissionDTO> getByFromOrderValueLessThanEqual(BigDecimal fromOrderValue);
    
    // MinCommission - Greater than or equal/Less than or equal - AHORA CON DTOs
    List<ContractOrderCommissionDTO> getByMinCommissionGreaterThanEqual(BigDecimal minCommission);
    List<ContractOrderCommissionDTO> getByMinCommissionLessThanEqual(BigDecimal minCommission);
    
    // Commission by basicContract - Greater than or equal/Less than or equal - AHORA CON DTOs
    List<ContractOrderCommissionDTO> getByBasicContractIdAndCommissionGreaterThanEqual(
        Long basicContractId, BigDecimal commission);
    List<ContractOrderCommissionDTO> getByBasicContractIdAndCommissionLessThanEqual(
        Long basicContractId, BigDecimal commission);
    
    // FromOrderValue by basicContract - Greater than or equal/Less than or equal - AHORA CON DTOs
    List<ContractOrderCommissionDTO> getByBasicContractIdAndFromOrderValueGreaterThanEqual(
        Long basicContractId, BigDecimal fromOrderValue);
    List<ContractOrderCommissionDTO> getByBasicContractIdAndFromOrderValueLessThanEqual(
        Long basicContractId, BigDecimal fromOrderValue);
    
    // MinCommission by basicContract - Greater than or equal/Less than or equal - AHORA CON DTOs
    List<ContractOrderCommissionDTO> getByBasicContractIdAndMinCommissionGreaterThanEqual(
        Long basicContractId, BigDecimal minCommission);
    List<ContractOrderCommissionDTO> getByBasicContractIdAndMinCommissionLessThanEqual(
        Long basicContractId, BigDecimal minCommission);
    
    // Additional useful methods with ranges - AHORA CON DTOs
    List<ContractOrderCommissionDTO> getByCommissionBetween(BigDecimal minCommission, BigDecimal maxCommission);
    List<ContractOrderCommissionDTO> getByFromOrderValueBetween(BigDecimal minFromOrderValue, BigDecimal maxFromOrderValue);
    List<ContractOrderCommissionDTO> getByMinCommissionBetween(BigDecimal minMinCommission, BigDecimal maxMinCommission);
    
    // Methods with basicContract and ranges - AHORA CON DTOs
    List<ContractOrderCommissionDTO> getByBasicContractIdAndCommissionBetween(
        Long basicContractId, BigDecimal minCommission, BigDecimal maxCommission);
    List<ContractOrderCommissionDTO> getByBasicContractIdAndFromOrderValueBetween(
        Long basicContractId, BigDecimal minFromOrderValue, BigDecimal maxFromOrderValue);
    List<ContractOrderCommissionDTO> getByBasicContractIdAndMinCommissionBetween(
        Long basicContractId, BigDecimal minMinCommission, BigDecimal maxMinCommission);

    // SORTING - AHORA CON DTOs
    List<ContractOrderCommissionDTO> getByBasicContractIdOrderByFromOrderValueAsc(Long basicContractId);
}