package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.Network;
import com.iws_manager.iws_manager_api.services.interfaces.NetworkService;
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
class NetworkControllerTest {

    private MockMvc mockMvc;
    private String uri = "/api/v1/networks";
    private String name = "$.name";
    private String ctname = "net 1";

    @Mock
    private NetworkService networkService;

    @InjectMocks
    private NetworkController networkController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Network Network1;
    private Network Network2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(networkController).build();
        
        Network1 = new Network();
        Network1.setId(1L);
        Network1.setName(ctname);

        Network2 = new Network();
        Network2.setId(2L);
        Network2.setName("Private");
    }

    @Test
    void createNetworkShouldReturnCreatedStatus() throws Exception {
        given(networkService.create(any(Network.class))).willReturn(Network1);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Network1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(ctname));
    }

    @Test
    void getNetworkByIdShouldReturnNetwork() throws Exception {
        given(networkService.findById(1L)).willReturn(Optional.of(Network1));

        mockMvc.perform(get(uri + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(ctname));
    }

    @Test
    void getNetworkByIdShouldReturnNotFound() throws Exception {
        given(networkService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri + "/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllNetworkesShouldReturnAllNetworkes() throws Exception {
        List<Network> Networkes = Arrays.asList(Network1, Network2);
        given(networkService.findAll()).willReturn(Networkes);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(ctname))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Private"));
    }

    @Test
    void updateNetworkShouldReturnUpdatedNetwork() throws Exception {
        Network updatedNetwork = new Network();
        updatedNetwork.setName("Public Updated");

        given(networkService.update(1L, updatedNetwork)).willReturn(updatedNetwork);

        mockMvc.perform(put(uri + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedNetwork)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(name).value("Public Updated"));
    }

    @Test
    void updateNetworkShouldReturnNotFound() throws Exception {
        given(networkService.update(anyLong(), any(Network.class)))
            .willThrow(new RuntimeException("Network not found"));

        mockMvc.perform(put(uri + "/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Network1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteNetworkShouldReturnNoContent() throws Exception {
        doNothing().when(networkService).delete(1L);

        mockMvc.perform(delete("/api/v1/networks/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createNetworkShouldValidateInput() throws Exception {
        Network invalidNetwork = new Network(); 

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidNetwork)))
                .andExpect(status().isBadRequest());
    }

    @Test
void createNetworkShouldReturnCreatedNetwork() throws Exception {
        Network validNetwork = new Network();
        validNetwork.setName(ctname);
        
        when(networkService.create(any(Network.class))).thenReturn(validNetwork);
        
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validNetwork)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(name).value(ctname));
    }
}