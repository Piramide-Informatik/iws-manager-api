package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SystemModuleTest {
    String name = "NameModule";
    SystemModule module = new SystemModule();

    @Test
    void testSystemModuleCreation() {
        module.setName(name);

        assertEquals(name, module.getName());
    }
    @Test
    void testModuleWithAuditFields(){
        module.setName(name);
        LocalDateTime now = LocalDateTime.now();

        module.setCreatedAt(now);
        module.setUpdatedAt(now);

        assertEquals(now, module.getCreatedAt());
        assertEquals(now, module.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        module.setName(name);
        module.setId(1L);

        SystemModule module2 = new SystemModule();
        module2.setName(name);
        module2.setId(1L);

        assertEquals(module, module2);
        assertEquals(module.hashCode(), module2.hashCode());
    }
}
