package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountryTest {

    private Country country = new Country();
    private String countryName = "Mexico";
    private String countryLabel = "MX";
    private Integer isDefault = 0;
    private LocalDateTime now;

    @Test
    void testCountryCreation() {
        // Act
        country.setName(countryName);
        country.setLabel(countryLabel);

        // Assert
        assertEquals(countryName, country.getName());
    }

    //faltan tests
}
