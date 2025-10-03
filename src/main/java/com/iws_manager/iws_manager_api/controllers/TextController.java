package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Text;
import com.iws_manager.iws_manager_api.services.interfaces.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller for managing {@link Text} entities.
 * Exposes CRUD endpoints with proper HTTP status codes and response formats.
 * 
 * <p>All endpoints follow RESTful conventions and include input validation.</p>
 * 
 * @RestController Indicates that this class handles HTTP requests and returns JSON responses
 * @RequestMapping Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/v1/texts")
public class TextController {

    private final TextService textService;

    /**
     * Constructor-based dependency injection.
     * 
     * @param textService the service layer for Text operations
     */
    @Autowired
    public TextController(TextService textService) {
        this.textService = textService;
    }

    /**
     * Creates a new Text.
     * 
     * @param text the Text to create (from request body)
     * @return ResponseEntity containing the created Text (HTTP 201) or error (HTTP 400)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createText(@RequestBody Text text) {
        Text createdText = textService.create(text);
        return new ResponseEntity<>(createdText, HttpStatus.CREATED);
    }

    /**
     * Retrieves a Text by its ID.
     * 
     * @param id the ID of the Text to retrieve
     * @return ResponseEntity with the Text (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Text> getTextById(@PathVariable Long id) {
        Optional<Text> text = textService.findById(id);
        return text.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                   .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all Texts.
     * 
     * @return ResponseEntity with list of all Texts (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<Text>> getAllTexts() {
        List<Text> texts = textService.findAll();
        return new ResponseEntity<>(texts, HttpStatus.OK);
    }

    /**
     * Updates an existing Text.
     * 
     * @param id the ID of the Text to update
     * @param textDetails the updated Text data
     * @return ResponseEntity with updated Text (HTTP 200) or not found (HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Text> updateText(
            @PathVariable Long id,
            @RequestBody Text textDetails) {
        try {
            Text updatedText = textService.update(id, textDetails);
            return new ResponseEntity<>(updatedText, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a Text by its ID.
     * 
     * @param id the ID of the Text to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteText(@PathVariable Long id) {
        textService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}