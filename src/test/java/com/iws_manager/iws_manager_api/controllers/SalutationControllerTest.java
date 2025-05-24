package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.Salutation;
import com.iws_manager.iws_manager_api.services.interfaces.SalutationService;
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
class SalutationControllerTest {

    private MockMvc mockMvc;
    private String uri = "/api/v1/salutations";
    private String name = "$.name";

    @Mock
    private SalutationService salutationService;

    @InjectMocks
    private SalutationController salutationController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Salutation salutation1;
    private Salutation salutation2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(salutationController).build();
        
        salutation1 = new Salutation();
        salutation1.setId(1L);
        salutation1.setName("Mr.");

        salutation2 = new Salutation();
        salutation2.setId(2L);
        salutation2.setName("Mrs.");
    }

    @Test
    void createSalutationShouldReturnCreatedStatus() throws Exception {
        given(salutationService.create(any(Salutation.class))).willReturn(salutation1);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(salutation1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value("Mr."));
    }

    @Test
    void getSalutationByIdShouldReturnSalutation() throws Exception {
        given(salutationService.findById(1L)).willReturn(Optional.of(salutation1));

        mockMvc.perform(get(uri + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value("Mr."));
    }

    @Test
    void getSalutationByIdShouldReturnNotFound() throws Exception {
        given(salutationService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri + "/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllSalutationsShouldReturnAllSalutations() throws Exception {
        List<Salutation> salutations = Arrays.asList(salutation1, salutation2);
        given(salutationService.findAll()).willReturn(salutations);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Mr."))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Mrs."));
    }

    @Test
    void updateSalutationShouldReturnUpdatedSalutation() throws Exception {
        Salutation updatedSalutation = new Salutation();
        updatedSalutation.setName("Mr. Updated");

        given(salutationService.update(1L, updatedSalutation)).willReturn(updatedSalutation);

        mockMvc.perform(put(uri + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedSalutation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(name).value("Mr. Updated"));
    }

    @Test
    void updateSalutationShouldReturnNotFound() throws Exception {
        given(salutationService.update(anyLong(), any(Salutation.class)))
            .willThrow(new RuntimeException("Salutation not found"));

        mockMvc.perform(put(uri + "/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(salutation1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSalutationShouldReturnNoContent() throws Exception {
        doNothing().when(salutationService).delete(1L);

        mockMvc.perform(delete("/api/v1/salutations/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createSalutationShouldValidateInput() throws Exception {
        Salutation invalidSalutation = new Salutation(); 

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidSalutation)))
                .andExpect(status().isBadRequest());
    }

    @Test
void createSalutationShouldReturnCreatedSalutation() throws Exception {
        Salutation validSalutation = new Salutation();
        validSalutation.setName("Mr.");
        
        when(salutationService.create(any(Salutation.class))).thenReturn(validSalutation);
        
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validSalutation)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(name).value("Mr."));
    }
}