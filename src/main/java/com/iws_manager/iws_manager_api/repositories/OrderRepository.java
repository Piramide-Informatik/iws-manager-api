package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findAllByOrderByOrderTitleAsc();

    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByCustomerIdOrderByOrderTitleAsc(Long customerId);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByCustomerIdOrderByOrderLabelAsc(Long customerId);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByCustomerIdOrderByOrderNoAsc(Long customerId);

    //PROPERTIES
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByAcronym(String acronym);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByApprovalDate(LocalDate approvalDate);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByApprovalStatusId(Long approvalStatusId);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByBasiccontractId(Long basiccontractId);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByContractData1(String contractData1);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByContractData2(String contractData2);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByContractorId(Long contractorId);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByContractStatusId(Long contractStatusId);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByCustomerId(Long customerId);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByEmployeeIwsId(Long employeeIwsId);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByFixCommission(BigDecimal fixCommission);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByFundingProgramId(Long fundingProgramId);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByIwsProvision(BigDecimal iwsProvision);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByMaxCommission(BigDecimal maxCommission);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByNextDeptDate(LocalDate nextDeptDate);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByNoOfDepts(Integer noOfDepts);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByOrderDate(LocalDate orderDate);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByOrderLabel(String orderLabel);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByOrderNo(Integer orderNo);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByOrderTypeId(Long orderTypeId);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByOrderTitle(String orderTitle);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByOrderValue(BigDecimal orderValue);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByProjectId(Long projectId);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByPromoterId(Long promoterId);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findBySignatureDate(LocalDate signatureDate);

    //HELPERS
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByOrderValueLessThanEqual(BigDecimal value);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByOrderValueGreaterThanEqual(BigDecimal value);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByOrderValueBetween(BigDecimal startValue, BigDecimal endValue);

    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByApprovalDateBetween(LocalDate start, LocalDate end);
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByApprovalDateIsNull();
    @EntityGraph(attributePaths = { "approvalStatus", "basiccontract", "contractor", "contractStatus", "customer", "employeeIws", "fundingProgram", "orderType", "project", "promoter" })
    List<Order> findByApprovalDateIsNotNull();
}
