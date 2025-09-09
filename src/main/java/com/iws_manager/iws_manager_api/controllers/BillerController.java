package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Biller;
import com.iws_manager.iws_manager_api.services.interfaces.BillerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for managing {@link Biller} entities.
 * Exposes CRUD endpoints with proper HTTP status codes and response formats.
 * 
 * <p>All endpoints follow RESTful conventions and include input validation.</p>
 * 
 * @RestController Indicates that this class handles HTTP requests and returns JSON responses
 * @RequestMapping Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/v1/billers")
public class BillerController {

    private final BillerService billerService;

    /**
     * Constructor-based dependency injection.
     * 
     * @param billerService the service layer for biller operations
     */
    @Autowired
    public BillerController(BillerService billerService) {
        this.billerService = billerService;
    }

    /**
     * Creates a new biller.
     * 
     * @param biller the biller to create (from request body)
     * @return ResponseEntity containing the created biller and HTTP 201 status
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createBiller(@RequestBody Biller biller) {
        
        if (biller.getName() == null || biller.getName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Name is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Biller createdBiller = billerService.create(biller);
        return new ResponseEntity<>(createdBiller, HttpStatus.CREATED);
    }

    /**
     * Retrieves a biller by its ID.
     * 
     * @param id the ID of the biller to retrieve
     * @return ResponseEntity with the biller (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Biller> getBillerById(@PathVariable Long id) {
        return billerService.findById(id)
                .map(biller -> new ResponseEntity<>(biller, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all billeres.
     * 
     * @return ResponseEntity with list of all billeres (HTTP 200) or empty list (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<Biller>> getAllBilleres() {
        List<Biller> billeres = billerService.findAll();
        return new ResponseEntity<>(billeres, HttpStatus.OK);
    }

    /**
     * Updates an existing biller.
     * 
     * @param id the ID of the biller to update
     * @param billerDetails the updated biller data
     * @return ResponseEntity with updated biller (HTTP 200) or not found (HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Biller> updateBiller(
            @PathVariable Long id,
            @RequestBody Biller billerDetails) {
        try {
            Biller updatedBiller = billerService.update(id, billerDetails);
            return new ResponseEntity<>(updatedBiller, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a biller by its ID.
     * 
     * @param id the ID of the biller to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteBiller(@PathVariable Long id) {
        billerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}