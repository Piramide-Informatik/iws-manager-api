package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Order;
import com.iws_manager.iws_manager_api.services.interfaces.OrderService;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Order order){
        Order createOrder = orderService.create(order);
        return new ResponseEntity<>(createOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id){
        return  orderService.findById(id)
                .map( order -> new ResponseEntity<>(order, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAll(){
        List<Order> orders = orderService.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody Order orderDetails){
        try {
            Order updateOrder = orderService.update(id, orderDetails);
            return new ResponseEntity<>(updateOrder, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            orderService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //PROPERTIES

    /**
     * Gets all the orders by customer ordered by title.
     * @param customerId customer ID.
     * @return Ordered list.
     */
    @GetMapping("/customer/{customerId}/sort-by-title")
    public ResponseEntity<List<Order>> getOrdersByCustomerSortedByTitle(
            @PathVariable Long customerId) {
        List<Order> orders = orderService.getByCustomerIdOrderByOrderTitleAsc(customerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Gets all the orders by customer ordered by label.
     * @param customerId customer ID.
     * @return Ordered list.
     */
    @GetMapping("/customer/{customerId}/sort-by-label")
    public ResponseEntity<List<Order>> getOrdersByCustomerSortedByLabel(
            @PathVariable Long customerId) {
        List<Order> orders = orderService.getByCustomerIdOrderByOrderLabelAsc(customerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-acronym/{acronym}")
    public ResponseEntity<List<Order>> getByAcronym(@PathVariable String acronym) {
        List<Order> orders = orderService.getByAcronym(acronym);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-approvaldate/{approvalDate}")
    public ResponseEntity<List<Order>> getByApprovalDate(@PathVariable LocalDate approvalDate) {
        List<Order> orders = orderService.getByApprovalDate(approvalDate);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    
    @GetMapping("/by-approvalstatus/{approvalStatusId}")
    public ResponseEntity<List<Order>> getByApprovalStatusId(@PathVariable Long approvalStatusId) {
        List<Order> orders = orderService.getByApprovalStatusId(approvalStatusId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-contractdata1/{contractData1}")
    public ResponseEntity<List<Order>> getByContractData1(@PathVariable String contractData1) {
        List<Order> orders = orderService.getByContractData1(contractData1);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-contractdata2/{contractData2}")
    public ResponseEntity<List<Order>> getByContractData2(@PathVariable String contractData2) {
        List<Order> orders = orderService.getByContractData2(contractData2);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-contractor/{contractorId}")
    public ResponseEntity<List<Order>> getByContractorId(@PathVariable Long contractorId) {
        List<Order> orders = orderService.getByContractorId(contractorId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-contractstatus/{contractStatusId}")
    public ResponseEntity<List<Order>> getByContractStatusId(@PathVariable Long contractStatusId) {
        List<Order> orders = orderService.getByContractStatusId(contractStatusId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<List<Order>> getByCustomerId(@PathVariable Long customerId) {
        List<Order> orders = orderService.getByCustomerId(customerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-employeeiws/{employeeIwsId}")
    public ResponseEntity<List<Order>> getByEmployeeIwsId(@PathVariable Long employeeIwsId) {
        List<Order> orders = orderService.getByEmployeeIwsId(employeeIwsId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-fixcommission/{fixCommission}")
    public ResponseEntity<List<Order>> getByFixCommission(@PathVariable BigDecimal fixCommission) {
        List<Order> orders = orderService.getByFixCommission(fixCommission);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-fundingprogram/{fundingProgramId}")
    public ResponseEntity<List<Order>> getByFundingProgramId(@PathVariable Long fundingProgramId) {
        List<Order> orders = orderService.getByFundingProgramId(fundingProgramId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-iwsprovision/{iwsProvision}")
    public ResponseEntity<List<Order>> getByIwsProvision(@PathVariable BigDecimal iwsProvision) {
        List<Order> orders = orderService.getByIwsProvision(iwsProvision);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-maxcommission/{maxCommission}")
    public ResponseEntity<List<Order>> getByMaxCommission(@PathVariable BigDecimal maxCommission) {
        List<Order> orders = orderService.getByMaxCommission(maxCommission);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-nextdeptdate/{nextDeptDate}")
    public ResponseEntity<List<Order>> getByNextDeptDate(@PathVariable LocalDate nextDeptDate) {
        List<Order> orders = orderService.getByNextDeptDate(nextDeptDate);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-noofdepts/{noOfDepts}")
    public ResponseEntity<List<Order>> getByNoOfDepts(@PathVariable Integer noOfDepts) {
        List<Order> orders = orderService.getByNoOfDepts(noOfDepts);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-orderdate/{orderDate}")
    public ResponseEntity<List<Order>> getByOrderDate(@PathVariable LocalDate orderDate) {
        List<Order> orders = orderService.getByOrderDate(orderDate);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-orderlabel/{orderLabel}")
    public ResponseEntity<List<Order>> getByOrderLabel(@PathVariable String orderLabel) {
        List<Order> orders = orderService.getByOrderLabel(orderLabel);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-orderno/{orderNo}")
    public ResponseEntity<List<Order>> getByOrderNo(@PathVariable Integer orderNo) {
        List<Order> orders = orderService.getByOrderNo(orderNo);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-ordertype/{orderTypeId}")
    public ResponseEntity<List<Order>> getByOrderTypeId(@PathVariable Long orderTypeId) {
        List<Order> orders = orderService.getByOrderTypeId(orderTypeId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-ordertitle/{orderTitle}")
    public ResponseEntity<List<Order>> getByOrderTitle(@PathVariable String orderTitle) {
        List<Order> orders = orderService.getByOrderTitle(orderTitle);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-ordervalue/{orderValue}")
    public ResponseEntity<List<Order>> getByOrderValue(@PathVariable BigDecimal orderValue) {
        List<Order> orders = orderService.getByOrderValue(orderValue);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-project/{projectId}")
    public ResponseEntity<List<Order>> getByProjectId(@PathVariable Long projectId) {
        List<Order> orders = orderService.getByProjectId(projectId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-promoter/{promoterId}")
    public ResponseEntity<List<Order>> getByPromoterId(@PathVariable Long promoterId) {
        List<Order> orders = orderService.getByPromoterId(promoterId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-signaturedate/{signatureDate}")
    public ResponseEntity<List<Order>> getBySignatureDate(@PathVariable LocalDate signatureDate) {
        List<Order> orders = orderService.getBySignatureDate(signatureDate);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    @GetMapping("/by-order-value-less-than/{value}")
    public ResponseEntity<List<Order>> getByOrderValueLessThanEqual(@PathVariable BigDecimal value) {
        List<Order> orders = orderService.getByOrderValueLessThanEqual(value);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/by-order-value-greater-than/{value}")
    public ResponseEntity<List<Order>> getByOrderValueGreaterThanEqual(@PathVariable BigDecimal value) {
        List<Order> orders = orderService.getByOrderValueGreaterThanEqual(value);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    ///api/v1/orders/by-order-value-between?startValue=1000&endValue=5000
    @GetMapping("/by-order-value-between")
    public ResponseEntity<?> getByOrderValueBetween(
            @RequestParam("startValue") BigDecimal startValue,
            @RequestParam("endValue") BigDecimal endValue) {
        
        try {
            if (startValue == null || endValue == null) {
                throw new IllegalArgumentException("Both values are required");
            }
            if (startValue.compareTo(endValue) > 0) {
                throw new IllegalArgumentException("Start value cannot be greater than end value");
            }

            List<Order> orders = orderService.getByOrderValueBetween(startValue, endValue);
            return ResponseEntity.ok(orders);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    "error", "Invalid parameters",
                    "message", ex.getMessage()
                )
            );
        }
    }

    /**
     * Gets orders by approval date range
     * @param start Start date (format yyyy-MM-dd)
     * @param end End date (format yyyy-MM-dd)
     * @return List of orders within the specified range
     */
    @GetMapping("/by-approval-date-range")
    public ResponseEntity<List<Order>> getByApprovalDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        
        // Parameter validation
        if (start == null || end == null) {
            throw new IllegalArgumentException("Both start and end dates are required");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        List<Order> orders = orderService.getByApprovalDateBetween(start, end);
        return ResponseEntity.ok(orders);
    }

    /**
     * Gets orders that don't have an approval date
     * @return List of orders without approval date
     */
    @GetMapping("/without-approval-date")
    public ResponseEntity<List<Order>> getByApprovalDateIsNull() {
        List<Order> orders = orderService.getByApprovalDateIsNull();
        return ResponseEntity.ok(orders);
    }

    /**
     * Gets orders that have an approval date
     * @return List of orders with approval date
     */
    @GetMapping("/with-approval-date")
    public ResponseEntity<List<Order>> getByApprovalDateIsNotNull() {
        List<Order> orders = orderService.getByApprovalDateIsNotNull();
        return ResponseEntity.ok(orders);
    }

}
