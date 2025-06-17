package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.Branch;
import com.iws_manager.iws_manager_api.services.interfaces.BranchService;
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
class BranchControllerTest {

    private MockMvc mockMvc;
    private String uri = "/api/v1/branches";
    private String name = "$.name";
    private String ctname = "Public";

    @Mock
    private BranchService branchService;

    @InjectMocks
    private BranchController branchController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Branch branch1;
    private Branch branch2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(branchController).build();
        
        branch1 = new Branch();
        branch1.setId(1L);
        branch1.setName(ctname);

        branch2 = new Branch();
        branch2.setId(2L);
        branch2.setName("Private");
    }

    @Test
    void createBranchShouldReturnCreatedStatus() throws Exception {
        given(branchService.create(any(Branch.class))).willReturn(branch1);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(branch1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(ctname));
    }

    @Test
    void getBranchByIdShouldReturnBranch() throws Exception {
        given(branchService.findById(1L)).willReturn(Optional.of(branch1));

        mockMvc.perform(get(uri + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(ctname));
    }

    @Test
    void getBranchByIdShouldReturnNotFound() throws Exception {
        given(branchService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri + "/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllBranchesShouldReturnAllBranches() throws Exception {
        List<Branch> branches = Arrays.asList(branch1, branch2);
        given(branchService.findAll()).willReturn(branches);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(ctname))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Private"));
    }

    @Test
    void updateBranchShouldReturnUpdatedBranch() throws Exception {
        Branch updatedBranch = new Branch();
        updatedBranch.setName("Public Updated");

        given(branchService.update(1L, updatedBranch)).willReturn(updatedBranch);

        mockMvc.perform(put(uri + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBranch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(name).value("Public Updated"));
    }

    @Test
    void updateBranchShouldReturnNotFound() throws Exception {
        given(branchService.update(anyLong(), any(Branch.class)))
            .willThrow(new RuntimeException("Branch not found"));

        mockMvc.perform(put(uri + "/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(branch1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBranchShouldReturnNoContent() throws Exception {
        doNothing().when(branchService).delete(1L);

        mockMvc.perform(delete("/api/v1/branches/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createBranchShouldValidateInput() throws Exception {
        Branch invalidBranch = new Branch(); 

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidBranch)))
                .andExpect(status().isBadRequest());
    }

    @Test
void createBranchShouldReturnCreatedBranch() throws Exception {
        Branch validBranch = new Branch();
        validBranch.setName(ctname);
        
        when(branchService.create(any(Branch.class))).thenReturn(validBranch);
        
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validBranch)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(name).value(ctname));
    }
}