package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.IwsCommission;
import com.iws_manager.iws_manager_api.models.State;
import com.iws_manager.iws_manager_api.services.interfaces.IwsCommissionService;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class IwsCommissionControllerTest {

    private MockMvc mockMvc;
    private String uri = "/api/v1/iws-commissions";
    private String commission1 = "$.commission";
    private BigDecimal commission = BigDecimal.valueOf(2.5);

    @Mock
    private IwsCommissionService iwsCommissionService;

    @InjectMocks
    private IwsCommissionController iwsCommissionController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private IwsCommission iwsCommission1;
    private IwsCommission iwsCommission2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(iwsCommissionController).build();

        iwsCommission1 = new IwsCommission();
        iwsCommission1.setId(1L);
        iwsCommission1.setCommission(commission);

        iwsCommission2 = new IwsCommission();
        iwsCommission2.setId(2L);
        iwsCommission2.setCommission(BigDecimal.valueOf(3.5));
    }

    @Test
    void createIwsCommissionShouldReturnCreatedStatus() throws Exception {
        given(iwsCommissionService.create(any(IwsCommission.class))).willReturn(iwsCommission1);

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(iwsCommission1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(commission1).value(iwsCommission1.getCommission().doubleValue()));
    }

    @Test
    void getStateByIdShouldReturnIwsCommission() throws Exception {
        given(iwsCommissionService.findById(1L)).willReturn(Optional.of(iwsCommission1));

        mockMvc.perform(get(uri + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(commission1).value(iwsCommission1.getCommission().doubleValue()));
    }

    @Test
    void getIwsCommissionByIdShouldReturnNotFound() throws Exception {
        given(iwsCommissionService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri + "/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllIwsCommissionShouldReturnAllStates() throws Exception {
        List<IwsCommission> iwsCommissions = Arrays.asList(iwsCommission1, iwsCommission2);
        given(iwsCommissionService.findAll()).willReturn(iwsCommissions);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].commission").value(iwsCommission1.getCommission().doubleValue()))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].commission").value(iwsCommission2.getCommission().doubleValue()));
    }

    @Test
    void updateIwsCommissionShouldReturnUpdatedIwsCommission() throws Exception {
        IwsCommission updatedIwsCommission = new IwsCommission();
        updatedIwsCommission.setCommission(BigDecimal.valueOf(4.5));

        given(iwsCommissionService.update(1L, updatedIwsCommission)).willReturn(updatedIwsCommission);

        mockMvc.perform(put(uri + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedIwsCommission)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(commission1).value(BigDecimal.valueOf(4.5)));
    }

    @Test
    void updateIwsCommissionShouldReturnNotFound() throws Exception {
        given(iwsCommissionService.update(anyLong(), any(IwsCommission.class)))
                .willThrow(new RuntimeException("IwsCommission not found"));

        mockMvc.perform(put(uri + "/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(iwsCommission1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteIwsCommissionShouldReturnNoContent() throws Exception {
        doNothing().when(iwsCommissionService).delete(1L);

        mockMvc.perform(delete(uri+"/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createIwsCommissionShouldValidateInput() throws Exception {
        IwsCommission invalidIwsCommission = new IwsCommission();

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidIwsCommission)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createIwsCommissionShouldReturnCreatedState() throws Exception {
        IwsCommission validIwsCommission = new IwsCommission();
        validIwsCommission.setCommission(commission);

        when(iwsCommissionService.create(any(IwsCommission.class))).thenReturn(validIwsCommission);

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validIwsCommission)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(commission1).value(commission));
    }
}