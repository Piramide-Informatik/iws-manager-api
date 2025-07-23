package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.ProjectCostCenter;
import com.iws_manager.iws_manager_api.repositories.ProjectCostCenterRepository;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectCostCenterService;

/**
 * Implementation of the {@link ProjectCostCenterService} interface for managing ProjectCostCenter entities.
 * Provides CRUD operations and business logic for ProjectCostCenter management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class ProjectCostCenterServiceImpl implements ProjectCostCenterService {

    private final ProjectCostCenterRepository projectCostCenterRepository;

    /**
     * Constructs a new ProjectCostCenterService with the required repository dependency.
     * 
     * @param ProjectCostCenterRepository the repository for ProjectCostCenter entity operations
     */
    @Autowired
    public ProjectCostCenterServiceImpl(ProjectCostCenterRepository projectCostCenterRepository) {
        this.projectCostCenterRepository = projectCostCenterRepository;
    }

    /**
     * Creates and persists a new ProjectCostCenter entity.
     * 
     * @param ProjectCostCenter the ProjectCostCenter entity to be created
     * @return the persisted ProjectCostCenter entity with generated ID
     * @throws IllegalArgumentException if the ProjectCostCenter parameter is null
     */
    @Override
    public ProjectCostCenter create(ProjectCostCenter projectCostCenter) {
        if (projectCostCenter == null) {
            throw new IllegalArgumentException("ProjectCostCenter cannot be null");
        }
        return projectCostCenterRepository.save(projectCostCenter);
    }

    /**
     * Retrieves a ProjectCostCenter by its unique identifier.
     * 
     * @param id the ID of the ProjectCostCenter to retrieve
     * @return an Optional containing the found ProjectCostCenter, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectCostCenter> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return projectCostCenterRepository.findById(id);
    }

    /**
     * Retrieves all ProjectCostCenter entities from the database.
     * 
     * @return a List of all ProjectCostCenter entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectCostCenter> findAll() {
        return projectCostCenterRepository.findAll();
    }

    /**
     * Updates an existing ProjectCostCenter entity.
     * 
     * @param id the ID of the ProjectCostCenter to update
     * @param projectCostCenterDetails the ProjectCostCenter object containing updated fields
     * @return the updated ProjectCostCenter entity
     * @throws RuntimeException if no ProjectCostCenter exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public ProjectCostCenter update(Long id, ProjectCostCenter projectCostCenterDetails) {
        if (id == null || projectCostCenterDetails == null) {
            throw new IllegalArgumentException("ID and projectCostCenter details cannot be null");
        }
        
        return projectCostCenterRepository.findById(id)
                .map(existingProjectCostCenter -> {
                    existingProjectCostCenter.setCostCenter(projectCostCenterDetails.getCostCenter());
                    existingProjectCostCenter.setKmuino(projectCostCenterDetails.getKmuino());
                    existingProjectCostCenter.setSequenceno(projectCostCenterDetails.getSequenceno());
                    return projectCostCenterRepository.save(existingProjectCostCenter);
                })
                .orElseThrow(() -> new RuntimeException("ProjectCostCenter not found with id: " + id));
    }

    /**
     * Deletes a ProjectCostCenter entity by its ID.
     * 
     * @param id the ID of the ProjectCostCenter to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        projectCostCenterRepository.deleteById(id);
    }
}