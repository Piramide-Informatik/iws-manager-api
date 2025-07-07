package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.QualificationFZ;
import com.iws_manager.iws_manager_api.repositories.QualificationFZRepository;
import com.iws_manager.iws_manager_api.services.interfaces.QualificationFZService;

/**
 * Implementation of the {@link BranchService} interface for managing Branch entities.
 * Provides CRUD operations and business logic for Branch management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class QualificationFZServiceImpl implements QualificationFZService {

    private final QualificationFZRepository qualificationFZRepository;

    /**
     * Constructs a new QualificationFZService with the required repository dependency.
     * 
     * @param qualificationFZRepository the repository for qualificationFZ entity operations
     */
    @Autowired
    public QualificationFZServiceImpl(QualificationFZRepository qualificationFZRepository) {
        this.qualificationFZRepository = qualificationFZRepository;
    }

    /**
     * Creates and persists a new QualificationFZ entity.
     * 
     * @param qualificationFZ the QualificationFZ entity to be created
     * @return the persisted QualificationFZ entity with generated ID
     * @throws IllegalArgumentException if the QualificationFZ parameter is null
     */
    @Override
    public QualificationFZ create(QualificationFZ qualificationFZ) {
        if (qualificationFZ == null || qualificationFZ.getQualification().trim().isEmpty()) {
            throw new IllegalArgumentException("QualificationFZ cannot be null");
        }
        return qualificationFZRepository.save(qualificationFZ);
    }

    /**
     * Retrieves a QualificationFZ by its unique identifier.
     * 
     * @param id the ID of the QualificationFZ to retrieve
     * @return an Optional containing the found QualificationFZ, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<QualificationFZ> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return qualificationFZRepository.findById(id);
    }

    /**
     * Retrieves all QualificationFZ entities from the database.
     * 
     * @return a List of all QualificationFZ entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<QualificationFZ> findAll() {
        return qualificationFZRepository.findAllByOrderByQualificationAsc();
    }

    /**
     * Updates an existing QualificationFZ entity.
     * 
     * @param id the ID of the QualificationFZ to update
     * @param qualificationFZDetails the QualificationFZ object containing updated fields
     * @return the updated QualificationFZ entity
     * @throws RuntimeException if no QualificationFZ exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public QualificationFZ update(Long id, QualificationFZ qualificationFZDetails) {
        if (id == null || qualificationFZDetails == null) {
            throw new IllegalArgumentException("ID and QualificationFZ details cannot be null");
        }
        
        return qualificationFZRepository.findById(id)
                .map(existingQualificationFZ -> {
                    existingQualificationFZ.setQualification(qualificationFZDetails.getQualification());
                    return qualificationFZRepository.save(existingQualificationFZ);
                })
                .orElseThrow(() -> new RuntimeException("Branch not found with id: " + id));
    }

    /**
     * Deletes a QualificationFZ entity by its ID.
     * 
     * @param id the ID of the QualificationFZ to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null || !qualificationFZRepository.existsById(id)) {
            throw new IllegalArgumentException("ID cannot be null or QualificationFZ not found with id:" + id);
        }

        qualificationFZRepository.deleteById(id);
    }
}