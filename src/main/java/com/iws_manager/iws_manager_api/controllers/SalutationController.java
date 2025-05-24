package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Salutation;
import com.iws_manager.iws_manager_api.services.interfaces.SalutationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for managing {@link Salutation} entities.
 * Exposes CRUD endpoints with proper HTTP status codes and response formats.
 * 
 * <p>All endpoints follow RESTful conventions and include input validation.</p>
 * 
 * @RestController Indicates that this class handles HTTP requests and returns JSON responses
 * @RequestMapping Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/v1/salutations")
public class SalutationController {

    private final SalutationService salutationService;

    /**
     * Constructor-based dependency injection.
     * 
     * @param salutationService the service layer for salutation operations
     */
    @Autowired
    public SalutationController(SalutationService salutationService) {
        this.salutationService = salutationService;
    }

    /**
     * Creates a new salutation.
     * 
     * @param salutation the salutation to create (from request body)
     * @return ResponseEntity containing the created salutation and HTTP 201 status
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createSalutation(@RequestBody Salutation salutation) {
        
        if (salutation.getName() == null || salutation.getName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Name is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Salutation createdSalutation = salutationService.create(salutation);
        return new ResponseEntity<>(createdSalutation, HttpStatus.CREATED);
    }

    /**
     * Retrieves a salutation by its ID.
     * 
     * @param id the ID of the salutation to retrieve
     * @return ResponseEntity with the salutation (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Salutation> getSalutationById(@PathVariable Long id) {
        return salutationService.findById(id)
                .map(salutation -> new ResponseEntity<>(salutation, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all salutations.
     * 
     * @return ResponseEntity with list of all salutations (HTTP 200) or empty list (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<Salutation>> getAllSalutations() {
        List<Salutation> salutations = salutationService.findAll();
        return new ResponseEntity<>(salutations, HttpStatus.OK);
    }

    /**
     * Updates an existing salutation.
     * 
     * @param id the ID of the salutation to update
     * @param salutationDetails the updated salutation data
     * @return ResponseEntity with updated salutation (HTTP 200) or not found (HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Salutation> updateSalutation(
            @PathVariable Long id,
            @RequestBody Salutation salutationDetails) {
        try {
            Salutation updatedSalutation = salutationService.update(id, salutationDetails);
            return new ResponseEntity<>(updatedSalutation, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a salutation by its ID.
     * 
     * @param id the ID of the salutation to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteSalutation(@PathVariable Long id) {
        salutationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}