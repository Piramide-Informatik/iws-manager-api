package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.Subcontract;
import com.iws_manager.iws_manager_api.services.interfaces.SubcontractService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class SubcontractControllerTest {

    private static final String CONTRACT_TITLE = "Test Contract";
    private static final String URI = "/api/v1/subcontracts";
    private static final String JSON_TITLE_PATH = "$.contractTitle";

    private MockMvc mockMvc;

    @Mock
    private SubcontractService subcontractService;

    @InjectMocks
    private SubcontractController subcontractController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Subcontract subcontract1;
    private Subcontract subcontract2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(subcontractController).build();

        subcontract1 = new Subcontract();
        subcontract1.setId(1L);
        subcontract1.setContractTitle(CONTRACT_TITLE);
        subcontract1.setInvoiceAmount(new BigDecimal("1000.00"));

        subcontract2 = new Subcontract();
        subcontract2.setId(2L);
        subcontract2.setContractTitle("Second Contract");
        subcontract2.setInvoiceAmount(new BigDecimal("2000.00"));
    }

    @Test
    void createSubcontractShouldReturnCreatedStatus() throws Exception {
        given(subcontractService.create(any(Subcontract.class))).willReturn(subcontract1);

        mockMvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subcontract1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(JSON_TITLE_PATH).value(CONTRACT_TITLE));
    }

    @Test
    void getSubcontractByIdShouldReturnSubcontract() throws Exception {
        given(subcontractService.findById(1L)).willReturn(Optional.of(subcontract1));

        mockMvc.perform(get(URI + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(JSON_TITLE_PATH).value(CONTRACT_TITLE));
    }

    @Test
    void getSubcontractByIdShouldReturnNotFound() throws Exception {
        given(subcontractService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(URI + "/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllSubcontractsShouldReturnAll() throws Exception {
        given(subcontractService.findAll()).willReturn(Arrays.asList(subcontract1, subcontract2));

        mockMvc.perform(get(URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].contractTitle").value(CONTRACT_TITLE))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].contractTitle").value("Second Contract"));
    }

    @Test
    void updateSubcontractShouldReturnUpdatedSubcontract() throws Exception {
        Subcontract updated = new Subcontract();
        updated.setContractTitle("Updated Contract");

        given(subcontractService.update(1L, updated)).willReturn(updated);

        mockMvc.perform(put(URI + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_TITLE_PATH).value("Updated Contract"));
    }

   @Test
    void updateSubcontractShouldReturnNotFound() throws Exception {
        given(subcontractService.update(anyLong(), any(Subcontract.class)))
            .willThrow(new RuntimeException("Not found"));

        mockMvc.perform(put(URI + "/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(subcontract1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSubcontractShouldReturnNoContent() throws Exception {
        doNothing().when(subcontractService).delete(1L);

        mockMvc.perform(delete(URI + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getByContractorIdShouldReturnList() throws Exception {
        given(subcontractService.findByContractorId(10L)).willReturn(Collections.singletonList(subcontract1));

        mockMvc.perform(get(URI + "/contractor/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void getByCustomerIdShouldReturnList() throws Exception {
        given(subcontractService.findByCustomerId(20L)).willReturn(Collections.singletonList(subcontract1));

        mockMvc.perform(get(URI + "/customer/20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].contractTitle").value(CONTRACT_TITLE));
    }

    @Test
    void getByProjectCostCenterIdShouldReturnList() throws Exception {
        given(subcontractService.findByProjectCostCenterId(30L)).willReturn(Collections.singletonList(subcontract1));

        mockMvc.perform(get(URI + "/projectcostcenter/30"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}
