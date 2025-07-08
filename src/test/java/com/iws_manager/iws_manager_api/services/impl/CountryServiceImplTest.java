package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;

import com.iws_manager.iws_manager_api.models.Country;
import com.iws_manager.iws_manager_api.repositories.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private CountryServiceImpl countryService;

    private Country country;

    @BeforeEach
    void setUp() {
        country = new Country();
        country.setId(1L);
        country.setName("Colombia");
        country.setLabel("CO");
        country.setIsDefault(true);
    }

    // ------------------- CREATE TESTS -------------------
    @Test
    void createShouldReturnSavedCountry() {
        when(countryRepository.save(any(Country.class))).thenReturn(country);

        Country savedCountry = countryService.create(country);

        assertNotNull(savedCountry);
        assertEquals("Colombia", savedCountry.getName());
        verify(countryRepository, times(1)).save(country);
    }

    @Test
    void createShouldThrowIllegalArgumentExceptionWhenCountryIsNull() {
        assertThrows(IllegalArgumentException.class, () -> countryService.create(null));
    }

    // ------------------- FIND BY ID TESTS -------------------
    @Test
    void findByIdShouldReturnCountryWhenIdExists() {
        when(countryRepository.findById(1L)).thenReturn(Optional.of(country));

        Optional<Country> foundCountry = countryService.findById(1L);

        assertTrue(foundCountry.isPresent());
        assertEquals("CO", foundCountry.get().getLabel());
    }

    @Test
    void findByIdShouldReturnEmptyWhenIdDoesNotExist() {
        when(countryRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Country> foundCountry = countryService.findById(2L);

        assertTrue(foundCountry.isEmpty());
    }

    @Test
    void findByIdShouldThrowIllegalArgumentExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> countryService.findById(null));
    }

    // ------------------- FIND ALL TESTS -------------------
    @Test
    void findAllShouldReturnAllCountriesOrderedByName() {
        Country country1 = new Country();
        country1.setName("Brasil");
        Country country2 = new Country();
        country2.setName("Argentina");
        
        when(countryRepository.findAllByOrderByNameAsc())
            .thenReturn(List.of(country2, country1)); 

        List<Country> countries = countryService.findAll();

        assertFalse(countries.isEmpty());
        assertEquals(2, countries.size());
        assertEquals("Argentina", countries.get(0).getName());
        assertEquals("Brasil", countries.get(1).getName());
        verify(countryRepository, times(1)).findAllByOrderByNameAsc();
    }

    // ------------------- UPDATE TESTS -------------------
    @Test
    void updateShouldUpdateCountryWhenIdExists() {
        Country updatedDetails = new Country();
        updatedDetails.setName("Argentina");
        updatedDetails.setLabel("AR");
        updatedDetails.setIsDefault(false);

        when(countryRepository.findById(1L)).thenReturn(Optional.of(country));
        when(countryRepository.save(any(Country.class))).thenReturn(country);

        Country updatedCountry = countryService.update(1L, updatedDetails);

        assertEquals("Argentina", updatedCountry.getName());
        assertEquals("AR", updatedCountry.getLabel());
        assertFalse(updatedCountry.getIsDefault());
    }

    @Test
    void updateShouldThrowRuntimeExceptionWhenIdDoesNotExist() {
        Country updatedDetails = new Country();
        when(countryRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> countryService.update(2L, updatedDetails));
    }

    @Test
    void updateShouldThrowIllegalArgumentExceptionWhenParamsAreNull() {
        assertThrows(IllegalArgumentException.class, () -> countryService.update(null, new Country()));
        assertThrows(IllegalArgumentException.class, () -> countryService.update(1L, null));
    }

    // ------------------- DELETE TESTS -------------------
    @Test
    void deleteShouldDeleteCountryWhenIdExists() {
        doNothing().when(countryRepository).deleteById(1L);

        countryService.delete(1L);

        verify(countryRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteShouldThrowIllegalArgumentExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> countryService.delete(null));
    }
}