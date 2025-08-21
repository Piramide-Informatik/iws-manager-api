package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoleRightTest {
    Integer accessRight = 1;
    private RoleRight roleRight = new RoleRight();

    @Test
    void testRoleCreation() {
        roleRight.setAccessRight(accessRight);

        assertEquals(accessRight, roleRight.getAccessRight());
    }

    @Test
    void testRoleWithAuditFields(){
        roleRight.setAccessRight(accessRight);
        LocalDateTime now = LocalDateTime.now();

        roleRight.setCreatedAt(now);
        roleRight.setUpdatedAt(now);

        assertEquals(now, roleRight.getCreatedAt());
        assertEquals(now, roleRight.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        roleRight.setAccessRight(accessRight);
        roleRight.setId(1L);

        RoleRight roleRight2 = new RoleRight();
        roleRight2.setAccessRight(accessRight);
        roleRight2.setId(1L);

        assertEquals(roleRight, roleRight2);
        assertEquals(roleRight.hashCode(), roleRight2.hashCode());
    }
}
