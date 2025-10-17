package com.iws_manager.iws_manager_api.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.SubcontractYear;
import com.iws_manager.iws_manager_api.repositories.SubcontractYearRepository;
import com.iws_manager.iws_manager_api.services.interfaces.SubcontractYearService;
import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link SubcontractYearService} interface for managing Branch entities.
 * Provides CRUD operations and business logic for SubcontractYear management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class SubcontractYearServiceImpl implements SubcontractYearService {

    private final SubcontractYearRepository subcontractYearRepository;
    
    /**
     * Constructs a new SubcontractYearService with the required repository dependency.
     * 
     * @param subcontractYearRepository the repository for SubcontractYear entity operations
     */
    @Autowired
    public SubcontractYearServiceImpl(SubcontractYearRepository subcontractYearRepository) {
        this.subcontractYearRepository = subcontractYearRepository;
    }


    /**
     * Creates and persists a new SubcontractYear entity.
     * 
     * @param subcontractYear the SubcontractYear entity to be created
     * @return the persisted SubcontractYear entity with generated ID
     * @throws IllegalArgumentException if the SubcontractYear parameter is null
     */
    @Override
    public SubcontractYear create(SubcontractYear subcontractYear) {
        if (subcontractYear == null) {
            throw new IllegalArgumentException("SubcontractYear cannot be null");
        }
        return subcontractYearRepository.save(subcontractYear);
    }

    /**
     * Retrieves a SubcontractYear by its unique identifier.
     * 
     * @param id the ID of the SubcontractYear to retrieve
     * @return an Optional containing the found SubcontractYear, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SubcontractYear> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return subcontractYearRepository.findById(id);
    }

    /**
     * Retrieves all SubcontractYear entities from the database.
     * 
     * @return a List of all SubcontractYear entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubcontractYear> findAll() {
        return subcontractYearRepository.findAll();
    }

    /**
     * Updates an existing SubcontractYear entity.
     * 
     * @param id the ID of the SubcontractYear to update
     * @param branchDetails the SubcontractYear object containing updated fields
     * @return the updated SubcontractYear entity
     * @throws RuntimeException if no SubcontractYear exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public SubcontractYear update(Long id, SubcontractYear subcontractYearDetails) {
        if (id == null || subcontractYearDetails == null) {
            throw new IllegalArgumentException("ID and subcontractYear details cannot be null");
        }
        
        return  subcontractYearRepository.findById(id)
                .map(existingSubcontractYear -> {
                    existingSubcontractYear.setMonths(subcontractYearDetails.getMonths());
                    existingSubcontractYear.setSubcontract(subcontractYearDetails.getSubcontract());
                    existingSubcontractYear.setYear(subcontractYearDetails.getYear());
        
                    return subcontractYearRepository.save(existingSubcontractYear);
                })
                .orElseThrow(() -> new RuntimeException("SubcontractYear not found with id: " + id));
    }

    /**
     * Deletes a SubcontractYear entity by its ID.
     * 
     * @param id the ID of the SubcontractYear to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!subcontractYearRepository.existsById(id)) {
            throw new EntityNotFoundException("SubcontractYear not found with id: " + id);
        }
        subcontractYearRepository.deleteById(id);
    }

    @Override
    public List<SubcontractYear> findByMonths(Integer months) {
        return subcontractYearRepository.findByMonths(months);
    }

    @Override
    public List<SubcontractYear> findBySubcontractId(Long subcontractId) {
        return subcontractYearRepository.findBySubcontractId(subcontractId);
    }

    @Override
    public List<SubcontractYear> findByYear(LocalDate year) {
        return subcontractYearRepository.findByYear(year);
    }

    @Override
    public List<SubcontractYear> getBySubcontractIdOrderByYearAsc(Long subcontractId) {
        return subcontractYearRepository.findBySubcontractIdOrderByYearAsc(subcontractId);
    }
}