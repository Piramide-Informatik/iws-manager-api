package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.CostType;
import com.iws_manager.iws_manager_api.services.interfaces.CostTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for managing {@link CostType} entities.
 * Exposes CRUD endpoints with proper HTTP status codes and response formats.
 * 
 * <p>All endpoints follow RESTful conventions and include input validation.</p>
 * 
 * @RestController Indicates that this class handles HTTP requests and returns JSON responses
 * @RequestMapping Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/v1/costtypes")
public class CostTypeController {

    private final CostTypeService costTypeService;

    /**
     * Constructor-based dependency injection.
     * 
     * @param costTypeService the service layer for costType operations
     */
    @Autowired
    public CostTypeController(CostTypeService costTypeService) {
        this.costTypeService = costTypeService;
    }

    /**
     * Creates a new costType.
     * 
     * @param costType the costType to create (from request body)
     * @return ResponseEntity containing the created costType and HTTP 201 status
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createCostType(@RequestBody CostType costType) {
        
        if (costType.getType() == null || costType.getType().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Type is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        CostType createdCostType = costTypeService.create(costType);
        return new ResponseEntity<>(createdCostType, HttpStatus.CREATED);
    }

    /**
     * Retrieves a costType by its ID.
     * 
     * @param id the ID of the costType to retrieve
     * @return ResponseEntity with the costType (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<CostType> getcostTypeById(@PathVariable Long id) {
        return costTypeService.findById(id)
                .map(costType -> new ResponseEntity<>(costType, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all costTypes.
     * 
     * @return ResponseEntity with list of all costTypes (HTTP 200) or empty list (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<CostType>> getAllCostTypes() {
        List<CostType> costTypes = costTypeService.findAll();
        return new ResponseEntity<>(costTypes, HttpStatus.OK);
    }

    /**
     * Updates an existing costType.
     * 
     * @param id the ID of the costType to update
     * @param costTypeDetails the updated costType data
     * @return ResponseEntity with updated costType (HTTP 200) or not found (HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<CostType> updateCostType(
            @PathVariable Long id,
            @RequestBody CostType costTypeDetails) {
        try {
            CostType updatedCostType = costTypeService.update(id, costTypeDetails);
            return new ResponseEntity<>(updatedCostType, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a costType by its ID.
     * 
     * @param id the ID of the costType to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCostType(@PathVariable Long id) {
        costTypeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}