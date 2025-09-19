package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.NetworkPartner;
import com.iws_manager.iws_manager_api.repositories.NetworkPartnerRepository;
import com.iws_manager.iws_manager_api.services.interfaces.NetworkPartnerService;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link NetworkPartnerService} interface for managing NetworkPartner entities.
 * Provides CRUD operations and business logic for NetworkPartner management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class NetworkPartnerServiceImpl implements NetworkPartnerService {

    private final NetworkPartnerRepository networkPartnerRepository;

    /**
     * Constructs a new NetworkPartnerService with the required repository dependency.
     * 
     * @param networkPartnerRepository the repository for NetworkPartner entity operations
     */
    @Autowired
    public NetworkPartnerServiceImpl(NetworkPartnerRepository networkPartnerRepository) {
        this.networkPartnerRepository = networkPartnerRepository;
    }

    /**
     * Creates and persists a new NetworkPartner entity.
     * 
     * @param networkPartner the NetworkPartner entity to be created
     * @return the persisted NetworkPartner entity with generated ID
     * @throws IllegalArgumentException if the NetworkPartner parameter is null
     */
    @Override
    public NetworkPartner create(NetworkPartner networkPartner) {
        if (networkPartner == null) {
            throw new IllegalArgumentException("NetworkPartner cannot be null");
        }
        return networkPartnerRepository.save(networkPartner);
    }

    /**
     * Retrieves a NetworkPartner by its unique identifier.
     * 
     * @param id the ID of the NetworkPartner to retrieve
     * @return an Optional containing the found NetworkPartner, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NetworkPartner> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return networkPartnerRepository.findById(id);
    }

    /**
     * Retrieves all NetworkPartner entities from the database.
     * 
     * @return a List of all NetworkPartner entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<NetworkPartner> findAll() {
        return networkPartnerRepository.findAllByOrderByPartnernoAsc();
    }

    /**
     * Updates an existing NetworkPartner entity.
     * 
     * @param id the ID of the NetworkPartner to update
     * @param networkPartnerDetails the NetworkPartner object containing updated fields
     * @return the updated NetworkPartner entity
     * @throws RuntimeException if no NetworkPartner exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public NetworkPartner update(Long id, NetworkPartner networkPartnerDetails) {
        if (id == null || networkPartnerDetails == null) {
            throw new IllegalArgumentException("ID and networkPartner details cannot be null");
        }
        
        return networkPartnerRepository.findById(id)
                .map(existingNetworkPartner -> {
                    existingNetworkPartner.setComment(networkPartnerDetails.getComment());
                    existingNetworkPartner.setContact(networkPartnerDetails.getContact());
                    existingNetworkPartner.setPartner(networkPartnerDetails.getPartner());
                    existingNetworkPartner.setNetwork(networkPartnerDetails.getNetwork());
                    existingNetworkPartner.setPartnerno(networkPartnerDetails.getPartnerno());
                    return networkPartnerRepository.save(existingNetworkPartner);
                })
                .orElseThrow(() -> new RuntimeException("NetworkPartner not found with id: " + id));
    }

    /**
     * Deletes a NetworkPartner entity by its ID.
     * 
     * @param id the ID of the NetworkPartner to delete
     * @throws IllegalArgumentException if the id parameter is null
     * @throws EntityNotFoundException if no NetworkPartner exists with the given ID
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!networkPartnerRepository.existsById(id)) {  
            throw new EntityNotFoundException("NetworkPartner not found with id: " + id);
        }
        
        networkPartnerRepository.deleteById(id);
    }

    /**
     * Retrieves NetworkPartner entities by network ID.
     * 
     * @param networkId the ID of the network
     * @return a List of NetworkPartner entities for the given network ID
     */
    @Override
    @Transactional(readOnly = true)
    public List<NetworkPartner> findByNetworkId(Long networkId) {
        if (networkId == null) {
            throw new IllegalArgumentException("Network ID cannot be null");
        }
        return networkPartnerRepository.findByNetworkIdOrderByPartnernoAsc(networkId);
    }

    /**
     * Retrieves NetworkPartner entities by partner ID.
     * 
     * @param partnerId the ID of the partner (customer)
     * @return a List of NetworkPartner entities for the given partner ID
     */
    @Override
    @Transactional(readOnly = true)
    public List<NetworkPartner> findByPartnerId(Long partnerId) {
        if (partnerId == null) {
            throw new IllegalArgumentException("Partner ID cannot be null");
        }
        return networkPartnerRepository.findByPartnerIdOrderByPartnernoAsc(partnerId);
    }

    /**
     * Retrieves NetworkPartner entities by contact ID.
     * 
     * @param contactId the ID of the contact person
     * @return a List of NetworkPartner entities for the given contact ID
     */
    @Override
    @Transactional(readOnly = true)
    public List<NetworkPartner> findByContactId(Long contactId) {
        if (contactId == null) {
            throw new IllegalArgumentException("Contact ID cannot be null");
        }
        return networkPartnerRepository.findByContactIdOrderByPartnernoAsc(contactId);
    }
}