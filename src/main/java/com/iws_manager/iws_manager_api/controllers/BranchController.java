package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Branch;
import com.iws_manager.iws_manager_api.services.interfaces.BranchService;
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
@RequestMapping("/api/v1/branches")
public class BranchController {

    private final BranchService branchService;

    /**
     * Constructor-based dependency injection.
     * 
     * @param branchService the service layer for branch operations
     */
    @Autowired
    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    /**
     * Creates a new branch.
     * 
     * @param branch the branch to create (from request body)
     * @return ResponseEntity containing the created branch and HTTP 201 status
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createBranch(@RequestBody Branch branch) {
        
        if (branch.getName() == null || branch.getName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Name is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Branch createdBranch = branchService.create(branch);
        return new ResponseEntity<>(createdBranch, HttpStatus.CREATED);
    }

    /**
     * Retrieves a branch by its ID.
     * 
     * @param id the ID of the branch to retrieve
     * @return ResponseEntity with the branch (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable Long id) {
        return branchService.findById(id)
                .map(branch -> new ResponseEntity<>(branch, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all branches.
     * 
     * @return ResponseEntity with list of all branches (HTTP 200) or empty list (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<Branch>> getAllBranches() {
        List<Branch> branches = branchService.findAll();
        return new ResponseEntity<>(branches, HttpStatus.OK);
    }

    /**
     * Updates an existing branch.
     * 
     * @param id the ID of the branch to update
     * @param branchDetails the updated branch data
     * @return ResponseEntity with updated branch (HTTP 200) or not found (HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Branch> updateBranch(
            @PathVariable Long id,
            @RequestBody Branch branchDetails) {
        try {
            Branch updatedBranch = branchService.update(id, branchDetails);
            return new ResponseEntity<>(updatedBranch, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a branch by its ID.
     * 
     * @param id the ID of the branch to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        branchService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}