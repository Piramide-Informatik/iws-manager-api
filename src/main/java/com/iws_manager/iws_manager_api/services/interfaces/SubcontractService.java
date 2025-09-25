package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.Subcontract;
import com.iws_manager.iws_manager_api.models.SubcontractProject;

public interface SubcontractService {

    Subcontract create(Subcontract subcontract);
    Optional<Subcontract> findById(Long id);
    List<Subcontract> findAll();
    Subcontract update(Long id, Subcontract subcontract);
    void delete(Long id);

    List<Subcontract> findByContractorId(Long contractorId);
    List<Subcontract> findByCustomerId(Long customerId);
    List<Subcontract> findByProjectCostCenterId(Long projectCostCenterId);
    List<Subcontract> getByCustomerIdOrderByContractorNameAsc(Long customerId);

    //calculation of amount
    List<SubcontractProject> recalculateSubcontractProjects(Long subcontractId);
    Subcontract updateAndRecalculate(Long id, Subcontract changes);
}
