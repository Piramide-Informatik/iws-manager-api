package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StateTest {

    String name = "Hamburg";
    State state = new State();

    @Test 
    void testStateCreation(){
        //Act
        state.setName(name);

        //Assert
        assertEquals(name, state.getName());
    }

    @Test
    void testStateWithAuditFields(){
        // Arrange
        state.setName(name);
        LocalDateTime now = LocalDateTime.now();

        // Act
        state.setCreatedAt(now);
        state.setUpdatedAt(now);

        // Assert
        assertEquals(now, state.getCreatedAt());
        assertEquals(now, state.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode(){
        // Arrange
        state.setName(name);
        state.setId(1L);

        State state2 = new State();
        state2.setName(name);
        state2.setId(1L);

        // Assert
        assertEquals(state, state2);
        assertEquals(state.hashCode(), state2.hashCode());
    }
}
