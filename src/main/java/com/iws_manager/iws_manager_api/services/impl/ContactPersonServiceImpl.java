package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.ContactPerson;
import com.iws_manager.iws_manager_api.repositories.ContactPersonRepository;
import com.iws_manager.iws_manager_api.services.interfaces.ContactPersonService;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link ContactPersonService} interface for managing ContactPerson entities.
 * Provides CRUD operations and business logic for ContactPerson management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class ContactPersonServiceImpl implements ContactPersonService {

    private final ContactPersonRepository contactPersonRepository;

    /**
     * Constructs a new ContactPersonService with the required repository dependency.
     * 
     * @param ContactPersonRepository the repository for ContactPerson entity operations
     */
    @Autowired
    public ContactPersonServiceImpl(ContactPersonRepository contactPersonRepository) {
        this.contactPersonRepository = contactPersonRepository;
    }

    /**
     * Creates and persists a new ContactPerson entity.
     * 
     * @param ContactPerson the ContactPerson entity to be created
     * @return the persisted ContactPerson entity with generated ID
     * @throws IllegalArgumentException if the ContactPerson parameter is null
     */
    @Override
    public ContactPerson create(ContactPerson contactPerson) {
        if (contactPerson == null) {
            throw new IllegalArgumentException("ContactPerson cannot be null");
        }
        return contactPersonRepository.save(contactPerson);
    }

    /**
     * Retrieves a ContactPerson by its unique identifier.
     * 
     * @param id the ID of the ContactPerson to retrieve
     * @return an Optional containing the found ContactPerson, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContactPerson> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return contactPersonRepository.findById(id);
    }

    /**
     * Retrieves all ContactPerson entities from the database.
     * 
     * @return a List of all ContactPerson entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContactPerson> findAll() {
        return contactPersonRepository.findAll();
    }

    /**
     * Updates an existing ContactPerson entity.
     * 
     * @param id the ID of the ContactPerson to update
     * @param contactPersonDetails the ContactPerson object containing updated fields
     * @return the updated ContactPerson entity
     * @throws RuntimeException if no ContactPerson exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public ContactPerson update(Long id, ContactPerson contactPersonDetails) {
        if (id == null || contactPersonDetails == null) {
            throw new IllegalArgumentException("ID and contactPerson details cannot be null");
        }
        
        return contactPersonRepository.findById(id)
                .map(existingContactPerson -> {
                    existingContactPerson.setCustomer(contactPersonDetails.getCustomer());
                    existingContactPerson.setFirstName(contactPersonDetails.getFirstName());
                    existingContactPerson.setForInvoicing(contactPersonDetails.getForInvoicing());
                    existingContactPerson.setFunction(contactPersonDetails.getFunction());
                    existingContactPerson.setLastName(contactPersonDetails.getLastName());
                    existingContactPerson.setSalutation(contactPersonDetails.getSalutation());
                    existingContactPerson.setTitle(contactPersonDetails.getTitle());
                    existingContactPerson.setEmail(contactPersonDetails.getEmail());
                    return contactPersonRepository.save(existingContactPerson);
                })
                .orElseThrow(() -> new RuntimeException("ContactPerson not found with id: " + id));
    }

    /**
     * Deletes a ContactPerson entity by its ID.
     * 
     * @param id the ID of the ContactPerson to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!contactPersonRepository.existsById(id)) {  
            throw new EntityNotFoundException("ContactPerson not found with id: " + id);
        }
        
        contactPersonRepository.deleteById(id);
    }

    @Override
    public List<ContactPerson> getAllByOrderByLastNameAsc() {
        return contactPersonRepository.findAllByOrderByLastNameAsc();
    }

    @Override
    public List<ContactPerson> getByCustomerIdOrderByLastNameAsc(Long customerId) {
        return contactPersonRepository.findByCustomerIdOrderByLastNameAsc(customerId);
    }
}