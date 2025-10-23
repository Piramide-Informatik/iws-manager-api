package com.iws_manager.iws_manager_api.repositories;

import java.util.List;

import com.iws_manager.iws_manager_api.models.Subcontract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcontractRepository extends JpaRepository<Subcontract, Long> {
    @EntityGraph(attributePaths = {"contractor", "customer", "projectCostCenter"})
    List<Subcontract> findAllByOrderByContractTitleAsc();

    @EntityGraph(attributePaths = {"contractor", "customer", "projectCostCenter"})
    List<Subcontract> findByContractorId(Long contractorId);

    @EntityGraph(attributePaths = {"contractor", "customer", "projectCostCenter"})
    List<Subcontract> findByProjectCostCenterId(Long projectCostCenterId);
    //find by customer id
    @EntityGraph(attributePaths = {"contractor", "customer", "projectCostCenter"})
    List<Subcontract> findByCustomerIdOrderByContractTitleAsc(Long customerId);

    @EntityGraph(attributePaths = {"contractor", "customer", "projectCostCenter"})
    List<Subcontract> findByCustomerIdOrderByContractor_NameAsc(Long customerId);
}