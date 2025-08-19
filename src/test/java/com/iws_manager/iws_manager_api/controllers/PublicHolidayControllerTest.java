package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.PublicHoliday;
import com.iws_manager.iws_manager_api.services.interfaces.PublicHolidayService;
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
public class PublicHolidayControllerTest {
    private  static  final String FIRST_NAME = "Independence Day";
    private  static  final String UPDATE_NAME = "Independence Day Updated";
    private  static  final String SECOND_NAME = "Corpus Christi";

    private MockMvc mockMvc;
    private String uri = "/api/v1/holidays";
    private String name = "$.name";

    @Mock
    private PublicHolidayService publicHolidayService;

    @InjectMocks
    private PublicHolidayController publicHolidayController;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private PublicHoliday publicHoliday1;
    private PublicHoliday publicHoliday2;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(publicHolidayController).build();

        publicHoliday1 = new PublicHoliday();
        publicHoliday1.setId(1L);
        publicHoliday1.setName(FIRST_NAME);

        publicHoliday2 = new PublicHoliday();
        publicHoliday2.setId(2L);
        publicHoliday2.setName(SECOND_NAME);
    }

    @Test
    void createPublicHolidayShouldReturnCreatedStatus() throws Exception {
        given(publicHolidayService.create(any(PublicHoliday.class))).willReturn(publicHoliday1);

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(publicHoliday1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(FIRST_NAME));
    }

    @Test
    void getPublicHolidayByIdShouldReturnPublicHoliday() throws Exception {
        given(publicHolidayService.findById(1L)).willReturn(Optional.of(publicHoliday1));

        mockMvc.perform(get(uri+"/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(name).value(FIRST_NAME));
    }

    @Test
    void getPublicHolidayByIdShouldReturnNotFound() throws Exception {
        given(publicHolidayService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri+"/99"))
                .andExpect(status().isNotFound());
    }


    @Test
    void getAllPublicHolidayShouldReturnAllPublicHoliday() throws Exception {
        List<PublicHoliday> publicHolidays = Arrays.asList(publicHoliday1, publicHoliday2);
        given(publicHolidayService.findAll()).willReturn(publicHolidays);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(FIRST_NAME
                ))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value(SECOND_NAME));
    }

    @Test
    void updatePublicHolidayShouldReturnUpdatedPublicHoliday() throws Exception {
        PublicHoliday updatedPublicHoliday = new PublicHoliday();
        updatedPublicHoliday.setName(UPDATE_NAME);

        given(publicHolidayService.update(1L, updatedPublicHoliday)).willReturn(updatedPublicHoliday);

        mockMvc.perform(put(uri+"/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPublicHoliday)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(name).value(UPDATE_NAME));
    }

    @Test
    void updatePublicHolidayShouldReturnNotFound() throws Exception {
        given(publicHolidayService.update(anyLong(),any(PublicHoliday.class)))
                .willThrow(new RuntimeException("PublicHoliday not found"));

        mockMvc.perform(put(uri+"/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(publicHoliday1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletePublicHolidayShouldReturnNoContent() throws Exception {
        doNothing().when(publicHolidayService).delete(1L);

        mockMvc.perform(delete(uri+"/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createPublicHolidayShouldValidateInput() throws Exception {
        PublicHoliday invalidPublicHoliday = new PublicHoliday();

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPublicHoliday)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createPublicHolidayShouldReturnCreatedContractor() throws Exception {
        PublicHoliday validPublicHoliday = new PublicHoliday();
        validPublicHoliday.setName(FIRST_NAME);

        when(publicHolidayService.create(any(PublicHoliday.class))).thenReturn(validPublicHoliday);

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validPublicHoliday)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(name).value(FIRST_NAME));
    }
}
