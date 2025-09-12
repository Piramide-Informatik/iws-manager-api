package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.Vat;
import com.iws_manager.iws_manager_api.repositories.VatRepository;
import com.iws_manager.iws_manager_api.services.interfaces.VatService;
import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link VatService} interface for managing Vat entities.
 * Provides CRUD operations and business logic for VAT (Value Added Tax) management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class VatServiceImpl implements VatService {

    private final VatRepository vatRepository;

    /**
     * Constructs a new VatService with the required repository dependency.
     * 
     * @param vatRepository the repository for Vat entity operations
     */
    @Autowired
    public VatServiceImpl(VatRepository vatRepository) {
        this.vatRepository = vatRepository;
    }

    /**
     * Creates and persists a new Vat entity.
     * 
     * @param vat the Vat entity to be created
     * @return the persisted Vat entity with generated ID
     * @throws IllegalArgumentException if the Vat parameter is null
     */
    @Override
    public Vat create(Vat vat) {
        if (vat == null) {
            throw new IllegalArgumentException("Vat cannot be null");
        }
        return vatRepository.save(vat);
    }

    /**
     * Retrieves a Vat by its unique identifier.
     * 
     * @param id the ID of the Vat to retrieve
     * @return an Optional containing the found Vat, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Vat> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return vatRepository.findById(id);
    }

    /**
     * Retrieves all Vat entities from the database.
     * 
     * @return a List of all Vat entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Vat> findAll() {
        return vatRepository.findAll();
    }

    /**
     * Updates an existing Vat entity.
     * 
     * @param id the ID of the Vat to update
     * @param vatDetails the Vat object containing updated fields
     * @return the updated Vat entity
     * @throws RuntimeException if no Vat exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public Vat update(Long id, Vat vatDetails) {
        if (id == null || vatDetails == null) {
            throw new IllegalArgumentException("ID and VAT details cannot be null");
        }
        
        return vatRepository.findById(id)
                .map(existingVat -> {
                    existingVat.setLabel(vatDetails.getLabel());
                    return vatRepository.save(existingVat);
                })
                .orElseThrow(() -> new RuntimeException("VAT not found with id: " + id));
    }

    /**
     * Deletes a Vat entity by its ID.
     * 
     * @param id the ID of the Vat to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

         if (!vatRepository.existsById(id)) {  
            throw new EntityNotFoundException("Vat not found with id: " + id);
        }
        vatRepository.deleteById(id);
    }
}