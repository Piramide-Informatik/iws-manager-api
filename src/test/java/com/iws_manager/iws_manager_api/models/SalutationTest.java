package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalutationTest {

    String salutationName = "Frau";
    private Salutation salutation = new Salutation();

    @Test
    void testSalutationCreation() {
        // Act
        salutation.setName(salutationName);

        // Assert
        assertEquals(salutationName, salutation.getName());
    }

    @Test
    void testSalutationWithAuditFields() {

        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        salutation.setName(salutationName);
        salutation.setCreatedAt(now);
        salutation.setUpdatedAt(now);

        // Assert
        assertEquals(now, salutation.getCreatedAt());
        assertEquals(now, salutation.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode(){
       // Arrange
       salutation.setName(salutationName);
       salutation.setId(1L);

       Salutation salutation2 = new Salutation();
       salutation2.setName(salutationName);
       salutation2.setId(1L);

       // Assert
       assertEquals(salutation, salutation2);
       assertEquals(salutation.hashCode(), salutation2.hashCode());
   }
}
