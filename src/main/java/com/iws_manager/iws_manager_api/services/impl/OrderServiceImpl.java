package com.iws_manager.iws_manager_api.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.Order;
import com.iws_manager.iws_manager_api.repositories.OrderRepository;
import com.iws_manager.iws_manager_api.services.interfaces.OrderService;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link OrderService} interface for managing Branch entities.
 * Provides CRUD operations and business logic for Order management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    
    /**
     * Constructs a new OrderService with the required repository dependency.
     * 
     * @param orderRepository the repository for Order entity operations
     */
    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    /**
     * Creates and persists a new Order entity.
     * 
     * @param order the Order entity to be created
     * @return the persisted Order entity with generated ID
     * @throws IllegalArgumentException if the Order parameter is null
     */
    @Override
    public Order create(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        return orderRepository.save(order);
    }

    /**
     * Retrieves a Order by its unique identifier.
     * 
     * @param id the ID of the Order to retrieve
     * @return an Optional containing the found Order, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return orderRepository.findById(id);
    }

    /**
     * Retrieves all Order entities from the database.
     * 
     * @return a List of all Order entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    /**
     * Updates an existing Order entity.
     * 
     * @param id the ID of the Order to update
     * @param branchDetails the Order object containing updated fields
     * @return the updated Order entity
     * @throws RuntimeException if no Order exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public Order update(Long id, Order orderDetails) {
        if (id == null || orderDetails == null) {
            throw new IllegalArgumentException("ID and order details cannot be null");
        }
        
        return  orderRepository.findById(id)
                .map(existingOrder -> {
                    existingOrder.setAcronym(orderDetails.getAcronym());
                    existingOrder.setApprovalDate(orderDetails.getApprovalDate());
                    existingOrder.setApprovalPdf(orderDetails.getApprovalPdf());
                    existingOrder.setApprovalStatus(orderDetails.getApprovalStatus());
                    existingOrder.setBasiccontract(orderDetails.getBasiccontract());
                    existingOrder.setContractData1(orderDetails.getContractData1());
                    existingOrder.setContractData2(orderDetails.getContractData2());
                    existingOrder.setContractor(orderDetails.getContractor());
                    existingOrder.setContractPdf(orderDetails.getContractPdf());
                    existingOrder.setContractStatus(orderDetails.getContractStatus());
                    existingOrder.setCustomer(orderDetails.getCustomer());
                    existingOrder.setEmployeeIws(orderDetails.getEmployeeIws());
                    existingOrder.setFixCommission(orderDetails.getFixCommission());
                    existingOrder.setFundingProgram(orderDetails.getFundingProgram());
                    existingOrder.setIwsProvision(orderDetails.getIwsProvision());
                    existingOrder.setMaxCommission(orderDetails.getMaxCommission());
                    existingOrder.setNextDeptDate(orderDetails.getNextDeptDate());
                    existingOrder.setNoOfDepts(orderDetails.getNoOfDepts());
                    existingOrder.setOrderDate(orderDetails.getOrderDate());
                    existingOrder.setOrderLabel(orderDetails.getOrderLabel());
                    existingOrder.setOrderNo(orderDetails.getOrderNo());
                    existingOrder.setOrderType(orderDetails.getOrderType());
                    existingOrder.setOrderTitle(orderDetails.getOrderTitle());
                    existingOrder.setOrderValue(orderDetails.getOrderValue());
                    existingOrder.setProject(orderDetails.getProject());
                    existingOrder.setPromoter(orderDetails.getPromoter());
                    existingOrder.setSignatureDate(orderDetails.getSignatureDate());

                    return orderRepository.save(existingOrder);
                })
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    /**
     * Deletes a Order entity by its ID.
     * 
     * @param id the ID of the Order to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!orderRepository.existsById(id)) {  
            throw new EntityNotFoundException("EmploymentContract not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    // PROPERTIES
    @Override
    public List<Order> getByAcronym(String acronym) {
        return orderRepository.findByAcronym(acronym);
    }

    @Override
    public List<Order> getByApprovalDate(LocalDate approvalDate) {
        return orderRepository.findByApprovalDate(approvalDate);
    }

    @Override
    public List<Order> getByApprovalStatusId(Long approvalStatusId){
        return orderRepository.findByApprovalStatusId(approvalStatusId);
    }

    @Override
    public List<Order> getByBasicContractId(Long basiccontractId){
        return orderRepository.findByBasiccontractId(basiccontractId);
    }

    @Override
    public List<Order> getByContractData1(String contractData1) {
        return orderRepository.findByContractData1(contractData1);
    }

    @Override
    public List<Order> getByContractData2(String contractData2) {
        return orderRepository.findByContractData2(contractData2);
    }

    @Override
    public List<Order> getByContractorId(Long contractorId) {
        return orderRepository.findByContractorId(contractorId);
    }

    @Override
    public List<Order> getByContractStatusId(Long contractStatusId) {
        return orderRepository.findByContractStatusId(contractStatusId);
    }

    @Override
    public List<Order> getByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Order> getByEmployeeIwsId(Long employeeIwsId) {
        return orderRepository.findByEmployeeIwsId(employeeIwsId);
    }

    @Override
    public List<Order> getByFixCommission(BigDecimal fixCommission) {
        return orderRepository.findByFixCommission(fixCommission);
    }

    @Override
    public List<Order> getByFundingProgramId(Long fundingProgramId) {
        return orderRepository.findByFundingProgramId(fundingProgramId);
    }

    @Override
    public List<Order> getByIwsProvision(BigDecimal iwsProvision) {
        return orderRepository.findByIwsProvision(iwsProvision);
    }

    @Override
    public List<Order> getByMaxCommission(BigDecimal maxCommission) {
        return orderRepository.findByMaxCommission(maxCommission);
    }

    @Override
    public List<Order> getByNextDeptDate(LocalDate nextDeptDate) {
        return orderRepository.findByNextDeptDate(nextDeptDate);
    }

    @Override
    public List<Order> getByNoOfDepts(Integer noOfDepts) {
        return orderRepository.findByNoOfDepts(noOfDepts);
    }

    @Override
    public List<Order> getByOrderDate(LocalDate orderDate) {
        return orderRepository.findByOrderDate(orderDate);
    }

    @Override
    public List<Order> getByOrderLabel(String orderLabel) {
        return orderRepository.findByOrderLabel(orderLabel);
    }

    @Override
    public List<Order> getByOrderNo(Integer orderNo) {
        return orderRepository.findByOrderNo(orderNo);
    }

    @Override
    public List<Order> getByOrderTypeId(Long orderTypeId) {
        return orderRepository.findByOrderTypeId(orderTypeId);
    }

    @Override
    public List<Order> getByOrderTitle(String orderTitle) {
        return orderRepository.findByOrderTitle(orderTitle);
    }
   
    @Override
    public List<Order> getByOrderValue(BigDecimal orderValue) {
        return orderRepository.findByOrderValue(orderValue);
    }

    @Override
    public List<Order> getByProjectId(Long projectId){
        return orderRepository.findByProjectId(projectId);
    }

    @Override
    public List<Order> getByPromoterId(Long promoterId) {
        return orderRepository.findByPromoterId(promoterId);
    }

    @Override
    public List<Order> getBySignatureDate(LocalDate signatureDate) {
        return orderRepository.findBySignatureDate(signatureDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getByOrderValueLessThanEqual(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        return orderRepository.findByOrderValueLessThanEqual(value);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getByOrderValueGreaterThanEqual(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        return orderRepository.findByOrderValueGreaterThanEqual(value);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getByOrderValueBetween(BigDecimal startValue, BigDecimal endValue) {
        if (startValue == null || endValue == null) {
            throw new IllegalArgumentException("Start and end values cannot be null");
        }
        if (startValue.compareTo(endValue) > 0) {
            throw new IllegalArgumentException("Start value must be less than or equal to end value");
        }
        return orderRepository.findByOrderValueBetween(startValue, endValue);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getByApprovalDateBetween(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates cannot be null");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }
        return orderRepository.findByApprovalDateBetween(start, end);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getByApprovalDateIsNull() {
        return orderRepository.findByApprovalDateIsNull();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getByApprovalDateIsNotNull() {
        return orderRepository.findByApprovalDateIsNotNull();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getByCustomerIdOrderByOrderTitleAsc(Long customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        return orderRepository.findByCustomerIdOrderByOrderTitleAsc(customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getByCustomerIdOrderByOrderLabelAsc(Long customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        return orderRepository.findByCustomerIdOrderByOrderLabelAsc(customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getByCustomerIdOrderByOrderNoAsc(Long customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        return orderRepository.findByCustomerIdOrderByOrderNoAsc(customerId);
    }
}