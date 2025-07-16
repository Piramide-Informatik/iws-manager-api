package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.ProjectStatus;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectStatusService;
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
public class ProjectStatusControllerTest {
    private static final String ACTIVE = "active"; // ← constante añadida
    private static final String UPDATED_ACTIVE = "active updated";
    private static final String COMPLETED = "completed";
    private MockMvc mockMvc;
    private String uri = "/api/v1/projectstatus";
    private String name = "$.name";

    @Mock
    private ProjectStatusService projectStatusService;

    @InjectMocks
    private ProjectStatusController projectStatusController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private ProjectStatus projectStatus1;
    private ProjectStatus projectStatus2;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(projectStatusController).build();

        projectStatus1 = new ProjectStatus();
        projectStatus1.setId(1L);
        projectStatus1.setName(ACTIVE);

        projectStatus2 = new ProjectStatus();
        projectStatus2.setId(2L);
        projectStatus2.setName(COMPLETED);
    }

    @Test
    void createTitleShouldReturnCreatedStatus() throws Exception {
        given(projectStatusService.create(any(ProjectStatus.class))).willReturn(projectStatus1);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectStatus1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(ACTIVE));
    }

    @Test
    void getTitleByIdShouldReturnProjectStatus() throws Exception {
        given(projectStatusService.findById(1L)).willReturn(Optional.of(projectStatus1));

        mockMvc.perform(get(uri+"/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(ACTIVE));
    }

    @Test
    void getProjectStatusByIdShouldReturnNotFound() throws Exception{
        given(projectStatusService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri+"/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllProjectStatusShouldReturnAllTitles() throws Exception{
        List<ProjectStatus> projectStatuses = Arrays.asList(projectStatus1,projectStatus2);
        given(projectStatusService.findAll()).willReturn(projectStatuses);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(ACTIVE))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value(COMPLETED));
    }

    @Test
    void updateProjectStatusShouldReturnUpdatedTitle() throws Exception{
        ProjectStatus updatedprojectStatus = new ProjectStatus();
        updatedprojectStatus.setName(UPDATED_ACTIVE);

        given(projectStatusService.update(1L, updatedprojectStatus)).willReturn(updatedprojectStatus);

        mockMvc.perform(put(uri+"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedprojectStatus)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(name).value(UPDATED_ACTIVE));
    }

    @Test
    void updateProjectStatusShouldReturnNotFound() throws Exception {
        given(projectStatusService.update(anyLong(), any(ProjectStatus.class)))
                .willThrow(new RuntimeException("ProjectStatus not found"));

        mockMvc.perform(put(uri+"/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectStatus1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProjectStatusShouldReturnNoContent() throws Exception {
        doNothing().when(projectStatusService).delete(1L);

        mockMvc.perform(delete("/api/v1/projectstatus/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createProjectStatusShouldValidateInput() throws Exception {
        ProjectStatus invalidProjectStatus = new ProjectStatus();

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidProjectStatus)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProjectStatusShouldReturnCreatedProjectStatus() throws Exception {
        ProjectStatus validprojectStatus = new ProjectStatus();
        validprojectStatus.setName(ACTIVE);

        when(projectStatusService.create(any(ProjectStatus.class))).thenReturn(validprojectStatus);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validprojectStatus)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(name).value(ACTIVE));
    }

}
