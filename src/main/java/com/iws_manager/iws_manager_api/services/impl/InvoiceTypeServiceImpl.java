package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.InvoiceType;
import com.iws_manager.iws_manager_api.repositories.InvoiceTypeRepository;
import com.iws_manager.iws_manager_api.services.interfaces.InvoiceTypeService;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link InvoiceTypeService} interface for managing InvoiceType entities.
 * Provides CRUD operations and business logic for Invoice Type management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class InvoiceTypeServiceImpl implements InvoiceTypeService {

    private final InvoiceTypeRepository invoiceTypeRepository;

    /**
     * Constructs a new InvoiceTypeService with the required repository dependency.
     * 
     * @param invoiceTypeRepository the repository for InvoiceType entity operations
     */
    @Autowired
    public InvoiceTypeServiceImpl(InvoiceTypeRepository invoiceTypeRepository) {
        this.invoiceTypeRepository = invoiceTypeRepository;
    }

    /**
     * Creates and persists a new InvoiceType entity.
     * 
     * @param invoiceType the InvoiceType entity to be created
     * @return the persisted InvoiceType entity with generated ID
     * @throws IllegalArgumentException if the InvoiceType parameter is null
     */
    @Override
    public InvoiceType create(InvoiceType invoiceType) {
        if (invoiceType == null) {
            throw new IllegalArgumentException("InvoiceType cannot be null");
        }
        return invoiceTypeRepository.save(invoiceType);
    }

    /**
     * Retrieves an InvoiceType by its unique identifier.
     * 
     * @param id the ID of the InvoiceType to retrieve
     * @return an Optional containing the found InvoiceType, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<InvoiceType> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return invoiceTypeRepository.findById(id);
    }

    /**
     * Retrieves all InvoiceType entities from the database, ordered by name ascending.
     * 
     * @return a List of all InvoiceType entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<InvoiceType> findAll() {
        return invoiceTypeRepository.findAllByOrderByNameAsc();
    }

    /**
     * Updates an existing InvoiceType entity.
     * 
     * @param id the ID of the InvoiceType to update
     * @param invoiceTypeDetails the InvoiceType object containing updated fields
     * @return the updated InvoiceType entity
     * @throws RuntimeException if no InvoiceType exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public InvoiceType update(Long id, InvoiceType invoiceTypeDetails) {
        if (id == null || invoiceTypeDetails == null) {
            throw new IllegalArgumentException("ID and InvoiceType details cannot be null");
        }
        
        return invoiceTypeRepository.findById(id)
                .map(existingInvoiceType -> {
                    existingInvoiceType.setName(invoiceTypeDetails.getName());
                    return invoiceTypeRepository.save(existingInvoiceType);
                })
                .orElseThrow(() -> new RuntimeException("InvoiceType not found with id: " + id));
    }

    /**
     * Deletes an InvoiceType entity by its ID.
     * 
     * @param id the ID of the InvoiceType to delete
     * @throws IllegalArgumentException if the id parameter is null
     * @throws EntityNotFoundException if no InvoiceType exists with the given ID
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!invoiceTypeRepository.existsById(id)) {  
            throw new EntityNotFoundException("InvoiceType not found with id: " + id);
        }
        invoiceTypeRepository.deleteById(id);
    }
}