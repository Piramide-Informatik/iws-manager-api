package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.ContractOrderCommission;
import com.iws_manager.iws_manager_api.models.EmploymentContract;
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
    List<ContractOrderCommission> findByEmploymentContractId(Long employmentContractId);
    
    // Commission 
    List<ContractOrderCommission> findByCommissionGreaterThanEqual(BigDecimal commission);
    List<ContractOrderCommission> findByCommissionLessThanEqual(BigDecimal commission);
    
    // FromOrderValue 
    List<ContractOrderCommission> findByFromOrderValueGreaterThanEqual(BigDecimal fromOrderValue);
    List<ContractOrderCommission> findByFromOrderValueLessThanEqual(BigDecimal fromOrderValue);
    
    // MinCommission 
    List<ContractOrderCommission> findByMinCommissionGreaterThanEqual(BigDecimal minCommission);
    List<ContractOrderCommission> findByMinCommissionLessThanEqual(BigDecimal minCommission);
    
    // Commission BY employmentContract 
    List<ContractOrderCommission> findByEmploymentContractIdAndCommissionGreaterThanEqual(
        Long employmentContractId, BigDecimal commission);
    List<ContractOrderCommission> findByEmploymentContractIdAndCommissionLessThanEqual(
        Long employmentContractId, BigDecimal commission);
    
    // FromOrderValue BY employmentContract 
    List<ContractOrderCommission> findByEmploymentContractIdAndFromOrderValueGreaterThanEqual(
        Long employmentContractId, BigDecimal fromOrderValue);
    List<ContractOrderCommission> findByEmploymentContractIdAndFromOrderValueLessThanEqual(
        Long employmentContractId, BigDecimal fromOrderValue);
    
    // MinCommission BY employmentContract 
    List<ContractOrderCommission> findByEmploymentContractIdAndMinCommissionGreaterThanEqual(
        Long employmentContractId, BigDecimal minCommission);
    List<ContractOrderCommission> findByEmploymentContractIdAndMinCommissionLessThanEqual(
        Long employmentContractId, BigDecimal minCommission);
    
    // RANGE
    List<ContractOrderCommission> findByCommissionBetween(BigDecimal minCommission, BigDecimal maxCommission);
    List<ContractOrderCommission> findByFromOrderValueBetween(BigDecimal minFromOrderValue, BigDecimal maxFromOrderValue);
    List<ContractOrderCommission> findByMinCommissionBetween(BigDecimal minMinCommission, BigDecimal maxMinCommission);
    
    // RANGE BY EMPLOYMENT CONTRACT
    List<ContractOrderCommission> findByEmploymentContractIdAndCommissionBetween(
        Long employmentContract, BigDecimal minCommission, BigDecimal maxCommission);
    List<ContractOrderCommission> findByEmploymentContractIdAndFromOrderValueBetween(
        Long employmentContract, BigDecimal minFromOrderValue, BigDecimal maxFromOrderValue);
    List<ContractOrderCommission> findByEmploymentContractIdAndMinCommissionBetween(
        Long employmentContract, BigDecimal minMinCommission, BigDecimal maxMinCommission);
}