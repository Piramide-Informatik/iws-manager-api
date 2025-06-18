package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Customer;
import com.iws_manager.iws_manager_api.models.ContactPerson;

import com.iws_manager.iws_manager_api.services.interfaces.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing {@link Customer} entities.
 * Exposes CRUD endpoints with proper HTTP status codes and response formats.
 * 
 * <p>All endpoints follow RESTful conventions and include input validation.</p>
 * 
 * @RestController Indicates that this class handles HTTP requests and returns JSON responses
 * @RequestMapping Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Constructor-based dependency injection.
     * 
     * @param customerService the service layer for customer operations
     */
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Creates a new customer.
     * 
     * @param customer the customer to create (from request body)
     * @return ResponseEntity containing the created customer (HTTP 201) or error (HTTP 400)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.create(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    /**
     * Retrieves a customer by its ID.
     * 
     * @param id the ID of the customer to retrieve
     * @return ResponseEntity with the customer (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        return customer.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                     .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all customers.
     * 
     * @return ResponseEntity with list of all customers (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    /**
     * Updates an existing customer.
     * 
     * @param id the ID of the customer to update
     * @param customerDetails the updated customer data
     * @return ResponseEntity with updated customer (HTTP 200) or not found (HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @RequestBody Customer customerDetails) {
        try {
            Customer updatedCustomer = customerService.update(id, customerDetails);
            return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a customer by its ID.
     * 
     * @param id the ID of the customer to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // GET CONTACTS BY CUSTOMER ID
    /**
     * Retrieves all contacts for a specific customer.
     * 
     * @param customerId the ID of the customer
     * @return List of contacts ordered by lastName and firstName (HTTP 200)
     * @throws CustomerNotFoundException if customer doesn't exist (HTTP 404)
     */
    @GetMapping("/{id}/contacts")
    public ResponseEntity<List<ContactPerson>> getContactsByCustomer(
            @PathVariable("id") Long customerId) {
        try {
            List<ContactPerson> contacts = customerService.findContactsByCustomerId(customerId);
            return ResponseEntity.ok(contacts);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}