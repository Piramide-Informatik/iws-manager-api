package com.iws_manager.iws_manager_api.controllers;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.CostType;
import com.iws_manager.iws_manager_api.services.interfaces.CostTypeService;
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

@ExtendWith(MockitoExtension.class)
class CostTypeControllerTest {

    private MockMvc mockMvc;
    private String uri = "/api/v1/costtypes";

    @Mock
    private CostTypeService costTypeService;

    @InjectMocks
    private CostTypeController costTypeController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private CostType costType1;
    private CostType costType2;

    private static final String TYPE1 = "Material";
    private static final String TYPE_TEST = "$.type";
    private static final String SEQNO_TEST = "$.sequenceNo";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(costTypeController).build();
        
        costType1 = new CostType();
        costType1.setId(1L);
        costType1.setType(TYPE1);
        costType1.setSequenceNo(1);

        costType2 = new CostType();
        costType2.setId(2L);
        costType2.setType("Labor");
        costType2.setSequenceNo(2);
    }

    @Test
    void createCostTypeShouldReturnCreatedStatus() throws Exception {
        given(costTypeService.create(any(CostType.class))).willReturn(costType1);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(costType1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(TYPE_TEST).value(TYPE1))
                .andExpect(jsonPath(SEQNO_TEST).value(1));
    }

    @Test
    void createCostTypeShouldValidateInputWhenTypeIsNull() throws Exception {
        CostType invalidCostType = new CostType();
        invalidCostType.setSequenceNo(1);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidCostType)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Type is required"));
    }

    @Test
    void createCostTypeShouldValidateInputWhenTypeIsEmpty() throws Exception {
        CostType invalidCostType = new CostType();
        invalidCostType.setType("   ");
        invalidCostType.setSequenceNo(1);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidCostType)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Type is required"));
    }

    @Test
    void getCostTypeByIdShouldReturnCostType() throws Exception {
        given(costTypeService.findById(1L)).willReturn(Optional.of(costType1));

        mockMvc.perform(get(uri + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(TYPE_TEST).value(TYPE1))
                .andExpect(jsonPath(SEQNO_TEST).value(1));
    }

    @Test
    void getCostTypeByIdShouldReturnNotFound() throws Exception {
        given(costTypeService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri + "/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllCostTypesShouldReturnAllCostTypes() throws Exception {
        List<CostType> costTypes = Arrays.asList(costType1, costType2);
        given(costTypeService.findAll()).willReturn(costTypes);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].type").value(TYPE1))
                .andExpect(jsonPath("$[0].sequenceNo").value(1))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].type").value("Labor"))
                .andExpect(jsonPath("$[1].sequenceNo").value(2));
    }

    @Test
    void getAllCostTypesShouldReturnEmptyList() throws Exception {
        given(costTypeService.findAll()).willReturn(Arrays.asList());

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void updateCostTypeShouldReturnUpdatedCostType() throws Exception {
        CostType updatedCostType = new CostType();
        updatedCostType.setType("Material Updated");
        updatedCostType.setSequenceNo(10);

        given(costTypeService.update(1L, updatedCostType)).willReturn(updatedCostType);

        mockMvc.perform(put(uri + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCostType)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(TYPE_TEST).value("Material Updated"))
                .andExpect(jsonPath(SEQNO_TEST).value(10));
    }

    @Test
    void updateCostTypeShouldReturnNotFound() throws Exception {
        given(costTypeService.update(anyLong(), any(CostType.class)))
            .willThrow(new RuntimeException("CostType not found"));

        mockMvc.perform(put(uri + "/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(costType1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCostTypeShouldReturnNoContent() throws Exception {
        doNothing().when(costTypeService).delete(1L);

        mockMvc.perform(delete(uri + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createCostTypeShouldReturnCreatedCostType() throws Exception {
        CostType validCostType = new CostType();
        validCostType.setType(TYPE1);
        validCostType.setSequenceNo(1);
        
        when(costTypeService.create(any(CostType.class))).thenReturn(costType1);
        
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validCostType)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(TYPE_TEST).value(TYPE1))
                .andExpect(jsonPath(SEQNO_TEST).value(1));
    }
}