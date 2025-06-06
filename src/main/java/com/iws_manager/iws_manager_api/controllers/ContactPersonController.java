package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.ContactPerson;
import com.iws_manager.iws_manager_api.services.interfaces.ContactPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing {@link ContactPerson} entities.
 * Exposes CRUD endpoints with proper HTTP status codes and response formats.
 * 
 * <p>All endpoints follow RESTful conventions and include input validation.</p>
 * 
 * @RestController Indicates that this class handles HTTP requests and returns JSON responses
 * @RequestMapping Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/v1/contacts")
public class ContactPersonController {

    private final ContactPersonService contactPersonService;

    /**
     * Constructor-based dependency injection.
     * 
     * @param contactPersonService the service layer for contactPerson operations
     */
    @Autowired
    public ContactPersonController(ContactPersonService contactPersonService) {
        this.contactPersonService = contactPersonService;
    }

    /**
     * Creates a new contactPerson.
     * 
     * @param contactPerson the contactPerson to create (from request body)
     * @return ResponseEntity containing the created contactPerson (HTTP 201) or error (HTTP 400)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createContactPerson(@RequestBody ContactPerson contactPerson) {
        ContactPerson createdContactPerson = contactPersonService.create(contactPerson);
        return new ResponseEntity<>(createdContactPerson, HttpStatus.CREATED);
    }

    /**
     * Retrieves a contactPerson by its ID.
     * 
     * @param id the ID of the contactPerson to retrieve
     * @return ResponseEntity with the contactPerson (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContactPerson> getContactPersonById(@PathVariable Long id) {
        Optional<ContactPerson> contactPerson = contactPersonService.findById(id);
        return contactPerson.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                     .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all contactPersons.
     * 
     * @return ResponseEntity with list of all contactPersons (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<ContactPerson>> getAllContactPersons() {
        List<ContactPerson> contactPersons = contactPersonService.findAll();
        return new ResponseEntity<>(contactPersons, HttpStatus.OK);
    }

    /**
     * Updates an existing contactPerson.
     * 
     * @param id the ID of the contactPerson to update
     * @param contactPersonDetails the updated contactPerson data
     * @return ResponseEntity with updated contactPerson (HTTP 200) or not found (HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContactPerson> updateContactPerson(
            @PathVariable Long id,
            @RequestBody ContactPerson contactPersonDetails) {
        try {
            ContactPerson updatedContactPerson = contactPersonService.update(id, contactPersonDetails);
            return new ResponseEntity<>(updatedContactPerson, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a contactPerson by its ID.
     * 
     * @param id the ID of the contactPerson to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteContactPerson(@PathVariable Long id) {
        contactPersonService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}