package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit Test for Country entity model
 */
public class CountryTest {

    private Country country;

    @Test
    void testCountryModel() {
        String uuid = UUID.randomUUID().toString();
        String countryName = "Mexico";
        String countryLabel = "MX";
        int isDefault = 0;

        country = new Country();
        country.setUuid(uuid);
        country.setCountryName(countryName);
        country.setCountryLabel(countryLabel);
        country.setIsDefault(isDefault);

        assertThat(country.getUuid()).isEqualTo(uuid);
        assertThat(country.getCountryName()).isEqualTo(countryName);
        assertThat(country.getCountryLabel()).isEqualTo(countryLabel);
        assertThat(country.getIsDefault()).isEqualTo(isDefault);
    }

    @Test
    void testCountryConstructor() {
        String uuid = UUID.randomUUID().toString();
        String countryName = "Mexico";
        String countryLabel = "MX";
        Integer isDefault = 0;
        LocalDateTime now = LocalDateTime.now();

        country = new Country(1, uuid, countryLabel, countryName, isDefault, now, now);

        assertThat(country.getUuid()).isEqualTo(uuid);
        assertThat(country.getCountryName()).isEqualTo(countryName);
        assertThat(country.getCountryLabel()).isEqualTo(countryLabel);
        assertThat(country.getIsDefault()).isEqualTo(isDefault);
    }

    @Test
    void testSalutationWithAuditFields(){
        String uuid = UUID.randomUUID().toString();
        String countryName = "Mexico";
        String countryLabel = "MX";
        int isDefault = 0;
        LocalDateTime now = LocalDateTime.now();

        country = new Country();
        country.setUuid(uuid);
        country.setCountryName(countryName);
        country.setCountryLabel(countryLabel);
        country.setIsDefault(isDefault);

        country.setCreatedAt(now);
        country.setUpdatedAt(now);

        assertEquals(now, country.getCreatedAt());
        assertEquals(now, country.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        String uuid = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        Country country1 = new Country(1, uuid, "MX", "Mexico", 0, now, now);
        Country country2 = new Country(1, uuid, "MX", "Mexico", 0, now, now);

        assertThat(country1).isEqualTo(country2);
        assertThat(country1.hashCode()).isEqualTo(country2.hashCode());
    }

}
