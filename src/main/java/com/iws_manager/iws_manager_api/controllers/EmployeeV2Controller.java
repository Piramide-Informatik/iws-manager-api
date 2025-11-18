package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.dtos.employee.*;
import com.iws_manager.iws_manager_api.services.interfaces.EmployeeServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller V2 for managing Employee entities with DTOs.
 * Provides endpoints for CRUD operations and various query operations using DTOs.
 */
@RestController
@RequestMapping("/api/v2/employees")
public class EmployeeV2Controller {

    private final EmployeeServiceV2 employeeService;

    @Autowired
    public EmployeeV2Controller(EmployeeServiceV2 employeeService) {
        this.employeeService = employeeService;
    }

    // ==== BASIC CRUD OPERATIONS ====

    @PostMapping
    public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeInputDTO employeeInputDTO) {
        EmployeeDTO created = employeeService.create(employeeInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDetailDTO> findById(@PathVariable Long id) {
        return employeeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable Long id, @RequestBody EmployeeInputDTO employeeInputDTO) {
        try {
            EmployeeDTO updated = employeeService.update(id, employeeInputDTO);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ==== FILTER ENDPOINTS ====

    @GetMapping("/lastname/{lastname}")
    public ResponseEntity<List<EmployeeDTO>> findByLastname(@PathVariable String lastname) {
        return ResponseEntity.ok(employeeService.findByLastname(lastname));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<EmployeeDetailDTO> findByEmail(@PathVariable String email) {
        return employeeService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/title/{titleId}")
    public ResponseEntity<List<EmployeeDTO>> findByTitleId(@PathVariable Long titleId) {
        return ResponseEntity.ok(employeeService.findByTitleId(titleId));
    }

    @GetMapping("/salutation/{salutationId}")
    public ResponseEntity<List<EmployeeDTO>> findBySalutationId(@PathVariable Long salutationId) {
        return ResponseEntity.ok(employeeService.findBySalutationId(salutationId));
    }

    @GetMapping("/qualification/{qualificationFZId}")
    public ResponseEntity<List<EmployeeDTO>> findByQualificationFZId(@PathVariable Long qualificationFZId) {
        return ResponseEntity.ok(employeeService.findByQualificationFZId(qualificationFZId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<EmployeeDTO>> findByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(employeeService.findByCustomerId(customerId));
    }

    @GetMapping("/employeecategory/{employeeCategoryId}")
    public ResponseEntity<List<EmployeeDTO>> findByEmployeeCategoryId(@PathVariable Long employeeCategoryId) {
        return ResponseEntity.ok(employeeService.findByEmployeeCategoryId(employeeCategoryId));
    }
}