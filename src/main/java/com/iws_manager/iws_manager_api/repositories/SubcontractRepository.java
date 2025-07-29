package com.iws_manager.iws_manager_api.repositories;

import java.util.List;
import com.iws_manager.iws_manager_api.models.Subcontract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcontractRepository extends JpaRepository<Subcontract, Long> {
    List<Subcontract> findAllByOrderByContractTitleAsc();

    List<Subcontract> findByContractorId(Long contractorId);
    List<Subcontract> findByProjectCostCenterId(Long projectCostCenterId);
    //find by customer id
    List<Subcontract> findByCustomerIdOrderByContractTitleAsc(Long customerId);
}