package com.iws_manager.iws_manager_api.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SystemFunctionTest {
    String functionName = "function 1";
    private SystemFunction function = new SystemFunction();

    @Test
    void testRoleCreation() {
        function.setFunctionName(functionName);

        assertEquals(functionName, function.getFunctionName());
    }

    @Test
    void testStateWithAuditFields(){
        function.setFunctionName(functionName);
        LocalDateTime now = LocalDateTime.now();

        function.setCreatedAt(now);
        function.setUpdatedAt(now);

        assertEquals(now, function.getCreatedAt());
        assertEquals(now, function.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode(){
        function.setFunctionName(functionName);
        function.setId(1L);

        SystemFunction function2 = new SystemFunction();
        function2.setFunctionName(functionName);
        function2.setId(1L);

        assertEquals(function, function2);
        assertEquals(function.hashCode(), function2.hashCode());
    }
}
