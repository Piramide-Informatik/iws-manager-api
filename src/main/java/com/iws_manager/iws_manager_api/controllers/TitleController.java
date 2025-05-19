package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Title;
import com.iws_manager.iws_manager_api.services.interfaces.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing {@link Title} entities.
 * Exposes CRUD endpoints with proper HTTP status codes and response formats.
 * 
 * <p>All endpoints follow RESTful conventions and include input validation.</p>
 * 
 * @RestController Indicates that this class handles HTTP requests and returns JSON responses
 * @RequestMapping Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/v1/titles")
public class TitleController {

    private final TitleService titleService;

    /**
     * Constructor-based dependency injection.
     * 
     * @param titleService the service layer for title operations
     */
    @Autowired
    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }

    /**
     * Creates a new title.
     * 
     * @param title the title to create (from request body)
     * @return ResponseEntity containing the created title and HTTP 201 status
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Title> createTitle(@RequestBody Title title) {
        Title createdTitle = titleService.create(title);
        return new ResponseEntity<>(createdTitle, HttpStatus.CREATED);
    }

    /**
     * Retrieves a title by its ID.
     * 
     * @param id the ID of the title to retrieve
     * @return ResponseEntity with the title (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Title> getTitleById(@PathVariable Long id) {
        return titleService.findById(id)
                .map(title -> new ResponseEntity<>(title, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all titles.
     * 
     * @return ResponseEntity with list of all titles (HTTP 200) or empty list (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<Title>> getAllTitles() {
        List<Title> titles = titleService.findAll();
        return new ResponseEntity<>(titles, HttpStatus.OK);
    }

    /**
     * Updates an existing title.
     * 
     * @param id the ID of the title to update
     * @param titleDetails the updated title data
     * @return ResponseEntity with updated title (HTTP 200) or not found (HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Title> updateTitle(
            @PathVariable Long id,
            @RequestBody Title titleDetails) {
        try {
            Title updatedTitle = titleService.update(id, titleDetails);
            return new ResponseEntity<>(updatedTitle, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a title by its ID.
     * 
     * @param id the ID of the title to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTitle(@PathVariable Long id) {
        titleService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}