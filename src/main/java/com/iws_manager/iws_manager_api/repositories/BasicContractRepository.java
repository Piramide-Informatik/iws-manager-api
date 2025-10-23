package com.iws_manager.iws_manager_api.repositories;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import com.iws_manager.iws_manager_api.models.BasicContract;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicContractRepository extends JpaRepository<BasicContract, Long> {
    @EntityGraph(attributePaths = {"contractStatus", "customer", "fundingProgram", "employeeIws"})
    List<BasicContract> findAll();

    @EntityGraph(attributePaths = {"contractStatus", "customer", "fundingProgram", "employeeIws"})
    Optional<BasicContract> findById(Long id);

    // Basic finders for each property
    @EntityGraph(attributePaths = {"contractStatus", "customer", "fundingProgram", "employeeIws"})
    List<BasicContract> findByConfirmationDate(LocalDate confirmationDate);

    @EntityGraph(attributePaths = {"contractStatus", "customer", "fundingProgram", "employeeIws"})
    List<BasicContract> findByContractLabel(String contractLabel);

    @EntityGraph(attributePaths = {"contractStatus", "customer", "fundingProgram", "employeeIws"})
    List<BasicContract> findByContractNo(Integer contractNo);

    @EntityGraph(attributePaths = {"contractStatus", "customer", "fundingProgram", "employeeIws"})
    List<BasicContract> findByContractStatusId(Long contractStatusId);
    @EntityGraph(attributePaths = {"contractStatus", "customer", "fundingProgram", "employeeIws"})
    List<BasicContract> findByContractTitle(String contractTitle);

    @EntityGraph(attributePaths = {"contractStatus", "customer", "fundingProgram", "employeeIws"})
    List<BasicContract> findByCustomerId(Long customerId);

    @EntityGraph(attributePaths = {"contractStatus", "customer", "fundingProgram", "employeeIws"})
    List<BasicContract> findByDate(LocalDate date);

    @EntityGraph(attributePaths = {"contractStatus", "customer", "fundingProgram", "employeeIws"})
    List<BasicContract> findByFundingProgramId(Long fundingProgramId);

    @EntityGraph(attributePaths = {"contractStatus", "customer", "fundingProgram", "employeeIws"})
    List<BasicContract> findByEmployeeIwsId(Long employeeIwsId);

    // Customer-specific ordered finders
    @EntityGraph(attributePaths = {"contractStatus", "customer", "fundingProgram", "employeeIws"})
    List<BasicContract> findByCustomerIdOrderByContractNoAsc(Long customerId);

    @EntityGraph(attributePaths = {"contractStatus", "customer", "fundingProgram", "employeeIws"})
    List<BasicContract> findByCustomerIdOrderByContractLabelAsc(Long customerId);

    // Date range queries
    @EntityGraph(attributePaths = {"contractStatus", "customer", "fundingProgram", "employeeIws"})
    List<BasicContract> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @EntityGraph(attributePaths = {"contractStatus", "customer", "fundingProgram", "employeeIws"})
    List<BasicContract> findByConfirmationDateBetween(LocalDate startDate, LocalDate endDate);

    // Other Queries
    @Query("SELECT MAX(b.contractNo) FROM BasicContract b")
    Optional<Integer> findMaxContractNoOptional();
}