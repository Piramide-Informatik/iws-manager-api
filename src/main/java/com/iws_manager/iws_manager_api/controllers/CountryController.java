package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Country;
import com.iws_manager.iws_manager_api.services.interfaces.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST Controller for managing {@link Country} entities.
 * Exposes CRUD endpoints with proper HTTP status codes and response formats.
 * 
 * <p>All endpoints follow RESTful conventions and include input validation.</p>
 * 
 * @RestController Indicates that this class handles HTTP requests and returns JSON responses
 * @RequestMapping Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    private final CountryService countryService;

    /**
     * Constructor-based dependency injection.
     * 
     * @param countryService the service layer for country operations
     */
    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * Creates a new country.
     * 
     * @param country the country to create (from request body)
     * @return ResponseEntity containing the created country (HTTP 201) or error (HTTP 400)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createCountry(@RequestBody Country country) {
        if (country.getName() == null || country.getName().trim().isEmpty() ||
            country.getLabel() == null || country.getLabel().trim().isEmpty()) {
            
            Map<String, String> error = new HashMap<>();
            error.put("error", "Both name and label are required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Country createdCountry = countryService.create(country);
        return new ResponseEntity<>(createdCountry, HttpStatus.CREATED);
    }

    /**
     * Retrieves a country by its ID.
     * 
     * @param id the ID of the country to retrieve
     * @return ResponseEntity with the country (HTTP 200) or not found (HTTP 404)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable Long id) {
        Optional<Country> country = countryService.findById(id);
        return country.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                     .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Retrieves all countries.
     * 
     * @return ResponseEntity with list of all countries (HTTP 200)
     */
    @GetMapping
    public ResponseEntity<List<Country>> getAllCountries() {
        List<Country> countries = countryService.findAll();
        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    /**
     * Updates an existing country.
     * 
     * @param id the ID of the country to update
     * @param countryDetails the updated country data
     * @return ResponseEntity with updated country (HTTP 200) or not found (HTTP 404)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(
            @PathVariable Long id,
            @RequestBody Country countryDetails) {
        try {
            Country updatedCountry = countryService.update(id, countryDetails);
            return new ResponseEntity<>(updatedCountry, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a country by its ID.
     * 
     * @param id the ID of the country to delete
     * @return ResponseEntity with no content (HTTP 204)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        countryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}