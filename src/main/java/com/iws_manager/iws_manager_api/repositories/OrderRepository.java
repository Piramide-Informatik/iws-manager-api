package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByOrderByOrderTitleAsc();

    //PROPERTIES
    List<Order> findByAcronym(String acronym);
    List<Order> findByApprovalDate(LocalDate approvalDate);
    List<Order> findByApprovalStatusId(Long approvalStatusId);
    List<Order> findByContractData1(String contractData1);
    List<Order> findByContractData2(String contractData2);
    List<Order> findByContractorId(Long contractorId);
    List<Order> findByContractStatusId(Long contractStatusId);
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByEmployeeIwsId(Long employeeIwsId);
    List<Order> findByFixCommission(BigDecimal fixCommission);
    List<Order> findByFundingProgramId(Long fundingProgramId);
    List<Order> findByIwsProvision(BigDecimal iwsProvision);
    List<Order> findByMaxCommission(BigDecimal maxCommission);
    List<Order> findByNextDeptDate(LocalDate nextDeptDate);
    List<Order> findByNoOfDepts(Integer noOfDepts);
    List<Order> findByOrderDate(LocalDate orderDate);
    List<Order> findByOrderLabel(String orderLabel);
    List<Order> findByOrderNo(Integer orderNo);
    List<Order> findByOrderTypeId(Long orderTypeId);
    List<Order> findByOrderTitle(String orderTitle);
    List<Order> findByOrderValue(BigDecimal orderValue);
    List<Order> findByProjectId(Long projectId);
    List<Order> findByPromoterId(Long promoterId);
    List<Order> findBySignatureDate(LocalDate signatureDate);

    //HELPERS
    List<Order> findByOrderValueLessThanEqual(BigDecimal value);
    List<Order> findByOrderValueGreaterThanEqual(BigDecimal value);
    List<Order> findByOrderValueBetween(BigDecimal startValue, BigDecimal endValue);

    List<Order> findByApprovalDateBetween(LocalDate start, LocalDate end);
    List<Order> findByApprovalDateIsNull();
    List<Order> findByApprovalDateIsNotNull();
}
