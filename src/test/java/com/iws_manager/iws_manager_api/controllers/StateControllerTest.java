package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.State;
import com.iws_manager.iws_manager_api.services.interfaces.StateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class StateControllerTest {

    private MockMvc mockMvc;
    private String uri = "/api/v1/states";
    private String name = "$.name";
    private String stateName = "California";

    @Mock
    private StateService stateService;

    @InjectMocks
    private StateController stateController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private State state1;
    private State state2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(stateController).build();
        
        state1 = new State();
        state1.setId(1L);
        state1.setName(stateName);

        state2 = new State();
        state2.setId(2L);
        state2.setName("Florida");
    }

    @Test
    void createStateShouldReturnCreatedStatus() throws Exception {
        given(stateService.create(any(State.class))).willReturn(state1);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(state1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(stateName));
    }

    @Test
    void getStateByIdShouldReturnState() throws Exception {
        given(stateService.findById(1L)).willReturn(Optional.of(state1));

        mockMvc.perform(get(uri + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(stateName));
    }

    @Test
    void getStateByIdShouldReturnNotFound() throws Exception {
        given(stateService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri + "/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllStatesShouldReturnAllStates() throws Exception {
        List<State> states = Arrays.asList(state1, state2);
        given(stateService.findAll()).willReturn(states);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(stateName))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Florida"));
    }

    @Test
    void updateStateShouldReturnUpdatedState() throws Exception {
        State updatedState = new State();
        updatedState.setName("California Updated");

        given(stateService.update(1L, updatedState)).willReturn(updatedState);

        mockMvc.perform(put(uri + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedState)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(name).value("California Updated"));
    }

    @Test
    void updateStateShouldReturnNotFound() throws Exception {
        given(stateService.update(anyLong(), any(State.class)))
            .willThrow(new RuntimeException("State not found"));

        mockMvc.perform(put(uri + "/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(state1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStateShouldReturnNoContent() throws Exception {
        doNothing().when(stateService).delete(1L);

        mockMvc.perform(delete("/api/v1/states/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createStateShouldValidateInput() throws Exception {
        State invalidState = new State(); 

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidState)))
                .andExpect(status().isBadRequest());
    }

    @Test
void createStateShouldReturnCreatedState() throws Exception {
        State validState = new State();
        validState.setName(stateName);
        
        when(stateService.create(any(State.class))).thenReturn(validState);
        
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validState)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(name).value(stateName));
    }
}