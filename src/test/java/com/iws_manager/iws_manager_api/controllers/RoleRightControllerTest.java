package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.RoleRight;
import com.iws_manager.iws_manager_api.services.interfaces.RoleRightService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RoleRightControllerTest {
    private static final Integer ACCESS_A = 1;
    private static final Integer UPDATE_ACCESS = 6;
    private static final Integer ACCESS_B = 3;
    private MockMvc mockMvc;
    private String uri = "/api/v1/rolerights";
    private String access = "$.accessRight";

    @Mock
    private RoleRightService roleRightService;

    @InjectMocks
    private RoleRightController roleRightController;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private RoleRight roleRight1;
    private RoleRight roleRight2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(roleRightController)
                .build();

        roleRight1 = new RoleRight();
        roleRight1.setId(1L);
        roleRight1.setAccessRight(ACCESS_A);

        roleRight2 = new RoleRight();
        roleRight2.setId(2L);
        roleRight2.setAccessRight(ACCESS_B);
    }

    @Test
    void createRoleRightShouldReturnCreatedStatus() throws Exception {
        given(roleRightService.create(any(RoleRight.class))).willReturn(roleRight1);

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleRight1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(access).value(ACCESS_A));
    }

    @Test
    void getRoleRightByIdShouldReturnRoleRight() throws Exception {
        given(roleRightService.findById(1L)).willReturn(Optional.of(roleRight1));

        mockMvc.perform(get(uri+"/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(access).value(ACCESS_A));
    }

    @Test
    void getRoleRightByIdShouldReturnNotFound() throws Exception {
        given(roleRightService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri+"/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllRoleRightShouldReturnAllRoleRight() throws Exception {
        List<RoleRight> roleRights = Arrays.asList(roleRight1, roleRight2);
        given(roleRightService.findAll()).willReturn(roleRights);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].accessRight").value(ACCESS_A))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].accessRight").value(ACCESS_B));
    }

    @Test
    void updateRoleRightShouldReturnUpdatedRoleRight() throws Exception {
        RoleRight updatedRoleRight = new RoleRight();
        updatedRoleRight.setAccessRight(UPDATE_ACCESS);

        given(roleRightService.update(1L, updatedRoleRight)).willReturn(updatedRoleRight);

        mockMvc.perform(put(uri+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRoleRight)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(access).value(UPDATE_ACCESS));
    }

    @Test
    void updateRoleRightShouldReturnNotFound() throws Exception {
        given(roleRightService.update(anyLong(),any(RoleRight.class)))
                .willThrow(new RuntimeException("RoleRight not found"));

        mockMvc.perform(put(uri+"/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roleRight1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteRoleRightShouldReturnNoContent() throws Exception {
        doNothing().when(roleRightService).delete(1L);

        mockMvc.perform(delete(uri+"/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createApprovalStatusShouldValidateInput() throws Exception {
        RoleRight invalidRoleRight = new RoleRight();

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRoleRight)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createAbsenceTypeShouldReturnCreatedContractor() throws Exception {
        RoleRight validRoleRight = new RoleRight();
        validRoleRight.setAccessRight(ACCESS_A);

        when(roleRightService.create(any(RoleRight.class))).thenReturn(validRoleRight);

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRoleRight)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(access).value(ACCESS_A));
    }
}