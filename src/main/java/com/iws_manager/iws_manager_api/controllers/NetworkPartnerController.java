package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.NetworkPartner;
import com.iws_manager.iws_manager_api.services.interfaces.NetworkPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing {@link NetworkPartner} entities.
 * Exposes CRUD endpoints with proper HTTP status codes and response formats.
 * 
 * <p>All endpoints follow RESTful conventions and include input validation.</p>
 * 
 * @RestController Indicates that this class handles HTTP requests and returns JSON responses
 * @RequestMapping Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/v1/network-partners")
public class NetworkPartnerController {

    private final NetworkPartnerService networkPartnerService;

    /**
     * Constructor-based dependency injection.
     * 
     * @param networkPartnerService the service layer for network partner operations
     */
    @Autowired
    public NetworkPartnerController(NetworkPartnerService networkPartnerService) {
        this.networkPartnerService = networkPartnerService;
    }

    /**
     * Creates a new network partner.
     * 
     * @param networkPartner the network partner to create (from request body)
     * @return ResponseEntity containing the created network partner (HTTP 201) or error (HTTP 400)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createNetworkPartner(@RequestBody NetworkPartner networkPartner) {
        NetworkPartner createdNetworkPartner = networkPartnerService.create(networkPartner);
        return new ResponseEntity<>(createdNetworkPartner, HttpStatus.CREATED);
    }

    /**
     * Retrieves a network partner by its ID.
     * 
     * @param id the ID of the network partner to retrieve
     * @return ResponseEntity with the network partner (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<NetworkPartner> getNetworkPartnerById(@PathVariable Long id) {
        Optional<NetworkPartner> networkPartner = networkPartnerService.findById(id);
        return networkPartner.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                           .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all network partners ordered by partner number.
     * 
     * @return ResponseEntity with list of all network partners (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<NetworkPartner>> getAllNetworkPartners() {
        List<NetworkPartner> networkPartners = networkPartnerService.findAll();
        return new ResponseEntity<>(networkPartners, HttpStatus.OK);
    }

    /**
     * Updates an existing network partner.
     * 
     * @param id the ID of the network partner to update
     * @param networkPartnerDetails the updated network partner data
     * @return ResponseEntity with updated network partner (HTTP 200) or not found (HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<NetworkPartner> updateNetworkPartner(
            @PathVariable Long id,
            @RequestBody NetworkPartner networkPartnerDetails) {
        try {
            NetworkPartner updatedNetworkPartner = networkPartnerService.update(id, networkPartnerDetails);
            return new ResponseEntity<>(updatedNetworkPartner, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a network partner by its ID.
     * 
     * @param id the ID of the network partner to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteNetworkPartner(@PathVariable Long id) {
        networkPartnerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves network partners by network ID.
     * 
     * @param networkId the ID of the network
     * @return ResponseEntity with list of network partners (HTTP 200) or error (HTTP 400/404)
     */
    @GetMapping("/by-network/{networkId}")
    public ResponseEntity<List<NetworkPartner>> getNetworkPartnersByNetworkId(@PathVariable Long networkId) {
        try {
            List<NetworkPartner> networkPartners = networkPartnerService.findByNetworkId(networkId);
            return new ResponseEntity<>(networkPartners, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves network partners by partner ID.
     * 
     * @param partnerId the ID of the partner (customer)
     * @return ResponseEntity with list of network partners (HTTP 200) or error (HTTP 400/404)
     */
    @GetMapping("/by-partner/{partnerId}")
    public ResponseEntity<List<NetworkPartner>> getNetworkPartnersByPartnerId(@PathVariable Long partnerId) {
        try {
            List<NetworkPartner> networkPartners = networkPartnerService.findByPartnerId(partnerId);
            return new ResponseEntity<>(networkPartners, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Retrieves network partners by contact ID.
     * 
     * @param contactId the ID of the contact person
     * @return ResponseEntity with list of network partners (HTTP 200) or error (HTTP 400/404)
     */
    @GetMapping("/by-contact/{contactId}")
    public ResponseEntity<List<NetworkPartner>> getNetworkPartnersByContactId(@PathVariable Long contactId) {
        try {
            List<NetworkPartner> networkPartners = networkPartnerService.findByContactId(contactId);
            return new ResponseEntity<>(networkPartners, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}