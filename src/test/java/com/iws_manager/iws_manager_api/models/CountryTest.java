package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountryTest {

    private Country country;
    private String countryName;
    private String countryLabel;
    private Integer isDefault;
    private LocalDateTime now;

    @BeforeEach
    public void setUp() {
        country = new Country();
        countryName = "Mexico";
        countryLabel = "MX";
        isDefault = 0;
        now = LocalDateTime.now();
    }

    @Test
    void testCountryModel() {

        country.setCountryName(countryName);
        country.setCountryLabel(countryLabel);
        country.setIsDefault(isDefault);

        assertThat(country.getCountryName()).isEqualTo(countryName);
        assertThat(country.getCountryLabel()).isEqualTo(countryLabel);
        assertThat(country.getIsDefault()).isEqualTo(isDefault);
    }

    @Test
    void testCountryConstructor() {
        country = new Country(1,  countryLabel, countryName, isDefault, now, now);

        assertThat(country.getCountryName()).isEqualTo(countryName);
        assertThat(country.getCountryLabel()).isEqualTo(countryLabel);
        assertThat(country.getIsDefault()).isEqualTo(isDefault);
    }

    @Test
    void testSalutationWithAuditFields(){
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
        Country country1 = new Country(1, countryLabel, countryName, isDefault, now, now);
        Country country2 = new Country(1, countryLabel, countryName, isDefault, now, now);

        assertThat(country1).isEqualTo(country2);
        assertThat(country1.hashCode()).isEqualTo(country2.hashCode());
    }
}
