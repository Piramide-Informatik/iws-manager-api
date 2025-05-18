package com.iws_manager.iws_manager_api.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
public class TitleTest {

    String titleName = "Dr.";
    private Title title = new Title();

    @Test
    void testTitleCreation() {        
        // Act
        title.setName(titleName);

        // Assert
        assertEquals(titleName, title.getName());
    }

    @Test
    void testTitleWithAuditFields() {
        // Arrange
        title.setName(titleName);
        LocalDateTime now = LocalDateTime.now();

        // Act
        title.setCreatedAt(now);
        title.setUpdatedAt(now);

        // Assert
        assertEquals(now, title.getCreatedAt());
        assertEquals(now, title.getUpdatedAt());
       }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        title.setName(titleName);
        title.setId(1L);

        Title title2 = new Title();
        title2.setName(titleName);
        title2.setId(1L);

        // Assert
        assertEquals(title, title2);
        assertEquals(title.hashCode(), title2.hashCode());
    }
}
