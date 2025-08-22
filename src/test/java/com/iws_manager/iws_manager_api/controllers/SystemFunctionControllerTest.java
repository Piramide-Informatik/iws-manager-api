package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.SystemFunction;
import com.iws_manager.iws_manager_api.services.interfaces.SystemFunctionService;
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
class SystemFunctionControllerTest {
    private static final String FUNCTION_A = "function A";
    private static final String UPDATE_FUNCTION = "function updated";
    private static final String FUNCTION_B = "function B";
    private MockMvc mockMvc;
    private String uri = "/api/v1/systemfunctions";
    private String name = "$.functionName";

    @Mock
    private SystemFunctionService systemFunctionService;

    @InjectMocks
    private SystemFunctionController systemFunctionController;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private SystemFunction systemFunction1;
    private SystemFunction systemFunction2;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(systemFunctionController).build();

        systemFunction1 = new SystemFunction();
        systemFunction1.setId(1L);
        systemFunction1.setFunctionName(FUNCTION_A);

        systemFunction2 = new SystemFunction();
        systemFunction2.setId(2L);
        systemFunction2.setFunctionName(FUNCTION_B);
    }

    @Test
    void createSystemFunctionShouldReturnCreatedStatus() throws Exception {
        given(systemFunctionService.create(any(SystemFunction.class))).willReturn(systemFunction1);

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(systemFunction1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(FUNCTION_A));
    }

    @Test
    void getSystemFunctionByIdShouldReturnSystemFunction() throws Exception {
        given(systemFunctionService.findById(1L)).willReturn(Optional.of(systemFunction1));

        mockMvc.perform(get(uri+"/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(FUNCTION_A));
    }

    @Test
    void getSystemFunctionByIdShouldReturnNotFound() throws Exception {
        given(systemFunctionService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri+"/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllSystemFunctionShouldReturnAllSystemFunction() throws Exception {
        List<SystemFunction> systemFunctions = Arrays.asList(systemFunction1, systemFunction2);
        given(systemFunctionService.findAll()).willReturn(systemFunctions);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].functionName").value(FUNCTION_A))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].functionName").value(FUNCTION_B));
    }

    @Test
    void updateSystemFunctionShouldReturnUpdatedSystemFunction() throws Exception {
        SystemFunction updatedSystemFunction = new SystemFunction();
        updatedSystemFunction.setFunctionName(UPDATE_FUNCTION);

        given(systemFunctionService.update(1L, updatedSystemFunction)).willReturn(updatedSystemFunction);

        mockMvc.perform(put(uri+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedSystemFunction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(name).value(UPDATE_FUNCTION));
    }

    @Test
    void updateSystemFunctionShouldReturnNotFound() throws Exception {
        given(systemFunctionService.update(anyLong(),any(SystemFunction.class)))
                .willThrow(new RuntimeException("SystemFunction not found"));

        mockMvc.perform(put(uri+"/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(systemFunction1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSystemFunctionShouldReturnNoContent() throws Exception {
        doNothing().when(systemFunctionService).delete(1L);

        mockMvc.perform(delete(uri+"/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createSystemFunctionShouldValidateInput() throws Exception {
        SystemFunction invalidSystemFunction = new SystemFunction();

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidSystemFunction)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createSystemFunctionShouldReturnCreatedContractor() throws Exception {
        SystemFunction validSystemFunction = new SystemFunction();
        validSystemFunction.setFunctionName(FUNCTION_A);

        when(systemFunctionService.create(any(SystemFunction.class))).thenReturn(validSystemFunction);

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validSystemFunction)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(name).value(FUNCTION_A));
    }
}