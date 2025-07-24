package com.iws_manager.iws_manager_api.repositories;

import java.util.List;
import com.iws_manager.iws_manager_api.models.Subcontract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcontractRepository extends JpaRepository<Subcontract, Long> {
    List<Subcontract> findAllByOrderByContractTitleAsc();

    List<Subcontract> findByContractorId(Long contractorId);
    List<Subcontract> findByCustomerId(Long customerId);
    List<Subcontract> findByProjectCostCenterId(Long projectCostCenterId);
    List<Subcontract> findByCustomerIdOrderByContractTitleAsc(Long customerId);

//     List<Subcontract> findByContractor_ContractorId(Long contractorId);
// List<Subcontract> findByCustomer_CustomerId(Long customerId);
// List<Subcontract> findByProjectCostCenter_ProjectCostCenterId(Long projectCostCenterId);
// List<Subcontract> findByCustomer_CustomerIdOrderByContractTitleAsc(Long customerId);
}