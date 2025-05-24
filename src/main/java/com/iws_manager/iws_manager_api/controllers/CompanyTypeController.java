package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.CompanyType;
import com.iws_manager.iws_manager_api.services.interfaces.CompanyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for managing {@link CompanyType} entities.
 * Exposes CRUD endpoints with proper HTTP status codes and response formats.
 * 
 * <p>All endpoints follow RESTful conventions and include input validation.</p>
 * 
 * @RestController Indicates that this class handles HTTP requests and returns JSON responses
 * @RequestMapping Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/v1/company-types")
public class CompanyTypeController {

    private final CompanyTypeService companyTypeService;

    /**
     * Constructor-based dependency injection.
     * 
     * @param companyTypeService the service layer for companyType operations
     */
    @Autowired
    public CompanyTypeController(CompanyTypeService companyTypeService) {
        this.companyTypeService = companyTypeService;
    }

    /**
     * Creates a new companyType.
     * 
     * @param companyType the companyType to create (from request body)
     * @return ResponseEntity containing the created companyType and HTTP 201 status
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createCompanyType(@RequestBody CompanyType companyType) {
        
        if (companyType.getName() == null || companyType.getName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Name is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        CompanyType createdCompanyType = companyTypeService.create(companyType);
        return new ResponseEntity<>(createdCompanyType, HttpStatus.CREATED);
    }

    /**
     * Retrieves a companyType by its ID.
     * 
     * @param id the ID of the companyType to retrieve
     * @return ResponseEntity with the companyType (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<CompanyType> getCompanyTypeById(@PathVariable Long id) {
        return companyTypeService.findById(id)
                .map(companyType -> new ResponseEntity<>(companyType, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all companyTypes.
     * 
     * @return ResponseEntity with list of all companyTypes (HTTP 200) or empty list (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<CompanyType>> getAllCompanyTypes() {
        List<CompanyType> companyTypes = companyTypeService.findAll();
        return new ResponseEntity<>(companyTypes, HttpStatus.OK);
    }

    /**
     * Updates an existing companyType.
     * 
     * @param id the ID of the companyType to update
     * @param companyTypeDetails the updated companyType data
     * @return ResponseEntity with updated companyType (HTTP 200) or not found (HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<CompanyType> updateCompanyType(
            @PathVariable Long id,
            @RequestBody CompanyType companyTypeDetails) {
        try {
            CompanyType updatedCompanyType = companyTypeService.update(id, companyTypeDetails);
            return new ResponseEntity<>(updatedCompanyType, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a companyType by its ID.
     * 
     * @param id the ID of the companyType to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCompanyType(@PathVariable Long id) {
        companyTypeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}