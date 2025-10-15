package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.ProjectStatus;
import com.iws_manager.iws_manager_api.repositories.ProjectStatusRepository;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectStatusService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link ProjectStatusService} interface for managing ProjectStatus entities.
 * Provides CRUD operations and business logic for ProjectStatus management.
 *
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class ProjectStatusServiceImpl implements ProjectStatusService {

    private final ProjectStatusRepository projectStatusRepository;

    /**
     * Constructs a new projectStatusService with the required repository dependency.
     *
     * @param ProjectStatusRepository the repository for projectStatus entity operations
     */
    @Autowired
    public ProjectStatusServiceImpl(ProjectStatusRepository projectStatusRepository) {
        this.projectStatusRepository = projectStatusRepository;
    }

    /**
     * Creates and persists a new ProjectStatus entity.
     *
     * @param projectStatus the ProjectStatus entity to be created
     * @return the persisted ProjectStatus entity with generated ID
     * @throws IllegalArgumentException if the projectStatus parameter is null
     */
    @Override
    public ProjectStatus create(ProjectStatus projectStatus) {
        if (projectStatus == null) {
            throw new IllegalArgumentException("ProjectStatus cannot be null");
        }
        return projectStatusRepository.save(projectStatus);
    }

    /**
     * Retrieves a ProjectStatus by its unique identifier.
     *
     * @param id the ID of the ProjectStatus to retrieve
     * @return an Optional containing the found ProjectStatus, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectStatus> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return projectStatusRepository.findById(id);
    }

    /**
     * Retrieves all ProjectStatus entities from the database.
     *
     * @return a List of all ProjectStatus entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProjectStatus> findAll() {
        return projectStatusRepository.findAll();
    }

    /**
     * Updates an existing ProjectStatus entity.
     *
     * @param id the ID of the ProjectStatus to update
     * @param projectStatusDetails the ProjectStatus object containing updated fields
     * @return the updated ProjectStatus entity
     * @throws RuntimeException if no ProjectStatus exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public ProjectStatus update(Long id, ProjectStatus projectStatusDetails) {
        if (id == null || projectStatusDetails == null) {
            throw new IllegalArgumentException("ID and projectStatus details cannot be null");
        }

        return projectStatusRepository.findById(id)
                .map(existingProjectStatus -> {
                    existingProjectStatus.setName(projectStatusDetails.getName());
                    return projectStatusRepository.save(existingProjectStatus);
                })
                .orElseThrow(() -> new RuntimeException("ProjectStatus not found with id: " + id));
    }

    /**
     * Deletes a ProjectStatus entity by its ID.
     *
     * @param id the ID of the ProjectStatus to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!projectStatusRepository.existsById(id)) {
            throw new EntityNotFoundException("projectStatus not found with id: " + id);
        }
        projectStatusRepository.deleteById(id);
    }

}
