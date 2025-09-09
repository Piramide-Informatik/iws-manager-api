package com.iws_manager.iws_manager_api.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import com.iws_manager.iws_manager_api.models.PayCondition;
import com.iws_manager.iws_manager_api.repositories.PayConditionRepository;
import com.iws_manager.iws_manager_api.services.interfaces.PayConditionService;

/**
 * Implementation of the {@link PayConditionService} interface for managing Branch entities.
 * Provides CRUD operations and business logic for PayCondition management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class PayConditionServiceImpl implements PayConditionService {

    private final PayConditionRepository payConditionRepository;
    
    /**
     * Constructs a new PayConditionService with the required repository dependency.
     * 
     * @param payConditionRepository the repository for PayCondition entity operations
     */
    @Autowired
    public PayConditionServiceImpl(PayConditionRepository payConditionRepository) {
        this.payConditionRepository = payConditionRepository;
    }


    /**
     * Creates and persists a new PayCondition entity.
     * 
     * @param payCondition the PayCondition entity to be created
     * @return the persisted PayCondition entity with generated ID
     * @throws IllegalArgumentException if the PayCondition parameter is null
     */
    @Override
    public PayCondition create(PayCondition payCondition) {
        if (payCondition == null) {
            throw new IllegalArgumentException("PayCondition cannot be null");
        }
        return payConditionRepository.save(payCondition);
    }

    /**
     * Retrieves a PayCondition by its unique identifier.
     * 
     * @param id the ID of the PayCondition to retrieve
     * @return an Optional containing the found PayCondition, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PayCondition> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return payConditionRepository.findById(id);
    }

    /**
     * Retrieves all PayCondition entities from the database.
     * 
     * @return a List of all PayCondition entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<PayCondition> findAll() {
        return payConditionRepository.findAllByOrderByNameAsc();
    }

    /**
     * Updates an existing PayCondition entity.
     * 
     * @param id the ID of the PayCondition to update
     * @param branchDetails the PayCondition object containing updated fields
     * @return the updated PayCondition entity
     * @throws RuntimeException if no PayCondition exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public PayCondition update(Long id, PayCondition payConditionDetails) {
        if (id == null || payConditionDetails == null) {
            throw new IllegalArgumentException("ID and payCondition details cannot be null");
        }
        
        return  payConditionRepository.findById(id)
                .map(existingPayCondition -> {
                    existingPayCondition.setName(payConditionDetails.getName());
                    existingPayCondition.setDeadline(payConditionDetails.getDeadline());
                    
                    return payConditionRepository.save(existingPayCondition);
                })
                .orElseThrow(() -> new RuntimeException("PayCondition not found with id: " + id));
    }

    /**
     * Deletes a PayCondition entity by its ID.
     * 
     * @param id the ID of the PayCondition to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!payConditionRepository.existsById(id)) {  
            throw new EntityNotFoundException("Pay Condition not found with id: " + id);
        }
        payConditionRepository.deleteById(id);
    }

    @Override
    public List<PayCondition> getByName(String name) {
        return payConditionRepository.findByName(name);
    }

    @Override
    public List<PayCondition> getByDeadline(Integer deadline) {
        return payConditionRepository.findByDeadline(deadline);
    }
}