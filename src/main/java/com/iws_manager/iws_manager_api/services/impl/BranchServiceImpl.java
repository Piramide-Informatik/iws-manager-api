package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.Branch;
import com.iws_manager.iws_manager_api.repositories.BranchRepository;
import com.iws_manager.iws_manager_api.services.interfaces.BranchService;

/**
 * Implementation of the {@link BranchService} interface for managing Branch entities.
 * Provides CRUD operations and business logic for Branch management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;

    /**
     * Constructs a new BranchService with the required repository dependency.
     * 
     * @param branchRepository the repository for Branch entity operations
     */
    @Autowired
    public BranchServiceImpl(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    /**
     * Creates and persists a new Branch entity.
     * 
     * @param branch the Branch entity to be created
     * @return the persisted Branch entity with generated ID
     * @throws IllegalArgumentException if the branch parameter is null
     */
    @Override
    public Title create(Title title) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }
        return titleRepository.save(title);
    }

    /**
     * Retrieves a Title by its unique identifier.
     * 
     * @param id the ID of the Title to retrieve
     * @return an Optional containing the found Title, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Title> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return titleRepository.findById(id);
    }

    /**
     * Retrieves all Title entities from the database.
     * 
     * @return a List of all Title entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Title> findAll() {
        return titleRepository.findAll();
    }

    /**
     * Updates an existing Title entity.
     * 
     * @param id the ID of the Title to update
     * @param titleDetails the Title object containing updated fields
     * @return the updated Title entity
     * @throws RuntimeException if no Title exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public Title update(Long id, Title titleDetails) {
        if (id == null || titleDetails == null) {
            throw new IllegalArgumentException("ID and title details cannot be null");
        }
        
        return titleRepository.findById(id)
                .map(existingTitle -> {
                    existingTitle.setName(titleDetails.getName());
                    return titleRepository.save(existingTitle);
                })
                .orElseThrow(() -> new RuntimeException("Title not found with id: " + id));
    }

    /**
     * Deletes a Title entity by its ID.
     * 
     * @param id the ID of the Title to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        titleRepository.deleteById(id);
    }
}