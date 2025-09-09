package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.Biller;
import com.iws_manager.iws_manager_api.services.interfaces.BillerService;
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
class BillerControllerTest {

    private MockMvc mockMvc;
    private String uri = "/api/v1/billers";
    private String name = "$.name";
    private String ctname = "Biller";

    @Mock
    private BillerService billerService;

    @InjectMocks
    private BillerController billerController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Biller biller1;
    private Biller biller2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(billerController).build();
        
        biller1 = new Biller();
        biller1.setId(1L);
        biller1.setName(ctname);

        biller2 = new Biller();
        biller2.setId(2L);
        biller2.setName("Biller 1");
    }

    @Test
    void createBillerShouldReturnCreatedStatus() throws Exception {
        given(billerService.create(any(Biller.class))).willReturn(biller1);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(biller1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(ctname));
    }

    @Test
    void getBillerByIdShouldReturnBiller() throws Exception {
        given(billerService.findById(1L)).willReturn(Optional.of(biller1));

        mockMvc.perform(get(uri + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(ctname));
    }

    @Test
    void getBillerByIdShouldReturnNotFound() throws Exception {
        given(billerService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri + "/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllBilleresShouldReturnAllBilleres() throws Exception {
        List<Biller> billeres = Arrays.asList(biller1, biller2);
        given(billerService.findAll()).willReturn(billeres);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(ctname))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Biller 1"));
    }

    @Test
    void updateBillerShouldReturnUpdatedBiller() throws Exception {
        Biller updatedBiller = new Biller();
        updatedBiller.setName("Biller Updated");

        given(billerService.update(1L, updatedBiller)).willReturn(updatedBiller);

        mockMvc.perform(put(uri + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBiller)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(name).value("Biller Updated"));
    }

    @Test
    void updateBillerShouldReturnNotFound() throws Exception {
        given(billerService.update(anyLong(), any(Biller.class)))
            .willThrow(new RuntimeException("Biller not found"));

        mockMvc.perform(put(uri + "/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(biller1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBillerShouldReturnNoContent() throws Exception {
        doNothing().when(billerService).delete(1L);

        mockMvc.perform(delete("/api/v1/billers/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createBillerShouldValidateInput() throws Exception {
        Biller invalidBiller = new Biller(); 

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidBiller)))
                .andExpect(status().isBadRequest());
    }

    @Test
void createBillerShouldReturnCreatedBiller() throws Exception {
        Biller validBiller = new Biller();
        validBiller.setName(ctname);
        
        when(billerService.create(any(Biller.class))).thenReturn(validBiller);
        
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validBiller)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(name).value(ctname));
    }
}