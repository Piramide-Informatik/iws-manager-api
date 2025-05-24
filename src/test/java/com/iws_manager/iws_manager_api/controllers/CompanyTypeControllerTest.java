package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.CompanyType;
import com.iws_manager.iws_manager_api.services.interfaces.CompanyTypeService;
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
class CompanyTypeControllerTest {

    private MockMvc mockMvc;
    private String uri = "/api/v1/company-types";
    private String name = "$.name";

    @Mock
    private CompanyTypeService companyTypeService;

    @InjectMocks
    private CompanyTypeController companyTypeController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private CompanyType companyType1;
    private CompanyType companyType2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(companyTypeController).build();
        
        companyType1 = new CompanyType();
        companyType1.setId(1L);
        companyType1.setName("Public");

        companyType2 = new CompanyType();
        companyType2.setId(2L);
        companyType2.setName("Private");
    }

    @Test
    void createCompanyTypeShouldReturnCreatedStatus() throws Exception {
        given(companyTypeService.create(any(CompanyType.class))).willReturn(companyType1);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyType1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value("Public"));
    }

    @Test
    void getCompanyTypeByIdShouldReturnCompanyType() throws Exception {
        given(companyTypeService.findById(1L)).willReturn(Optional.of(companyType1));

        mockMvc.perform(get(uri + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value("Public"));
    }

    @Test
    void getCompanyTypeByIdShouldReturnNotFound() throws Exception {
        given(companyTypeService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri + "/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllCompanyTypesShouldReturnAllCompanyTypes() throws Exception {
        List<CompanyType> companyTypes = Arrays.asList(companyType1, companyType2);
        given(companyTypeService.findAll()).willReturn(companyTypes);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Public"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Private"));
    }

    @Test
    void updateCompanyTypeShouldReturnUpdatedCompanyType() throws Exception {
        CompanyType updatedCompanyType = new CompanyType();
        updatedCompanyType.setName("Public Updated");

        given(companyTypeService.update(1L, updatedCompanyType)).willReturn(updatedCompanyType);

        mockMvc.perform(put(uri + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCompanyType)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(name).value("Public Updated"));
    }

    @Test
    void updateCompanyTypeShouldReturnNotFound() throws Exception {
        given(companyTypeService.update(anyLong(), any(CompanyType.class)))
            .willThrow(new RuntimeException("CompanyType not found"));

        mockMvc.perform(put(uri + "/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyType1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCompanyTypeShouldReturnNoContent() throws Exception {
        doNothing().when(companyTypeService).delete(1L);

        mockMvc.perform(delete("/api/v1/company-types/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createCompanyTypeShouldValidateInput() throws Exception {
        CompanyType invalidCompanyType = new CompanyType(); 

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidCompanyType)))
                .andExpect(status().isBadRequest());
    }

    @Test
void createCompanyTypeShouldReturnCreatedCompanyType() throws Exception {
        CompanyType validCompanyType = new CompanyType();
        validCompanyType.setName("Public");
        
        when(companyTypeService.create(any(CompanyType.class))).thenReturn(validCompanyType);
        
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validCompanyType)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(name).value("Public"));
    }
}