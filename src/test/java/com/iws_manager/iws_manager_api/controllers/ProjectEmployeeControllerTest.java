package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.ProjectEmployee;
import com.iws_manager.iws_manager_api.services.interfaces.ProjectEmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProjectEmployeeControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Configurable path variables
    private String baseUri;
    private String pathEmployee;
    private String pathProject;
    private String pathOrdered;

    // Constants for specific paths
    private static final String PATH_ID = "/1";
    private static final String PATH_NOT_FOUND = "/99";

    // JSON paths
    private static final String JSON_ID = "$.id";
    private static final String JSON_QUALIFICATION_K_MUI = "$.qualificationkmui";
    private static final String QUALIFICATION_K_MUI_0 = "$[0].qualificationkmui";

    // Test data
    private static final String QUALIFICATION_K_MUI_1 = "Senior Developer";
    private static final String QUALIFICATION_K_MUI_2 = "Junior Developer";
    private static final String UPDATED_QUALIFICATION_K_MUI = "Updated Developer";
    private static final BigDecimal HOURLY_RATE_1 = new BigDecimal("45.50");
    private static final BigDecimal HOURLY_RATE_2 = new BigDecimal("35.75");
    private static final BigDecimal PLANNED_HOURS_1 = new BigDecimal("160.00");
    private static final BigDecimal PLANNED_HOURS_2 = new BigDecimal("120.00");
    private static final String KEYWORD = "Developer";
    private static final Long EMPLOYEE_ID = 1L;
    private static final Long PROJECT_ID = 2L;
    private static final List<String> QUALIFICATIONS_LIST = Arrays.asList(QUALIFICATION_K_MUI_1, QUALIFICATION_K_MUI_2);

    @Mock
    private ProjectEmployeeService projectEmployeeService;

    @InjectMocks
    private ProjectEmployeeController projectEmployeeController;

    private ProjectEmployee projectEmployee1;
    private ProjectEmployee projectEmployee2;

    @BeforeEach
    void setUp() {
        // Initialize paths (in a real environment, these would come from @Value or
        // system properties)
        baseUri = getConfigurableProperty("api.base.uri", "/api/v1/project-employees");
        pathEmployee = getConfigurableProperty("api.path.employee", "/employee/");
        pathProject = getConfigurableProperty("api.path.project", "/project/");
        pathOrdered = getConfigurableProperty("api.path.ordered", "/ordered");

        mockMvc = MockMvcBuilders.standaloneSetup(projectEmployeeController).build();

        projectEmployee1 = new ProjectEmployee();
        projectEmployee1.setId(1L);
        projectEmployee1.setQualificationkmui(QUALIFICATION_K_MUI_1);
        projectEmployee1.setHourlyrate(HOURLY_RATE_1);
        projectEmployee1.setPlannedhours(PLANNED_HOURS_1);

        projectEmployee2 = new ProjectEmployee();
        projectEmployee2.setId(2L);
        projectEmployee2.setQualificationkmui(QUALIFICATION_K_MUI_2);
        projectEmployee2.setHourlyrate(HOURLY_RATE_2);
        projectEmployee2.setPlannedhours(PLANNED_HOURS_2);
    }

    /**
     * Helper method to get configurable properties.
     * First searches in System Properties, then in environment variables,
     * and finally uses a default value.
     */
    private String getConfigurableProperty(String key, String defaultValue) {
        return System.getProperty(key, System.getenv().getOrDefault(key, defaultValue));
    }

    // ===== BASIC CRUD OPERATIONS TESTS =====

    @Test
    void createShouldReturnCreated() throws Exception {
        when(projectEmployeeService.create(any(ProjectEmployee.class))).thenReturn(projectEmployee1);

        mockMvc.perform(post(baseUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectEmployee1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID).value(1L))
                .andExpect(jsonPath(JSON_QUALIFICATION_K_MUI).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByIdShouldReturnProjectEmployee() throws Exception {
        when(projectEmployeeService.getById(1L)).thenReturn(Optional.of(projectEmployee1));

        mockMvc.perform(get(baseUri + PATH_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID).value(1L))
                .andExpect(jsonPath(JSON_QUALIFICATION_K_MUI).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByIdShouldReturnNotFound() throws Exception {
        when(projectEmployeeService.getById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get(baseUri + PATH_NOT_FOUND))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllShouldReturnList() throws Exception {
        when(projectEmployeeService.getAll()).thenReturn(Arrays.asList(projectEmployee1, projectEmployee2));

        mockMvc.perform(get(baseUri))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1))
                .andExpect(jsonPath("$[1].qualificationkmui").value(QUALIFICATION_K_MUI_2));
    }

    @Test
    void updateShouldReturnUpdated() throws Exception {
        ProjectEmployee updated = new ProjectEmployee();
        updated.setQualificationkmui(UPDATED_QUALIFICATION_K_MUI);
        updated.setHourlyrate(HOURLY_RATE_1);
        updated.setPlannedhours(PLANNED_HOURS_1);

        when(projectEmployeeService.update(eq(1L), any(ProjectEmployee.class))).thenReturn(updated);

        mockMvc.perform(put(baseUri + PATH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_QUALIFICATION_K_MUI).value(UPDATED_QUALIFICATION_K_MUI));
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        doNothing().when(projectEmployeeService).delete(1L);

        mockMvc.perform(delete(baseUri + PATH_ID))
                .andExpect(status().isNoContent());
    }

    // ===== GET OPERATIONS BY EMPLOYEE TESTS =====

    @Test
    void getByEmployeeIdShouldReturnList() throws Exception {
        when(projectEmployeeService.getByEmployeeId(EMPLOYEE_ID)).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByEmployeeIdOrderByIdAscShouldReturnList() throws Exception {
        when(projectEmployeeService.getByEmployeeIdOrderByIdAsc(EMPLOYEE_ID)).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID + pathOrdered))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY PROJECT TESTS =====

    @Test
    void getByProjectIdShouldReturnList() throws Exception {
        when(projectEmployeeService.getByProjectId(PROJECT_ID)).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + pathProject + PROJECT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByProjectIdOrderByIdAscShouldReturnList() throws Exception {
        when(projectEmployeeService.getByProjectIdOrderByIdAsc(PROJECT_ID)).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + pathProject + PROJECT_ID + pathOrdered))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY QUALIFICATION K MUI TESTS =====

    @Test
    void getByQualificationkmuiShouldReturnList() throws Exception {
        when(projectEmployeeService.getByQualificationkmui(QUALIFICATION_K_MUI_1))
                .thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/qualification-kmui/" + QUALIFICATION_K_MUI_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByQualificationkmuiContainingIgnoreCaseShouldReturnList() throws Exception {
        when(projectEmployeeService.getByQualificationkmuiContainingIgnoreCase(KEYWORD))
                .thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/qualification-kmui/contains/" + KEYWORD))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByQualificationkmuiOrderByIdAscShouldReturnList() throws Exception {
        when(projectEmployeeService.getByQualificationkmuiOrderByIdAsc(QUALIFICATION_K_MUI_1))
                .thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/qualification-kmui/" + QUALIFICATION_K_MUI_1 + pathOrdered))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS WITH COMBINED CRITERIA TESTS =====

    @Test
    void getByEmployeeIdAndProjectIdShouldReturnList() throws Exception {
        when(projectEmployeeService.getByEmployeeIdAndProjectId(EMPLOYEE_ID, PROJECT_ID))
                .thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID + pathProject + PROJECT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByProjectIdAndQualificationkmuiShouldReturnList() throws Exception {
        when(projectEmployeeService.getByProjectIdAndQualificationkmui(PROJECT_ID, QUALIFICATION_K_MUI_1))
                .thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + pathProject + PROJECT_ID + "/qualification-kmui/" + QUALIFICATION_K_MUI_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByEmployeeIdAndQualificationkmuiShouldReturnList() throws Exception {
        when(projectEmployeeService.getByEmployeeIdAndQualificationkmui(EMPLOYEE_ID, QUALIFICATION_K_MUI_1))
                .thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID + "/qualification-kmui/" + QUALIFICATION_K_MUI_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByEmployeeProjectAndQualificationShouldReturnList() throws Exception {
        when(projectEmployeeService.getByEmployeeProjectAndQualification(EMPLOYEE_ID, PROJECT_ID,
                QUALIFICATION_K_MUI_1))
                .thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID + pathProject + PROJECT_ID + "/qualification-kmui/"
                + QUALIFICATION_K_MUI_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY HOURLY RATE RANGE TESTS =====

    @Test
    void getByHourlyrateGreaterThanShouldReturnList() throws Exception {
        when(projectEmployeeService.getByHourlyrateGreaterThan(HOURLY_RATE_1)).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/hourly-rate/greater-than")
                .param("hourlyrate", HOURLY_RATE_1.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByHourlyrateLessThanShouldReturnList() throws Exception {
        when(projectEmployeeService.getByHourlyrateLessThan(HOURLY_RATE_2)).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/hourly-rate/less-than")
                .param("hourlyrate", HOURLY_RATE_2.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByHourlyrateBetweenShouldReturnList() throws Exception {
        BigDecimal minRate = new BigDecimal("40.00");
        BigDecimal maxRate = new BigDecimal("50.00");
        when(projectEmployeeService.getByHourlyrateBetween(minRate, maxRate)).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/hourly-rate/between")
                .param("minHourlyrate", minRate.toString())
                .param("maxHourlyrate", maxRate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY PLANNED HOURS RANGE TESTS =====

    @Test
    void getByPlannedhoursGreaterThanShouldReturnList() throws Exception {
        when(projectEmployeeService.getByPlannedhoursGreaterThan(PLANNED_HOURS_1))
                .thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/planned-hours/greater-than")
                .param("plannedhours", PLANNED_HOURS_1.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByPlannedhoursLessThanShouldReturnList() throws Exception {
        when(projectEmployeeService.getByPlannedhoursLessThan(PLANNED_HOURS_2)).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/planned-hours/less-than")
                .param("plannedhours", PLANNED_HOURS_2.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByPlannedhoursBetweenShouldReturnList() throws Exception {
        BigDecimal minHours = new BigDecimal("150.00");
        BigDecimal maxHours = new BigDecimal("170.00");
        when(projectEmployeeService.getByPlannedhoursBetween(minHours, maxHours)).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/planned-hours/between")
                .param("minPlannedhours", minHours.toString())
                .param("maxPlannedhours", maxHours.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY ESTIMATED COST RANGE TESTS =====

    @Test
    void getByEstimatedCostGreaterThanShouldReturnList() throws Exception {
        BigDecimal minCost = new BigDecimal("1000.00");
        when(projectEmployeeService.getByEstimatedCostGreaterThan(minCost)).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/estimated-cost/greater-than")
                .param("minCost", minCost.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByEstimatedCostLessThanShouldReturnList() throws Exception {
        BigDecimal maxCost = new BigDecimal("10000.00");
        when(projectEmployeeService.getByEstimatedCostLessThan(maxCost)).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/estimated-cost/less-than")
                .param("maxCost", maxCost.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByEstimatedCostBetweenShouldReturnList() throws Exception {
        BigDecimal minCost = new BigDecimal("1000.00");
        BigDecimal maxCost = new BigDecimal("5000.00");
        when(projectEmployeeService.getByEstimatedCostBetween(minCost, maxCost)).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/estimated-cost/between")
                .param("minCost", minCost.toString())
                .param("maxCost", maxCost.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS WITH MINIMUM RATE AND HOURS =====

    @Test
    void getWithMinimumRateAndHoursShouldReturnList() throws Exception {
        BigDecimal minRate = new BigDecimal("40.00");
        BigDecimal minHours = new BigDecimal("150.00");
        when(projectEmployeeService.getWithMinimumRateAndHours(minRate, minHours))
                .thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/minimum-rate-hours")
                .param("minRate", minRate.toString())
                .param("minHours", minHours.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY QUALIFICATION CONTAINING KEYWORD =====

    @Test
    void getByQualificationContainingShouldReturnList() throws Exception {
        when(projectEmployeeService.getByQualificationContaining(KEYWORD)).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/search/" + KEYWORD))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY MULTIPLE QUALIFICATIONS =====

    @Test
    void getByQualificationsInShouldReturnList() throws Exception {
        when(projectEmployeeService.getByQualificationsIn(QUALIFICATIONS_LIST)).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/qualifications/in")
                .param("qualifications", QUALIFICATIONS_LIST.toArray(new String[0])))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== ORDERING OPERATIONS TESTS =====

    @Test
    void getAllOrderByHourlyrateAscShouldReturnList() throws Exception {
        when(projectEmployeeService.getAllOrderByHourlyrateAsc()).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/ordered/hourly-rate-asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getAllOrderByHourlyrateDescShouldReturnList() throws Exception {
        when(projectEmployeeService.getAllOrderByHourlyrateDesc()).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/ordered/hourly-rate-desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getAllOrderByPlannedhoursAscShouldReturnList() throws Exception {
        when(projectEmployeeService.getAllOrderByPlannedhoursAsc()).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/ordered/planned-hours-asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getAllOrderByPlannedhoursDescShouldReturnList() throws Exception {
        when(projectEmployeeService.getAllOrderByPlannedhoursDesc()).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/ordered/planned-hours-desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getAllOrderByQualificationkmuiAscShouldReturnList() throws Exception {
        when(projectEmployeeService.getAllOrderByQualificationkmuiAsc()).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/ordered/qualification-kmui-asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getAllOrderByEstimatedCostAscShouldReturnList() throws Exception {
        when(projectEmployeeService.getAllOrderByEstimatedCostAsc()).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/ordered/estimated-cost-asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getAllOrderByEstimatedCostDescShouldReturnList() throws Exception {
        when(projectEmployeeService.getAllOrderByEstimatedCostDesc()).thenReturn(List.of(projectEmployee1));

        mockMvc.perform(get(baseUri + "/ordered/estimated-cost-desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== CALCULATION OPERATIONS TESTS =====

    @Test
    void calculateTotalCostByProjectShouldReturnBigDecimal() throws Exception {
        BigDecimal totalCost = new BigDecimal("7280.00"); // 45.50 * 160.00
        when(projectEmployeeService.calculateTotalCostByProject(PROJECT_ID)).thenReturn(totalCost);

        mockMvc.perform(get(baseUri + pathProject + PROJECT_ID + "/total-cost"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(7280.0));
    }

    @Test
    void calculateTotalCostByEmployeeShouldReturnBigDecimal() throws Exception {
        BigDecimal totalCost = new BigDecimal("7280.00");
        when(projectEmployeeService.calculateTotalCostByEmployee(EMPLOYEE_ID)).thenReturn(totalCost);

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID + "/total-cost"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(7280.0));
    }

    @Test
    void calculateTotalPlannedHoursByProjectShouldReturnBigDecimal() throws Exception {
        BigDecimal totalHours = new BigDecimal("320.00");
        when(projectEmployeeService.calculateTotalPlannedHoursByProject(PROJECT_ID)).thenReturn(totalHours);

        mockMvc.perform(get(baseUri + pathProject + PROJECT_ID + "/total-planned-hours"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(320.0));
    }

    @Test
    void calculateTotalPlannedHoursByEmployeeShouldReturnBigDecimal() throws Exception {
        BigDecimal totalHours = new BigDecimal("320.00");
        when(projectEmployeeService.calculateTotalPlannedHoursByEmployee(EMPLOYEE_ID)).thenReturn(totalHours);

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID + "/total-planned-hours"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(320.0));
    }

    // ===== COUNT OPERATIONS TESTS =====

    @Test
    void countByProjectShouldReturnLong() throws Exception {
        Long count = 5L;
        when(projectEmployeeService.countByProject(PROJECT_ID)).thenReturn(count);

        mockMvc.perform(get(baseUri + "/count/project/" + PROJECT_ID))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void countByEmployeeShouldReturnLong() throws Exception {
        Long count = 3L;
        when(projectEmployeeService.countByEmployee(EMPLOYEE_ID)).thenReturn(count);

        mockMvc.perform(get(baseUri + "/count/employee/" + EMPLOYEE_ID))
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }

    @Test
    void countByProjectAndEmployeeShouldReturnLong() throws Exception {
        Long count = 2L;
        when(projectEmployeeService.countByProjectAndEmployee(PROJECT_ID, EMPLOYEE_ID)).thenReturn(count);

        mockMvc.perform(get(baseUri + "/count/project/" + PROJECT_ID + pathEmployee + EMPLOYEE_ID))
                .andExpect(status().isOk())
                .andExpect(content().string("2"));
    }

    // ===== GET AVERAGE HOURLY RATE OPERATIONS TESTS =====

    @Test
    void getAverageHourlyRateByProjectShouldReturnBigDecimal() throws Exception {
        BigDecimal averageRate = new BigDecimal("47.50");
        when(projectEmployeeService.getAverageHourlyRateByProject(PROJECT_ID)).thenReturn(averageRate);

        mockMvc.perform(get(baseUri + pathProject + PROJECT_ID + "/average-hourly-rate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(47.5));
    }

    @Test
    void getAverageHourlyRateByEmployeeShouldReturnBigDecimal() throws Exception {
        BigDecimal averageRate = new BigDecimal("47.50");
        when(projectEmployeeService.getAverageHourlyRateByEmployee(EMPLOYEE_ID)).thenReturn(averageRate);

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID + "/average-hourly-rate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(47.5));
    }

    // ===== GET STATISTICS OPERATIONS TESTS =====

    @Test
    void getProjectStatisticsShouldReturnArray() throws Exception {
        Object[] stats = new Object[] { 5L, new BigDecimal("800.00"), HOURLY_RATE_1,
                new BigDecimal("36400.00") };
        when(projectEmployeeService.getProjectStatistics(PROJECT_ID)).thenReturn(stats);

        mockMvc.perform(get(baseUri + pathProject + PROJECT_ID + "/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(5))
                .andExpect(jsonPath("$[1]").value(800.0))
                .andExpect(jsonPath("$[2]").value(45.5))
                .andExpect(jsonPath("$[3]").value(36400.0));
    }

    @Test
    void getEmployeeStatisticsShouldReturnArray() throws Exception {
        Object[] stats = new Object[] { 3L, new BigDecimal("480.00"), HOURLY_RATE_1,
                new BigDecimal("21840.00") };
        when(projectEmployeeService.getEmployeeStatistics(EMPLOYEE_ID)).thenReturn(stats);

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID + "/statistics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(3))
                .andExpect(jsonPath("$[1]").value(480.0))
                .andExpect(jsonPath("$[2]").value(45.5))
                .andExpect(jsonPath("$[3]").value(21840.0));
    }

    // ===== VALIDATION TESTS =====

    @Test
    void validateProjectEmployeeShouldReturnBoolean() throws Exception {
        when(projectEmployeeService.validateProjectEmployee(any(ProjectEmployee.class))).thenReturn(true);

        mockMvc.perform(post(baseUri + "/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(projectEmployee1)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    // ===== ADDITIONAL TESTS FOR SPECIFIC METHODS =====

    @Test
    void getDistinctEmployeesByProjectShouldReturnList() throws Exception {
        // This endpoint returns List<Employee> not List<ProjectEmployee>
        when(projectEmployeeService.getDistinctEmployeesByProject(PROJECT_ID)).thenReturn(Collections.emptyList());

        mockMvc.perform(get(baseUri + pathProject + PROJECT_ID + "/distinct-employees"))
                .andExpect(status().isOk());
    }

    @Test
    void getDistinctProjectsByEmployeeShouldReturnList() throws Exception {
        // This endpoint returns List<Project> not List<ProjectEmployee>
        when(projectEmployeeService.getDistinctProjectsByEmployee(EMPLOYEE_ID)).thenReturn(Collections.emptyList());

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID + "/distinct-projects"))
                .andExpect(status().isOk());
    }

}