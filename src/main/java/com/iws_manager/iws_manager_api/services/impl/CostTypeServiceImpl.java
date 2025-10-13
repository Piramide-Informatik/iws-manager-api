package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.CostType;
import com.iws_manager.iws_manager_api.repositories.CostTypeRepository;
import com.iws_manager.iws_manager_api.services.interfaces.CostTypeService;
import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link CostTypeService} interface for managing CostType entities.
 * Provides CRUD operations and business logic for CostType management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class CostTypeServiceImpl implements CostTypeService {

    private final CostTypeRepository costTypeRepository;

    /**
     * Constructs a new CostTypeService with the required repository dependency.
     * 
     * @param costTypeRepository the repository for CostType entity operations
     */
    @Autowired
    public CostTypeServiceImpl(CostTypeRepository costTypeRepository) {
        this.costTypeRepository = costTypeRepository;
    }

    /**
     * Creates and persists a new CostType entity.
     * 
     * @param costType the CostType entity to be created
     * @return the persisted CostType entity with generated ID
     * @throws IllegalArgumentException if the costType parameter is null
     */
    @Override
    public CostType create(CostType costType) {
        if (costType == null) {
            throw new IllegalArgumentException("CostType cannot be null");
        }
        return costTypeRepository.save(costType);
    }

    /**
     * Retrieves a CostType by its unique identifier.
     * 
     * @param id the ID of the CostType to retrieve
     * @return an Optional containing the found CostType, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CostType> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return costTypeRepository.findById(id);
    }

    /**
     * Retrieves all CostType entities from the database.
     * 
     * @return a List of all CostType entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<CostType> findAll() {
        return costTypeRepository.findAllByOrderByTypeAsc();
    }

    /**
     * Updates an existing CostType entity.
     * 
     * @param id the ID of the CostType to update
     * @param costTypeDetails the CostType object containing updated fields
     * @return the updated CostType entity
     * @throws RuntimeException if no CostType exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public CostType update(Long id, CostType costTypeDetails) {
        if (id == null || costTypeDetails == null) {
            throw new IllegalArgumentException("ID and costType details cannot be null");
        }
        
        return costTypeRepository.findById(id)
                .map(existingCostType -> {
                    existingCostType.setType(costTypeDetails.getType());
                    existingCostType.setSequenceNo(costTypeDetails.getSequenceNo());

                    return costTypeRepository.save(existingCostType);
                })
                .orElseThrow(() -> new RuntimeException("CostType not found with id: " + id));
    }

    /**
     * Deletes a CostType entity by its ID.
     * 
     * @param id the ID of the CostType to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!costTypeRepository.existsById(id)) {
            throw new EntityNotFoundException("CostType not found with id: " + id);
        }

        costTypeRepository.deleteById(id);
    }
}