package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalutationTest {

    private Salutation salutation;

    @Test
    void testSalutationModel() {
        String uuid = UUID.randomUUID().toString();
        String salutationName = "Frau";

        salutation = new Salutation();
        salutation.setUuid(uuid);
        salutation.setSalutation(salutationName);

        assertThat(salutation.getUuid()).isEqualTo(uuid);
        assertThat(salutation.getSalutation()).isEqualTo(salutationName);
    }

    @Test
    void testSalutationConstructor() {
        String uuid = UUID.randomUUID().toString();
        String salutationName = "Frau";
        LocalDateTime now = LocalDateTime.now();

        salutation = new Salutation(1, uuid, salutationName, now, now);

        assertThat(salutation.getId()).isEqualTo(1);
        assertThat(salutation.getUuid()).isEqualTo(uuid);
        assertThat(salutation.getSalutation()).isEqualTo(salutationName);
    }

    @Test
    void testSalutationWithAuditFields(){
        String uuid = UUID.randomUUID().toString();
        String salutationName = "Frau";

        salutation = new Salutation();
        salutation.setUuid(uuid);
        salutation.setSalutation(salutationName);
        LocalDateTime now = LocalDateTime.now();

        salutation.setCreatedAt(now);
        salutation.setUpdatedAt(now);

        assertEquals(now, salutation.getCreatedAt());
        assertEquals(now, salutation.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        String uuid = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        Salutation salutation1 = new Salutation(1, uuid, "Herr", now,now);
        Salutation salutation2 = new Salutation(1, uuid, "Herr", now, now);

        assertThat(salutation1).isEqualTo(salutation2);
        assertThat(salutation2.hashCode()).isEqualTo(salutation2.hashCode());
    }
}
