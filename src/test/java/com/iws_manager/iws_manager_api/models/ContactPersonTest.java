package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ContactPersonTest {

    String firstName = "Joe";
    String lastName = "Doe";
    Integer forInvoicing = 12345;
    String function = "Chief Financial Officer";

    private ContactPerson buildContactPerson(LocalDateTime createdAt, LocalDateTime updatedAt) {
        ContactPerson cp = new ContactPerson();
        cp.setFirstName(firstName);
        cp.setLastName(lastName);
        cp.setForInvoicing(forInvoicing);
        cp.setFunction(function);
        cp.setCreatedAt(createdAt);
        cp.setUpdatedAt(updatedAt);
        return cp;
    }

    private ContactPerson buildContactPerson() {
        return buildContactPerson(null, null);
    }

    @Test
    void testContactPersonCreation() {
        // Arrange
        ContactPerson contactPerson = buildContactPerson();

        // Assert
        assertEquals(firstName, contactPerson.getFirstName());
        assertEquals(lastName, contactPerson.getLastName());
        assertEquals(forInvoicing, contactPerson.getForInvoicing());
        assertEquals(function, contactPerson.getFunction());
    }

    @Test
    void testContactPersonWithAuditFields() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();

        // Act
        ContactPerson contactPerson = buildContactPerson(now, now);

        // Assert
        assertEquals(now, contactPerson.getCreatedAt());
        assertEquals(now, contactPerson.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
    LocalDateTime now = LocalDateTime.now();
    
    ContactPerson contactPerson1 = buildContactPerson(now, now);
    contactPerson1.setId(1L);
    
    ContactPerson contactPerson2 = buildContactPerson(now, now);
    contactPerson2.setId(1L);

    // Assert
    assertEquals(contactPerson1, contactPerson2);
    assertEquals(contactPerson1.hashCode(), contactPerson2.hashCode());
    }
}
