package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.Role;
import com.iws_manager.iws_manager_api.services.interfaces.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

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
public class RoleControllerTest {
    private static final String FIRST_ROLE ="admin";
    private static final String UPDATE_ROLE ="admin updated";
    private static final String SECOND_ROLE ="tester";

    private MockMvc mockMvc;
    private String uri = "/api/v1/roles";
    private String name = "$.name";

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RoleController roleController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Role role1;
    private Role role2;

    @BeforeEach
    void setUp() {
        mockMvc= MockMvcBuilders.standaloneSetup(roleController).build();

        role1 = new Role();
        role1.setId(1L);
        role1.setName(FIRST_ROLE);

        role2 = new Role();
        role2.setId(2L);
        role2.setName(SECOND_ROLE);
    }

    @Test
    void createRoleShouldReturnCreatedStatus() throws Exception {
        given(roleService.create(any(Role.class))).willReturn(role1);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(role1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(FIRST_ROLE));
    }

    @Test
    void getRoleByIdShouldReturnRole() throws Exception {
        given(roleService.findById(1L)).willReturn(Optional.of(role1));

        mockMvc.perform(get(uri+"/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(FIRST_ROLE));
    }

    @Test
    void getRoleByIdShouldReturnNotFound() throws Exception {
        given(roleService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri+"/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllRolesShouldReturnAllRoles() throws Exception {
        List<Role> roles = Arrays.asList(role1, role2);
        given(roleService.findAll()).willReturn(roles);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(FIRST_ROLE))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value(SECOND_ROLE));
    }

    @Test
    void updateRoleShouldReturnUpdatedRole() throws Exception {
        Role updatedRole = new Role();
        updatedRole.setName(UPDATE_ROLE);

        given(roleService.update(1L, updatedRole)).willReturn(updatedRole);

        mockMvc.perform(put(uri+"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedRole)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(name).value(UPDATE_ROLE));
    }

    @Test
    void updateRoleShouldReturnNotFound() throws Exception {
        given(roleService.update(anyLong(), any(Role.class)))
                .willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));

        mockMvc.perform(put(uri+"/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(role1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteRoleShouldReturnNoContent() throws Exception {
        doNothing().when(roleService).delete(1L);

        mockMvc.perform(delete(uri+"/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createRoleShouldValidateInput() throws Exception {
        Role invalidRole = new Role();

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRole)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTitleShouldReturnCreatedTitle() throws Exception {
        Role validRole = new Role();
        validRole.setName(FIRST_ROLE);

        when(roleService.create(any(Role.class))).thenReturn(validRole);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRole)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(name).value(FIRST_ROLE));
    }
}
