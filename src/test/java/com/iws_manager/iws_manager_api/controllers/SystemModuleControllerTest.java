package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.SystemModule;
import com.iws_manager.iws_manager_api.services.interfaces.SystemModuleService;
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
class SystemModuleControllerTest {
    private static final String MODULE_A = "module A";
    private static final String UPDATE_MODULE_A = "module updated";
    private static final String MODULE_B = "module B";
    private MockMvc mockMvc;
    private String uri = "/api/v1/systemmodules";
    private String name = "$.name";

    @Mock
    private SystemModuleService systemModuleService;

    @InjectMocks
    private SystemModuleController systemModuleController;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private SystemModule systemModule1;
    private SystemModule systemModule2;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(systemModuleController).build();

        systemModule1 = new SystemModule();
        systemModule1.setId(1L);
        systemModule1.setName(MODULE_A);

        systemModule2 = new SystemModule();
        systemModule2.setId(2L);
        systemModule2.setName(MODULE_B);
    }
    @Test
    void createSystemModuleShouldReturnCreatedStatus() throws Exception {
        given(systemModuleService.create(any(SystemModule.class))).willReturn(systemModule1);

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(systemModule1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(MODULE_A));
    }

    @Test
    void getSystemModuleByIdShouldReturnSystemModule() throws Exception {
        given(systemModuleService.findById(1L)).willReturn(Optional.of(systemModule1));

        mockMvc.perform(get(uri+"/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(MODULE_A));
    }

    @Test
    void getSystemModuleByIdShouldReturnNotFound() throws Exception {
        given(systemModuleService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri+"/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllSystemModuleShouldReturnAllSystemModule() throws Exception {
        List<SystemModule> systemModules = Arrays.asList(systemModule1, systemModule2);
        given(systemModuleService.findAll()).willReturn(systemModules);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(MODULE_A))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value(MODULE_B));
    }

    @Test
    void updateSystemModuleShouldReturnUpdatedSystemModule() throws Exception {
        SystemModule updatedSystemModule = new SystemModule();
        updatedSystemModule.setName(UPDATE_MODULE_A);

        given(systemModuleService.update(1L, updatedSystemModule)).willReturn(updatedSystemModule);

        mockMvc.perform(put(uri+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedSystemModule)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(name).value(UPDATE_MODULE_A));
    }

    @Test
    void updateSystemModuleShouldReturnNotFound() throws Exception {
        given(systemModuleService.update(anyLong(),any(SystemModule.class)))
                .willThrow(new RuntimeException("SystemModule not found"));

        mockMvc.perform(put(uri+"/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(systemModule1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSystemModuleShouldReturnNoContent() throws Exception {
        doNothing().when(systemModuleService).delete(1L);

        mockMvc.perform(delete(uri+"/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createSystemModuleShouldValidateInput() throws Exception {
        SystemModule invalidASystemModule = new SystemModule();

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidASystemModule)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createSystemModuleShouldReturnCreatedContractor() throws Exception {
        SystemModule validSystemModule = new SystemModule();
        validSystemModule.setName(MODULE_A);

        when(systemModuleService.create(any(SystemModule.class))).thenReturn(validSystemModule);

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validSystemModule)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(name).value(MODULE_A));
    }
}