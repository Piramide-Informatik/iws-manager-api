package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Network;
import com.iws_manager.iws_manager_api.services.interfaces.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for managing {@link Network} entities.
 * Exposes CRUD endpoints with proper HTTP status codes and response formats.
 * 
 * <p>All endpoints follow RESTful conventions and include input validation.</p>
 * 
 * @RestController Indicates that this class handles HTTP requests and returns JSON responses
 * @RequestMapping Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/v1/networks")
public class NetworkController {

    private final NetworkService networkService;

    /**
     * Constructor-based dependency injection.
     * 
     * @param networkService the service layer for Network operations
     */
    @Autowired
    public NetworkController(NetworkService networkService) {
        this.networkService = networkService;
    }

    /**
     * Creates a new Network.
     * 
     * @param network the Network to create (from request body)
     * @return ResponseEntity containing the created Network and HTTP 201 status
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createNetwork(@RequestBody Network network) {
        
        if (network.getName() == null || network.getName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Name is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Network createdNetwork = networkService.create(network);
        return new ResponseEntity<>(createdNetwork, HttpStatus.CREATED);
    }

    /**
     * Retrieves a Network by its ID.
     * 
     * @param id the ID of the Network to retrieve
     * @return ResponseEntity with the Network (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Network> getNetworkById(@PathVariable Long id) {
        return networkService.findById(id)
                .map(network -> new ResponseEntity<>(network, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all Networks.
     * 
     * @return ResponseEntity with list of all Networkes (HTTP 200) or empty list (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<Network>> getAllNetworks() {
        List<Network> networks = networkService.findAll();
        return new ResponseEntity<>(networks, HttpStatus.OK);
    }

    /**
     * Updates an existing Network.
     * 
     * @param id the ID of the Network to update
     * @param networkDetails the updated Network data
     * @return ResponseEntity with updated Network (HTTP 200) or not found (HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Network> updateNetwork(
            @PathVariable Long id,
            @RequestBody Network networkDetails) {
        try {
            Network updatedNetwork = networkService.update(id, networkDetails);
            return new ResponseEntity<>(updatedNetwork, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a Network by its ID.
     * 
     * @param id the ID of the Network to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteNetwork(@PathVariable Long id) {
        networkService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}