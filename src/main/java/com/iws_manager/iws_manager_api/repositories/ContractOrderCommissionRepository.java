package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.ContractOrderCommission;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContractOrderCommissionRepository extends JpaRepository<ContractOrderCommission, Long> {
    @EntityGraph(attributePaths = {"basicContract"})
    Optional<ContractOrderCommission> findById(Long id);
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
    @EntityGraph(attributePaths = {"basicContract"})
    List<ContractOrderCommission> findByBasicContractIdAndCommissionGreaterThanEqual(
        Long basicContractId, BigDecimal commission);
    @EntityGraph(attributePaths = {"basicContract"})
    List<ContractOrderCommission> findByBasicContractIdAndCommissionLessThanEqual(
        Long basicContractId, BigDecimal commission);
    
    // FromOrderValue BY basicContract
    @EntityGraph(attributePaths = {"basicContract"})
    List<ContractOrderCommission> findByBasicContractIdAndFromOrderValueGreaterThanEqual(
        Long basicContractId, BigDecimal fromOrderValue);
    @EntityGraph(attributePaths = {"basicContract"})
    List<ContractOrderCommission> findByBasicContractIdAndFromOrderValueLessThanEqual(
        Long basicContractId, BigDecimal fromOrderValue);
    
    // MinCommission BY basicContract
    @EntityGraph(attributePaths = {"basicContract"})
    List<ContractOrderCommission> findByBasicContractIdAndMinCommissionGreaterThanEqual(
        Long basicContractId, BigDecimal minCommission);
    @EntityGraph(attributePaths = {"basicContract"})
    List<ContractOrderCommission> findByBasicContractIdAndMinCommissionLessThanEqual(
        Long basicContractId, BigDecimal minCommission);
    
    // RANGE
    List<ContractOrderCommission> findByCommissionBetween(BigDecimal minCommission, BigDecimal maxCommission);
    List<ContractOrderCommission> findByFromOrderValueBetween(BigDecimal minFromOrderValue, BigDecimal maxFromOrderValue);
    List<ContractOrderCommission> findByMinCommissionBetween(BigDecimal minMinCommission, BigDecimal maxMinCommission);
    
    // RANGE BY BASIC CONTRACT
    @EntityGraph(attributePaths = {"basicContract"})
    List<ContractOrderCommission> findByBasicContractIdAndCommissionBetween(
        Long basicContract, BigDecimal minCommission, BigDecimal maxCommission);
    @EntityGraph(attributePaths = {"basicContract"})
    List<ContractOrderCommission> findByBasicContractIdAndFromOrderValueBetween(
        Long basicContract, BigDecimal minFromOrderValue, BigDecimal maxFromOrderValue);
    @EntityGraph(attributePaths = {"basicContract"})
    List<ContractOrderCommission> findByBasicContractIdAndMinCommissionBetween(
        Long basicContract, BigDecimal minMinCommission, BigDecimal maxMinCommission);

    //SORTING
    @EntityGraph(attributePaths = {"basicContract"})
    List<ContractOrderCommission> findByBasicContractIdOrderByFromOrderValueAsc(Long basicContractId);
}