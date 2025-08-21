package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.EmployeeIws;
import com.iws_manager.iws_manager_api.services.interfaces.EmployeeIwsService;

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
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
class EmployeeIwsControllerTest {

    // Test Data Constants
    private static final String BASE_URI = "/api/v1/employeesiws";
    private static final String FIRSTNAME_JOHN = "John";
    private static final String LASTNAME_DOE = "Doe";
    private static final String FIRSTNAME_JANE = "Jane";
    private static final String LASTNAME_SMITH = "Smith";
    private static final String MAIL_JOHN = "john.doe@example.com";
    private static final String EMPLOYEE_LABEL = "DEV001";
    private static final Integer EMPLOYEE_NO = 1001;
    private static final Integer ACTIVE_STATUS = 1;
    private static final Integer INACTIVE_STATUS = 0;
    private static final String UPDATED_FIRSTNAME = "JohnUpdated";
    private static final String UPDATED_LASTNAME = "DoeUpdated";
    private static final String UPDATED_MAIL = "john.updated@example.com";
    private static final String UPDATED_LABEL = "DEV002";
    private static final Integer UPDATED_EMPLOYEE_NO = 1002;
    private static final String FIRSTNAME_VAR = "$.firstname";
    private static final String LENGHT_VAR = "$.length()";

    // Test Dates
    private static final LocalDate START_DATE_2023 = LocalDate.of(2023, 1, 1);
    private static final LocalDate END_DATE_2025 = LocalDate.of(2025, 12, 31);
    private static final LocalDate START_DATE_2024 = LocalDate.of(2024, 1, 1);
    private static final LocalDate END_DATE_2026 = LocalDate.of(2026, 12, 31);

    // Test IDs
    private static final Long EMPLOYEE_ID_1 = 1L;
    private static final Long EMPLOYEE_ID_2 = 2L;
    private static final Long TEAM_IWS_ID = 10L;
    private static final Long USER_ID = 20L;
    private static final Long NON_EXISTENT_ID = 999L;

    private MockMvc mockMvc;

    @Mock
    private EmployeeIwsService employeeIwsService;

