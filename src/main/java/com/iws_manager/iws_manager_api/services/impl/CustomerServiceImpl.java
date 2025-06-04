package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.Customer;
import com.iws_manager.iws_manager_api.repositories.CustomerRepository;
import com.iws_manager.iws_manager_api.services.interfaces.CustomerService;

import jakarta.persistence.EntityNotFoundException;

/**
 * Implementation of the {@link CustomerService} interface for managing Customer entities.
 * Provides CRUD operations and business logic for Customer management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * Constructs a new CustomerService with the required repository dependency.
     * 
     * @param CustomerRepository the repository for Customer entity operations
     */
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Creates and persists a new Customer entity.
     * 
     * @param Customer the Customer entity to be created
     * @return the persisted Customer entity with generated ID
     * @throws IllegalArgumentException if the Customer parameter is null
     */
    @Override
    public Customer create(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        return customerRepository.save(customer);
    }

    /**
     * Retrieves a Customer by its unique identifier.
     * 
     * @param id the ID of the Customer to retrieve
     * @return an Optional containing the found Customer, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return customerRepository.findById(id);
    }

    /**
     * Retrieves all Customer entities from the database.
     * 
     * @return a List of all Customer entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    /**
     * Updates an existing Customer entity.
     * 
     * @param id the ID of the Customer to update
     * @param customerDetails the Customer object containing updated fields
     * @return the updated Customer entity
     * @throws RuntimeException if no Customer exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public Customer update(Long id, Customer customerDetails) {
        if (id == null || customerDetails == null) {
            throw new IllegalArgumentException("ID and customer details cannot be null");
        }
        
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    existingCustomer.setBranch(customerDetails.getBranch());
                    existingCustomer.setCity(customerDetails.getCity());
                    existingCustomer.setCompanytype(customerDetails.getCompanytype());
                    existingCustomer.setCountry(customerDetails.getCountry());
                    existingCustomer.setCustomerno(customerDetails.getCustomerno());
                    existingCustomer.setCustomername1(customerDetails.getCustomername1());
                    existingCustomer.setCustomername2(customerDetails.getCustomername2());
                    existingCustomer.setEmail1(customerDetails.getEmail1());
                    existingCustomer.setEmail2(customerDetails.getEmail2());
                    existingCustomer.setEmail3(customerDetails.getEmail3());
                    existingCustomer.setEmail4(customerDetails.getEmail4());
                    existingCustomer.setHomepage(customerDetails.getHomepage());
                    existingCustomer.setHoursperweek(customerDetails.getHoursperweek());
                    existingCustomer.setMaxhoursmonth(customerDetails.getMaxhoursmonth());
                    existingCustomer.setMaxhoursyear(customerDetails.getMaxhoursyear());
                    existingCustomer.setNote(customerDetails.getNote());
                    existingCustomer.setPhone(customerDetails.getPhone());
                    existingCustomer.setState(customerDetails.getState());
                    existingCustomer.setStreet(customerDetails.getStreet());
                    existingCustomer.setTaxno(customerDetails.getTaxno());
                    existingCustomer.setTaxoffice(customerDetails.getTaxoffice());
                    existingCustomer.setZipcode(customerDetails.getZipcode());
                    return customerRepository.save(existingCustomer);
                })
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    /**
     * Deletes a Customer entity by its ID.
     * 
     * @param id the ID of the Customer to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!customerRepository.existsById(id)) {  
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        
        customerRepository.deleteById(id);
    }
}