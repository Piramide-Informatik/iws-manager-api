package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Country;
import com.iws_manager.iws_manager_api.services.interfaces.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CountryControllerTest {

    // Constants for reusable values
    private static final String BASE_URL = "/api/v1/countries";
    private static final String COUNTRY_NAME = "Colombia";
    private static final String COUNTRY_LABEL = "CO";
    private static final String NAME_JSON_PATH = "$.name";
    private static final String LABEL_JSON_PATH = "$.label";
    private static final String ID = "/{id}";
    private static final long VALID_ID = 1L;
    private static final long INVALID_ID = 99L;

    private MockMvc mockMvc;

    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    private Country validCountry;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
        validCountry = createTestCountry(VALID_ID, COUNTRY_NAME, COUNTRY_LABEL, true);
    }

    private Country createTestCountry(Long id, String name, String label, Boolean isDefault) {
        Country country = new Country();
        country.setId(id);
        country.setName(name);
        country.setLabel(label);
        country.setIsDefault(isDefault);
        return country;
    }

    private String buildCountryJson(String name, String label, Boolean isDefault) {
        return String.format("""
            {
                "name": "%s",
                "label": "%s",
                "isDefault": %s
            }
            """, 
            name != null ? name : "", 
            label != null ? label : "", 
            isDefault != null ? isDefault : "null");
    }

    // ------------------- CREATE TESTS -------------------
    @Test
    void createCountryShouldReturnCreatedWhenValidInput() throws Exception {
        when(countryService.create(any(Country.class))).thenReturn(validCountry);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildCountryJson(COUNTRY_NAME, COUNTRY_LABEL, true)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(NAME_JSON_PATH).value(COUNTRY_NAME))
                .andExpect(jsonPath(LABEL_JSON_PATH).value(COUNTRY_LABEL));
    }

    // ------------------- GET BY ID TESTS -------------------
    @Test
    void getCountryByIdShouldReturnCountryWhenValidId() throws Exception {
        when(countryService.findById(VALID_ID)).thenReturn(Optional.of(validCountry));

        mockMvc.perform(get(BASE_URL + ID, VALID_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(NAME_JSON_PATH).value(COUNTRY_NAME))
                .andExpect(jsonPath(LABEL_JSON_PATH).value(COUNTRY_LABEL));
    }

    @Test
    void getCountryByIdShouldReturnNotFoundWhenInvalidId() throws Exception {
        when(countryService.findById(INVALID_ID)).thenReturn(Optional.empty());

        mockMvc.perform(get(BASE_URL + ID, INVALID_ID))
                .andExpect(status().isNotFound());
    }

    // ------------------- GET ALL TESTS -------------------
    @Test
    void getAllCountriesShouldReturnAllCountries() throws Exception {
        when(countryService.findAll()).thenReturn(Arrays.asList(validCountry));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]" + NAME_JSON_PATH.substring(1)).value(COUNTRY_NAME))
                .andExpect(jsonPath("$[0]" + LABEL_JSON_PATH.substring(1)).value(COUNTRY_LABEL));
    }

    // ------------------- UPDATE TESTS -------------------
    @Test
    void updateCountryShouldReturnUpdatedCountryWhenValidInput() throws Exception {
        when(countryService.update(eq(VALID_ID), any(Country.class))).thenReturn(validCountry);

        mockMvc.perform(put(BASE_URL + ID, VALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildCountryJson(COUNTRY_NAME, COUNTRY_LABEL, true)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(NAME_JSON_PATH).value(COUNTRY_NAME));
    }

    // ------------------- DELETE TESTS -------------------
    @Test
    void deleteCountryShouldReturnNoContentWhenValidId() throws Exception {
        mockMvc.perform(delete(BASE_URL + ID, VALID_ID))
                .andExpect(status().isNoContent());

        verify(countryService, times(1)).delete(VALID_ID);
    }
}