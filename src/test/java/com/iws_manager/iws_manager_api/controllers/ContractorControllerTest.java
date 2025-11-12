package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.Contractor;
import com.iws_manager.iws_manager_api.services.interfaces.ContractorService;
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
import jakarta.persistence.EntityNotFoundException;
import com.iws_manager.iws_manager_api.exception.GlobalExceptionHandler;

@ExtendWith(MockitoExtension.class)
public class ContractorControllerTest {
    private static final String SRL = "srl"; // ← constante añadida
    private static final String UPDATED_SRL = "srl updated";
    private static final String LRS = "lrs";
    private MockMvc mockMvc;
    private String uri = "/api/v1/contractors";
    private String name = "$.name";

    @Mock
    private ContractorService contractorService;

    @InjectMocks
    private ContractorController contractorController;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Contractor contractor1;
    private  Contractor contractor2;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(contractorController).setControllerAdvice(new GlobalExceptionHandler()).build();

        contractor1 = new Contractor();
        contractor1.setId(1L);
        contractor1.setName(SRL);

        contractor2 = new Contractor();
        contractor2.setId(2L);
        contractor2.setName(LRS);
    }

    @Test
    void createContractorShouldReturnCreatedStatus() throws Exception {
        given(contractorService.create(any(Contractor.class))).willReturn(contractor1);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contractor1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(SRL));
    }

    @Test
    void getContractorByIdShouldReturnContractor() throws Exception {
        given(contractorService.findById(1L)).willReturn(Optional.of(contractor1));

        mockMvc.perform(get(uri+"/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(SRL));
    }

    @Test
    void getContractorByIdShouldReturnNotFound() throws Exception {
        given(contractorService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri+"/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllContractorShouldReturnAllContractors() throws Exception {
        List<Contractor> contractors = Arrays.asList(contractor1, contractor2);
        given(contractorService.findAll()).willReturn(contractors);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(SRL))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value(LRS));
    }

    @Test
    void updateContractorShouldReturnUpdatedContrator() throws Exception {
        Contractor updatedContractor = new Contractor();
        updatedContractor.setName(UPDATED_SRL);

        given(contractorService.update(1L, updatedContractor)).willReturn(updatedContractor);

        mockMvc.perform(put(uri+"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedContractor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(name).value(UPDATED_SRL));
    }

    @Test
    void updateContractorShouldReturnNotFound() throws Exception {
        given(contractorService.update(anyLong(), any(Contractor.class)))
                .willThrow(new EntityNotFoundException("Contractor not found with id: 99"));

        mockMvc.perform(put(uri+"/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contractor1)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Contractor not found with id: 99"));
    }

    @Test
    void deleteContractorShouldReturnNoContent() throws Exception {
        doNothing().when(contractorService).delete(1L);

        mockMvc.perform(delete(uri+"/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createContractorShouldReturnCreatedContractor() throws Exception {
        Contractor validContractor = new Contractor();
        validContractor.setName(SRL);

        when(contractorService.create(any(Contractor.class))).thenReturn(validContractor);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validContractor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(name).value(SRL));
    }
}
