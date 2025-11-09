package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.Country;

public interface CountryService {
    Country create(Country country);
    Optional<Country> findById(Long id);
    List<Country> findAll();
    Country update(Long id, Country countryDetails);
    void delete(Long id);

    Country updateWithDefaultHandling(Long id, Country countryDetails);
    Country createWithDefaultHandling(Country country);
}
