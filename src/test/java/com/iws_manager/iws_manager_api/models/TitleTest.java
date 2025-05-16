package com.iws_manager.iws_manager_api.models;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
public class TitleTest {

    private Title title;

    @Test
    void testTitleModel() {
        // Arrange
        long id = 1L;
        String titleName = "Dr.";

        // Act
        title = new Title();
        title.setId(id);
        title.setTitle(titleName);

        // Assert
        assertThat(title.getId()).isEqualTo(id);
        assertThat(title.getTitle()).isEqualTo(titleName);
    }

    @Test
    void testTitleConstructor() {
        // Arrange
        long id = 2L;
        String titleName = "Dr.";

        // Act
        title = new Title(id, titleName);

        // Assert
        assertThat(title.getId()).isEqualTo(id);
        assertThat(title.getTitle()).isEqualTo(titleName);
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Title title1 = new Title(1L, "Dr.");
        Title title2 = new Title(1L, "Dr.");

        // Assert
        assertThat(title1).isEqualTo(title2);
        assertThat(title1.hashCode()).isEqualTo(title2.hashCode());
    }
}
