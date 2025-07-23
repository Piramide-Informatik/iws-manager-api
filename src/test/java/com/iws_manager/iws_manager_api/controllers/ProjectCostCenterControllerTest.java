package com.iws_manager.iws_manager_api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.ProjectCostCenter;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectCostCenterService;

@ExtendWith(MockitoExtension.class)
class ProjectCostCenterControllerTest {

    private static final Long ID = 1L;
    private static final String COST_CENTER = "CC-1001";
    private static final String KMUINO = "0837";
    private static final Integer SEQUENCE_NO = 1;
    private static final String UPDATED_COST_CENTER = "CC-1002";
    private static final String UPDATED_KMUINO = "9999";
    private static final Integer UPDATED_SEQUENCE_NO = 2;
    private static final String BASE_URL = "/api/v1/projectcostcenters";
    private static final String ID_URL = BASE_URL + "/{id}";
    private static final String COST_CENTER_STR = "$.costCenter";

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private ProjectCostCenterService projectCostCenterService;

    @InjectMocks
    private ProjectCostCenterController projectCostCenterController;

    private ProjectCostCenter projectCostCenter;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(projectCostCenterController).build();
        
        projectCostCenter = new ProjectCostCenter();
        projectCostCenter.setId(ID);
        projectCostCenter.setCostCenter(COST_CENTER);
        projectCostCenter.setKmuino(KMUINO);
        projectCostCenter.setSequenceno(SEQUENCE_NO);
    }

    @Test
    void createShouldReturnCreatedProjectCostCenter() throws Exception {
        when(projectCostCenterService.create(any(ProjectCostCenter.class))).thenReturn(projectCostCenter);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectCostCenter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath(COST_CENTER_STR).value(COST_CENTER))
                .andExpect(jsonPath("$.kmuino").value(KMUINO))
                .andExpect(jsonPath("$.sequenceno").value(SEQUENCE_NO));

        verify(projectCostCenterService, times(1)).create(any(ProjectCostCenter.class));
    }

    @Test
    void findByIdShouldReturnProjectCostCenterWhenExists() throws Exception {
        when(projectCostCenterService.findById(ID)).thenReturn(Optional.of(projectCostCenter));

        mockMvc.perform(get(ID_URL, ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath(COST_CENTER_STR).value(COST_CENTER));

        verify(projectCostCenterService, times(1)).findById(ID);
    }

    @Test
    void findByIdShouldReturnNotFoundWhenNotExists() throws Exception {
        when(projectCostCenterService.findById(ID)).thenReturn(Optional.empty());

        mockMvc.perform(get(ID_URL, ID))
                .andExpect(status().isNotFound());

        verify(projectCostCenterService, times(1)).findById(ID);
    }

    @Test
    void findAllShouldReturnAllProjectCostCenters() throws Exception {
        List<ProjectCostCenter> costCenters = Arrays.asList(projectCostCenter);
        when(projectCostCenterService.findAll()).thenReturn(costCenters);

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(ID))
                .andExpect(jsonPath("$[0].costCenter").value(COST_CENTER));

        verify(projectCostCenterService, times(1)).findAll();
    }

    @Test
    void updateShouldReturnUpdatedProjectCostCenter() throws Exception {
        ProjectCostCenter updated = new ProjectCostCenter();
        updated.setCostCenter(UPDATED_COST_CENTER);
        updated.setKmuino(UPDATED_KMUINO);
        updated.setSequenceno(UPDATED_SEQUENCE_NO);

        when(projectCostCenterService.update(eq(ID), any(ProjectCostCenter.class))).thenReturn(updated);

        mockMvc.perform(put(ID_URL, ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(COST_CENTER_STR).value(UPDATED_COST_CENTER))
                .andExpect(jsonPath("$.kmuino").value(UPDATED_KMUINO))
                .andExpect(jsonPath("$.sequenceno").value(UPDATED_SEQUENCE_NO));

        verify(projectCostCenterService, times(1)).update(eq(ID), any(ProjectCostCenter.class));
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        doNothing().when(projectCostCenterService).delete(ID);

        mockMvc.perform(delete(ID_URL, ID))
                .andExpect(status().isNoContent());

        verify(projectCostCenterService, times(1)).delete(ID);
    }
}