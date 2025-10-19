package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.OrderCommission;
import com.iws_manager.iws_manager_api.services.interfaces.OrderCommissionService;

import java.math.BigDecimal;

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
@RequestMapping("/api/v1/ordercommissions")
public class OrderCommissionController {
    private final OrderCommissionService orderCommissionService;

    @Autowired
    public OrderCommissionController(OrderCommissionService orderCommissionService) {
        this.orderCommissionService = orderCommissionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody OrderCommission orderCommission){
        OrderCommission createOrderCommission = orderCommissionService.create(orderCommission);
        return new ResponseEntity<>(createOrderCommission, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderCommission> getById(@PathVariable Long id){
        return  orderCommissionService.findById(id)
                .map( orderCommission -> new ResponseEntity<>(orderCommission, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<OrderCommission>> getAll(){
        List<OrderCommission> orderCommissions = orderCommissionService.findAll();
        return new ResponseEntity<>(orderCommissions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderCommission> update(@PathVariable Long id, @RequestBody OrderCommission orderCommissionDetails){
        try {
            OrderCommission updateOrderCommission = orderCommissionService.update(id, orderCommissionDetails);
            return new ResponseEntity<>(updateOrderCommission, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        orderCommissionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //PROPERTIES

    @GetMapping("/by-commission/{commission}")
    public ResponseEntity<List<OrderCommission>> getByCommission(@PathVariable BigDecimal commission) {
        List<OrderCommission> orderCommissions = orderCommissionService.getByCommission(commission);
        return new ResponseEntity<>(orderCommissions, HttpStatus.OK);
    }

    @GetMapping("/by-from-order-value/{fromOrderValue}")
    public ResponseEntity<List<OrderCommission>> getByFromOrderValue(@PathVariable BigDecimal fromOrderValue) {
        List<OrderCommission> orderCommissions = orderCommissionService.getByFromOrderValue(fromOrderValue);
        return new ResponseEntity<>(orderCommissions, HttpStatus.OK);
    }

    @GetMapping("/by-min-commission/{minCommission}")
    public ResponseEntity<List<OrderCommission>> getByMinCommission(@PathVariable BigDecimal minCommission) {
        List<OrderCommission> orderCommissions = orderCommissionService.getByMinCommission(minCommission);
        return new ResponseEntity<>(orderCommissions, HttpStatus.OK);
    }

    @GetMapping("/by-order/{orderId}")
    public ResponseEntity<List<OrderCommission>> getByOrderId(@PathVariable Long orderId) {
        List<OrderCommission> orderCommissions = orderCommissionService.getByOrderId(orderId);
        return new ResponseEntity<>(orderCommissions, HttpStatus.OK);
    }

    //HELPERS
    //COMMISSION
    
    @GetMapping("/by-commission/less-than-equal/{value}")
    public ResponseEntity<List<OrderCommission>> getByCommissionLessThanEqual(@PathVariable BigDecimal value) {
        List<OrderCommission> orderCommissions = orderCommissionService.getByCommissionLessThanEqual(value);
        return new ResponseEntity<>(orderCommissions, HttpStatus.OK);
    }

    @GetMapping("/by-commission/greater-than-equal/{value}")
    public ResponseEntity<List<OrderCommission>> getByCommissionGreaterThanEqual(@PathVariable BigDecimal value) {
        List<OrderCommission> orderCommissions = orderCommissionService.getByCommissionGreaterThanEqual(value);
        return new ResponseEntity<>(orderCommissions, HttpStatus.OK);
    }

    @GetMapping("/by-commission-between/{startValue}/{endValue}")
    public ResponseEntity<List<OrderCommission>> getByCommissionBetween(@PathVariable BigDecimal startValue, @PathVariable BigDecimal endValue) {
        List<OrderCommission> orderCommissions = orderCommissionService.getByCommissionBetween(startValue, endValue);
        return new ResponseEntity<>(orderCommissions, HttpStatus.OK);
    }

    //FROM ORDER VALUE
    @GetMapping("/by-from-order-value/less-than-equal/{value}")
    public ResponseEntity<List<OrderCommission>> getByFromOrderValueLessThanEqual(@PathVariable BigDecimal value) {
        List<OrderCommission> orderCommissions = orderCommissionService.getByFromOrderValueLessThanEqual(value);
        return new ResponseEntity<>(orderCommissions, HttpStatus.OK);
    }

    @GetMapping("/by-from-order-value/greater-than-equal/{value}")
    public ResponseEntity<List<OrderCommission>> getByFromOrderValueGreaterThanEqual(@PathVariable BigDecimal value) {
        List<OrderCommission> orderCommissions = orderCommissionService.getByFromOrderValueGreaterThanEqual(value);
        return new ResponseEntity<>(orderCommissions, HttpStatus.OK);
    }

    @GetMapping("/by-from-order-value/between/{startValue}/{endValue}")
    public ResponseEntity<List<OrderCommission>> getByFromOrderValueBetween(@PathVariable BigDecimal startValue, @PathVariable BigDecimal endValue) {
        List<OrderCommission> orderCommissions = orderCommissionService.getByFromOrderValueBetween(startValue, endValue);
        return new ResponseEntity<>(orderCommissions, HttpStatus.OK);
    }

    //MIN COMMISSION
    
    @GetMapping("/by-min-commission/less-than-equal/{value}")
    public ResponseEntity<List<OrderCommission>> getByMinCommissionLessThanEqual(@PathVariable BigDecimal value) {
        List<OrderCommission> orderCommissions = orderCommissionService.getByMinCommissionLessThanEqual(value);
        return new ResponseEntity<>(orderCommissions, HttpStatus.OK);
    }

    @GetMapping("/by-min-commission/greater-than-equal/{value}")
    public ResponseEntity<List<OrderCommission>> getByMinCommissionGreaterThanEqual(@PathVariable BigDecimal value) {
        List<OrderCommission> orderCommissions = orderCommissionService.getByMinCommissionGreaterThanEqual(value);
        return new ResponseEntity<>(orderCommissions, HttpStatus.OK);
    }

    @GetMapping("/by-min-commission/between/{startValue}/{endValue}")
    public ResponseEntity<List<OrderCommission>> getByMinCommissionBetween(@PathVariable BigDecimal startValue, @PathVariable BigDecimal endValue) {
        List<OrderCommission> orderCommissions = orderCommissionService.getByMinCommissionBetween(startValue, endValue);
        return new ResponseEntity<>(orderCommissions, HttpStatus.OK);
    }

    // SORT
    @GetMapping("/by-order/{orderId}/sort-by-from-order-value")
    public ResponseEntity<List<OrderCommission>> getByOrderIdOrderByFromOrderValueAsc(@PathVariable Long orderId) {
        List<OrderCommission> orderCommissions = orderCommissionService.getByOrderIdOrderByFromOrderValueAsc(orderId);
        return new ResponseEntity<>(orderCommissions, HttpStatus.OK);
    }
}
