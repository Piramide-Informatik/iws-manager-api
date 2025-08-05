package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RolesTest {

    String roleName = "admin";
    private Role role = new Role();

    @Test
    void testRoleCreation() {
        role.setName(roleName);

        assertEquals(roleName, role.getName());
    }

    @Test
    void testRoleWithAuditFields(){
        role.setName(roleName);
        LocalDateTime now = LocalDateTime.now();

        role.setCreatedAt(now);
        role.setUpdatedAt(now);

        assertEquals(now, role.getCreatedAt());
        assertEquals(now, role.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        role.setName(roleName);
        role.setId(1L);

        Role role2 = new Role();
        role2.setName(roleName);
        role2.setId(1L);

        assertEquals(role, role2);
        assertEquals(role.hashCode(), role2.hashCode());
    }
}
