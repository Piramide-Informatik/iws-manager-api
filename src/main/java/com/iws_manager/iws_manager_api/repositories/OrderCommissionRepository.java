package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.OrderCommission;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCommissionRepository extends JpaRepository<OrderCommission, Long> {

    @EntityGraph(attributePaths = {
        "order",
        "order.customer", "order.customer.branch", "order.customer.companytype", "order.customer.country", "order.customer.state",
        "order.employeeIws", "order.employeeIws.teamIws",
        "order.fundingProgram",
        "order.orderType"
    })
    List<OrderCommission> findAll();

    //PROPERTIES
    @EntityGraph(attributePaths = {"order", "order.customer", "order.customer.branch", "order.customer.companytype", "order.customer.country", "order.customer.state", "order.employeeIws", "order.employeeIws.teamIws", "order.fundingProgram", "order.orderType"})
    List<OrderCommission> findByCommission(BigDecimal commission);
    @EntityGraph(attributePaths = {"order", "order.customer", "order.customer.branch", "order.customer.companytype", "order.customer.country", "order.customer.state", "order.employeeIws", "order.employeeIws.teamIws", "order.fundingProgram", "order.orderType"})
    List<OrderCommission> findByFromOrderValue(BigDecimal fromOrderValue);
    @EntityGraph(attributePaths = {"order", "order.customer", "order.customer.branch", "order.customer.companytype", "order.customer.country", "order.customer.state", "order.employeeIws", "order.employeeIws.teamIws", "order.fundingProgram", "order.orderType"})
    List<OrderCommission> findByMinCommission(BigDecimal minCommission);
    @EntityGraph(attributePaths = {"order", "order.customer", "order.customer.branch", "order.customer.companytype", "order.customer.country", "order.customer.state", "order.employeeIws", "order.employeeIws.teamIws", "order.fundingProgram", "order.orderType"})
    List<OrderCommission> findByOrderId(Long orderId);
    
    //HELPERS
    @EntityGraph(attributePaths = {"order", "order.customer", "order.customer.branch", "order.customer.companytype", "order.customer.country", "order.customer.state", "order.employeeIws", "order.employeeIws.teamIws", "order.fundingProgram", "order.orderType"})
    List<OrderCommission> findByCommissionLessThanEqual(BigDecimal value);
    @EntityGraph(attributePaths = {"order", "order.customer", "order.customer.branch", "order.customer.companytype", "order.customer.country", "order.customer.state", "order.employeeIws", "order.employeeIws.teamIws", "order.fundingProgram", "order.orderType"})
    List<OrderCommission> findByCommissionGreaterThanEqual(BigDecimal value);
    @EntityGraph(attributePaths = {"order", "order.customer", "order.customer.branch", "order.customer.companytype", "order.customer.country", "order.customer.state", "order.employeeIws", "order.employeeIws.teamIws", "order.fundingProgram", "order.orderType"})
    List<OrderCommission> findByCommissionBetween(BigDecimal startValue, BigDecimal endValue);

    @EntityGraph(attributePaths = {"order", "order.customer", "order.customer.branch", "order.customer.companytype", "order.customer.country", "order.customer.state", "order.employeeIws", "order.employeeIws.teamIws", "order.fundingProgram", "order.orderType"})
    List<OrderCommission> findByFromOrderValueLessThanEqual(BigDecimal value);
    @EntityGraph(attributePaths = {"order", "order.customer", "order.customer.branch", "order.customer.companytype", "order.customer.country", "order.customer.state", "order.employeeIws", "order.employeeIws.teamIws", "order.fundingProgram", "order.orderType"})
    List<OrderCommission> findByFromOrderValueGreaterThanEqual(BigDecimal value);
    @EntityGraph(attributePaths = {"order", "order.customer", "order.customer.branch", "order.customer.companytype", "order.customer.country", "order.customer.state", "order.employeeIws", "order.employeeIws.teamIws", "order.fundingProgram", "order.orderType"})
    List<OrderCommission> findByFromOrderValueBetween(BigDecimal startValue, BigDecimal endValue);

    @EntityGraph(attributePaths = {"order", "order.customer", "order.customer.branch", "order.customer.companytype", "order.customer.country", "order.customer.state", "order.employeeIws", "order.employeeIws.teamIws", "order.fundingProgram", "order.orderType"})
    List<OrderCommission> findByMinCommissionLessThanEqual(BigDecimal value);
    @EntityGraph(attributePaths = {"order", "order.customer", "order.customer.branch", "order.customer.companytype", "order.customer.country", "order.customer.state", "order.employeeIws", "order.employeeIws.teamIws", "order.fundingProgram", "order.orderType"})
    List<OrderCommission> findByMinCommissionGreaterThanEqual(BigDecimal value);
    @EntityGraph(attributePaths = {"order", "order.customer", "order.customer.branch", "order.customer.companytype", "order.customer.country", "order.customer.state", "order.employeeIws", "order.employeeIws.teamIws", "order.fundingProgram", "order.orderType"})
    List<OrderCommission> findByMinCommissionBetween(BigDecimal startValue, BigDecimal endValue);

    //SORTS
    @EntityGraph(attributePaths = {"order", "order.customer", "order.customer.branch", "order.customer.companytype", "order.customer.country", "order.customer.state", "order.employeeIws", "order.employeeIws.teamIws", "order.fundingProgram", "order.orderType"})
    List<OrderCommission> findByOrderIdOrderByFromOrderValueAsc(Long orderId);

}
