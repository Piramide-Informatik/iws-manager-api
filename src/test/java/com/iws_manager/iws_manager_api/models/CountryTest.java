package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountryTest {

    private Country country = new Country();
    private String countryName = "Mexico";
    private String countryLabel = "MX";
    private Integer isDefault = 0;
    private LocalDateTime now = LocalDateTime.now();

    @Test
    void testCountryCreation() {
        // Act
        country.setName(countryName);
        country.setLabel(countryLabel);
        country.setIsDefault(isDefault);

        // Assert
        assertEquals(countryName, country.getName());
        assertEquals(countryLabel, country.getLabel());
        assertEquals(isDefault, country.getIsDefault());
    }

    @Test
    void testCountryWithAuditFields() {
        // Arrange
        country.setName(countryName);

        // Act
        country.setCreatedAt(now);
        country.setUpdatedAt(now);

        // Assert
        assertEquals(now, country.getCreatedAt());
        assertEquals(now, country.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        country.setName(countryName);
        country.setId(1L);

        Country country2 = new Country();
        country2.setName(countryName);
        country2.setId(1L);

        // Assert
        assertEquals(country, country2);
        assertEquals(country.hashCode(), country2.hashCode());
    }
}
