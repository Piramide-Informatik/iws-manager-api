package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.Chance;
import com.iws_manager.iws_manager_api.repositories.ChanceRepository;
import com.iws_manager.iws_manager_api.services.interfaces.ChanceService;
import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link ChanceService} interface for managing Chance entities.
 * Provides CRUD operations and business logic for Chance management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class ChanceServiceImpl implements ChanceService {

    private final ChanceRepository chanceRepository;
    
    /**
     * Constructs a new ChanceService with the required repository dependency.
     * 
     * @param chanceRepository the repository for Chance entity operations
     */
    @Autowired
    public ChanceServiceImpl(ChanceRepository chanceRepository) {
        this.chanceRepository = chanceRepository;
    }

    /**
     * Creates and persists a new Chance entity.
     * 
     * @param chance the Chance entity to be created
     * @return the persisted Chance entity with generated ID
     * @throws IllegalArgumentException if the Chance parameter is null
     */
    @Override
    public Chance create(Chance chance) {
        if (chance == null) {
            throw new IllegalArgumentException("Chance cannot be null");
        }
        return chanceRepository.save(chance);
    }

    /**
     * Retrieves a Chance by its unique identifier.
     * 
     * @param id the ID of the Chance to retrieve
     * @return an Optional containing the found Chance, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Chance> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return chanceRepository.findById(id);
    }

    /**
     * Retrieves all Chance entities from the database, ordered by probability in ascending order.
     * 
     * @return a List of all Chance entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Chance> findAll() {
        return chanceRepository.findAllByOrderByProbabilityAsc();
    }

    /**
     * Updates an existing Chance entity.
     * 
     * @param id the ID of the Chance to update
     * @param chanceDetails the Chance object containing updated fields
     * @return the updated Chance entity
     * @throws RuntimeException if no Chance exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public Chance update(Long id, Chance chanceDetails) {
        if (id == null || chanceDetails == null) {
            throw new IllegalArgumentException("ID and chance details cannot be null");
        }
        
        return chanceRepository.findById(id)
                .map(existingChance -> {
                    existingChance.setProbability(chanceDetails.getProbability());
                    return chanceRepository.save(existingChance);
                })
                .orElseThrow(() -> new RuntimeException("Chance not found with id: " + id));
    }

    /**
     * Deletes a Chance entity by its ID.
     * 
     * @param id the ID of the Chance to delete
     * @throws IllegalArgumentException if the id parameter is null
     * @throws EntityNotFoundException if no Chance exists with the given ID
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!chanceRepository.existsById(id)) {  
            throw new EntityNotFoundException("Chance not found with id: " + id);
        }
        chanceRepository.deleteById(id);
    }
}