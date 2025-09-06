package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.TeamIws;
import com.iws_manager.iws_manager_api.services.interfaces.TeamIwsService;
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
class TeamIwsControllerTest {
    private MockMvc mockMvc;
    private String uri = "/api/v1/teams-iws";
    private String name = "$.name";
    private String teamName = "Team A";

    @Mock
    private TeamIwsService teamIwsService;

    @InjectMocks
    private TeamIwsController teamIwsController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private TeamIws teamIws1;
    private TeamIws teamIws2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(teamIwsController).build();

        teamIws1 = new TeamIws();
        teamIws1.setId(1L);
        teamIws1.setName(teamName);

        teamIws2 = new TeamIws();
        teamIws2.setId(2L);
        teamIws2.setName("Team B");
    }

    @Test
    void createStateShouldReturnCreatedTeamIws() throws Exception {
        given(teamIwsService.create(any(TeamIws.class))).willReturn(teamIws1);

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamIws1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(teamName));
    }

    @Test
    void getTeamIwsByIdShouldReturnTeamIws() throws Exception {
        given(teamIwsService.findById(1L)).willReturn(Optional.of(teamIws1));

        mockMvc.perform(get(uri + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(teamName));
    }

    @Test
    void getTeamIwsByIdShouldReturnNotFound() throws Exception {
        given(teamIwsService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri + "/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllTeamsIwsShouldReturnAllTeamsIws() throws Exception {
        List<TeamIws> teamsIws = Arrays.asList(teamIws1, teamIws2);
        given(teamIwsService.findAll()).willReturn(teamsIws);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(teamName))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Team B"));
    }

    @Test
    void updateTeamIwsShouldReturnUpdatedTeamIws() throws Exception {
        TeamIws updatedTeamIws = new TeamIws();
        updatedTeamIws.setName("Team A Updated");

        given(teamIwsService.update(1L, updatedTeamIws)).willReturn(updatedTeamIws);

        mockMvc.perform(put(uri + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTeamIws)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(name).value("Team A Updated"));
    }

    @Test
    void updateTeamIwsShouldReturnNotFound() throws Exception {
        given(teamIwsService.update(anyLong(), any(TeamIws.class)))
                .willThrow(new RuntimeException("TeamIws not found"));

        mockMvc.perform(put(uri + "/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamIws1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTeamIwsShouldReturnNoContent() throws Exception {
        doNothing().when(teamIwsService).delete(1L);

        mockMvc.perform(delete(uri+"/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createTeamIwsShouldValidateInput() throws Exception {
        TeamIws invalidTeamIws = new TeamIws();

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTeamIws)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTeamIwsShouldReturnCreatedTeamIws() throws Exception {
        TeamIws validTeamIws = new TeamIws();
        validTeamIws.setName(teamName);

        when(teamIwsService.create(any(TeamIws.class))).thenReturn(validTeamIws);

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validTeamIws)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(name).value(teamName));
    }
}