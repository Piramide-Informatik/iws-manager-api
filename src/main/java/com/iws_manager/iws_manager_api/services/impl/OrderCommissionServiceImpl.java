package com.iws_manager.iws_manager_api.services.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.OrderCommission;
import com.iws_manager.iws_manager_api.repositories.OrderCommissionRepository;
import com.iws_manager.iws_manager_api.services.interfaces.OrderCommissionService;

/**
 * Implementation of the {@link OrderCommissionService} interface for managing Branch entities.
 * Provides CRUD operations and business logic for OrderCommission management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class OrderCommissionServiceImpl implements OrderCommissionService {

    private final OrderCommissionRepository orderCommissionRepository;
    
    /**
     * Constructs a new OrderCommissionService with the required repository dependency.
     * 
     * @param orderCommissionRepository the repository for OrderCommission entity operations
     */
    @Autowired
    public OrderCommissionServiceImpl(OrderCommissionRepository orderCommissionRepository) {
        this.orderCommissionRepository = orderCommissionRepository;
    }


    /**
     * Creates and persists a new OrderCommission entity.
     * 
     * @param orderCommission the OrderCommission entity to be created
     * @return the persisted OrderCommission entity with generated ID
     * @throws IllegalArgumentException if the OrderCommission parameter is null
     */
    @Override
    public OrderCommission create(OrderCommission orderCommission) {
        if (orderCommission == null) {
            throw new IllegalArgumentException("OrderCommission cannot be null");
        }
        return orderCommissionRepository.save(orderCommission);
    }

    /**
     * Retrieves a OrderCommission by its unique identifier.
     * 
     * @param id the ID of the OrderCommission to retrieve
     * @return an Optional containing the found OrderCommission, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<OrderCommission> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return orderCommissionRepository.findById(id);
    }

    /**
     * Retrieves all OrderCommission entities from the database.
     * 
     * @return a List of all OrderCommission entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderCommission> findAll() {
        return orderCommissionRepository.findAll();
    }

    /**
     * Updates an existing OrderCommission entity.
     * 
     * @param id the ID of the OrderCommission to update
     * @param branchDetails the OrderCommission object containing updated fields
     * @return the updated OrderCommission entity
     * @throws RuntimeException if no OrderCommission exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public OrderCommission update(Long id, OrderCommission orderCommissionDetails) {
        if (id == null || orderCommissionDetails == null) {
            throw new IllegalArgumentException("ID and orderCommission details cannot be null");
        }
        
        return  orderCommissionRepository.findById(id)
                .map(existingOrderCommission -> {
                    existingOrderCommission.setCommission(orderCommissionDetails.getCommission());
                    existingOrderCommission.setFromOrderValue(orderCommissionDetails.getFromOrderValue());
                    existingOrderCommission.setMinCommission(orderCommissionDetails.getMinCommission());
                    existingOrderCommission.setOrder(orderCommissionDetails.getOrder());

                    return orderCommissionRepository.save(existingOrderCommission);
                })
                .orElseThrow(() -> new RuntimeException("OrderCommission not found with id: " + id));
    }

    /**
     * Deletes a OrderCommission entity by its ID.
     * 
     * @param id the ID of the OrderCommission to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        orderCommissionRepository.deleteById(id);
    }

    // PROPERTIES
    @Override
    public List<OrderCommission> getByCommission(BigDecimal commission) {
        return orderCommissionRepository.findByCommission(commission);
    }

    @Override
    public List<OrderCommission> getByFromOrderValue(BigDecimal fromOrderValue) {
        return orderCommissionRepository.findByFromOrderValue(fromOrderValue);
    }

    @Override
    public List<OrderCommission> getByMinCommission(BigDecimal minCommission) {
        return orderCommissionRepository.findByMinCommission(minCommission);
    }

    @Override
    public List<OrderCommission> getByOrderId(Long orderId) {
        return orderCommissionRepository.findByOrderId(orderId);
    }

    //HELPERS

    @Override
    public List<OrderCommission> getByCommissionLessThanEqual(BigDecimal value) {
        return orderCommissionRepository.findByCommissionLessThanEqual(value);
    }

    @Override
    public List<OrderCommission> getByCommissionGreaterThanEqual(BigDecimal value) {
        return orderCommissionRepository.findByCommissionGreaterThanEqual(value);
    }

    @Override
    public List<OrderCommission> getByCommissionBetween(BigDecimal startValue, BigDecimal endValue) {
        return orderCommissionRepository.findByCommissionBetween(startValue, endValue);
    }


    @Override
    public List<OrderCommission> getByFromOrderValueLessThanEqual(BigDecimal value) {
        return orderCommissionRepository.findByFromOrderValueLessThanEqual(value);
    }

    @Override
    public List<OrderCommission> getByFromOrderValueGreaterThanEqual(BigDecimal value) {
        return orderCommissionRepository.findByFromOrderValueGreaterThanEqual(value);
    }

    @Override
    public List<OrderCommission> getByFromOrderValueBetween(BigDecimal startValue, BigDecimal endValue) {
        return orderCommissionRepository.findByFromOrderValueBetween(startValue, endValue);
    }


    @Override
    public List<OrderCommission> getByMinCommissionLessThanEqual(BigDecimal value) {
        return orderCommissionRepository.findByMinCommissionLessThanEqual(value);
    }

    @Override
    public List<OrderCommission> getByMinCommissionGreaterThanEqual(BigDecimal value) {
        return orderCommissionRepository.findByMinCommissionGreaterThanEqual(value);
    }

    @Override
    public List<OrderCommission> getByMinCommissionBetween(BigDecimal startValue, BigDecimal endValue) {
        return orderCommissionRepository.findByMinCommissionBetween(startValue, endValue);
    }

    // SORTS

    @Override
    public List<OrderCommission> getByOrderIdOrderByFromOrderValueAsc(Long orderId) {
        return orderCommissionRepository.findByOrderIdOrderByFromOrderValueAsc(orderId);
    }

}