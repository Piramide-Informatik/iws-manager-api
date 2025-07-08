package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.QualificationFZ;
import com.iws_manager.iws_manager_api.services.interfaces.QualificationFZService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for managing {@link Branch} entities.
 * Exposes CRUD endpoints with proper HTTP status codes and response formats.
 * 
 * <p>All endpoints follow RESTful conventions and include input validation.</p>
 * 
 * @RestController Indicates that this class handles HTTP requests and returns JSON responses
 * @RequestMapping Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/v1/qualificationfz")
public class QualificationFZController {

    private final QualificationFZService qualificationFZService;

    /**
     * Constructor-based dependency injection.
     * 
     * @param qualificationFZService the service layer for QualificationFZ operations
     */
    @Autowired
    public QualificationFZController(QualificationFZService qualificationFZService) {
        this.qualificationFZService = qualificationFZService;
    }

    /**
     * Creates a new qualificationFZ.
     * 
     * @param qualificationFZ the qualificationFZ to create (from request body)
     * @return ResponseEntity containing the created qualificationFZ and HTTP 201 status
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createBranch(@RequestBody  QualificationFZ qualificationFZ) {
        
        if (qualificationFZ.getQualification() == null || qualificationFZ.getQualification().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Name is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        QualificationFZ createdQualificationFZ = qualificationFZService.create(qualificationFZ);
        return new ResponseEntity<>(createdQualificationFZ, HttpStatus.CREATED);
    }

    /**
     * Retrieves a qualificationFZ by its ID.
     * 
     * @param id the ID of the qualificationFZ to retrieve
     * @return ResponseEntity with the qualificationFZ (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<QualificationFZ> getQualificationFZById(@PathVariable Long id) {
        return qualificationFZService.findById(id)
                .map(qualificationFZ -> new ResponseEntity<>(qualificationFZ, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all qualificationFZ.
     * 
     * @return ResponseEntity with list of all qualificationFZ (HTTP 200) or empty list (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<QualificationFZ>> getAllQualificationFZ() {
        List<QualificationFZ> qualificationsFZ = qualificationFZService.findAll();
        return new ResponseEntity<>(qualificationsFZ, HttpStatus.OK);
    }

    /**
     * Updates an existing qualificationFZ.
     * 
     * @param id the ID of the qualificationFZ to update
     * @param qualificationFZDetails the updated qualificationFZ data
     * @return ResponseEntity with updated qualificationFZ (HTTP 200) or not found (HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<QualificationFZ> updateQualificationFZ(
            @PathVariable Long id,
            @RequestBody QualificationFZ qualificationFZDetails) {
        try {
            QualificationFZ updatedBranch = qualificationFZService.update(id, qualificationFZDetails);
            return new ResponseEntity<>(updatedBranch, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a qualificationFZ by its ID.
     * 
     * @param id the ID of the qualificationFZ to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteQualificationFZ(@PathVariable Long id) {
        qualificationFZService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}