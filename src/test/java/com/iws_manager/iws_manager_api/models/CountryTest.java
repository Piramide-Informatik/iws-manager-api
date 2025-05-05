package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

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

        country = new Country(1, uuid, countryLabel, countryName, isDefault);

        assertThat(country.getUuid()).isEqualTo(uuid);
        assertThat(country.getCountryName()).isEqualTo(countryName);
        assertThat(country.getCountryLabel()).isEqualTo(countryLabel);
        assertThat(country.getIsDefault()).isEqualTo(isDefault);
    }

    @Test
    void testEqualsAndHashCode() {
        String uuid = UUID.randomUUID().toString();
        Country country1 = new Country(1, uuid, "MX", "Mexico", 0);
        Country country2 = new Country(1, uuid, "MX", "Mexico", 0);

        assertThat(country1).isEqualTo(country2);
        assertThat(country1.hashCode()).isEqualTo(country2.hashCode());
    }

}
