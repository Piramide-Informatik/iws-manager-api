package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.OrderCommission;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCommissionRepository extends JpaRepository<OrderCommission, Long> {

    //PROPERTIES
    @EntityGraph(attributePaths = {"order"})
    List<OrderCommission> findByCommission(BigDecimal commission);
    @EntityGraph(attributePaths = {"order"})
    List<OrderCommission> findByFromOrderValue(BigDecimal fromOrderValue);
    @EntityGraph(attributePaths = {"order"})
    List<OrderCommission> findByMinCommission(BigDecimal minCommission);
    @EntityGraph(attributePaths = {"order"})
    List<OrderCommission> findByOrderId(Long orderId);
    
    //HELPERS
    @EntityGraph(attributePaths = {"order"})
    List<OrderCommission> findByCommissionLessThanEqual(BigDecimal value);
    @EntityGraph(attributePaths = {"order"})
    List<OrderCommission> findByCommissionGreaterThanEqual(BigDecimal value);
    @EntityGraph(attributePaths = {"order"})
    List<OrderCommission> findByCommissionBetween(BigDecimal startValue, BigDecimal endValue);

    @EntityGraph(attributePaths = {"order"})
    List<OrderCommission> findByFromOrderValueLessThanEqual(BigDecimal value);
    @EntityGraph(attributePaths = {"order"})
    List<OrderCommission> findByFromOrderValueGreaterThanEqual(BigDecimal value);
    @EntityGraph(attributePaths = {"order"})
    List<OrderCommission> findByFromOrderValueBetween(BigDecimal startValue, BigDecimal endValue);

    @EntityGraph(attributePaths = {"order"})
    List<OrderCommission> findByMinCommissionLessThanEqual(BigDecimal value);
    @EntityGraph(attributePaths = {"order"})
    List<OrderCommission> findByMinCommissionGreaterThanEqual(BigDecimal value);
    @EntityGraph(attributePaths = {"order"})
    List<OrderCommission> findByMinCommissionBetween(BigDecimal startValue, BigDecimal endValue);

    //SORTS
    @EntityGraph(attributePaths = {"order"})
    List<OrderCommission> findByOrderIdOrderByFromOrderValueAsc(Long orderId);

}
