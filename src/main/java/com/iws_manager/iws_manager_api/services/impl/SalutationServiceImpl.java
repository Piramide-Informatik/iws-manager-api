package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.Salutation;
import com.iws_manager.iws_manager_api.repositories.SalutationRepository;
import com.iws_manager.iws_manager_api.services.interfaces.SalutationService;

/**
 * Implementation of the {@link SalutationService} interface for managing Salutation entities.
 * Provides CRUD operations and business logic for Salutation management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class SalutationServiceImpl implements SalutationService {

    private final SalutationRepository salutationRepository;

    /**
     * Constructs a new SalutationService with the required repository dependency.
     * 
     * @param salutationRepository the repository for Salutation entity operations
     */
    @Autowired
    public SalutationServiceImpl(SalutationRepository salutationRepository) {
        this.salutationRepository = salutationRepository;
    }

    /**
     * Creates and persists a new Salutation entity.
     * 
     * @param salutation the Salutation entity to be created
     * @return the persisted Salutation entity with generated ID
     * @throws IllegalArgumentException if the salutation parameter is null
     */
    @Override
    public Salutation create(Salutation salutation) {
        if (salutation == null) {
            throw new IllegalArgumentException("Salutation cannot be null");
        }
        return salutationRepository.save(salutation);
    }

    /**
     * Retrieves a Salutation by its unique identifier.
     * 
     * @param id the ID of the Salutation to retrieve
     * @return an Optional containing the found Salutation, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Salutation> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return salutationRepository.findById(id);
    }

    /**
     * Retrieves all Salutation entities from the database.
     * 
     * @return a List of all Salutation entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Salutation> findAll() {
        return salutationRepository.findAllByOrderByNameAsc();
    }

    /**
     * Updates an existing Salutation entity.
     * 
     * @param id the ID of the Salutation to update
     * @param salutationDetails the Salutation object containing updated fields
     * @return the updated Salutation entity
     * @throws RuntimeException if no Salutation exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public Salutation update(Long id, Salutation salutationDetails) {
        if (id == null || salutationDetails == null) {
            throw new IllegalArgumentException("ID and salutation details cannot be null");
        }
        
        return salutationRepository.findById(id)
                .map(existingSalutation -> {
                    existingSalutation.setName(salutationDetails.getName());
                    return salutationRepository.save(existingSalutation);
                })
                .orElseThrow(() -> new RuntimeException("Salutation not found with id: " + id));
    }

    /**
     * Deletes a Salutation entity by its ID.
     * 
     * @param id the ID of the Salutation to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        salutationRepository.deleteById(id);
    }
}