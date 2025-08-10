package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.FundingProgram;
import com.iws_manager.iws_manager_api.services.interfaces.FundingProgramService;
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
class FundingProgramControllerTest {

    private MockMvc mockMvc;
    private final String uri = "/api/v1/funding-programs";
    private final String namePath = "$.name";
    private final String testName = "ZIM";

    @Mock
    private FundingProgramService fundingProgramService;

    @InjectMocks
    private FundingProgramController fundingProgramController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private FundingProgram fp1;
    private FundingProgram fp2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fundingProgramController).build();

        fp1 = new FundingProgram();
        fp1.setId(1L);
        fp1.setName(testName);
        fp1.setDefaultFundingRate(12.5);
        fp1.setDefaultHoursPerYear(1800.0);
        fp1.setDefaultResearchShare(30.0);
        fp1.setDefaultStuffFlat(15.0);

        fp2 = new FundingProgram();
        fp2.setId(2L);
        fp2.setName("KMU-i");
    }

    @Test
    void createFundingProgramShouldReturnCreatedStatus() throws Exception {
        given(fundingProgramService.create(any(FundingProgram.class))).willReturn(fp1);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fp1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(namePath).value(testName));
    }

    @Test
    void getFundingProgramByIdShouldReturnFundingProgram() throws Exception {
        given(fundingProgramService.findById(1L)).willReturn(Optional.of(fp1));

        mockMvc.perform(get(uri + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(namePath).value(testName));
    }

    @Test
    void getFundingProgramByIdShouldReturnNotFound() throws Exception {
        given(fundingProgramService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri + "/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllFundingProgramsShouldReturnAllFundingPrograms() throws Exception {
        List<FundingProgram> programs = Arrays.asList(fp1, fp2);
        given(fundingProgramService.findAll()).willReturn(programs);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(testName))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("KMU-i"));
    }

    @Test
    void updateFundingProgramShouldReturnUpdatedFundingProgram() throws Exception {
        FundingProgram updated = new FundingProgram();
        updated.setName("ZIM Updated");

        given(fundingProgramService.update(1L, updated)).willReturn(updated);

        mockMvc.perform(put(uri + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(namePath).value("ZIM Updated"));
    }

    @Test
    void updateFundingProgramShouldReturnNotFound() throws Exception {
        given(fundingProgramService.update(anyLong(), any(FundingProgram.class)))
            .willThrow(new RuntimeException("FundingProgram not found"));

        mockMvc.perform(put(uri + "/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fp1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteFundingProgramShouldReturnNoContent() throws Exception {
        doNothing().when(fundingProgramService).delete(1L);

        mockMvc.perform(delete(uri + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createFundingProgramShouldValidateInput() throws Exception {
        FundingProgram invalidFP = new FundingProgram();

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidFP)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createFundingProgramShouldReturnCreatedFundingProgram() throws Exception {
        FundingProgram validFP = new FundingProgram();
        validFP.setName(testName);

        when(fundingProgramService.create(any(FundingProgram.class))).thenReturn(validFP);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFP)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(namePath).value(testName));
    }
}

