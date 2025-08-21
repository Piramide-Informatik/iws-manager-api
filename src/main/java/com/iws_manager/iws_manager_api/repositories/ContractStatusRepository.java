package com.iws_manager.iws_manager_api.repositories;

import java.math.BigDecimal;
import java.util.List;

import com.iws_manager.iws_manager_api.models.ContractStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractStatusRepository extends JpaRepository<ContractStatus, Long> {
    List<ContractStatus> findAllByOrderByStatusAsc();

    List<ContractStatus> findByChanceGreaterThanEqual(BigDecimal chance);
    List<ContractStatus> findByChanceLessThanEqual(BigDecimal chance);
    List<ContractStatus> findByChanceBetween(BigDecimal minChance, BigDecimal maxChance);
}