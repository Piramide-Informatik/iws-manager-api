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
    public Branch create(Branch branch) {
        if (branch == null) {
            throw new IllegalArgumentException("Branch cannot be null");
        }
        return branchRepository.save(branch);
    }

    /**
     * Retrieves a Branch by its unique identifier.
     * 
     * @param id the ID of the Branch to retrieve
     * @return an Optional containing the found Branch, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Branch> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return branchRepository.findById(id);
    }

    /**
     * Retrieves all Branch entities from the database.
     * 
     * @return a List of all Branch entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Branch> findAll() {
        return branchRepository.findAll();
    }

    /**
     * Updates an existing Branch entity.
     * 
     * @param id the ID of the Branch to update
     * @param branchDetails the Branch object containing updated fields
     * @return the updated Branch entity
     * @throws RuntimeException if no Branch exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public Branch update(Long id, Branch branchDetails) {
        if (id == null || branchDetails == null) {
            throw new IllegalArgumentException("ID and branch details cannot be null");
        }
        
        return branchRepository.findById(id)
                .map(existingBranch -> {
                    existingBranch.setName(branchDetails.getName());
                    return branchRepository.save(existingBranch);
                })
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
    }

    /**
     * Deletes a Branch entity by its ID.
     * 
     * @param id the ID of the Branch to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        branchRepository.deleteById(id);
    }
}