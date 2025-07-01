package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.Employee;
import com.iws_manager.iws_manager_api.services.interfaces.EmployeeService;
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
class EmployeeControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String BASE_URI = "/api/v1/employees";
    private static final String PATH_ID = "/1";
    private static final String PATH_NOT_FOUND = "/99";

    private static final String JSON_ID = "$.id";
    private static final String JSON_FIRSTNAME = "$.firstname";

    private static final String FIRSTNAME_0 = "$[0].firstname";
    private static final String FIRSTNAME_1 = "Alice";
    private static final String FIRSTNAME_2 = "Bob";
    private static final String UPDATED_FIRSTNAME = "Alice Updated";
    private static final String LASTNAME = "Doe";
    private static final String EMAIL = "alice@mail.com";
    private static final String EMAIL_NOT_FOUND = "notfound@mail.com";

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();

        employee1 = new Employee();
        employee1.setId(1L);
        employee1.setFirstname(FIRSTNAME_1);
        employee1.setLastname(LASTNAME);
        employee1.setEmail(EMAIL);

        employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstname(FIRSTNAME_2);
    }

    @Test
    void createEmployeeShouldReturnCreated() throws Exception {
        when(employeeService.create(any(Employee.class))).thenReturn(employee1);

        mockMvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID).value(1L))
                .andExpect(jsonPath(JSON_FIRSTNAME).value(FIRSTNAME_1));
    }

    @Test
    void findByIdShouldReturnEmployee() throws Exception {
        when(employeeService.findById(1L)).thenReturn(Optional.of(employee1));

        mockMvc.perform(get(BASE_URI + PATH_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID).value(1L))
                .andExpect(jsonPath(JSON_FIRSTNAME).value(FIRSTNAME_1));
    }

    @Test
    void findByIdShouldReturnNotFound() throws Exception {
        when(employeeService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get(BASE_URI + PATH_NOT_FOUND))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllShouldReturnList() throws Exception {
        when(employeeService.findAll()).thenReturn(Arrays.asList(employee1, employee2));

        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath(FIRSTNAME_0).value(FIRSTNAME_1))
                .andExpect(jsonPath("$[1].firstname").value(FIRSTNAME_2));
    }

    @Test
    void updateShouldReturnUpdated() throws Exception {
        Employee updated = new Employee();
        updated.setFirstname(UPDATED_FIRSTNAME);

        when(employeeService.update(1L, updated)).thenReturn(updated);

        mockMvc.perform(put(BASE_URI + PATH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_FIRSTNAME).value(UPDATED_FIRSTNAME));
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        doNothing().when(employeeService).delete(1L);

        mockMvc.perform(delete(BASE_URI + PATH_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void findByLastnameShouldReturnList() throws Exception {
        when(employeeService.findByLastname(LASTNAME)).thenReturn(List.of(employee1));

        mockMvc.perform(get(BASE_URI + "/lastname/" + LASTNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath(FIRSTNAME_0).value(FIRSTNAME_1));
    }

    @Test
    void findByEmailShouldReturnEmployee() throws Exception {
        when(employeeService.findByEmail(EMAIL)).thenReturn(Optional.of(employee1));

        mockMvc.perform(get(BASE_URI + "/email/" + EMAIL))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_FIRSTNAME).value(FIRSTNAME_1));
    }

    @Test
    void findByEmailShouldReturnNotFound() throws Exception {
        when(employeeService.findByEmail(EMAIL_NOT_FOUND)).thenReturn(Optional.empty());

        mockMvc.perform(get(BASE_URI + "/email/" + EMAIL_NOT_FOUND))
                .andExpect(status().isNotFound());
    }

    @Test
    void findByTitleIdShouldReturnList() throws Exception {
        when(employeeService.findByTitleId(1L)).thenReturn(List.of(employee1));

        mockMvc.perform(get(BASE_URI + "/title/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(FIRSTNAME_0).value(FIRSTNAME_1));
    }

    @Test
    void findBySalutationIdShouldReturnList() throws Exception {
        when(employeeService.findBySalutationId(1L)).thenReturn(List.of(employee1));

        mockMvc.perform(get(BASE_URI + "/salutation/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(FIRSTNAME_0).value(FIRSTNAME_1));
    }

    @Test
    void findByQualificationFZIdShouldReturnList() throws Exception {
        when(employeeService.findByQualificationFZId(1L)).thenReturn(List.of(employee1));

        mockMvc.perform(get(BASE_URI + "/qualification/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(FIRSTNAME_0).value(FIRSTNAME_1));
    }

    @Test
    void findByCustomerIdShouldReturnList() throws Exception {
        when(employeeService.findByCustomerId(1L)).thenReturn(List.of(employee1));

        mockMvc.perform(get(BASE_URI + "/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(FIRSTNAME_0).value(FIRSTNAME_1));
    }
}
