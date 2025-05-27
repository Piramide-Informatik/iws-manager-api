package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.State;
import com.iws_manager.iws_manager_api.repositories.StateRepository;
import com.iws_manager.iws_manager_api.services.interfaces.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link StateService} interface for managing State entities.
 * Provides CRUD operations and business logic for State management.
 *
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;

    /**
     * Constructs a new StateService with the required repository dependency.
     *
     * @param stateRepository the repository for State entity operations
     */
    @Autowired
    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    /**
     * Creates and persists a new State entity.
     *
     * @param state the State entity to be created
     * @return the persisted State entity with generated ID
     * @throws IllegalArgumentException if the state parameter is null
     */
    @Override
    public State create(State state) {
        if (state == null) {
            throw new IllegalArgumentException("State cannot be null");
        }
        return stateRepository.save(state);
    }

    /**
     * Retrieves a State by its unique identifier.
     *
     * @param id the ID of the State to retrieve
     * @return an Optional containing the found State, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<State> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return stateRepository.findById(id);
    }

    /**
     * Retrieves all State entities from the database.
     *
     * @return a List of all State entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<State> findAll() {
        return stateRepository.findAll();
    }

    /**
     * Updates an existing State entity.
     *
     * @param id the ID of the State to update
     * @param stateDetails the State object containing updated fields
     * @return the updated State entity
     * @throws RuntimeException if no State exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public State update(Long id, State stateDetails) {
        if (id == null || stateDetails == null) {
            throw new IllegalArgumentException("ID and state details cannot be null");
        }

        return stateRepository.findById(id)
                .map(existingState -> {
                    existingState.setName(existingState.getName());
                    return stateRepository.save(existingState);
                })
                .orElseThrow(() -> new RuntimeException("State not found with id: " + id));
    }

    /**
     * Deletes a State entity by its ID.
     *
     * @param id the ID of the State to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        stateRepository.deleteById(id);
    }

}
