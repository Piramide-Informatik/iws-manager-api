package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TextTest {

    private Text text = new Text();
    private String label = "Welcome Message";
    private String content = "Welcome to our application! This is a sample text template.";
    private LocalDateTime now = LocalDateTime.now();

    @Test
    void testTextCreation() {
        // Act
        text.setLabel(label);
        text.setContent(content);

        // Assert
        assertEquals(label, text.getLabel());
        assertEquals(content, text.getContent());
    }

    @Test
    void testTextWithAuditFields() {
        // Arrange
        text.setLabel(label);
        text.setContent(content);

        // Act
        text.setCreatedAt(now);
        text.setUpdatedAt(now);

        // Assert
        assertEquals(now, text.getCreatedAt());
        assertEquals(now, text.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        text.setLabel(label);
        text.setContent(content);
        text.setId(1L);

        Text text2 = new Text();
        text2.setLabel(label);
        text2.setContent(content);
        text2.setId(1L);

        Text text3 = new Text();
        text3.setLabel("Farewell Message");
        text3.setContent("Goodbye!");
        text3.setId(2L);

        // Assert - Same ID and content should be equal
        assertEquals(text, text2);
        assertEquals(text.hashCode(), text2.hashCode());

        // Assert - Different ID should not be equal
        assertNotEquals(text, text3);
        assertNotEquals(text.hashCode(), text3.hashCode());
    }

    @Test
    void testLongContent() {
        // Arrange
        String longContent = "This is a very long text content that should be properly stored " +
                            "in the TEXT column. The TEXT column type can handle large amounts " +
                            "of text data without issues. This is important for storing " +
                            "templates, messages, and other lengthy content.";

        // Act
        text.setContent(longContent);

        // Assert
        assertEquals(longContent, text.getContent());
    }
}