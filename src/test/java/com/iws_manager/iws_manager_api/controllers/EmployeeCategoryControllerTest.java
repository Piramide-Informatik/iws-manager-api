package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.EmployeeCategory;
import com.iws_manager.iws_manager_api.services.interfaces.EmployeeCategoryService;
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
class EmployeeCategoryControllerTest {
    private MockMvc mockMvc;
    private String uri = "/api/v1/employee-category";
    private String title = "$.title";
    private String categoryTitle = "Title A";

    @Mock
    private EmployeeCategoryService categoryService;

    @InjectMocks
    private EmployeeCategoryController categoryController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private EmployeeCategory category1;
    private EmployeeCategory category2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

        category1 = new EmployeeCategory();
        category1.setId(1L);
        category1.setTitle(categoryTitle);

        category2 = new EmployeeCategory();
        category2.setId(2L);
        category2.setTitle("Title B");
    }

    @Test
    void createEmployeeCategoryShouldReturnCreatedStatus() throws Exception {
        given(categoryService.create(any(EmployeeCategory.class))).willReturn(category1);

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(title).value(categoryTitle));
    }

    @Test
    void getEmployeeCategoryByIdShouldReturnState() throws Exception {
        given(categoryService.findById(1L)).willReturn(Optional.of(category1));

        mockMvc.perform(get(uri + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(title).value(categoryTitle));
    }

    @Test
    void getEmployeeCategoryByIdShouldReturnNotFound() throws Exception {
        given(categoryService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri + "/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllCategoriesShouldReturnAllCategories() throws Exception {
        List<EmployeeCategory> categories = Arrays.asList(category1, category2);
        given(categoryService.findAll()).willReturn(categories);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value(categoryTitle))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].title").value("Title B"));
    }

    @Test
    void updateEmployeeCategoryShouldReturnUpdatedEmployeeCategory() throws Exception {
        EmployeeCategory updatedEmployeeCategory = new EmployeeCategory();
        updatedEmployeeCategory.setTitle("title A Updated");

        given(categoryService.update(1L, updatedEmployeeCategory)).willReturn(updatedEmployeeCategory);

        mockMvc.perform(put(uri + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedEmployeeCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(title).value("title A Updated"));
    }

    @Test
    void updateEmployeeCategoryShouldReturnNotFound() throws Exception {
        given(categoryService.update(anyLong(), any(EmployeeCategory.class)))
                .willThrow(new RuntimeException("EmployeeCategory not found"));

        mockMvc.perform(put(uri + "/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteEmployeeCategoryShouldReturnNoContent() throws Exception {
        doNothing().when(categoryService).delete(1L);

        mockMvc.perform(delete(uri+"/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createEmployeeCategoryShouldValidateInput() throws Exception {
        EmployeeCategory invalidEmployeeCategory = new EmployeeCategory();

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmployeeCategory)))
                .andExpect(status().isBadRequest());
    }
    @Test
    void createEmployeeCategoryShouldReturnCreatedEmployeeCategory() throws Exception {
        EmployeeCategory validEmployeeCategory = new EmployeeCategory();
        validEmployeeCategory.setTitle(categoryTitle);

        when(categoryService.create(any(EmployeeCategory.class))).thenReturn(validEmployeeCategory);

        mockMvc.perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validEmployeeCategory)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(title).value(categoryTitle));
    }
}