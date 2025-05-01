package com.iws_manager.iws_manager_api.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.iws_manager.iws_manager_api.models.Title;

public class TitleTest {

    private Title title;

    @Test
    void testTitleModel() {
        // Arrange
        String uuid = UUID.randomUUID().toString();
        String titleName = "Dr.";

        // Act
        title = new Title();
        title.setUuid(uuid);
        title.setHonorific(titleName);

        // Assert
        assertThat(title.getUuid()).isEqualTo(uuid);
        assertThat(title.getHonorific()).isEqualTo(titleName);
    }

    @Test
    void testTitleConstructor() {
        // Arrange
        String uuid = UUID.randomUUID().toString();
        String titleName = "Dr.";

        // Act
        title = new Title(null, uuid, titleName);

        // Assert
        assertThat(title.getUuid()).isEqualTo(uuid);
        assertThat(title.getHonorific()).isEqualTo(titleName);
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        String uuid = UUID.randomUUID().toString();
        Title title1 = new Title(1, uuid, "Dr.");
        Title title2 = new Title(1, uuid, "Dr.");

        // Assert
        assertThat(title1).isEqualTo(title2);
        assertThat(title1.hashCode()).isEqualTo(title2.hashCode());
    }

}