    @InjectMocks
    private EmployeeIwsController employeeIwsController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private EmployeeIws employeeIws;
    private EmployeeIws employeeIws2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeIwsController).build();

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // Employee 1 - Datos básicos sin relaciones
        employeeIws = new EmployeeIws();
        employeeIws.setId(EMPLOYEE_ID_1);
        employeeIws.setActive(ACTIVE_STATUS);
        employeeIws.setEmployeeLabel(EMPLOYEE_LABEL);
        employeeIws.setEmployeeNo(EMPLOYEE_NO);
        employeeIws.setFirstname(FIRSTNAME_JOHN);
        employeeIws.setLastname(LASTNAME_DOE);
        employeeIws.setMail(MAIL_JOHN);
        employeeIws.setStartDate(START_DATE_2023);
        employeeIws.setEndDate(END_DATE_2025);
        // Relaciones como null para evitar problemas de serialización
        employeeIws.setTeamIws(null);
        employeeIws.setUser(null);

        // Employee 2
        employeeIws2 = new EmployeeIws();
        employeeIws2.setId(EMPLOYEE_ID_2);
        employeeIws2.setFirstname(FIRSTNAME_JANE);
        employeeIws2.setLastname(LASTNAME_SMITH);
        employeeIws2.setActive(ACTIVE_STATUS);
        employeeIws2.setTeamIws(null);
        employeeIws2.setUser(null);
    }

    @Test
    void createShouldReturnCreatedEmployee() throws Exception {
        // Request sin relaciones
        EmployeeIws inputEmployee = new EmployeeIws();
        inputEmployee.setFirstname(FIRSTNAME_JOHN);
        inputEmployee.setLastname(LASTNAME_DOE);
        inputEmployee.setMail(MAIL_JOHN);
        inputEmployee.setActive(ACTIVE_STATUS);
        inputEmployee.setEmployeeLabel(EMPLOYEE_LABEL);
        inputEmployee.setEmployeeNo(EMPLOYEE_NO);
        inputEmployee.setStartDate(START_DATE_2023);
        inputEmployee.setEndDate(END_DATE_2025);

        given(employeeIwsService.create(any(EmployeeIws.class))).willReturn(employeeIws);

        mockMvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(EMPLOYEE_ID_1))
                .andExpect(jsonPath(FIRSTNAME_VAR).value(FIRSTNAME_JOHN));
    }

    @Test
    void findByIdShouldReturnEmployee() throws Exception {
        given(employeeIwsService.findById(EMPLOYEE_ID_1)).willReturn(Optional.of(employeeIws));

        mockMvc.perform(get("/api/v1/employeesiws/{id}", EMPLOYEE_ID_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(EMPLOYEE_ID_1))
                .andExpect(jsonPath(FIRSTNAME_VAR).value(FIRSTNAME_JOHN));
    }

    @Test
    void findByIdShouldReturnNotFound() throws Exception {
        given(employeeIwsService.findById(NON_EXISTENT_ID)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/employeesiws/{id}", NON_EXISTENT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void findAllShouldReturnAllEmployees() throws Exception {
        List<EmployeeIws> employees = Arrays.asList(employeeIws, employeeIws2);
        given(employeeIwsService.findAll()).willReturn(employees);

        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(2))
                .andExpect(jsonPath("$[0].firstname").value(FIRSTNAME_JOHN))
                .andExpect(jsonPath("$[1].firstname").value(FIRSTNAME_JANE));
    }

    @Test
    void updateShouldReturnUpdatedEmployee() throws Exception {
        // Request de actualización sin relaciones
        EmployeeIws updateRequest = new EmployeeIws();
        updateRequest.setFirstname(UPDATED_FIRSTNAME);
        updateRequest.setLastname(UPDATED_LASTNAME);
        updateRequest.setMail(UPDATED_MAIL);
        updateRequest.setActive(INACTIVE_STATUS);
        updateRequest.setEmployeeLabel(UPDATED_LABEL);
        updateRequest.setEmployeeNo(UPDATED_EMPLOYEE_NO);
        updateRequest.setStartDate(START_DATE_2024);
        updateRequest.setEndDate(END_DATE_2026);

        // Respuesta esperada
        EmployeeIws updatedEmployee = new EmployeeIws();
        updatedEmployee.setId(EMPLOYEE_ID_1);
        updatedEmployee.setFirstname(UPDATED_FIRSTNAME);
        updatedEmployee.setLastname(UPDATED_LASTNAME);
        updatedEmployee.setMail(UPDATED_MAIL);
        updatedEmployee.setActive(INACTIVE_STATUS);
        updatedEmployee.setEmployeeLabel(UPDATED_LABEL);
        updatedEmployee.setEmployeeNo(UPDATED_EMPLOYEE_NO);
        updatedEmployee.setStartDate(START_DATE_2024);
        updatedEmployee.setEndDate(END_DATE_2026);
        updatedEmployee.setTeamIws(null);
        updatedEmployee.setUser(null);

        given(employeeIwsService.update(eq(EMPLOYEE_ID_1), any(EmployeeIws.class))).willReturn(updatedEmployee);

        mockMvc.perform(put("/api/v1/employeesiws/{id}", EMPLOYEE_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(EMPLOYEE_ID_1))
                .andExpect(jsonPath(FIRSTNAME_VAR).value(UPDATED_FIRSTNAME));
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        doNothing().when(employeeIwsService).delete(EMPLOYEE_ID_1);

        mockMvc.perform(delete("/api/v1/employeesiws/{id}", EMPLOYEE_ID_1))
                .andExpect(status().isNoContent());
    }

    // FIND ALL - ORDER Tests
    @Test
    void getAllByOrderByLastnameAscShouldReturnOrderedList() throws Exception {
        List<EmployeeIws> employees = Arrays.asList(employeeIws, employeeIws2);
        given(employeeIwsService.getAllByOrderByLastnameAsc()).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/by-lastname/ordered-asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(2));
    }

    @Test
    void getAllByOrderByFirstnameAscShouldReturnOrderedList() throws Exception {
        List<EmployeeIws> employees = Arrays.asList(employeeIws, employeeIws2);
        given(employeeIwsService.getAllByOrderByFirstnameAsc()).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/by-firstname/ordered-asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(2));
    }

    // PROPERTIES Tests
    @Test
    void getByActiveShouldReturnEmployees() throws Exception {
        List<EmployeeIws> employees = Arrays.asList(employeeIws, employeeIws2);
        given(employeeIwsService.getByActive(ACTIVE_STATUS)).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/by-active/{active}", ACTIVE_STATUS))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(2));
    }

    @Test
    void getByEmployeeLabelShouldReturnEmployees() throws Exception {
        List<EmployeeIws> employees = Collections.singletonList(employeeIws);
        given(employeeIwsService.getByEmployeeLabel(EMPLOYEE_LABEL)).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/by-label/{employeeLabel}", EMPLOYEE_LABEL))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(1));
    }

    @Test
    void getByEmployeeNoShouldReturnEmployees() throws Exception {
        List<EmployeeIws> employees = Collections.singletonList(employeeIws);
        given(employeeIwsService.getByEmployeeNo(EMPLOYEE_NO)).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/by-number/{employeeNo}", EMPLOYEE_NO))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(1));
    }

    @Test
    void getByEndDateShouldReturnEmployees() throws Exception {
        List<EmployeeIws> employees = Collections.singletonList(employeeIws);
        given(employeeIwsService.getByEndDate(END_DATE_2025)).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/by-enddate/{endDate}", END_DATE_2025))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(1));
    }

    @Test
    void getByFirstnameShouldReturnEmployees() throws Exception {
        List<EmployeeIws> employees = Collections.singletonList(employeeIws);
        given(employeeIwsService.getByFirstname(FIRSTNAME_JOHN)).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/by-firstname/{firstname}", FIRSTNAME_JOHN))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(1));
    }

    @Test
    void getByLastnameShouldReturnEmployees() throws Exception {
        List<EmployeeIws> employees = Collections.singletonList(employeeIws);
        given(employeeIwsService.getByLastname(LASTNAME_DOE)).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/by-lastname/{lastname}", LASTNAME_DOE))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(1));
    }

    @Test
    void getByMailShouldReturnEmployees() throws Exception {
        List<EmployeeIws> employees = Collections.singletonList(employeeIws);
        given(employeeIwsService.getByMail(MAIL_JOHN)).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/by-mail/{mail}", MAIL_JOHN))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(1));
    }

    @Test
    void getByStartDateShouldReturnEmployees() throws Exception {
        List<EmployeeIws> employees = Collections.singletonList(employeeIws);
        given(employeeIwsService.getByStartDate(START_DATE_2023)).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/by-startdate/{startDate}", START_DATE_2023))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(1));
    }

    @Test
    void getByTeamIwsIdShouldReturnEmployees() throws Exception {
        List<EmployeeIws> employees = Collections.singletonList(employeeIws);
        given(employeeIwsService.getByTeamIwsId(TEAM_IWS_ID)).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/by-team/{teamIwsId}", TEAM_IWS_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(1));
    }

    @Test
    void getByUserIdShouldReturnEmployees() throws Exception {
        List<EmployeeIws> employees = Collections.singletonList(employeeIws);
        given(employeeIwsService.getByUserId(USER_ID)).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/by-user/{userId}", USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(1));
    }

    // HELPERS Tests
    @Test
    void getByStartDateAfterShouldReturnEmployees() throws Exception {
        LocalDate date = LocalDate.of(2022, 12, 31);
        List<EmployeeIws> employees = Collections.singletonList(employeeIws);
        given(employeeIwsService.getByStartDateAfter(date)).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/by-startdate/after/{date}", date))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(1));
    }

    @Test
    void getByStartDateBeforeShouldReturnEmployees() throws Exception {
        LocalDate date = LocalDate.of(2024, 1, 1);
        List<EmployeeIws> employees = Collections.singletonList(employeeIws);
        given(employeeIwsService.getByStartDateBefore(date)).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/by-startdate/before/{date}", date))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(1));
    }

    @Test
    void getByStartDateBetweenShouldReturnEmployees() throws Exception {
        LocalDate start = LocalDate.of(2022, 12, 31);
        LocalDate end = LocalDate.of(2024, 1, 1);
        List<EmployeeIws> employees = Collections.singletonList(employeeIws);
        given(employeeIwsService.getByStartDateBetween(start, end)).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/by-startdate/between/{start}/{end}", start, end))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(1));
    }

    @Test
    void getByEndDateAfterShouldReturnEmployees() throws Exception {
        LocalDate date = LocalDate.of(2024, 12, 31);
        List<EmployeeIws> employees = Collections.singletonList(employeeIws);
        given(employeeIwsService.getByEndDateAfter(date)).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/by-enddate/after/{date}", date))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(1));
    }

    @Test
    void getByEndDateBeforeShouldReturnEmployees() throws Exception {
        LocalDate date = LocalDate.of(2026, 1, 1);
        List<EmployeeIws> employees = Collections.singletonList(employeeIws);
        given(employeeIwsService.getByEndDateBefore(date)).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/by-enddate/before/{date}", date))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(1));
    }

    @Test
    void getByEndDateBetweenShouldReturnEmployees() throws Exception {
        LocalDate start = LocalDate.of(2024, 12, 31);
        LocalDate end = LocalDate.of(2026, 1, 1);
        List<EmployeeIws> employees = Collections.singletonList(employeeIws);
        given(employeeIwsService.getByEndDateBetween(start, end)).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/by-enddate/between/{start}/{end}", start, end))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(1));
    }

    // ACTIVE - ORDER Tests
    @Test
    void getByActiveOrderByFirstnameAscShouldReturnOrderedEmployees() throws Exception {
        List<EmployeeIws> employees = Arrays.asList(employeeIws, employeeIws2);
        given(employeeIwsService.getByActiveOrderByFirstnameAsc(ACTIVE_STATUS)).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/active/{active}/by-firstname/ordered-asc", ACTIVE_STATUS))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(2));
    }

    @Test
    void getByActiveOrderByLastnameAscShouldReturnOrderedEmployees() throws Exception {
        List<EmployeeIws> employees = Arrays.asList(employeeIws, employeeIws2);
        given(employeeIwsService.getByActiveOrderByLastnameAsc(ACTIVE_STATUS)).willReturn(employees);

        mockMvc.perform(get("/api/v1/employeesiws/active/{active}/by-lastname/ordered-asc", ACTIVE_STATUS))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_VAR).value(2));
    }

    @Test
    void findAllWhenEmptyShouldReturnEmptyList() throws Exception {
        given(employeeIwsService.findAll()).willReturn(Collections.emptyList());

        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getByNonExistentPropertyShouldReturnEmptyList() throws Exception {
        given(employeeIwsService.getByFirstname("NonExistent")).willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/employeesiws/by-firstname/{firstname}", "NonExistent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void updateShouldReturnUpdatedEmployeeMocked() throws Exception {
        // Usar un Map en lugar del objeto completo
        String updateJson = """
        {
            "firstname": "JohnUpdated",
            "lastname": "DoeUpdated",
            "mail": "john.updated@example.com",
            "active": 0,
            "employeeLabel": "DEV002",
            "employeeNo": 1002,
            "startDate": "2024-01-01",
            "endDate": "2026-12-31"
        }
        """;

        EmployeeIws updatedResponse = new EmployeeIws();
        updatedResponse.setId(EMPLOYEE_ID_1);
        updatedResponse.setFirstname(UPDATED_FIRSTNAME);
        updatedResponse.setLastname(UPDATED_LASTNAME);
        updatedResponse.setMail("john.updated@example.com");
        updatedResponse.setActive(0);
        updatedResponse.setEmployeeLabel("DEV002");
        updatedResponse.setEmployeeNo(1002);
        updatedResponse.setStartDate(LocalDate.of(2024, 1, 1));
        updatedResponse.setEndDate(LocalDate.of(2026, 12, 31));

        given(employeeIwsService.update(eq(EMPLOYEE_ID_1), any(EmployeeIws.class))).willReturn(updatedResponse);

        mockMvc.perform(put("/api/v1/employeesiws/{id}", EMPLOYEE_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(EMPLOYEE_ID_1))
                .andExpect(jsonPath(FIRSTNAME_VAR).value(UPDATED_FIRSTNAME));
    }
}