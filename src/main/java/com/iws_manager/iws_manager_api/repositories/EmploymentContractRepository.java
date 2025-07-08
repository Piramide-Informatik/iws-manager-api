package com.iws_manager.iws_manager_api.repositories;

import java.time.LocalDate;
import java.util.List;

import com.iws_manager.iws_manager_api.models.EmploymentContract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmploymentContractRepository extends JpaRepository<EmploymentContract, Long> {
    /**
     * Finds contracts by employee ID
     * @param employeeId ID of the employee
     * @return List of the employee's contracts
     */
    List<EmploymentContract> findByEmployeeId(Long employeeId);

    /**
     * Finds contracts by customer ID
     * @param customerId ID of the customer
     * @return List of the customer's contracts
     */
    List<EmploymentContract> findByCustomerId(Long customerId);

}