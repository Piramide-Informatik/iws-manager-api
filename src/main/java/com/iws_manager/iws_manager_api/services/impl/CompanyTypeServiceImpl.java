package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.CompanyType;
import com.iws_manager.iws_manager_api.repositories.CompanyTypeRepository;
import com.iws_manager.iws_manager_api.services.interfaces.CompanyTypeService;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link CompanyTypeService} interface for managing CompanyType entities.
 * Provides CRUD operations and business logic for CompanyType management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class CompanyTypeServiceImpl implements CompanyTypeService {

    private final CompanyTypeRepository companyTypeRepository;

    /**
     * Constructs a new CompanyTypeService with the required repository dependency.
     * 
     * @param companyTypeRepository the repository for CompanyType entity operations
     */
    @Autowired
    public CompanyTypeServiceImpl(CompanyTypeRepository companyTypeRepository) {
        this.companyTypeRepository = companyTypeRepository;
    }

    /**
     * Creates and persists a new CompanyType entity.
     * 
     * @param companyType the CompanyType entity to be created
     * @return the persisted CompanyType entity with generated ID
     * @throws IllegalArgumentException if the companyType parameter is null
     */
    @Override
    public CompanyType create(CompanyType companyType) {
        if (companyType == null) {
            throw new IllegalArgumentException("CompanyType cannot be null");
        }
        return companyTypeRepository.save(companyType);
    }

    /**
     * Retrieves a CompanyType by its unique identifier.
     * 
     * @param id the ID of the CompanyType to retrieve
     * @return an Optional containing the found CompanyType, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyType> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return companyTypeRepository.findById(id);
    }

    /**
     * Retrieves all CompanyType entities from the database.
     * 
     * @return a List of all CompanyType entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<CompanyType> findAll() {
        return companyTypeRepository.findAllByOrderByNameAsc();
    }

    /**
     * Updates an existing CompanyType entity.
     * 
     * @param id the ID of the CompanyType to update
     * @param companyTypeDetails the CompanyType object containing updated fields
     * @return the updated CompanyType entity
     * @throws RuntimeException if no CompanyType exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public CompanyType update(Long id, CompanyType companyTypeDetails) {
        if (id == null || companyTypeDetails == null) {
            throw new IllegalArgumentException("ID and companyType details cannot be null");
        }
        
        return companyTypeRepository.findById(id)
                .map(existingCompanyType -> {
                    existingCompanyType.setName(companyTypeDetails.getName());
                    return companyTypeRepository.save(existingCompanyType);
                })
                .orElseThrow(() -> new RuntimeException("CompanyType not found with id: " + id));
    }

    /**
     * Deletes a CompanyType entity by its ID.
     * 
     * @param id the ID of the CompanyType to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!companyTypeRepository.existsById(id)) {
            throw new EntityNotFoundException("companyType not found with id: " + id);
        }
        companyTypeRepository.deleteById(id);
    }
}