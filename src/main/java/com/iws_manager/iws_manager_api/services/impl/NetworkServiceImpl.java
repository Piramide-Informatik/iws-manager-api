package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.Network;
import com.iws_manager.iws_manager_api.repositories.NetworkRepository;
import com.iws_manager.iws_manager_api.services.interfaces.NetworkService;
import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link NetworkService} interface for managing Network entities.
 * Provides CRUD operations and business logic for Network management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class NetworkServiceImpl implements NetworkService {

    private final NetworkRepository networkRepository;

    /**
     * Constructs a new NetworkService with the required repository dependency.
     * 
     * @param networkRepository the repository for Network entity operations
     */
    @Autowired
    public NetworkServiceImpl(NetworkRepository networkRepository) {
        this.networkRepository = networkRepository;
    }

    /**
     * Creates and persists a new Network entity.
     * 
     * @param network the Network entity to be created
     * @return the persisted Network entity with generated ID
     * @throws IllegalArgumentException if the Network parameter is null
     */
    @Override
    public Network create(Network network) {
        if (network == null) {
            throw new IllegalArgumentException("Network cannot be null");
        }
        return networkRepository.save(network);
    }

    /**
     * Retrieves a Network by its unique identifier.
     * 
     * @param id the ID of the Network to retrieve
     * @return an Optional containing the found Network, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Network> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return networkRepository.findById(id);
    }

    /**
     * Retrieves all Network entities from the database.
     * 
     * @return a List of all Network entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Network> findAll() {
        return networkRepository.findAllByOrderByNameAsc();
    }

    /**
     * Updates an existing Network entity.
     * 
     * @param id the ID of the Network to update
     * @param networkDetails the Network object containing updated fields
     * @return the updated Network entity
     * @throws RuntimeException if no Network exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public Network update(Long id, Network networkDetails) {
        if (id == null || networkDetails == null) {
            throw new IllegalArgumentException("ID and Network details cannot be null");
        }
        
        return networkRepository.findById(id)
                .map(existingNetwork -> {
                    existingNetwork.setName(networkDetails.getName());
                    return networkRepository.save(existingNetwork);
                })
                .orElseThrow(() -> new RuntimeException("Network not found with id: " + id));
    }

    /**
     * Deletes a Network entity by its ID.
     * 
     * @param id the ID of the Network to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        if (!networkRepository.existsById(id)) {  
            throw new EntityNotFoundException("Network not found with id: " + id);
        }
        networkRepository.deleteById(id);
    }
}