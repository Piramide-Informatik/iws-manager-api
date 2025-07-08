package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.EmploymentContract;

public interface EmploymentContractService {
    EmploymentContract create(EmploymentContract employmentContract);
    Optional<EmploymentContract> findById(Long id);
    List<EmploymentContract> findAll();
    EmploymentContract update(Long id, EmploymentContract employmentContractDetails);
    void delete(Long id);

    List<EmploymentContract> findByEmployeeId(Long employeeId);
    List<EmploymentContract> findByCustomerId(Long customerId);
}
