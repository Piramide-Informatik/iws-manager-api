package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.ContractStatus;
import com.iws_manager.iws_manager_api.services.interfaces.ContractStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;


/**
 * REST Controller for managing {@link ContractStatus} entities.
 * Exposes CRUD endpoints with proper HTTP status codes and response formats.
 * 
 * <p>All endpoints follow RESTful conventions and include input validation.</p>
 * 
 * @RestController Indicates that this class handles HTTP requests and returns JSON responses
 * @RequestMapping Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/v1/contractstatuses")
public class ContractStatusController {

    private final ContractStatusService contractStatusService;
    private static final String ERROR_STATUS = "error";

    /**
     * Constructor-based dependency injection.
     * 
     * @param contractStatusService the service layer for contractStatus operations
     */
    @Autowired
    public ContractStatusController(ContractStatusService contractStatusService) {
        this.contractStatusService = contractStatusService;
    }

    /**
     * Creates a new contractStatus.
     * 
     * @param contractStatus the contractStatus to create (from request body)
     * @return ResponseEntity containing the created contractStatus and HTTP 201 status
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createContractStatus(@RequestBody ContractStatus contractStatus) {
        
        if (contractStatus.getStatus() == null || contractStatus.getStatus().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put(ERROR_STATUS, "Status is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        ContractStatus createdContractStatus = contractStatusService.create(contractStatus);
        return new ResponseEntity<>(createdContractStatus, HttpStatus.CREATED);
    }

    /**
     * Retrieves a contractStatus by its ID.
     * 
     * @param id the ID of the contractStatus to retrieve
     * @return ResponseEntity with the contractStatus (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContractStatus> getcontractStatusById(@PathVariable Long id) {
        return contractStatusService.findById(id)
                .map(contractStatus -> new ResponseEntity<>(contractStatus, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all contractStatuss.
     * 
     * @return ResponseEntity with list of all contractStatuss (HTTP 200) or empty list (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<ContractStatus>> getAllContractStatuss() {
        List<ContractStatus> contractStatuss = contractStatusService.findAll();
        return new ResponseEntity<>(contractStatuss, HttpStatus.OK);
    }

    /**
     * Updates an existing contractStatus.
     * 
     * @param id the ID of the contractStatus to update
     * @param contractStatusDetails the updated contractStatus data
     * @return ResponseEntity with updated contractStatus (HTTP 200) or not found (HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContractStatus> updateContractStatus(
            @PathVariable Long id,
            @RequestBody ContractStatus contractStatusDetails) {
        try {
            ContractStatus updatedContractStatus = contractStatusService.update(id, contractStatusDetails);
            return new ResponseEntity<>(updatedContractStatus, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a contractStatus by its ID.
     * 
     * @param id the ID of the contractStatus to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteContractStatus(@PathVariable Long id) {
        contractStatusService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //HELPERS

    /**
     * Retrieves contractStatuses with chance greater than or equal to the specified value.
     * 
     * @param chance the minimum chance value (inclusive)
     * @return ResponseEntity with list of matching contractStatuses (HTTP 200) or error (HTTP 400)
     */
    @GetMapping("/by-chance-greater-than/{chance}")
    public ResponseEntity<?> getByChanceGreaterThanEqual(@PathVariable BigDecimal chance) {
        try {
            List<ContractStatus> contractStatuses = contractStatusService.getByChanceGreaterThanEqual(chance);
            return new ResponseEntity<>(contractStatuses, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put(ERROR_STATUS, e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves contractStatuses with chance less than or equal to the specified value.
     * 
     * @param chance the maximum chance value (inclusive)
     * @return ResponseEntity with list of matching contractStatuses (HTTP 200) or error (HTTP 400)
     */
    @GetMapping("/by-chance-less-than/{chance}")
    public ResponseEntity<?> getByChanceLessThanEqual(@PathVariable BigDecimal chance) {
        try {
            List<ContractStatus> contractStatuses = contractStatusService.getByChanceLessThanEqual(chance);
            return new ResponseEntity<>(contractStatuses, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put(ERROR_STATUS, e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves contractStatuses with chance within the specified range.
     * 
     * @param minChance the minimum chance value (inclusive)
     * @param maxChance the maximum chance value (inclusive)
     * @return ResponseEntity with list of matching contractStatuses (HTTP 200) or error (HTTP 400)
     */
    @GetMapping("/by-chance-between/{minChance}/{maxChance}")
    public ResponseEntity<?> getByChanceBetween(
            @PathVariable BigDecimal minChance,
            @PathVariable BigDecimal maxChance) {
        try {
            List<ContractStatus> contractStatuses = contractStatusService.getByChanceBetween(minChance, maxChance);
            return new ResponseEntity<>(contractStatuses, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put(ERROR_STATUS, e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}