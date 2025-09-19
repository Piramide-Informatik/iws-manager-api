package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.VatRate;
import com.iws_manager.iws_manager_api.repositories.VatRateRepository;
import com.iws_manager.iws_manager_api.services.interfaces.VatRateService;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link VatRateService} interface for managing VatRate entities.
 * Provides CRUD operations and business logic for VatRate management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class VatRateServiceImpl implements VatRateService {

    private final VatRateRepository vatRateRepository;

    /**
     * Constructs a new VatRateService with the required repository dependency.
     * 
     * @param vatRateRepository the repository for VatRate entity operations
     */
    @Autowired
    public VatRateServiceImpl(VatRateRepository vatRateRepository) {
        this.vatRateRepository = vatRateRepository;
    }

    /**
     * Creates and persists a new VatRate entity.
     * 
     * @param vatRate the VatRate entity to be created
     * @return the persisted VatRate entity with generated ID
     * @throws IllegalArgumentException if the VatRate parameter is null
     */
    @Override
    public VatRate create(VatRate vatRate) {
        if (vatRate == null) {
            throw new IllegalArgumentException("VatRate cannot be null");
        }
        return vatRateRepository.save(vatRate);
    }

    /**
     * Retrieves a VatRate by its unique identifier.
     * 
     * @param id the ID of the VatRate to retrieve
     * @return an Optional containing the found VatRate, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VatRate> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return vatRateRepository.findById(id);
    }

    /**
     * Retrieves all VatRate entities from the database ordered by fromdate ascending.
     * 
     * @return a List of all VatRate entities ordered by fromdate (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<VatRate> findAll() {
        return vatRateRepository.findAllByOrderByFromdateAsc();
    }

    /**
     * Updates an existing VatRate entity.
     * 
     * @param id the ID of the VatRate to update
     * @param vatRateDetails the VatRate object containing updated fields
     * @return the updated VatRate entity
     * @throws RuntimeException if no VatRate exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public VatRate update(Long id, VatRate vatRateDetails) {
        if (id == null || vatRateDetails == null) {
            throw new IllegalArgumentException("ID and VatRate details cannot be null");
        }
        
        return vatRateRepository.findById(id)
                .map(existingVatRate -> {
                    existingVatRate.setFromdate(vatRateDetails.getFromdate());
                    existingVatRate.setRate(vatRateDetails.getRate());
                    existingVatRate.setVat(vatRateDetails.getVat());
                    return vatRateRepository.save(existingVatRate);
                })
                .orElseThrow(() -> new RuntimeException("VatRate not found with id: " + id));
    }

    /**
     * Deletes a VatRate entity by its ID.
     * 
     * @param id the ID of the VatRate to delete
     * @throws IllegalArgumentException if the id parameter is null
     * @throws EntityNotFoundException if no VatRate exists with the given ID
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!vatRateRepository.existsById(id)) {  
            throw new EntityNotFoundException("VatRate not found with id: " + id);
        }
        
        vatRateRepository.deleteById(id);
    }

    /**
     * Retrieves VatRate entities by VAT ID ordered by fromdate ascending.
     * 
     * @param vatId the ID of the VAT
     * @return a List of VatRate entities for the given VAT ID ordered by fromdate
     * @throws IllegalArgumentException if the vatId parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public List<VatRate> getByVatId(Long vatId) {
        if (vatId == null) {
            throw new IllegalArgumentException("VAT ID cannot be null");
        }
        return vatRateRepository.findByVatIdOrderByFromdateAsc(vatId);
    }
}