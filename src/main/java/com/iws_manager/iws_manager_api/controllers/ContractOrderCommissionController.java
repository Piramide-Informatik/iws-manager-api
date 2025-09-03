package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.ContractOrderCommission;
import com.iws_manager.iws_manager_api.services.interfaces.ContractOrderCommissionService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing ContractOrderCommission entities.
 * Provides endpoints for CRUD operations and various query operations.
 */
@RestController
@RequestMapping("/api/v1/contract-order-commissions")
public class ContractOrderCommissionController {
    
    private final ContractOrderCommissionService contractOrderCommissionService;

    private static final String ERROR_MESSAGE = "Invalid parameters";
    private static final String ERROR_TITLE = "error";
    private static final String MESSAGE_TITLE = "message";

    @Autowired
    public ContractOrderCommissionController(ContractOrderCommissionService contractOrderCommissionService) {
        this.contractOrderCommissionService = contractOrderCommissionService;
    }

    /**
     * Creates a new ContractOrderCommission.
     * 
     * @param contractOrderCommission the ContractOrderCommission to create
     * @return the created ContractOrderCommission with HTTP status 201
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ContractOrderCommission> create(@RequestBody ContractOrderCommission contractOrderCommission) {
        ContractOrderCommission createdCommission = contractOrderCommissionService.create(contractOrderCommission);
        return new ResponseEntity<>(createdCommission, HttpStatus.CREATED);
    }

    /**
     * Retrieves a ContractOrderCommission by its ID.
     * 
     * @param id the ID of the ContractOrderCommission to retrieve
     * @return the ContractOrderCommission with HTTP status 200 if found, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContractOrderCommission> getById(@PathVariable Long id) {
        return contractOrderCommissionService.findById(id)
                .map(commission -> new ResponseEntity<>(commission, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all ContractOrderCommission entities.
     * 
     * @return a list of all ContractOrderCommission entities with HTTP status 200
     */
    @GetMapping
    public ResponseEntity<List<ContractOrderCommission>> getAll() {
        List<ContractOrderCommission> commissions = contractOrderCommissionService.findAll();
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    /**
     * Updates an existing ContractOrderCommission.
     * 
     * @param id the ID of the ContractOrderCommission to update
     * @param commissionDetails the updated ContractOrderCommission details
     * @return the updated ContractOrderCommission with HTTP status 200 if successful, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContractOrderCommission> update(@PathVariable Long id, @RequestBody ContractOrderCommission commissionDetails) {
        try {
            ContractOrderCommission updatedCommission = contractOrderCommissionService.update(id, commissionDetails);
            return new ResponseEntity<>(updatedCommission, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a ContractOrderCommission by its ID.
     * 
     * @param id the ID of the ContractOrderCommission to delete
     * @return HTTP status 204 if successful, or 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            contractOrderCommissionService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // PROPERTY-BASED ENDPOINTS

    @GetMapping("/by-commission/{commission}")
    public ResponseEntity<List<ContractOrderCommission>> getByCommission(@PathVariable BigDecimal commission) {
        List<ContractOrderCommission> commissions = contractOrderCommissionService.getByCommission(commission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-from-order-value/{fromOrderValue}")
    public ResponseEntity<List<ContractOrderCommission>> getByFromOrderValue(@PathVariable BigDecimal fromOrderValue) {
        List<ContractOrderCommission> commissions = contractOrderCommissionService.getByFromOrderValue(fromOrderValue);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-min-commission/{minCommission}")
    public ResponseEntity<List<ContractOrderCommission>> getByMinCommission(@PathVariable BigDecimal minCommission) {
        List<ContractOrderCommission> commissions = contractOrderCommissionService.getByMinCommission(minCommission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-basic-contract/{basicContractId}")
    public ResponseEntity<List<ContractOrderCommission>> getByBasicContractId(@PathVariable Long basicContractId) {
        List<ContractOrderCommission> commissions = contractOrderCommissionService.getByBasicContractId(basicContractId);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    // COMPARISON ENDPOINTS

    @GetMapping("/by-commission-greater-than/{commission}")
    public ResponseEntity<List<ContractOrderCommission>> getByCommissionGreaterThanEqual(@PathVariable BigDecimal commission) {
        List<ContractOrderCommission> commissions = contractOrderCommissionService.getByCommissionGreaterThanEqual(commission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-commission-less-than/{commission}")
    public ResponseEntity<List<ContractOrderCommission>> getByCommissionLessThanEqual(@PathVariable BigDecimal commission) {
        List<ContractOrderCommission> commissions = contractOrderCommissionService.getByCommissionLessThanEqual(commission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-from-order-value-greater-than/{fromOrderValue}")
    public ResponseEntity<List<ContractOrderCommission>> getByFromOrderValueGreaterThanEqual(@PathVariable BigDecimal fromOrderValue) {
        List<ContractOrderCommission> commissions = contractOrderCommissionService.getByFromOrderValueGreaterThanEqual(fromOrderValue);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-from-order-value-less-than/{fromOrderValue}")
    public ResponseEntity<List<ContractOrderCommission>> getByFromOrderValueLessThanEqual(@PathVariable BigDecimal fromOrderValue) {
        List<ContractOrderCommission> commissions = contractOrderCommissionService.getByFromOrderValueLessThanEqual(fromOrderValue);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-min-commission-greater-than/{minCommission}")
    public ResponseEntity<List<ContractOrderCommission>> getByMinCommissionGreaterThanEqual(@PathVariable BigDecimal minCommission) {
        List<ContractOrderCommission> commissions = contractOrderCommissionService.getByMinCommissionGreaterThanEqual(minCommission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-min-commission-less-than/{minCommission}")
    public ResponseEntity<List<ContractOrderCommission>> getByMinCommissionLessThanEqual(@PathVariable BigDecimal minCommission) {
        List<ContractOrderCommission> commissions = contractOrderCommissionService.getByMinCommissionLessThanEqual(minCommission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    // BASIC CONTRACT WITH CONDITIONS ENDPOINTS

    @GetMapping("/by-basic-contract/{basicContractId}/commission-greater-than/{commission}")
    public ResponseEntity<List<ContractOrderCommission>> getByBasicContractIdAndCommissionGreaterThanEqual(
            @PathVariable Long basicContractId, @PathVariable BigDecimal commission) {
        List<ContractOrderCommission> commissions = contractOrderCommissionService
                .getByBasicContractIdAndCommissionGreaterThanEqual(basicContractId, commission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-basic-contract/{basicContractId}/commission-less-than/{commission}")
    public ResponseEntity<List<ContractOrderCommission>> getByBasicContractIdAndCommissionLessThanEqual(
            @PathVariable Long basicContractId, @PathVariable BigDecimal commission) {
        List<ContractOrderCommission> commissions = contractOrderCommissionService
                .getByBasicContractIdAndCommissionLessThanEqual(basicContractId, commission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-basic-contract/{basicContractId}/from-order-value-greater-than/{fromOrderValue}")
    public ResponseEntity<List<ContractOrderCommission>> getByBasicContractIdAndFromOrderValueGreaterThanEqual(
            @PathVariable Long basicContractId, @PathVariable BigDecimal fromOrderValue) {
        List<ContractOrderCommission> commissions = contractOrderCommissionService
                .getByBasicContractIdAndFromOrderValueGreaterThanEqual(basicContractId, fromOrderValue);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-basic-contract/{basicContractId}/from-order-value-less-than/{fromOrderValue}")
    public ResponseEntity<List<ContractOrderCommission>> getByBasicContractIdAndFromOrderValueLessThanEqual(
            @PathVariable Long basicContractId, @PathVariable BigDecimal fromOrderValue) {
        List<ContractOrderCommission> commissions = contractOrderCommissionService
                .getByBasicContractIdAndFromOrderValueLessThanEqual(basicContractId, fromOrderValue);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-basic-contract/{basicContractId}/min-commission-greater-than/{minCommission}")
    public ResponseEntity<List<ContractOrderCommission>> getByBasicContractIdAndMinCommissionGreaterThanEqual(
            @PathVariable Long basicContractId, @PathVariable BigDecimal minCommission) {
        List<ContractOrderCommission> commissions = contractOrderCommissionService
                .getByBasicContractIdAndMinCommissionGreaterThanEqual(basicContractId, minCommission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-basic-contract/{basicContractId}/min-commission-less-than/{minCommission}")
    public ResponseEntity<List<ContractOrderCommission>> getByBasicContractIdAndMinCommissionLessThanEqual(
            @PathVariable Long basicContractId, @PathVariable BigDecimal minCommission) {
        List<ContractOrderCommission> commissions = contractOrderCommissionService
                .getByBasicContractIdAndMinCommissionLessThanEqual(basicContractId, minCommission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    // RANGE ENDPOINTS

    @GetMapping("/by-commission-between")
    public ResponseEntity<?> getByCommissionBetween(
            @RequestParam("minCommission") BigDecimal minCommission,
            @RequestParam("maxCommission") BigDecimal maxCommission) {
        
        try {
            List<ContractOrderCommission> commissions = contractOrderCommissionService
                    .getByCommissionBetween(minCommission, maxCommission);
            return ResponseEntity.ok(commissions);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    ERROR_TITLE, ERROR_MESSAGE,
                    MESSAGE_TITLE, ex.getMessage()
                )
            );
        }
    }

    @GetMapping("/by-from-order-value-between")
    public ResponseEntity<?> getByFromOrderValueBetween(
            @RequestParam("minFromOrderValue") BigDecimal minFromOrderValue,
            @RequestParam("maxFromOrderValue") BigDecimal maxFromOrderValue) {
        
        try {
            List<ContractOrderCommission> commissions = contractOrderCommissionService
                    .getByFromOrderValueBetween(minFromOrderValue, maxFromOrderValue);
            return ResponseEntity.ok(commissions);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    ERROR_TITLE, ERROR_MESSAGE,
                    MESSAGE_TITLE, ex.getMessage()
                )
            );
        }
    }

    @GetMapping("/by-min-commission-between")
    public ResponseEntity<?> getByMinCommissionBetween(
            @RequestParam("minMinCommission") BigDecimal minMinCommission,
            @RequestParam("maxMinCommission") BigDecimal maxMinCommission) {
        
        try {
            List<ContractOrderCommission> commissions = contractOrderCommissionService
                    .getByMinCommissionBetween(minMinCommission, maxMinCommission);
            return ResponseEntity.ok(commissions);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    ERROR_TITLE, ERROR_MESSAGE,
                    MESSAGE_TITLE, ex.getMessage()
                )
            );
        }
    }

    // BASIC CONTRACT WITH RANGE ENDPOINTS

    @GetMapping("/by-basic-contract/{basicContractId}/commission-between")
    public ResponseEntity<?> getByBasicContractIdAndCommissionBetween(
            @PathVariable Long basicContractId,
            @RequestParam("minCommission") BigDecimal minCommission,
            @RequestParam("maxCommission") BigDecimal maxCommission) {
        
        try {
            List<ContractOrderCommission> commissions = contractOrderCommissionService
                    .getByBasicContractIdAndCommissionBetween(basicContractId, minCommission, maxCommission);
            return ResponseEntity.ok(commissions);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    ERROR_TITLE, ERROR_MESSAGE,
                    MESSAGE_TITLE, ex.getMessage()
                )
            );
        }
    }

    @GetMapping("/by-basic-contract/{basicContractId}/from-order-value-between")
    public ResponseEntity<?> getByBasicContractIdAndFromOrderValueBetween(
            @PathVariable Long basicContractId,
            @RequestParam("minFromOrderValue") BigDecimal minFromOrderValue,
            @RequestParam("maxFromOrderValue") BigDecimal maxFromOrderValue) {
        
        try {
            List<ContractOrderCommission> commissions = contractOrderCommissionService
                    .getByBasicContractIdAndFromOrderValueBetween(basicContractId, minFromOrderValue, maxFromOrderValue);
            return ResponseEntity.ok(commissions);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    ERROR_TITLE, ERROR_MESSAGE,
                    MESSAGE_TITLE, ex.getMessage()
                )
            );
        }
    }

    @GetMapping("/by-basic-contract/{basicContractId}/min-commission-between")
    public ResponseEntity<?> getByBasicContractIdAndMinCommissionBetween(
            @PathVariable Long basicContractId,
            @RequestParam("minMinCommission") BigDecimal minMinCommission,
            @RequestParam("maxMinCommission") BigDecimal maxMinCommission) {
        
        try {
            List<ContractOrderCommission> commissions = contractOrderCommissionService
                    .getByBasicContractIdAndMinCommissionBetween(basicContractId, minMinCommission, maxMinCommission);
            return ResponseEntity.ok(commissions);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    ERROR_TITLE, ERROR_MESSAGE,
                    MESSAGE_TITLE, ex.getMessage()
                )
            );
        }
    }

    // SORTING
    @GetMapping("/by-basic-contract/{basicContractId}/sort-by-from-order-value")
    public ResponseEntity<List<ContractOrderCommission>> getByBasicContractIdOrderByFromOrderValueAsc(@PathVariable Long basicContractId) {
        List<ContractOrderCommission> commissions = contractOrderCommissionService.getByBasicContractIdOrderByFromOrderValueAsc(basicContractId);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }
}