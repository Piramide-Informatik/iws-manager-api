package com.iws_manager.iws_manager_api.services.interfaces;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.Order;

public interface OrderService {
    Order create(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAll();
    Order update(Long id, Order orderDetails);
    void delete(Long id);

    List<Order> getByCustomerIdOrderByOrderTitleAsc(Long customerId);
    List<Order> getByCustomerIdOrderByOrderLabelAsc(Long customerId);
    List<Order> getByCustomerIdOrderByOrderNoAsc(Long customerId);

    //PROPERTIES
    List<Order> getByAcronym(String acronym);
    List<Order> getByApprovalDate(LocalDate approvalDate);
    List<Order> getByApprovalStatusId(Long approvalStatusId);
    List<Order> getByBasicContractId(Long basiccontractId);
    List<Order> getByContractData1(String contractData1);
    List<Order> getByContractData2(String contractData2);
    List<Order> getByContractorId(Long contractorId);
    List<Order> getByContractStatusId(Long contractStatusId);
    List<Order> getByCustomerId(Long customerId);
    List<Order> getByEmployeeIwsId(Long employeeIwsId);
    List<Order> getByFixCommission(BigDecimal fixCommission);
    List<Order> getByFundingProgramId(Long fundingProgramId);
    List<Order> getByIwsProvision(BigDecimal iwsProvision);
    List<Order> getByMaxCommission(BigDecimal maxCommission);
    List<Order> getByNextDeptDate(LocalDate nextDeptDate);
    List<Order> getByNoOfDepts(Integer noOfDepts);
    List<Order> getByOrderDate(LocalDate orderDate);
    List<Order> getByOrderLabel(String orderLabel);
    List<Order> getByOrderNo(Integer orderNo);
    List<Order> getByOrderTypeId(Long orderTypeId);
    List<Order> getByOrderTitle(String orderTitle);
    List<Order> getByOrderValue(BigDecimal orderValue);
    List<Order> getByProjectId(Long projectId);
    List<Order> getByPromoterId(Long promoterId);
    List<Order> getBySignatureDate(LocalDate signatureDate);

    //HELPERS
    List<Order> getByOrderValueLessThanEqual(BigDecimal value);
    List<Order> getByOrderValueGreaterThanEqual(BigDecimal value);
    List<Order> getByOrderValueBetween(BigDecimal startValue, BigDecimal endValue);

    List<Order> getByApprovalDateBetween(LocalDate start, LocalDate end);
    List<Order> getByApprovalDateIsNull();
    List<Order> getByApprovalDateIsNotNull();
}
