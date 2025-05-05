package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

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

        salutation = new Salutation(1, uuid, salutationName);

        assertThat(salutation.getId()).isEqualTo(1);
        assertThat(salutation.getUuid()).isEqualTo(uuid);
        assertThat(salutation.getSalutation()).isEqualTo(salutationName);
    }

    @Test
    void testEqualsAndHashCode() {
        String uuid = UUID.randomUUID().toString();
        Salutation salutation1 = new Salutation(1, uuid, "Herr");
        Salutation salutation2 = new Salutation(1, uuid, "Herr");

        assertThat(salutation1).isEqualTo(salutation2);
        assertThat(salutation2.hashCode()).isEqualTo(salutation2.hashCode());
    }
}
