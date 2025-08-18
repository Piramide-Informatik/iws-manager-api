package com.iws_manager.iws_manager_api.services.interfaces;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.BasicContract;

public interface BasicContractService {

    BasicContract create(BasicContract subcontractProject);
    Optional<BasicContract> findById(Long id);
    List<BasicContract> findAll();
    BasicContract update(Long id, BasicContract subcontractProject);
    void delete(Long id);

    List<BasicContract> getByConfirmationDate(LocalDate confirmationDate);
    List<BasicContract> getByContractLabel(String contractLabel);
    List<BasicContract> getByContractNo(Integer contractNo);
    List<BasicContract> getByContractStatusId(Long contractStatusId);
    List<BasicContract> getByContractTitle(String contractTitle);
    List<BasicContract> getByCustomerId(Long customerId);
    List<BasicContract> getByDate(LocalDate date);
    List<BasicContract> getByFundingProgramId(Long fundingProgramId);
    List<BasicContract> getByEmployeeIwsId(Long employeeIwsId);

    // Customer-specific ordered finders
    List<BasicContract> getByCustomerIdOrderByContractNoAsc(Long customerId);
    List<BasicContract> getByCustomerIdOrderByContractLabelAsc(Long customerId);

    // Date range queries
    List<BasicContract> getByDateBetween(LocalDate startDate, LocalDate endDate);
    List<BasicContract> getByConfirmationDateBetween(LocalDate startDate, LocalDate endDate);

    List<BasicContract> findFilteredContracts(
            Long customerId,
            Long contractStatusId,
            LocalDate startDate,
            LocalDate endDate);
}
