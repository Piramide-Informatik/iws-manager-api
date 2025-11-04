package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.AbsenceType;
import com.iws_manager.iws_manager_api.services.interfaces.AbsenceTypeService;

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

import com.iws_manager.iws_manager_api.exception.GlobalExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class AbsenceTypeControllerTest {
    private static final String VACATION_NAME = "Vacation";
    private static final String UPDATED_VACATION = "Vacation updated";
    private static final String PERSONAL_PERMISSION_NAME="Personal permission";
    private MockMvc mockMvc;
    private String uri = "/api/v1/absensetypes";
    private String name = "$.name";

    @Mock
    private AbsenceTypeService absenceTypeService;

    @InjectMocks
    private  AbsenceTypeController absenceTypeController;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private AbsenceType absenceType1;
    private  AbsenceType absenceType2;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(absenceTypeController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        absenceType1 = new AbsenceType();
        absenceType1.setId(1L);
        absenceType1.setName(VACATION_NAME);

        absenceType2 = new AbsenceType();
        absenceType2.setId(2L);
        absenceType2.setName(PERSONAL_PERMISSION_NAME);
    }

    @Test
    void createAbsenceTypeShouldReturnCreatedStatus() throws Exception {
        given(absenceTypeService.create(any(AbsenceType.class))).willReturn(absenceType1);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(absenceType1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(VACATION_NAME));
    }

    @Test
    void getAbsenceTypeByIdShouldReturnAbsenceType() throws Exception {
        given(absenceTypeService.findById(1L)).willReturn(Optional.of(absenceType1));

        mockMvc.perform(get(uri+"/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(VACATION_NAME));
    }

    @Test
    void getAbsenceTypeByIdShouldReturnNotFound() throws Exception {
        given(absenceTypeService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri+"/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllAbsenceTypeShouldReturnAllAbsenceType() throws Exception {
        List<AbsenceType> absenceTypes = Arrays.asList(absenceType1,absenceType2);
        given(absenceTypeService.findAll()).willReturn(absenceTypes);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(VACATION_NAME))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value(PERSONAL_PERMISSION_NAME));
    }

    @Test
    void updateAbsenceTypeShouldReturnUpdatedAbsenceType() throws Exception {
        AbsenceType updatedAbsenceType = new AbsenceType();
        updatedAbsenceType.setName(UPDATED_VACATION);

        given(absenceTypeService.update(1L, updatedAbsenceType)).willReturn(updatedAbsenceType);

        mockMvc.perform(put(uri+"/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedAbsenceType)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(name).value(UPDATED_VACATION));
    }

   @Test
    void updateAbsenceTypeShouldReturnNotFound() throws Exception {
        given(absenceTypeService.update(anyLong(), any(AbsenceType.class)))
                .willThrow(new EntityNotFoundException("AbsenseType not found with id: 99")); // Cambiado a "AbsenseType"

        mockMvc.perform(put(uri+"/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(absenceType1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAbsenceTypeShouldReturnNoContent() throws Exception {
        doNothing().when(absenceTypeService).delete(1L);

        mockMvc.perform(delete(uri+"/1"))
                .andExpect(status().isNoContent());
    }


    @Test
    void createAbsenceTypeShouldReturnCreatedContractor() throws Exception {
        AbsenceType validAbsenceType = new AbsenceType();
        validAbsenceType.setName(VACATION_NAME);

        when(absenceTypeService.create(any(AbsenceType.class))).thenReturn(validAbsenceType);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validAbsenceType)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(name).value(VACATION_NAME));
    }
}
