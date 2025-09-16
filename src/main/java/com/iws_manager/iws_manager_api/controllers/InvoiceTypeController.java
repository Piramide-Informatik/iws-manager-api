package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.InvoiceType;
import com.iws_manager.iws_manager_api.services.interfaces.InvoiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller for managing {@link InvoiceType} entities.
 * Exposes CRUD endpoints with proper HTTP status codes and response formats.
 * 
 * <p>All endpoints follow RESTful conventions and include input validation.</p>
 * 
 * @RestController Indicates that this class handles HTTP requests and returns JSON responses
 * @RequestMapping Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/v1/invoice-types")
public class InvoiceTypeController {

    private final InvoiceTypeService invoiceTypeService;

    /**
     * Constructor-based dependency injection.
     * 
     * @param invoiceTypeService the service layer for InvoiceType operations
     */
    @Autowired
    public InvoiceTypeController(InvoiceTypeService invoiceTypeService) {
        this.invoiceTypeService = invoiceTypeService;
    }

    /**
     * Creates a new InvoiceType.
     * 
     * @param invoiceType the InvoiceType to create (from request body)
     * @return ResponseEntity containing the created InvoiceType (HTTP 201) or error (HTTP 400)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createInvoiceType(@RequestBody InvoiceType invoiceType) {
        if (invoiceType.getName() == null || invoiceType.getName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Name is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        InvoiceType createdInvoiceType = invoiceTypeService.create(invoiceType);
        return new ResponseEntity<>(createdInvoiceType, HttpStatus.CREATED);
    }

    /**
     * Retrieves an InvoiceType by its ID.
     * 
     * @param id the ID of the InvoiceType to retrieve
     * @return ResponseEntity with the InvoiceType (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceType> getInvoiceTypeById(@PathVariable Long id) {
        Optional<InvoiceType> invoiceType = invoiceTypeService.findById(id);
        return invoiceType.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                         .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all InvoiceTypes.
     * 
     * @return ResponseEntity with list of all InvoiceTypes (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<InvoiceType>> getAllInvoiceTypes() {
        List<InvoiceType> invoiceTypes = invoiceTypeService.findAll();
        return new ResponseEntity<>(invoiceTypes, HttpStatus.OK);
    }

    /**
     * Updates an existing InvoiceType.
     * 
     * @param id the ID of the InvoiceType to update
     * @param invoiceTypeDetails the updated InvoiceType data
     * @return ResponseEntity with updated InvoiceType (HTTP 200) or not found (HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<InvoiceType> updateInvoiceType(
            @PathVariable Long id,
            @RequestBody InvoiceType invoiceTypeDetails) {
        try {
            InvoiceType updatedInvoiceType = invoiceTypeService.update(id, invoiceTypeDetails);
            return new ResponseEntity<>(updatedInvoiceType, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes an InvoiceType by its ID.
     * 
     * @param id the ID of the InvoiceType to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteInvoiceType(@PathVariable Long id) {
        invoiceTypeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}