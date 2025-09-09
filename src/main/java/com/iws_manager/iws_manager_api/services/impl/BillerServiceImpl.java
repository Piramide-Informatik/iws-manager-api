package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.Biller;
import com.iws_manager.iws_manager_api.repositories.BillerRepository;
import com.iws_manager.iws_manager_api.services.interfaces.BillerService;
import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link BillerService} interface for managing Biller entities.
 * Provides CRUD operations and business logic for Biller management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class BillerServiceImpl implements BillerService {

    private final BillerRepository billerRepository;

    /**
     * Constructs a new BillerService with the required repository dependency.
     * 
     * @param billerRepository the repository for Biller entity operations
     */
    @Autowired
    public BillerServiceImpl(BillerRepository billerRepository) {
        this.billerRepository = billerRepository;
    }

    /**
     * Creates and persists a new Biller entity.
     * 
     * @param biller the Biller entity to be created
     * @return the persisted Biller entity with generated ID
     * @throws IllegalArgumentException if the biller parameter is null
     */
    @Override
    public Biller create(Biller biller) {
        if (biller == null) {
            throw new IllegalArgumentException("Biller cannot be null");
        }
        return billerRepository.save(biller);
    }

    /**
     * Retrieves a Biller by its unique identifier.
     * 
     * @param id the ID of the Biller to retrieve
     * @return an Optional containing the found Biller, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Biller> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return billerRepository.findById(id);
    }

    /**
     * Retrieves all Biller entities from the database.
     * 
     * @return a List of all Biller entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Biller> findAll() {
        return billerRepository.findAllByOrderByNameAsc();
    }

    /**
     * Updates an existing Biller entity.
     * 
     * @param id the ID of the Biller to update
     * @param billerDetails the Biller object containing updated fields
     * @return the updated Biller entity
     * @throws RuntimeException if no Biller exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public Biller update(Long id, Biller billerDetails) {
        if (id == null || billerDetails == null) {
            throw new IllegalArgumentException("ID and biller details cannot be null");
        }
        
        return billerRepository.findById(id)
                .map(existingBiller -> {
                    existingBiller.setName(billerDetails.getName());
                    return billerRepository.save(existingBiller);
                })
                .orElseThrow(() -> new RuntimeException("Biller not found with id: " + id));
    }

    /**
     * Deletes a Biller entity by its ID.
     * 
     * @param id the ID of the Biller to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!billerRepository.existsById(id)) {  
            throw new EntityNotFoundException("Biller not found with id: " + id);
        }
        billerRepository.deleteById(id);
    }
}