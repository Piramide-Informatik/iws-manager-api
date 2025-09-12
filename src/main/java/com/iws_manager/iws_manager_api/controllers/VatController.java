package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Vat;
import com.iws_manager.iws_manager_api.services.interfaces.VatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller for managing {@link Vat} entities.
 * Exposes CRUD endpoints with proper HTTP status codes and response formats.
 * 
 * <p>All endpoints follow RESTful conventions and include input validation.</p>
 * 
 * @RestController Indicates that this class handles HTTP requests and returns JSON responses
 * @RequestMapping Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/v1/vats")
public class VatController {

    private final VatService vatService;

    /**
     * Constructor-based dependency injection.
     * 
     * @param vatService the service layer for VAT operations
     */
    @Autowired
    public VatController(VatService vatService) {
        this.vatService = vatService;
    }

    /**
     * Creates a new VAT.
     * 
     * @param vat the VAT to create (from request body)
     * @return ResponseEntity containing the created VAT (HTTP 201) or error (HTTP 400)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createVat(@RequestBody Vat vat) {
        if (vat.getLabel() == null || vat.getLabel().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Label is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Vat createdVat = vatService.create(vat);
        return new ResponseEntity<>(createdVat, HttpStatus.CREATED);
    }

    /**
     * Retrieves a VAT by its ID.
     * 
     * @param id the ID of the VAT to retrieve
     * @return ResponseEntity with the VAT (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vat> getVatById(@PathVariable Long id) {
        Optional<Vat> vat = vatService.findById(id);
        return vat.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                  .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all VATs.
     * 
     * @return ResponseEntity with list of all VATs (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<Vat>> getAllVats() {
        List<Vat> vats = vatService.findAll();
        return new ResponseEntity<>(vats, HttpStatus.OK);
    }

    /**
     * Updates an existing VAT.
     * 
     * @param id the ID of the VAT to update
     * @param vatDetails the updated VAT data
     * @return ResponseEntity with updated VAT (HTTP 200) or not found (HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vat> updateVat(
            @PathVariable Long id,
            @RequestBody Vat vatDetails) {
        try {
            Vat updatedVat = vatService.update(id, vatDetails);
            return new ResponseEntity<>(updatedVat, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a VAT by its ID.
     * 
     * @param id the ID of the VAT to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteVat(@PathVariable Long id) {
        vatService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}