package com.iws_manager.iws_manager_api.services.interfaces;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.OrderCommission;

public interface OrderCommissionService {
    OrderCommission create(OrderCommission orderCommission);
    Optional<OrderCommission> findById(Long id);
    List<OrderCommission> findAll();
    OrderCommission update(Long id, OrderCommission orderCommissionDetails);
    void delete(Long id);

     //PROPERTIES
    List<OrderCommission> getByCommission(BigDecimal commission);
    List<OrderCommission> getByFromOrderValue(BigDecimal fromOrderValue);
    List<OrderCommission> getByMinCommission(BigDecimal minCommission);
    List<OrderCommission> getByOrderId(Long orderId);
    
    //HELPERS
    List<OrderCommission> getByCommissionLessThanEqual(BigDecimal value);
    List<OrderCommission> getByCommissionGreaterThanEqual(BigDecimal value);
    List<OrderCommission> getByCommissionBetween(BigDecimal startValue, BigDecimal endValue);

    List<OrderCommission> getByFromOrderValueLessThanEqual(BigDecimal value);
    List<OrderCommission> getByFromOrderValueGreaterThanEqual(BigDecimal value);
    List<OrderCommission> getByFromOrderValueBetween(BigDecimal startValue, BigDecimal endValue);


    List<OrderCommission> getByMinCommissionLessThanEqual(BigDecimal value);
    List<OrderCommission> getByMinCommissionGreaterThanEqual(BigDecimal value);
    List<OrderCommission> getByMinCommissionBetween(BigDecimal startValue, BigDecimal endValue);
}
