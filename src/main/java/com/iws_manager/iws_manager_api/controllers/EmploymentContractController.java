package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.EmploymentContract;
import com.iws_manager.iws_manager_api.services.interfaces.EmploymentContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for managing {@link Branch} entities.
 * Exposes CRUD endpoints with proper HTTP status codes and response formats.
 * 
 * <p>All endpoints follow RESTful conventions and include input validation.</p>
 * 
 * @RestController Indicates that this class handles HTTP requests and returns JSON responses
 * @RequestMapping Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/v1/employment-contracts")
public class EmploymentContractController {

    private final EmploymentContractService employmentContractService;

    /**
     * Constructor-based dependency injection.
     * 
     * @param employmentContractService the service layer for EmploymentContract operations
     */
    @Autowired
    public EmploymentContractController(EmploymentContractService employmentContractService) {
        this.employmentContractService = employmentContractService;
    }

    /**
     * Creates a new employmentContract.
     * 
     * @param employmentContract the employmentContract to create (from request body)
     * @return ResponseEntity containing the created employmentContract and HTTP 201 status
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmploymentContract> create(@RequestBody EmploymentContract employmentContract) {

        EmploymentContract createdEmploymentContract = employmentContractService.create(employmentContract);
        return new ResponseEntity<>(createdEmploymentContract, HttpStatus.CREATED);
    }

    /**
     * Retrieves a EmploymentContract by its ID.
     * 
     * @param id the ID of the EmploymentContract to retrieve
     * @return ResponseEntity with the EmploymentContract (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmploymentContract> getById(@PathVariable Long id) {
        return employmentContractService.findById(id)
                .map(employmentContract -> new ResponseEntity<>(employmentContract, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all employmentContracts.
     * 
     * @return ResponseEntity with list of all employmentContracts (HTTP 200) or empty list (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<EmploymentContract>> getAll() {
        List<EmploymentContract> contracts = employmentContractService.findAll();
        return new ResponseEntity<>(contracts, HttpStatus.OK);
    }

    /**
     * Updates an existing EmploymentContract.
     * 
     * @param id the ID of the EmploymentContract to update
     * @param employmentContractDetails the updated EmploymentContract data
     * @return ResponseEntity with updated EmploymentContract (HTTP 200) or not found (HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmploymentContract> update(
            @PathVariable Long id,
            @RequestBody EmploymentContract employmentContractDetails) {
        try {
            EmploymentContract updatedEmploymentContract = employmentContractService.update(id, employmentContractDetails);
            return new ResponseEntity<>(updatedEmploymentContract, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a employmentContract by its ID.
     * 
     * @param id the ID of the employmentContract to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employmentContractService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmploymentContract>> getByEmployeeId(@PathVariable Long employeeId) {
        List<EmploymentContract> contracts = employmentContractService.findByEmployeeId(employeeId);
        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<EmploymentContract>> getByCustomerId(@PathVariable Long customerId) {
        List<EmploymentContract> contracts = employmentContractService.findByCustomerId(customerId);
        return ResponseEntity.ok(contracts);
    }
}