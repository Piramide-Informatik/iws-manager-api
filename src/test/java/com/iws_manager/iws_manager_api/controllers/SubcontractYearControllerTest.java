package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.SubcontractYear;
import com.iws_manager.iws_manager_api.services.interfaces.SubcontractYearService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class SubcontractYearControllerTest {

    private static final String URI = "/api/v1/subcontractyear";
    private static final String JSON_MONTHS_PATH = "$.months";
    private static final Integer MONTHS = 12;

    private MockMvc mockMvc;

    @Mock
    private SubcontractYearService subcontractYearService;

    @InjectMocks
    private SubcontractYearController subcontractYearController;

    private final ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule());


    private SubcontractYear sy1;
    private SubcontractYear sy2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(subcontractYearController).build();

        sy1 = new SubcontractYear();
        sy1.setId(1L);
        sy1.setMonths(MONTHS);
        sy1.setYear(LocalDate.of(2025, 1, 1));
        sy1.setSubcontract(null);

        sy2 = new SubcontractYear();
        sy2.setId(2L);
        sy2.setMonths(6);
        sy2.setYear(LocalDate.of(2026, 1, 1));
        sy2.setSubcontract(null);
    }

    @Test
    void createSubcontractYearShouldReturnCreated() throws Exception {
        given(subcontractYearService.create(any(SubcontractYear.class))).willReturn(sy1);

        mockMvc.perform(post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sy1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.months").value(MONTHS));
    }

    @Test
    void getSubcontractYearByIdShouldReturnEntity() throws Exception {
        given(subcontractYearService.findById(1L)).willReturn(Optional.of(sy1));

        mockMvc.perform(get(URI + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(JSON_MONTHS_PATH).value(12));
    }

    @Test
    void getSubcontractYearByIdShouldReturnNotFound() throws Exception {
        given(subcontractYearService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(URI + "/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllSubcontractYearsShouldReturnList() throws Exception {
        given(subcontractYearService.findAll()).willReturn(Arrays.asList(sy1, sy2));

        mockMvc.perform(get(URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    void updateSubcontractYearShouldReturnUpdated() throws Exception {
        SubcontractYear updated = new SubcontractYear();
        updated.setId(1L);
        updated.setMonths(8);
        updated.setYear(LocalDate.of(2025, 5, 1));

        given(subcontractYearService.update(1L, updated)).willReturn(updated);

        mockMvc.perform(put(URI + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_MONTHS_PATH).value(8));
    }

    @Test
    void updateSubcontractYearShouldReturnNotFound() throws Exception {
        given(subcontractYearService.update(anyLong(), any(SubcontractYear.class)))
                .willThrow(new RuntimeException("Not found"));

        mockMvc.perform(put(URI + "/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sy1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteSubcontractYearShouldReturnNoContent() throws Exception {
        doNothing().when(subcontractYearService).delete(1L);

        mockMvc.perform(delete(URI + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteSubcontractYearShouldReturnNotFound() throws Exception {
        doThrow(new RuntimeException("Not found")).when(subcontractYearService).delete(999L);

        mockMvc.perform(delete(URI + "/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByMonthsShouldReturnList() throws Exception {
        given(subcontractYearService.findByMonths(12)).willReturn(Collections.singletonList(sy1));

        mockMvc.perform(get(URI + "/months/12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void findBySubcontractIdShouldReturnList() throws Exception {
        given(subcontractYearService.findBySubcontractId(1L)).willReturn(Collections.singletonList(sy1));

        mockMvc.perform(get(URI + "/subcontract/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void findByYearShouldReturnList() throws Exception {
        LocalDate year = LocalDate.of(2025, 1, 1);
        given(subcontractYearService.findByYear(year)).willReturn(Collections.singletonList(sy1));

        mockMvc.perform(get(URI + "/year/2025-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }
}
