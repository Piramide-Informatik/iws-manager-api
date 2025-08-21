package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.ContractStatus;
import com.iws_manager.iws_manager_api.repositories.ContractStatusRepository;
import com.iws_manager.iws_manager_api.services.interfaces.ContractStatusService;

/**
 * Implementation of the {@link ContractStatusService} interface for managing ContractStatus entities.
 * Provides CRUD operations and business logic for ContractStatus management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class ContractStatusServiceImpl implements ContractStatusService {

    private final ContractStatusRepository contractStatusRepository;

    /**
     * Constructs a new ContractStatusService with the required repository dependency.
     * 
     * @param contractStatusRepository the repository for ContractStatus entity operations
     */
    @Autowired
    public ContractStatusServiceImpl(ContractStatusRepository contractStatusRepository) {
        this.contractStatusRepository = contractStatusRepository;
    }

    /**
     * Creates and persists a new ContractStatus entity.
     * 
     * @param contractStatus the ContractStatus entity to be created
     * @return the persisted ContractStatus entity with generated ID
     * @throws IllegalArgumentException if the contractStatus parameter is null
     */
    @Override
    public ContractStatus create(ContractStatus contractStatus) {
        if (contractStatus == null) {
            throw new IllegalArgumentException("ContractStatus cannot be null");
        }
        return contractStatusRepository.save(contractStatus);
    }

    /**
     * Retrieves a ContractStatus by its unique identifier.
     * 
     * @param id the ID of the ContractStatus to retrieve
     * @return an Optional containing the found ContractStatus, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContractStatus> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return contractStatusRepository.findById(id);
    }

    /**
     * Retrieves all ContractStatus entities from the database.
     * 
     * @return a List of all ContractStatus entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContractStatus> findAll() {
        return contractStatusRepository.findAllByOrderByStatusAsc();
    }

    /**
     * Updates an existing ContractStatus entity.
     * 
     * @param id the ID of the ContractStatus to update
     * @param contractStatusDetails the ContractStatus object containing updated fields
     * @return the updated ContractStatus entity
     * @throws RuntimeException if no ContractStatus exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public ContractStatus update(Long id, ContractStatus contractStatusDetails) {
        if (id == null || contractStatusDetails == null) {
            throw new IllegalArgumentException("ID and contractStatus details cannot be null");
        }
        
        return contractStatusRepository.findById(id)
                .map(existingContractStatus -> {
                    existingContractStatus.setChance(contractStatusDetails.getChance());
                    existingContractStatus.setStatus(contractStatusDetails.getStatus());

                    return contractStatusRepository.save(existingContractStatus);
                })
                .orElseThrow(() -> new RuntimeException("ContractStatus not found with id: " + id));
    }

    /**
     * Deletes a ContractStatus entity by its ID.
     * 
     * @param id the ID of the ContractStatus to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        contractStatusRepository.deleteById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ContractStatus> getByChanceGreaterThanEqual(BigDecimal chance) {
        if (chance == null) {
            throw new IllegalArgumentException("Chance cannot be null");
        }
        return contractStatusRepository.findByChanceGreaterThanEqual(chance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractStatus> getByChanceLessThanEqual(BigDecimal chance) {
        if (chance == null) {
            throw new IllegalArgumentException("Chance cannot be null");
        }
        return contractStatusRepository.findByChanceLessThanEqual(chance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractStatus> getByChanceBetween(BigDecimal minChance, BigDecimal maxChance) {
        if (minChance == null || maxChance == null) {
            throw new IllegalArgumentException("Min and max chance cannot be null");
        }
        if (minChance.compareTo(maxChance) > 0) {
            throw new IllegalArgumentException("Min chance cannot be greater than max chance");
        }
        return contractStatusRepository.findByChanceBetween(minChance, maxChance);
    }
}