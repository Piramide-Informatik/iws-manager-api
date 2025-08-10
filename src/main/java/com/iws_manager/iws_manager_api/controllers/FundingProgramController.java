package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.FundingProgram;
import com.iws_manager.iws_manager_api.services.interfaces.FundingProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller for managing {@link FundingProgram} entities.
 * Exposes CRUD endpoints with proper HTTP status codes and response formats.
 *
 * <p>All endpoints follow RESTful conventions and include input validation.</p>
 *
 * @RestController Indicates that this class handles HTTP requests and returns JSON responses
 * @RequestMapping Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/v1/funding-programs")
public class FundingProgramController {

    private final FundingProgramService fundingProgramService;

    /**
     * Constructor-based dependency injection.
     *
     * @param fundingProgramService the service layer for funding program operations
     */
    @Autowired
    public FundingProgramController(FundingProgramService fundingProgramService) {
        this.fundingProgramService = fundingProgramService;
    }

    /**
     * Creates a new funding program.
     *
     * @param fundingProgram the funding program to create (from request body)
     * @return ResponseEntity containing the created funding program and HTTP 201 status
     */
    @PostMapping
    public ResponseEntity<?> createFundingProgram(@RequestBody FundingProgram fundingProgram) {
        if (fundingProgram.getName() == null || fundingProgram.getName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Name is required");
            return ResponseEntity.badRequest().body(error);
        }
        FundingProgram created = fundingProgramService.create(fundingProgram);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves a funding program by its ID.
     *
     * @param id the ID of the funding program to retrieve
     * @return ResponseEntity with the funding program (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<FundingProgram> getFundingProgramById(@PathVariable Long id) {
        Optional<FundingProgram> program = fundingProgramService.findById(id);
        return program.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all funding programs.
     *
     * @return ResponseEntity with list of all funding programs (HTTP 200) or empty list (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<FundingProgram>> getAllFundingPrograms() {
        List<FundingProgram> programs = fundingProgramService.findAll();
        return ResponseEntity.ok(programs);
    }

    /**
     * Updates an existing funding program.
     *
     * @param id the ID of the funding program to update
     * @param fundingProgramDetails the updated funding program data
     * @return ResponseEntity with the updated funding program (HTTP 200)
     */
    @PutMapping("/{id}")
    public ResponseEntity<FundingProgram> updateFundingProgram(@PathVariable Long id, @RequestBody FundingProgram fundingProgramDetails) {
        try {
            FundingProgram updated = fundingProgramService.update(id, fundingProgramDetails);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a funding program by its ID.
     *
     * @param id the ID of the funding program to delete
     * @return ResponseEntity with HTTP 204 if deleted successfully
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFundingProgram(@PathVariable Long id) {
        fundingProgramService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
