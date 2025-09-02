package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.ContractOrderCommission;
import com.iws_manager.iws_manager_api.models.BasicContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ContractOrderCommissionRepository extends JpaRepository<ContractOrderCommission, Long> {
    
    // PROPERTIES
    List<ContractOrderCommission> findByCommission(BigDecimal commission);
    List<ContractOrderCommission> findByFromOrderValue(BigDecimal fromOrderValue);
    List<ContractOrderCommission> findByMinCommission(BigDecimal minCommission);
    List<ContractOrderCommission> findByBasicContractId(Long basicContractId);
    
    // Commission 
    List<ContractOrderCommission> findByCommissionGreaterThanEqual(BigDecimal commission);
    List<ContractOrderCommission> findByCommissionLessThanEqual(BigDecimal commission);
    
    // FromOrderValue 
    List<ContractOrderCommission> findByFromOrderValueGreaterThanEqual(BigDecimal fromOrderValue);
    List<ContractOrderCommission> findByFromOrderValueLessThanEqual(BigDecimal fromOrderValue);
    
    // MinCommission 
    List<ContractOrderCommission> findByMinCommissionGreaterThanEqual(BigDecimal minCommission);
    List<ContractOrderCommission> findByMinCommissionLessThanEqual(BigDecimal minCommission);
    
    // Commission BY BasicContract 
    List<ContractOrderCommission> findByBasicContractIdAndCommissionGreaterThanEqual(
        Long basicContractId, BigDecimal commission);
    List<ContractOrderCommission> findByBasicContractIdAndCommissionLessThanEqual(
        Long basicContractId, BigDecimal commission);
    
    // FromOrderValue BY basicContract 
    List<ContractOrderCommission> findByBasicContractIdAndFromOrderValueGreaterThanEqual(
        Long basicContractId, BigDecimal fromOrderValue);
    List<ContractOrderCommission> findByBasicContractIdAndFromOrderValueLessThanEqual(
        Long basicContractId, BigDecimal fromOrderValue);
    
    // MinCommission BY basicContract 
    List<ContractOrderCommission> findByBasicContractIdAndMinCommissionGreaterThanEqual(
        Long basicContractId, BigDecimal minCommission);
    List<ContractOrderCommission> findByBasicContractIdAndMinCommissionLessThanEqual(
        Long basicContractId, BigDecimal minCommission);
    
    // RANGE
    List<ContractOrderCommission> findByCommissionBetween(BigDecimal minCommission, BigDecimal maxCommission);
    List<ContractOrderCommission> findByFromOrderValueBetween(BigDecimal minFromOrderValue, BigDecimal maxFromOrderValue);
    List<ContractOrderCommission> findByMinCommissionBetween(BigDecimal minMinCommission, BigDecimal maxMinCommission);
    
    // RANGE BY BASIC CONTRACT
    List<ContractOrderCommission> findByBasicContractIdAndCommissionBetween(
        Long basicContract, BigDecimal minCommission, BigDecimal maxCommission);
    List<ContractOrderCommission> findByBasicContractIdAndFromOrderValueBetween(
        Long basicContract, BigDecimal minFromOrderValue, BigDecimal maxFromOrderValue);
    List<ContractOrderCommission> findByBasicContractIdAndMinCommissionBetween(
        Long basicContract, BigDecimal minMinCommission, BigDecimal maxMinCommission);
}