package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.VatRate;
import com.iws_manager.iws_manager_api.services.interfaces.VatRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing {@link VatRate} entities.
 * Exposes CRUD endpoints with proper HTTP status codes and response formats.
 * 
 * <p>All endpoints follow RESTful conventions and include input validation.</p>
 * 
 * @RestController Indicates that this class handles HTTP requests and returns JSON responses
 * @RequestMapping Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/v1/vat-rates")
public class VatRateController {

    private final VatRateService vatRateService;

    /**
     * Constructor-based dependency injection.
     * 
     * @param vatRateService the service layer for VAT rate operations
     */
    @Autowired
    public VatRateController(VatRateService vatRateService) {
        this.vatRateService = vatRateService;
    }

    /**
     * Creates a new VAT rate.
     * 
     * @param vatRate the VAT rate to create (from request body)
     * @return ResponseEntity containing the created VAT rate (HTTP 201) or error (HTTP 400)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createVatRate(@RequestBody VatRate vatRate) {
        VatRate createdVatRate = vatRateService.create(vatRate);
        return new ResponseEntity<>(createdVatRate, HttpStatus.CREATED);
    }

    /**
     * Retrieves a VAT rate by its ID.
     * 
     * @param id the ID of the VAT rate to retrieve
     * @return ResponseEntity with the VAT rate (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<VatRate> getVatRateById(@PathVariable Long id) {
        Optional<VatRate> vatRate = vatRateService.findById(id);
        return vatRate.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                     .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all VAT rates ordered by fromdate ascending.
     * 
     * @return ResponseEntity with list of all VAT rates (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<VatRate>> getAllVatRates() {
        List<VatRate> vatRates = vatRateService.findAll();
        return new ResponseEntity<>(vatRates, HttpStatus.OK);
    }

    /**
     * Updates an existing VAT rate.
     * 
     * @param id the ID of the VAT rate to update
     * @param vatRateDetails the updated VAT rate data
     * @return ResponseEntity with updated VAT rate (HTTP 200) or not found (HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<VatRate> updateVatRate(
            @PathVariable Long id,
            @RequestBody VatRate vatRateDetails) {
        try {
            VatRate updatedVatRate = vatRateService.update(id, vatRateDetails);
            return new ResponseEntity<>(updatedVatRate, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a VAT rate by its ID.
     * 
     * @param id the ID of the VAT rate to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteVatRate(@PathVariable Long id) {
        vatRateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves VAT rates by VAT ID ordered by fromdate ascending.
     * 
     * @param vatId the ID of the VAT
     * @return ResponseEntity with list of VAT rates (HTTP 200) or error (HTTP 400)
     */
    @GetMapping("/by-vat/{vatId}")
    public ResponseEntity<List<VatRate>> getVatRatesByVatId(@PathVariable Long vatId) {
        try {
            List<VatRate> vatRates = vatRateService.getByVatId(vatId);
            return new ResponseEntity<>(vatRates, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}