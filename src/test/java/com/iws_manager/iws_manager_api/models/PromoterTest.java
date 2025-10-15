package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PromoterTest {

    @Test
    void testPromoterCreation() {
        // Arrange
        String city = "Barcelona";
        String promoterName = "Tech Solutions Inc";
        Country country = new Country();
        country.setId(1L);
        
        // Act
        Promoter promoter = new Promoter();
        promoter.setCity(city);
        promoter.setProjectPromoter(promoterName);
        promoter.setCountry(country);
        
        // Assert
        assertEquals(city, promoter.getCity());
        assertEquals(promoterName, promoter.getProjectPromoter());
        assertEquals(country, promoter.getCountry());
    }

    @Test
    void testPromoterWithAuditFields() {
        // Arrange
        Promoter promoter = new Promoter();
        promoter.setProjectPromoter("Innovate Corp");
        LocalDateTime now = LocalDateTime.now();
        
        // Act
        promoter.setCreatedAt(now);
        promoter.setUpdatedAt(now);
        
        // Assert
        assertEquals(now, promoter.getCreatedAt());
        assertEquals(now, promoter.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Promoter promoter1 = new Promoter();
        promoter1.setProjectPromoter("Global Ventures");
        promoter1.setId(1L); 
        
        Promoter promoter2 = new Promoter();
        promoter2.setProjectPromoter("Global Ventures");
        promoter2.setId(1L);
        
        // Assert
        assertEquals(promoter1, promoter2);
        assertEquals(promoter1.hashCode(), promoter2.hashCode());
    }

    @Test
    void testPromoterWithAllFields() {
        // Arrange
        Country country = new Country();
        country.setId(1L);
        
        // Act
        Promoter promoter = new Promoter();
        promoter.setId(1L);
        promoter.setCity("Madrid");
        promoter.setCountry(country);
        promoter.setProjectPromoter("Madrid Tech Hub");
        promoter.setPromoterName1("John");
        promoter.setPromoterName2("Smith");
        promoter.setPromoterNo(123);
        promoter.setStreet("Innovation Street");
        promoter.setZipCode("28001");
        
        // Assert
        assertAll("Promoter properties",
            () -> assertEquals(1L, promoter.getId()),
            () -> assertEquals("Madrid", promoter.getCity()),
            () -> assertEquals(country, promoter.getCountry()),
            () -> assertEquals("Madrid Tech Hub", promoter.getProjectPromoter()),
            () -> assertEquals("John", promoter.getPromoterName1()),
            () -> assertEquals("Smith", promoter.getPromoterName2()),
            () -> assertEquals(123, promoter.getPromoterNo()),
            () -> assertEquals("Innovation Street", promoter.getStreet()),
            () -> assertEquals("28001", promoter.getZipCode())
        );
    }
}