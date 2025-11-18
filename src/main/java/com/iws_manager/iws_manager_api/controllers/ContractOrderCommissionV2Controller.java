package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.dtos.contractordercommission.*;
import com.iws_manager.iws_manager_api.services.interfaces.ContractOrderCommissionServiceV2;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller V2 for managing ContractOrderCommission entities with DTOs.
 * Provides endpoints for CRUD operations and various query operations using DTOs.
 */
@RestController
@RequestMapping("/api/v2/contract-order-commissions")
public class ContractOrderCommissionV2Controller {
    
    private final ContractOrderCommissionServiceV2 contractOrderCommissionService;

    private static final String ERROR_MESSAGE = "Invalid parameters";
    private static final String ERROR_TITLE = "error";
    private static final String MESSAGE_TITLE = "message";

    @Autowired
    public ContractOrderCommissionV2Controller(ContractOrderCommissionServiceV2 contractOrderCommissionService) {
        this.contractOrderCommissionService = contractOrderCommissionService;
    }

    /**
     * Creates a new ContractOrderCommission using DTO.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ContractOrderCommissionDTO> create(@RequestBody ContractOrderCommissionInputDTO dto) {
        ContractOrderCommissionDTO createdCommission = contractOrderCommissionService.create(dto);
        return new ResponseEntity<>(createdCommission, HttpStatus.CREATED);
    }

    /**
     * Retrieves a ContractOrderCommission by its ID using DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContractOrderCommissionDetailDTO> getById(@PathVariable Long id) {
        return contractOrderCommissionService.findById(id)
                .map(commission -> new ResponseEntity<>(commission, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all ContractOrderCommission entities using DTOs.
     */
    @GetMapping
    public ResponseEntity<List<ContractOrderCommissionDTO>> getAll() {
        List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService.findAll();
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    /**
     * Updates an existing ContractOrderCommission using DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContractOrderCommissionDTO> update(@PathVariable Long id, @RequestBody ContractOrderCommissionInputDTO dto) {
        try {
            ContractOrderCommissionDTO updatedCommission = contractOrderCommissionService.update(id, dto);
            return new ResponseEntity<>(updatedCommission, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a ContractOrderCommission by its ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contractOrderCommissionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // PROPERTY-BASED ENDPOINTS CON DTOs

    @GetMapping("/by-commission/{commission}")
    public ResponseEntity<List<ContractOrderCommissionDTO>> getByCommission(@PathVariable BigDecimal commission) {
        List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService.getByCommission(commission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-from-order-value/{fromOrderValue}")
    public ResponseEntity<List<ContractOrderCommissionDTO>> getByFromOrderValue(@PathVariable BigDecimal fromOrderValue) {
        List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService.getByFromOrderValue(fromOrderValue);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-min-commission/{minCommission}")
    public ResponseEntity<List<ContractOrderCommissionDTO>> getByMinCommission(@PathVariable BigDecimal minCommission) {
        List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService.getByMinCommission(minCommission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-basic-contract/{basicContractId}")
    public ResponseEntity<List<ContractOrderCommissionDTO>> getByBasicContractId(@PathVariable Long basicContractId) {
        List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService.getByBasicContractId(basicContractId);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    // COMPARISON ENDPOINTS CON DTOs

    @GetMapping("/by-commission-greater-than/{commission}")
    public ResponseEntity<List<ContractOrderCommissionDTO>> getByCommissionGreaterThanEqual(@PathVariable BigDecimal commission) {
        List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService.getByCommissionGreaterThanEqual(commission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-commission-less-than/{commission}")
    public ResponseEntity<List<ContractOrderCommissionDTO>> getByCommissionLessThanEqual(@PathVariable BigDecimal commission) {
        List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService.getByCommissionLessThanEqual(commission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-from-order-value-greater-than/{fromOrderValue}")
    public ResponseEntity<List<ContractOrderCommissionDTO>> getByFromOrderValueGreaterThanEqual(@PathVariable BigDecimal fromOrderValue) {
        List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService.getByFromOrderValueGreaterThanEqual(fromOrderValue);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-from-order-value-less-than/{fromOrderValue}")
    public ResponseEntity<List<ContractOrderCommissionDTO>> getByFromOrderValueLessThanEqual(@PathVariable BigDecimal fromOrderValue) {
        List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService.getByFromOrderValueLessThanEqual(fromOrderValue);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-min-commission-greater-than/{minCommission}")
    public ResponseEntity<List<ContractOrderCommissionDTO>> getByMinCommissionGreaterThanEqual(@PathVariable BigDecimal minCommission) {
        List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService.getByMinCommissionGreaterThanEqual(minCommission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-min-commission-less-than/{minCommission}")
    public ResponseEntity<List<ContractOrderCommissionDTO>> getByMinCommissionLessThanEqual(@PathVariable BigDecimal minCommission) {
        List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService.getByMinCommissionLessThanEqual(minCommission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    // BASIC CONTRACT WITH CONDITIONS ENDPOINTS CON DTOs

    @GetMapping("/by-basic-contract/{basicContractId}/commission-greater-than/{commission}")
    public ResponseEntity<List<ContractOrderCommissionDTO>> getByBasicContractIdAndCommissionGreaterThanEqual(
            @PathVariable Long basicContractId, @PathVariable BigDecimal commission) {
        List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService
                .getByBasicContractIdAndCommissionGreaterThanEqual(basicContractId, commission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-basic-contract/{basicContractId}/commission-less-than/{commission}")
    public ResponseEntity<List<ContractOrderCommissionDTO>> getByBasicContractIdAndCommissionLessThanEqual(
            @PathVariable Long basicContractId, @PathVariable BigDecimal commission) {
        List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService
                .getByBasicContractIdAndCommissionLessThanEqual(basicContractId, commission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-basic-contract/{basicContractId}/from-order-value-greater-than/{fromOrderValue}")
    public ResponseEntity<List<ContractOrderCommissionDTO>> getByBasicContractIdAndFromOrderValueGreaterThanEqual(
            @PathVariable Long basicContractId, @PathVariable BigDecimal fromOrderValue) {
        List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService
                .getByBasicContractIdAndFromOrderValueGreaterThanEqual(basicContractId, fromOrderValue);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-basic-contract/{basicContractId}/from-order-value-less-than/{fromOrderValue}")
    public ResponseEntity<List<ContractOrderCommissionDTO>> getByBasicContractIdAndFromOrderValueLessThanEqual(
            @PathVariable Long basicContractId, @PathVariable BigDecimal fromOrderValue) {
        List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService
                .getByBasicContractIdAndFromOrderValueLessThanEqual(basicContractId, fromOrderValue);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-basic-contract/{basicContractId}/min-commission-greater-than/{minCommission}")
    public ResponseEntity<List<ContractOrderCommissionDTO>> getByBasicContractIdAndMinCommissionGreaterThanEqual(
            @PathVariable Long basicContractId, @PathVariable BigDecimal minCommission) {
        List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService
                .getByBasicContractIdAndMinCommissionGreaterThanEqual(basicContractId, minCommission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    @GetMapping("/by-basic-contract/{basicContractId}/min-commission-less-than/{minCommission}")
    public ResponseEntity<List<ContractOrderCommissionDTO>> getByBasicContractIdAndMinCommissionLessThanEqual(
            @PathVariable Long basicContractId, @PathVariable BigDecimal minCommission) {
        List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService
                .getByBasicContractIdAndMinCommissionLessThanEqual(basicContractId, minCommission);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }

    // RANGE ENDPOINTS CON DTOs

    @GetMapping("/by-commission-between")
    public ResponseEntity<?> getByCommissionBetween(
            @RequestParam("minCommission") BigDecimal minCommission,
            @RequestParam("maxCommission") BigDecimal maxCommission) {
        
        try {
            List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService
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
            List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService
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
            List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService
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

    // BASIC CONTRACT WITH RANGE ENDPOINTS CON DTOs

    @GetMapping("/by-basic-contract/{basicContractId}/commission-between")
    public ResponseEntity<?> getByBasicContractIdAndCommissionBetween(
            @PathVariable Long basicContractId,
            @RequestParam("minCommission") BigDecimal minCommission,
            @RequestParam("maxCommission") BigDecimal maxCommission) {
        
        try {
            List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService
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
            List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService
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
            List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService
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

    // SORTING CON DTOs
    @GetMapping("/by-basic-contract/{basicContractId}/sort-by-from-order-value")
    public ResponseEntity<List<ContractOrderCommissionDTO>> getByBasicContractIdOrderByFromOrderValueAsc(@PathVariable Long basicContractId) {
        List<ContractOrderCommissionDTO> commissions = contractOrderCommissionService.getByBasicContractIdOrderByFromOrderValueAsc(basicContractId);
        return new ResponseEntity<>(commissions, HttpStatus.OK);
    }
}