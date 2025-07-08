package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.QualificationFZ;
import com.iws_manager.iws_manager_api.services.interfaces.QualificationFZService;

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

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class QualificationFZControllerTest {

    private MockMvc mockMvc;
    private String uri = "/api/v1/qualificationfz";
    private String qualificationPath = "$.qualification";
    private String testQualification = "Wissenschaftler";

    @Mock
    private QualificationFZService qualificationFZService;

    @InjectMocks
    private QualificationFZController qualificationFZController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private QualificationFZ qualification1;
    private QualificationFZ qualification2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(qualificationFZController).build();
        
        qualification1 = new QualificationFZ();
        qualification1.setId(1L);
        qualification1.setQualification(testQualification);

        qualification2 = new QualificationFZ();
        qualification2.setId(2L);
        qualification2.setQualification("Techniker");
    }

    @Test
    void createQualificationFZShouldReturnCreatedStatus() throws Exception {
        given(qualificationFZService.create(any(QualificationFZ.class))).willReturn(qualification1);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(qualification1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(qualificationPath).value(testQualification));
    }

    @Test
    void createQualificationFZShouldValidateEmptyName() throws Exception {
        QualificationFZ invalidQualification = new QualificationFZ();
        invalidQualification.setQualification("  ");

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidQualification)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Name is required"));
    }

    @Test
    void getQualificationFZByIdShouldReturnQualification() throws Exception {
        given(qualificationFZService.findById(1L)).willReturn(Optional.of(qualification1));

        mockMvc.perform(get(uri + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(qualificationPath).value(testQualification));
    }

    @Test
    void getQualificationFZByIdShouldReturnNotFound() throws Exception {
        given(qualificationFZService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri + "/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllQualificationFZShouldReturnAllQualifications() throws Exception {
        List<QualificationFZ> qualifications = Arrays.asList(qualification1, qualification2);
        given(qualificationFZService.findAll()).willReturn(qualifications);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].qualification").value(testQualification))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].qualification").value("Techniker"));
    }

    @Test
    void updateQualificationFZShouldReturnUpdatedQualification() throws Exception {
        QualificationFZ updatedQualification = new QualificationFZ();
        updatedQualification.setQualification("Updated Qualification");

        given(qualificationFZService.update(1L, updatedQualification)).willReturn(updatedQualification);

        mockMvc.perform(put(uri + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedQualification)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(qualificationPath).value("Updated Qualification"));
    }

    @Test
    void updateQualificationFZShouldReturnNotFound() throws Exception {
        given(qualificationFZService.update(anyLong(), any(QualificationFZ.class)))
            .willThrow(new RuntimeException("Qualification not found"));

        mockMvc.perform(put(uri + "/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(qualification1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteQualificationFZShouldReturnNoContent() throws Exception {
        doNothing().when(qualificationFZService).delete(1L);

        mockMvc.perform(delete(uri + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createQualificationFZShouldReturnCreatedQualification() throws Exception {
        QualificationFZ validQualification = new QualificationFZ();
        validQualification.setQualification(testQualification);
        
        when(qualificationFZService.create(any(QualificationFZ.class))).thenReturn(validQualification);
        
        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validQualification)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(qualificationPath).value(testQualification));
    }
}