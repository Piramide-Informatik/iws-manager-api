package com.iws_manager.iws_manager_api.repositories;

import java.util.List;
import java.time.LocalDate;

import com.iws_manager.iws_manager_api.models.BasicContract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicContractRepository extends JpaRepository<BasicContract, Long> {

    // Basic finders for each property
    List<BasicContract> findByConfirmationDate(LocalDate confirmationDate);
    List<BasicContract> findByContractLabel(String contractLabel);
    List<BasicContract> findByContractNo(Integer contractNo);
    List<BasicContract> findByContractStatus_ContractStatusId(Long contractStatusId);
    List<BasicContract> findByContractTitle(String contractTitle);
    List<BasicContract> findByCustomer_CustomerId(Long customerId);
    List<BasicContract> findByDate(LocalDate date);
    List<BasicContract> findByFundingProgram_FundingProgramId(Long fundingProgramId);
    List<BasicContract> findByEmployeeIws_EmployeeIwsId(Long employeeIwsId);

    // Customer-specific ordered finders
    List<BasicContract> findByCustomer_CustomerIdOrderByContractNoAsc(Long customerId);
    List<BasicContract> findByCustomer_CustomerIdOrderByContractLabelAsc(Long customerId);

    // Date range queries
    List<BasicContract> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<BasicContract> findByConfirmationDateBetween(LocalDate startDate, LocalDate endDate);
}