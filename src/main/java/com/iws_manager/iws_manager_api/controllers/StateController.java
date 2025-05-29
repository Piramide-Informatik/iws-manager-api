package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.State;
import com.iws_manager.iws_manager_api.services.interfaces.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for managing {@link State} entities.
 * Exposes CRUD endpoints with proper HTTP status codes and response formats.
 *
 * <p>All endpoints follow RESTful conventions and include input validation.</p>
 *
 * @RestController Indicates that this class handles HTTP requests and returns JSON responses
 * @RequestMapping Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/v1/states")
public class StateController {
    private final StateService stateService;

    /**
     * Constructor-based dependency injection.
     *
     * @param stateService the service layer for state operations
     */
    @Autowired
    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    /**
     * Creates a new state.
     *
     * @param state the state to create (from request body)
     * @return ResponseEntity containing the created state and HTTP 201 status
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createState(@RequestBody State state) {

        if (state.getName() == null || state.getName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Name is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        State createdState = stateService.create(state);
        return new ResponseEntity<>(createdState, HttpStatus.CREATED);
    }

    /**
     * Retrieves a state by its ID.
     *
     * @param id the ID of the state to retrieve
     * @return ResponseEntity with the state (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<State> getStateById(@PathVariable Long id) {
        return stateService.findById(id)
                .map(state -> new ResponseEntity<>(state, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all states.
     *
     * @return ResponseEntity with list of all states (HTTP 200) or empty list (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<State>> getAllStates() {
        List<State> states = stateService.findAll();
        return new ResponseEntity<>(states, HttpStatus.OK);
    }

    /**
     * Updates an existing state.
     *
     * @param id the ID of the state to update
     * @param stateDetails the updated state data
     * @return ResponseEntity with updated state (HTTP 200) or not found (HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<State> updateState(
            @PathVariable Long id,
            @RequestBody State stateDetails) {
        try {
            State updatedState = stateService.update(id, stateDetails);
            return new ResponseEntity<>(updatedState, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a state by its ID.
     *
     * @param id the ID of the state to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteState(@PathVariable Long id) {
        stateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
