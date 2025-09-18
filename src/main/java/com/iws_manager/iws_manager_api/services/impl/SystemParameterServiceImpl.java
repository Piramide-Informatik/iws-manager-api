package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.SystemParameter;
import com.iws_manager.iws_manager_api.repositories.SystemParameterRepository;
import com.iws_manager.iws_manager_api.services.interfaces.SystemParameterService;
import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link SystemParameterService} interface for managing SystemParameter entities.
 * Provides CRUD operations and business logic for SystemParameter management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class SystemParameterServiceImpl implements SystemParameterService {

    private final SystemParameterRepository systemParameterRepository;
    
    /**
     * Constructs a new SystemParameterService with the required repository dependency.
     * 
     * @param systemParameterRepository the repository for SystemParameter entity operations
     */
    @Autowired
    public SystemParameterServiceImpl(SystemParameterRepository systemParameterRepository) {
        this.systemParameterRepository = systemParameterRepository;
    }

    /**
     * Creates and persists a new SystemParameter entity.
     * 
     * @param systemParameter the SystemParameter entity to be created
     * @return the persisted SystemParameter entity with generated ID
     * @throws IllegalArgumentException if the SystemParameter parameter is null
     */
    @Override
    public SystemParameter create(SystemParameter systemParameter) {
        if (systemParameter == null) {
            throw new IllegalArgumentException("SystemParameter cannot be null");
        }
        return systemParameterRepository.save(systemParameter);
    }

    /**
     * Retrieves a SystemParameter by its unique identifier.
     * 
     * @param id the ID of the SystemParameter to retrieve
     * @return an Optional containing the found SystemParameter, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SystemParameter> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return systemParameterRepository.findById(id);
    }

    /**
     * Retrieves all SystemParameter entities from the database, ordered by name in ascending order.
     * 
     * @return a List of all SystemParameter entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<SystemParameter> findAll() {
        return systemParameterRepository.findAllByOrderByNameAsc();
    }

    /**
     * Updates an existing SystemParameter entity.
     * 
     * @param id the ID of the SystemParameter to update
     * @param systemParameterDetails the SystemParameter object containing updated fields
     * @return the updated SystemParameter entity
     * @throws RuntimeException if no SystemParameter exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public SystemParameter update(Long id, SystemParameter systemParameterDetails) {
        if (id == null || systemParameterDetails == null) {
            throw new IllegalArgumentException("ID and system parameter details cannot be null");
        }
        
        return systemParameterRepository.findById(id)
                .map(existingSystemParameter -> {
                    existingSystemParameter.setName(systemParameterDetails.getName());
                    existingSystemParameter.setValueChar(systemParameterDetails.getValueChar());
                    existingSystemParameter.setValueNum(systemParameterDetails.getValueNum());
                    
                    return systemParameterRepository.save(existingSystemParameter);
                })
                .orElseThrow(() -> new RuntimeException("SystemParameter not found with id: " + id));
    }

    /**
     * Deletes a SystemParameter entity by its ID.
     * 
     * @param id the ID of the SystemParameter to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

         if (!systemParameterRepository.existsById(id)) {  
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        systemParameterRepository.deleteById(id);
    }
}