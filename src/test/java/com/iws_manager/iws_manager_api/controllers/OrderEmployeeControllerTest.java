package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.OrderEmployee;
import com.iws_manager.iws_manager_api.services.interfaces.OrderEmployeeService;
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
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrderEmployeeControllerTest {

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Variables configurables en lugar de constantes hardcodeadas
    private String baseUri;
    private String pathEmployee;
    private String pathOrder;
    private String pathQualificationFz;
    private String pathOrdered;

    // Constantes que siguen siendo aceptables (no son URIs completas)
    private static final String PATH_ID = "/1";
    private static final String PATH_NOT_FOUND = "/99";

    // JSON paths (no son URIs)
    private static final String JSON_ID = "$.id";
    private static final String JSON_QUALIFICATION_K_MUI = "$.qualificationkmui";
    private static final String JSON_TITLE = "$.title";
    private static final String QUALIFICATION_K_MUI_0 = "$[0].qualificationkmui";

    // Datos de prueba
    private static final String QUALIFICATION_K_MUI_1 = "Senior Developer";
    private static final String QUALIFICATION_K_MUI_2 = "Junior Developer";
    private static final String TITLE_1 = "FZ-Kurzbezeichnung FuE-Tätigkeit";
    private static final String TITLE_2 = "Software Engineer";
    private static final String UPDATED_QUALIFICATION_K_MUI = "Updated Developer";
    private static final String UPDATED_TITLE = "Updated Title";
    private static final BigDecimal HOURLY_RATE_1 = new BigDecimal("45.50");
    private static final BigDecimal HOURLY_RATE_2 = new BigDecimal("35.75");
    private static final BigDecimal PLANNED_HOURS_1 = new BigDecimal("160.00");
    private static final BigDecimal PLANNED_HOURS_2 = new BigDecimal("120.00");
    private static final String KEYWORD = "Developer";
    private static final Long EMPLOYEE_ID = 1L;
    private static final Long ORDER_ID = 2L;
    private static final Long QUALIFICATION_FZ_ID = 3L;

    @Mock
    private OrderEmployeeService orderEmployeeService;

    @InjectMocks
    private OrderEmployeeController orderEmployeeController;

    private OrderEmployee orderEmployee1;
    private OrderEmployee orderEmployee2;

    @BeforeEach
    void setUp() {
        // Inicializar las rutas desde propiedades configurables
        // En un entorno real, estas vendrían de @Value o propiedades del sistema
        baseUri = getConfigurableProperty("api.base.uri", "/api/v1/order-employees");
        pathEmployee = getConfigurableProperty("api.path.employee", "/employee/");
        pathOrder = getConfigurableProperty("api.path.order", "/order/");
        pathQualificationFz = getConfigurableProperty("api.path.qualification-fz", "/qualification-fz/");
        pathOrdered = getConfigurableProperty("api.path.ordered", "/ordered");

        mockMvc = MockMvcBuilders.standaloneSetup(orderEmployeeController).build();

        orderEmployee1 = new OrderEmployee();
        orderEmployee1.setId(1L);
        orderEmployee1.setQualificationkmui(QUALIFICATION_K_MUI_1);
        orderEmployee1.setTitle(TITLE_1);
        orderEmployee1.setHourlyrate(HOURLY_RATE_1);
        orderEmployee1.setPlannedhours(PLANNED_HOURS_1);

        orderEmployee2 = new OrderEmployee();
        orderEmployee2.setId(2L);
        orderEmployee2.setQualificationkmui(QUALIFICATION_K_MUI_2);
        orderEmployee2.setTitle(TITLE_2);
        orderEmployee2.setHourlyrate(HOURLY_RATE_2);
        orderEmployee2.setPlannedhours(PLANNED_HOURS_2);
    }

    /**
     * Método helper para obtener propiedades configurables.
     * Primero busca en System Properties, luego en variables de entorno,
     * y finalmente usa un valor por defecto.
     */
    private String getConfigurableProperty(String key, String defaultValue) {
        return System.getProperty(key, System.getenv().getOrDefault(key, defaultValue));
    }

    // ===== BASIC CRUD OPERATIONS TESTS =====

    @Test
    void createShouldReturnCreated() throws Exception {
        when(orderEmployeeService.create(any(OrderEmployee.class))).thenReturn(orderEmployee1);

        mockMvc.perform(post(baseUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderEmployee1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID).value(1L))
                .andExpect(jsonPath(JSON_QUALIFICATION_K_MUI).value(QUALIFICATION_K_MUI_1))
                .andExpect(jsonPath(JSON_TITLE).value(TITLE_1));
    }

    @Test
    void getByIdShouldReturnOrderEmployee() throws Exception {
        when(orderEmployeeService.getById(1L)).thenReturn(Optional.of(orderEmployee1));

        mockMvc.perform(get(baseUri + PATH_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID).value(1L))
                .andExpect(jsonPath(JSON_QUALIFICATION_K_MUI).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByIdShouldReturnNotFound() throws Exception {
        when(orderEmployeeService.getById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get(baseUri + PATH_NOT_FOUND))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllShouldReturnList() throws Exception {
        when(orderEmployeeService.getAll()).thenReturn(Arrays.asList(orderEmployee1, orderEmployee2));

        mockMvc.perform(get(baseUri))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1))
                .andExpect(jsonPath("$[1].qualificationkmui").value(QUALIFICATION_K_MUI_2));
    }

    @Test
    void updateShouldReturnUpdated() throws Exception {
        OrderEmployee updated = new OrderEmployee();
        updated.setQualificationkmui(UPDATED_QUALIFICATION_K_MUI);
        updated.setTitle(UPDATED_TITLE);
        updated.setHourlyrate(HOURLY_RATE_1);
        updated.setPlannedhours(PLANNED_HOURS_1);

        when(orderEmployeeService.update(eq(1L), any(OrderEmployee.class))).thenReturn(updated);

        mockMvc.perform(put(baseUri + PATH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_QUALIFICATION_K_MUI).value(UPDATED_QUALIFICATION_K_MUI))
                .andExpect(jsonPath(JSON_TITLE).value(UPDATED_TITLE));
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        doNothing().when(orderEmployeeService).delete(1L);

        mockMvc.perform(delete(baseUri + PATH_ID))
                .andExpect(status().isNoContent());
    }

    // ===== GET OPERATIONS BY EMPLOYEE TESTS =====

    @Test
    void getByEmployeeIdShouldReturnList() throws Exception {
        when(orderEmployeeService.getByEmployeeId(EMPLOYEE_ID)).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByEmployeeIdOrderByIdAscShouldReturnList() throws Exception {
        when(orderEmployeeService.getByEmployeeIdOrderByIdAsc(EMPLOYEE_ID)).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID + pathOrdered))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY ORDER TESTS =====

    @Test
    void getByOrderIdShouldReturnList() throws Exception {
        when(orderEmployeeService.getByOrderId(ORDER_ID)).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + pathOrder + ORDER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByOrderIdOrderByIdAscShouldReturnList() throws Exception {
        when(orderEmployeeService.getByOrderIdOrderByIdAsc(ORDER_ID)).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + pathOrder + ORDER_ID + pathOrdered))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY QUALIFICATION FZ TESTS =====

    @Test
    void getByQualificationFZIdShouldReturnList() throws Exception {
        when(orderEmployeeService.getByQualificationFZId(QUALIFICATION_FZ_ID)).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + pathQualificationFz + QUALIFICATION_FZ_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByQualificationFZIdOrderByIdAscShouldReturnList() throws Exception {
        when(orderEmployeeService.getByQualificationFZIdOrderByIdAsc(QUALIFICATION_FZ_ID))
                .thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + pathQualificationFz + QUALIFICATION_FZ_ID + pathOrdered))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY QUALIFICATION K MUI TESTS =====

    @Test
    void getByQualificationkmuiShouldReturnList() throws Exception {
        when(orderEmployeeService.getByQualificationkmui(QUALIFICATION_K_MUI_1)).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/qualification-kmui/" + QUALIFICATION_K_MUI_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByQualificationkmuiContainingIgnoreCaseShouldReturnList() throws Exception {
        when(orderEmployeeService.getByQualificationkmuiContainingIgnoreCase(KEYWORD))
                .thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/qualification-kmui/contains/" + KEYWORD))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByQualificationkmuiOrderByIdAscShouldReturnList() throws Exception {
        when(orderEmployeeService.getByQualificationkmuiOrderByIdAsc(QUALIFICATION_K_MUI_1))
                .thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/qualification-kmui/" + QUALIFICATION_K_MUI_1 + pathOrdered))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY TITLE TESTS =====

    @Test
    void getByTitleShouldReturnList() throws Exception {
        when(orderEmployeeService.getByTitle(TITLE_1)).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/title/" + TITLE_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByTitleContainingIgnoreCaseShouldReturnList() throws Exception {
        when(orderEmployeeService.getByTitleContainingIgnoreCase(KEYWORD)).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/title/contains/" + KEYWORD))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByTitleOrderByIdAscShouldReturnList() throws Exception {
        when(orderEmployeeService.getByTitleOrderByIdAsc(TITLE_1)).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/title/" + TITLE_1 + pathOrdered))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS WITH COMBINED CRITERIA TESTS =====

    @Test
    void getByEmployeeIdAndOrderIdShouldReturnList() throws Exception {
        when(orderEmployeeService.getByEmployeeIdAndOrderId(EMPLOYEE_ID, ORDER_ID)).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID + pathOrder + ORDER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByOrderIdAndQualificationFZIdShouldReturnList() throws Exception {
        when(orderEmployeeService.getByOrderIdAndQualificationFZId(ORDER_ID, QUALIFICATION_FZ_ID))
                .thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + pathOrder + ORDER_ID + pathQualificationFz + QUALIFICATION_FZ_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByEmployeeIdAndQualificationFZIdShouldReturnList() throws Exception {
        when(orderEmployeeService.getByEmployeeIdAndQualificationFZId(EMPLOYEE_ID, QUALIFICATION_FZ_ID))
                .thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID + pathQualificationFz + QUALIFICATION_FZ_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByEmployeeOrderAndQualificationShouldReturnList() throws Exception {
        when(orderEmployeeService.getByEmployeeOrderAndQualification(EMPLOYEE_ID, ORDER_ID, QUALIFICATION_FZ_ID))
                .thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID + pathOrder + ORDER_ID + pathQualificationFz
                + QUALIFICATION_FZ_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY HOURLY RATE RANGE TESTS =====

    @Test
    void getByHourlyrateGreaterThanShouldReturnList() throws Exception {
        when(orderEmployeeService.getByHourlyrateGreaterThan(HOURLY_RATE_1)).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/hourly-rate/greater-than")
                .param("hourlyrate", HOURLY_RATE_1.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByHourlyrateLessThanShouldReturnList() throws Exception {
        when(orderEmployeeService.getByHourlyrateLessThan(HOURLY_RATE_2)).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/hourly-rate/less-than")
                .param("hourlyrate", HOURLY_RATE_2.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByHourlyrateBetweenShouldReturnList() throws Exception {
        BigDecimal minRate = new BigDecimal("40.00");
        BigDecimal maxRate = new BigDecimal("50.00");
        when(orderEmployeeService.getByHourlyrateBetween(minRate, maxRate)).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/hourly-rate/between")
                .param("minHourlyrate", minRate.toString())
                .param("maxHourlyrate", maxRate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY PLANNED HOURS RANGE TESTS =====

    @Test
    void getByPlannedhoursGreaterThanShouldReturnList() throws Exception {
        when(orderEmployeeService.getByPlannedhoursGreaterThan(PLANNED_HOURS_1)).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/planned-hours/greater-than")
                .param("plannedhours", PLANNED_HOURS_1.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByPlannedhoursLessThanShouldReturnList() throws Exception {
        when(orderEmployeeService.getByPlannedhoursLessThan(PLANNED_HOURS_2)).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/planned-hours/less-than")
                .param("plannedhours", PLANNED_HOURS_2.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByPlannedhoursBetweenShouldReturnList() throws Exception {
        BigDecimal minHours = new BigDecimal("150.00");
        BigDecimal maxHours = new BigDecimal("170.00");
        when(orderEmployeeService.getByPlannedhoursBetween(minHours, maxHours)).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/planned-hours/between")
                .param("minPlannedhours", minHours.toString())
                .param("maxPlannedhours", maxHours.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS WITH MINIMUM RATE AND HOURS =====

    @Test
    void getWithMinimumRateAndHoursShouldReturnList() throws Exception {
        BigDecimal minRate = new BigDecimal("40.00");
        BigDecimal minHours = new BigDecimal("150.00");
        when(orderEmployeeService.getWithMinimumRateAndHours(minRate, minHours)).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/minimum-rate-hours")
                .param("minRate", minRate.toString())
                .param("minHours", minHours.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY QUALIFICATION OR TITLE CONTAINING KEYWORD =====

    @Test
    void getByQualificationOrTitleContainingShouldReturnList() throws Exception {
        when(orderEmployeeService.getByQualificationOrTitleContaining(KEYWORD)).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/search/" + KEYWORD))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== ORDERING OPERATIONS TESTS =====

    @Test
    void getAllOrderByHourlyrateAscShouldReturnList() throws Exception {
        when(orderEmployeeService.getAllOrderByHourlyrateAsc()).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/ordered/hourly-rate-asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getAllOrderByHourlyrateDescShouldReturnList() throws Exception {
        when(orderEmployeeService.getAllOrderByHourlyrateDesc()).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/ordered/hourly-rate-desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getAllOrderByPlannedhoursAscShouldReturnList() throws Exception {
        when(orderEmployeeService.getAllOrderByPlannedhoursAsc()).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/ordered/planned-hours-asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getAllOrderByPlannedhoursDescShouldReturnList() throws Exception {
        when(orderEmployeeService.getAllOrderByPlannedhoursDesc()).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/ordered/planned-hours-desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getAllOrderByQualificationkmuiAscShouldReturnList() throws Exception {
        when(orderEmployeeService.getAllOrderByQualificationkmuiAsc()).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/ordered/qualification-kmui-asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getAllOrderByTitleAscShouldReturnList() throws Exception {
        when(orderEmployeeService.getAllOrderByTitleAsc()).thenReturn(List.of(orderEmployee1));

        mockMvc.perform(get(baseUri + "/ordered/title-asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== CALCULATION OPERATIONS TESTS =====

    @Test
    void calculateTotalCostByOrderShouldReturnBigDecimal() throws Exception {
        BigDecimal totalCost = new BigDecimal("7280.00"); // 45.50 * 160.00
        when(orderEmployeeService.calculateTotalCostByOrder(ORDER_ID)).thenReturn(totalCost);

        mockMvc.perform(get(baseUri + pathOrder + ORDER_ID + "/total-cost"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(7280.0));
    }

    @Test
    void calculateTotalCostByEmployeeShouldReturnBigDecimal() throws Exception {
        BigDecimal totalCost = new BigDecimal("7280.00");
        when(orderEmployeeService.calculateTotalCostByEmployee(EMPLOYEE_ID)).thenReturn(totalCost);

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID + "/total-cost"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(7280.0));
    }

    // ===== CHECK EXISTENCE OPERATIONS TESTS =====

    @Test
    void existsByEmployeeIdAndOrderIdShouldReturnBoolean() throws Exception {
        when(orderEmployeeService.existsByEmployeeIdAndOrderId(EMPLOYEE_ID, ORDER_ID)).thenReturn(true);

        mockMvc.perform(get(baseUri + "/exists/employee/" + EMPLOYEE_ID + pathOrder + ORDER_ID))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void existsByOrderIdShouldReturnBoolean() throws Exception {
        when(orderEmployeeService.existsByOrderId(ORDER_ID)).thenReturn(true);

        mockMvc.perform(get(baseUri + "/exists/order/" + ORDER_ID))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void existsByEmployeeIdShouldReturnBoolean() throws Exception {
        when(orderEmployeeService.existsByEmployeeId(EMPLOYEE_ID)).thenReturn(true);

        mockMvc.perform(get(baseUri + "/exists/employee/" + EMPLOYEE_ID))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void existsByQualificationFZIdShouldReturnBoolean() throws Exception {
        when(orderEmployeeService.existsByQualificationFZId(QUALIFICATION_FZ_ID)).thenReturn(true);

        mockMvc.perform(get(baseUri + "/exists/qualification-fz/" + QUALIFICATION_FZ_ID))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    // ===== COUNT OPERATIONS TESTS =====

    @Test
    void countByOrderShouldReturnLong() throws Exception {
        Long count = 5L;
        when(orderEmployeeService.countByOrder(ORDER_ID)).thenReturn(count);

        mockMvc.perform(get(baseUri + "/count/order/" + ORDER_ID))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void countByEmployeeShouldReturnLong() throws Exception {
        Long count = 3L;
        when(orderEmployeeService.countByEmployee(EMPLOYEE_ID)).thenReturn(count);

        mockMvc.perform(get(baseUri + "/count/employee/" + EMPLOYEE_ID))
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }

    // ===== VALIDATION TESTS =====

    @Test
    void validateOrderEmployeeShouldReturnBoolean() throws Exception {
        when(orderEmployeeService.validateOrderEmployee(any(OrderEmployee.class))).thenReturn(true);

        mockMvc.perform(post(baseUri + "/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderEmployee1)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}