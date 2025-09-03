package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.OrderCommission;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCommissionRepository extends JpaRepository<OrderCommission, Long> {

    //PROPERTIES
    List<OrderCommission> findByCommission(BigDecimal commission);
    List<OrderCommission> findByFromOrderValue(BigDecimal fromOrderValue);
    List<OrderCommission> findByMinCommission(BigDecimal minCommission);
    List<OrderCommission> findByOrderId(Long orderId);
    
    //HELPERS
    List<OrderCommission> findByCommissionLessThanEqual(BigDecimal value);
    List<OrderCommission> findByCommissionGreaterThanEqual(BigDecimal value);
    List<OrderCommission> findByCommissionBetween(BigDecimal startValue, BigDecimal endValue);

    List<OrderCommission> findByFromOrderValueLessThanEqual(BigDecimal value);
    List<OrderCommission> findByFromOrderValueGreaterThanEqual(BigDecimal value);
    List<OrderCommission> findByFromOrderValueBetween(BigDecimal startValue, BigDecimal endValue);


    List<OrderCommission> findByMinCommissionLessThanEqual(BigDecimal value);
    List<OrderCommission> findByMinCommissionGreaterThanEqual(BigDecimal value);
    List<OrderCommission> findByMinCommissionBetween(BigDecimal startValue, BigDecimal endValue);

    //SORTS
    List<OrderCommission> findByOrderIdOrderByFromOrderValueAsc(Long orderId);

}
