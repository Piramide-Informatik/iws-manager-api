package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.Title;
import com.iws_manager.iws_manager_api.services.interfaces.TitleService;
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
class TitleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TitleService titleService;

    @InjectMocks
    private TitleController titleController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Title title1;
    private Title title2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(titleController).build();
        
        title1 = new Title();
        title1.setId(1L);
        title1.setName("Dr.");

        title2 = new Title();
        title2.setId(2L);
        title2.setName("Prof.");
    }

    @Test
    void createTitleShouldReturnCreatedStatus() throws Exception {
        given(titleService.create(any(Title.class))).willReturn(title1);

        mockMvc.perform(post("/api/v1/titles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(title1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Dr."));
    }

    @Test
    void getTitleByIdShouldReturnTitle() throws Exception {
        given(titleService.findById(1L)).willReturn(Optional.of(title1));

        mockMvc.perform(get("/api/v1/titles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Dr."));
    }

    @Test
    void getTitleByIdShouldReturnNotFound() throws Exception {
        given(titleService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/titles/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllTitlesShouldReturnAllTitles() throws Exception {
        List<Title> titles = Arrays.asList(title1, title2);
        given(titleService.findAll()).willReturn(titles);

        mockMvc.perform(get("/api/v1/titles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Dr."))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Prof."));
    }

    @Test
    void updateTitleShouldReturnUpdatedTitle() throws Exception {
        Title updatedTitle = new Title();
        updatedTitle.setName("Dr. Updated");

        given(titleService.update(1L, updatedTitle)).willReturn(updatedTitle);

        mockMvc.perform(put("/api/v1/titles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTitle)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dr. Updated"));
    }

    @Test
    void updateTitleShouldReturnNotFound() throws Exception {
        given(titleService.update(anyLong(), any(Title.class)))
            .willThrow(new RuntimeException("Title not found"));

        mockMvc.perform(put("/api/v1/titles/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(title1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTitleShouldReturnNoContent() throws Exception {
        doNothing().when(titleService).delete(1L);

        mockMvc.perform(delete("/api/v1/titles/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createTitleShouldValidateInput() throws Exception {
        Title invalidTitle = new Title(); 

        mockMvc.perform(post("/api/v1/titles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidTitle)))
                .andExpect(status().isBadRequest());
    }

    @Test
void createTitleShouldReturnCreatedTitle() throws Exception {
        Title validTitle = new Title();
        validTitle.setName("Dr.");
        
        when(titleService.create(any(Title.class))).thenReturn(validTitle);
        
        mockMvc.perform(post("/api/v1/titles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validTitle)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Dr."));
    }
}