package com.iws_manager.iws_manager_api.services.impl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.Country;
import com.iws_manager.iws_manager_api.repositories.CountryRepository;
import com.iws_manager.iws_manager_api.services.interfaces.CountryService;

/**
 * Implementation of the {@link CountryService} interface for managing Country entities.
 * Provides CRUD operations and business logic for Country management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    /**
     * Constructs a new CountryService with the required repository dependency.
     * 
     * @param CountryRepository the repository for Country entity operations
     */
    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    /**
     * Creates and persists a new Country entity.
     * 
     * @param Country the Country entity to be created
     * @return the persisted Country entity with generated ID
     * @throws IllegalArgumentException if the Country parameter is null
     */
    @Override
    public Country create(Country country) {
        if (country == null) {
            throw new IllegalArgumentException("Country cannot be null");
        }
        return countryRepository.save(country);
    }

    /**
     * Retrieves a Country by its unique identifier.
     * 
     * @param id the ID of the Country to retrieve
     * @return an Optional containing the found Country, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Country> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return countryRepository.findById(id);
    }

    /**
     * Retrieves all Country entities from the database.
     * 
     * @return a List of all Country entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Country> findAll() {
        return countryRepository.findAllByOrderByNameAsc();
    }

    /**
     * Updates an existing Country entity.
     * 
     * @param id the ID of the Country to update
     * @param countryDetails the Country object containing updated fields
     * @return the updated Country entity
     * @throws RuntimeException if no Country exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public Country update(Long id, Country countryDetails) {
        if (id == null || countryDetails == null) {
            throw new IllegalArgumentException("ID and country details cannot be null");
        }
        
        return countryRepository.findById(id)
                .map(existingCountry -> {
                    existingCountry.setName(countryDetails.getName());
                    existingCountry.setLabel(countryDetails.getLabel());
                    existingCountry.setIsDefault(countryDetails.getIsDefault());
                    return countryRepository.save(existingCountry);
                })
                .orElseThrow(() -> new RuntimeException("Country not found with id: " + id));
    }

    /**
     * Deletes a Country entity by its ID.
     * 
     * @param id the ID of the Country to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (!countryRepository.existsById(id)) {
            throw new EntityNotFoundException("country not found with id: " + id);
        }
        countryRepository.deleteById(id);
    }

    /**
     * Creates a new Country entity with exclusive default handling.
     * If the country is being created as default (isDefault = true), all other countries
     * will be automatically set to isDefault = false to maintain only one default country.
     * 
     * @param country the Country entity to be created
     * @return the persisted Country entity with generated ID
     * @throws IllegalArgumentException if the Country parameter is null
     */
    @Override
    public Country createWithDefaultHandling(Country country) {
        if (country == null) {
            throw new IllegalArgumentException("Country cannot be null");
        }

        if (Boolean.TRUE.equals(country.getIsDefault())) {
            resetOtherDefaults(null); 
        }

        return countryRepository.save(country);
    }

    /**
     * Updates an existing Country entity with exclusive default handling.
     * If the country is being set as default (isDefault = true), all other countries
     * will be automatically set to isDefault = false to maintain only one default country.
     * 
     * @param id the ID of the Country to update
     * @param countryDetails the Country object containing updated fields
     * @return the updated Country entity
     * @throws RuntimeException if no Country exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public Country updateWithDefaultHandling(Long id, Country countryDetails) {
        if (id == null || countryDetails == null) {
            throw new IllegalArgumentException("ID and country details cannot be null");
        }
        
        return countryRepository.findById(id)
                .map(existingCountry -> {
                    if (Boolean.TRUE.equals(countryDetails.getIsDefault())) {
                        resetOtherDefaults(id);
                    }
                    
                    existingCountry.setName(countryDetails.getName());
                    existingCountry.setLabel(countryDetails.getLabel());
                    existingCountry.setIsDefault(countryDetails.getIsDefault());
                    return countryRepository.save(existingCountry);
                })
                .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + id));
    }

    private void resetOtherDefaults(Long currentCountryId) {
        List<Country> defaultCountries = countryRepository.findByIsDefaultTrue();
        
        for (Country country : defaultCountries) {
            if (!country.getId().equals(currentCountryId)) {
                country.setIsDefault(false);
                countryRepository.save(country);
            }
        }
    }
}