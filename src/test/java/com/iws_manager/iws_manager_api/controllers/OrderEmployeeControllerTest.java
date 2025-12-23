package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.dtos.orderemployee.OrderEmployeeRequestDTO;
import com.iws_manager.iws_manager_api.dtos.orderemployee.OrderEmployeeResponseDTO;
import com.iws_manager.iws_manager_api.dtos.shared.BasicReferenceDTO;
import com.iws_manager.iws_manager_api.dtos.shared.EmployeeBasicDTO;
import com.iws_manager.iws_manager_api.dtos.shared.OrderReferenceDTO;
import com.iws_manager.iws_manager_api.dtos.shared.QualificationFZReferenceDTO;
import com.iws_manager.iws_manager_api.services.interfaces.OrderEmployeeServiceV2;
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

    private String baseUri;
    private String pathEmployee;
    private String pathOrder;
    private String pathQualificationFz;
    private String pathOrdered;

    private static final String PATH_ID = "/1";

    private static final String JSON_ID = "$.id";
    private static final String JSON_QUALIFICATION_K_MUI = "$.qualificationkmui";
    private static final String JSON_TITLE = "$.title";
    private static final String JSON_HOURLY_RATE = "$.hourlyrate";
    private static final String JSON_PLANNED_HOURS = "$.plannedhours";
    private static final String QUALIFICATION_K_MUI_0 = "$[0].qualificationkmui";

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
    private static final Integer ORDER_EMPLOYEE_NO_1 = 1001;
    private static final Integer ORDER_EMPLOYEE_NO_2 = 1002;
    private static final Integer VERSION = 1;
    private static final Integer EMPLOYEE_NO = 10001;
    private static final Integer ORDER_NO = 20001;

    @Mock
    private OrderEmployeeServiceV2 orderEmployeeService;

    @InjectMocks
    private OrderEmployeeControllerV2 orderEmployeeController;

    private OrderEmployeeRequestDTO requestDTO1;
    private OrderEmployeeResponseDTO responseDTO1;
    private OrderEmployeeResponseDTO responseDTO2;

    @BeforeEach
    void setUp() {
        baseUri = getConfigurableProperty("api.base.uri", "/api/v2/order-employees");
        pathEmployee = getConfigurableProperty("api.path.employee", "/employee/");
        pathOrder = getConfigurableProperty("api.path.order", "/order/");
        pathQualificationFz = getConfigurableProperty("api.path.qualification-fz", "/qualification-fz/");
        pathOrdered = getConfigurableProperty("api.path.ordered", "/ordered");

        mockMvc = MockMvcBuilders.standaloneSetup(orderEmployeeController).build();

        BasicReferenceDTO employeeRef = new BasicReferenceDTO(EMPLOYEE_ID, VERSION);
        BasicReferenceDTO orderRef = new BasicReferenceDTO(ORDER_ID, VERSION);
        BasicReferenceDTO qualificationRef = new BasicReferenceDTO(QUALIFICATION_FZ_ID, VERSION);
        requestDTO1 = new OrderEmployeeRequestDTO(
            HOURLY_RATE_1,
            PLANNED_HOURS_1,
            QUALIFICATION_K_MUI_1,
            TITLE_1,
            ORDER_EMPLOYEE_NO_1,
            orderRef,
            qualificationRef,
            employeeRef
        );

        EmployeeBasicDTO employeeBasicDTO = new EmployeeBasicDTO(
            EMPLOYEE_ID,
            EMPLOYEE_NO,
            "John",
            "Doe",
            "John Doe (10001)",
            VERSION
        );

        OrderReferenceDTO orderReferenceDTO = new OrderReferenceDTO(
            ORDER_ID,
            "ACRO",
            "Order Label",
            ORDER_NO,
            "Order Title",
            null,
            VERSION
        );

        QualificationFZReferenceDTO qualificationRefDTO = new QualificationFZReferenceDTO(
            QUALIFICATION_FZ_ID,
            "Senior Developer"
        );
        responseDTO1 = new OrderEmployeeResponseDTO(
            1L,
            ORDER_EMPLOYEE_NO_1,
            HOURLY_RATE_1,
            PLANNED_HOURS_1,
            QUALIFICATION_K_MUI_1,
            TITLE_1,
            VERSION,
            employeeBasicDTO,
            orderReferenceDTO,
            qualificationRefDTO
        );

        responseDTO2 = new OrderEmployeeResponseDTO(
            2L,
            ORDER_EMPLOYEE_NO_2,
            HOURLY_RATE_2,
            PLANNED_HOURS_2,
            QUALIFICATION_K_MUI_2,
            TITLE_2,
            VERSION,
            employeeBasicDTO,
            orderReferenceDTO,
            qualificationRefDTO
        );
    }

    private String getConfigurableProperty(String key, String defaultValue) {
        return System.getProperty(key, System.getenv().getOrDefault(key, defaultValue));
    }

    // ===== BASIC CRUD OPERATIONS TESTS =====

    @Test
    void createShouldReturnCreated() throws Exception {
        when(orderEmployeeService.create(any(OrderEmployeeRequestDTO.class))).thenReturn(responseDTO1);

        mockMvc.perform(post(baseUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(JSON_ID).value(1L))
                .andExpect(jsonPath(JSON_QUALIFICATION_K_MUI).value(QUALIFICATION_K_MUI_1))
                .andExpect(jsonPath(JSON_TITLE).value(TITLE_1))
                .andExpect(jsonPath(JSON_HOURLY_RATE).value(45.50))
                .andExpect(jsonPath(JSON_PLANNED_HOURS).value(160.00));
    }

    @Test
    void getByIdShouldReturnOrderEmployee() throws Exception {
        when(orderEmployeeService.getById(1L)).thenReturn(responseDTO1);

        mockMvc.perform(get(baseUri + PATH_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID).value(1L))
                .andExpect(jsonPath(JSON_QUALIFICATION_K_MUI).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getAllShouldReturnList() throws Exception {
        when(orderEmployeeService.getAll()).thenReturn(Arrays.asList(responseDTO1, responseDTO2));

        mockMvc.perform(get(baseUri))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1))
                .andExpect(jsonPath("$[1].qualificationkmui").value(QUALIFICATION_K_MUI_2));
    }

    @Test
    void updateShouldReturnUpdated() throws Exception {
        OrderEmployeeRequestDTO updatedRequest = new OrderEmployeeRequestDTO(
            HOURLY_RATE_1,
            PLANNED_HOURS_1,
            UPDATED_QUALIFICATION_K_MUI,
            UPDATED_TITLE,
            ORDER_EMPLOYEE_NO_1,
            new BasicReferenceDTO(ORDER_ID, VERSION),
            new BasicReferenceDTO(QUALIFICATION_FZ_ID, VERSION),
            new BasicReferenceDTO(EMPLOYEE_ID, VERSION)
        );

        OrderEmployeeResponseDTO updatedResponse = new OrderEmployeeResponseDTO(
            1L,
            ORDER_EMPLOYEE_NO_1,
            HOURLY_RATE_1,
            PLANNED_HOURS_1,
            UPDATED_QUALIFICATION_K_MUI,
            UPDATED_TITLE,
            VERSION + 1,
            responseDTO1.employee(),
            responseDTO1.order(),
            responseDTO1.qualificationFZ()
        );

        when(orderEmployeeService.update(eq(1L), any(OrderEmployeeRequestDTO.class))).thenReturn(updatedResponse);

        mockMvc.perform(put(baseUri + PATH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_QUALIFICATION_K_MUI).value(UPDATED_QUALIFICATION_K_MUI))
                .andExpect(jsonPath(JSON_TITLE).value(UPDATED_TITLE));
    }

    @Test
    void partialUpdateShouldReturnUpdated() throws Exception {
        OrderEmployeeRequestDTO partialRequest = new OrderEmployeeRequestDTO(
            HOURLY_RATE_1,
            null, 
            null,
            null,
            null,
            null,
            null,
            null
        );

        OrderEmployeeResponseDTO updatedResponse = new OrderEmployeeResponseDTO(
            1L,
            ORDER_EMPLOYEE_NO_1,
            HOURLY_RATE_1,
            PLANNED_HOURS_1,
            QUALIFICATION_K_MUI_1,
            TITLE_1,
            VERSION + 1,
            responseDTO1.employee(),
            responseDTO1.order(),
            responseDTO1.qualificationFZ()
        );

        when(orderEmployeeService.partialUpdate(eq(1L), any(OrderEmployeeRequestDTO.class)))
            .thenReturn(updatedResponse);

        mockMvc.perform(patch(baseUri + PATH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(partialRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_HOURLY_RATE).value(45.50))
                .andExpect(jsonPath(JSON_PLANNED_HOURS).value(160.00));
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
        when(orderEmployeeService.getByEmployeeId(EMPLOYEE_ID)).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1))
                .andExpect(jsonPath("$[0].employee.id").value(EMPLOYEE_ID));
    }

    @Test
    void getByEmployeeIdOrderByIdAscShouldReturnList() throws Exception {
        when(orderEmployeeService.getByEmployeeIdOrderByIdAsc(EMPLOYEE_ID)).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID + pathOrdered))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY ORDER TESTS =====

    @Test
    void getByOrderIdShouldReturnList() throws Exception {
        when(orderEmployeeService.getByOrderId(ORDER_ID)).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + pathOrder + ORDER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1))
                .andExpect(jsonPath("$[0].order.id").value(ORDER_ID));
    }

    @Test
    void getByOrderIdOrderByIdAscShouldReturnList() throws Exception {
        when(orderEmployeeService.getByOrderIdOrderByIdAsc(ORDER_ID)).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + pathOrder + ORDER_ID + pathOrdered))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY QUALIFICATION FZ TESTS =====

    @Test
    void getByQualificationFZIdShouldReturnList() throws Exception {
        when(orderEmployeeService.getByQualificationFZId(QUALIFICATION_FZ_ID)).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + pathQualificationFz + QUALIFICATION_FZ_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1))
                .andExpect(jsonPath("$[0].qualificationFZ.id").value(QUALIFICATION_FZ_ID));
    }

    @Test
    void getByQualificationFZIdOrderByIdAscShouldReturnList() throws Exception {
        when(orderEmployeeService.getByQualificationFZIdOrderByIdAsc(QUALIFICATION_FZ_ID))
                .thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + pathQualificationFz + QUALIFICATION_FZ_ID + pathOrdered))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY QUALIFICATION K MUI TESTS =====

    @Test
    void getByQualificationkmuiShouldReturnList() throws Exception {
        when(orderEmployeeService.getByQualificationkmui(QUALIFICATION_K_MUI_1)).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + "/qualification-kmui/" + QUALIFICATION_K_MUI_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByQualificationkmuiContainingIgnoreCaseShouldReturnList() throws Exception {
        when(orderEmployeeService.getByQualificationkmuiContainingIgnoreCase(KEYWORD))
                .thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + "/qualification-kmui/contains/" + KEYWORD))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByQualificationkmuiOrderByIdAscShouldReturnList() throws Exception {
        when(orderEmployeeService.getByQualificationkmuiOrderByIdAsc(QUALIFICATION_K_MUI_1))
                .thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + "/qualification-kmui/" + QUALIFICATION_K_MUI_1 + pathOrdered))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY TITLE TESTS =====

    @Test
    void getByTitleShouldReturnList() throws Exception {
        when(orderEmployeeService.getByTitle(TITLE_1)).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + "/title/" + TITLE_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByTitleContainingIgnoreCaseShouldReturnList() throws Exception {
        when(orderEmployeeService.getByTitleContainingIgnoreCase(KEYWORD)).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + "/title/contains/" + KEYWORD))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByTitleOrderByIdAscShouldReturnList() throws Exception {
        when(orderEmployeeService.getByTitleOrderByIdAsc(TITLE_1)).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + "/title/" + TITLE_1 + pathOrdered))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS WITH COMBINED CRITERIA TESTS =====

    @Test
    void getByEmployeeIdAndOrderIdShouldReturnList() throws Exception {
        when(orderEmployeeService.getByEmployeeIdAndOrderId(EMPLOYEE_ID, ORDER_ID)).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID + pathOrder + ORDER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByOrderIdAndQualificationFZIdShouldReturnList() throws Exception {
        when(orderEmployeeService.getByOrderIdAndQualificationFZId(ORDER_ID, QUALIFICATION_FZ_ID))
                .thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + pathOrder + ORDER_ID + pathQualificationFz + QUALIFICATION_FZ_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByEmployeeIdAndQualificationFZIdShouldReturnList() throws Exception {
        when(orderEmployeeService.getByEmployeeIdAndQualificationFZId(EMPLOYEE_ID, QUALIFICATION_FZ_ID))
                .thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID + pathQualificationFz + QUALIFICATION_FZ_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByEmployeeOrderAndQualificationShouldReturnList() throws Exception {
        when(orderEmployeeService.getByEmployeeOrderAndQualification(EMPLOYEE_ID, ORDER_ID, QUALIFICATION_FZ_ID))
                .thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + pathEmployee + EMPLOYEE_ID + pathOrder + ORDER_ID + pathQualificationFz
                + QUALIFICATION_FZ_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY HOURLY RATE RANGE TESTS =====

    @Test
    void getByHourlyrateGreaterThanShouldReturnList() throws Exception {
        when(orderEmployeeService.getByHourlyrateGreaterThan(HOURLY_RATE_1)).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + "/hourly-rate/greater-than")
                .param("hourlyrate", HOURLY_RATE_1.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByHourlyrateLessThanShouldReturnList() throws Exception {
        when(orderEmployeeService.getByHourlyrateLessThan(HOURLY_RATE_2)).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + "/hourly-rate/less-than")
                .param("hourlyrate", HOURLY_RATE_2.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByHourlyrateBetweenShouldReturnList() throws Exception {
        BigDecimal minRate = new BigDecimal("40.00");
        BigDecimal maxRate = new BigDecimal("50.00");
        when(orderEmployeeService.getByHourlyrateBetween(minRate, maxRate)).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + "/hourly-rate/between")
                .param("minHourlyrate", minRate.toString())
                .param("maxHourlyrate", maxRate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY PLANNED HOURS RANGE TESTS =====

    @Test
    void getByPlannedhoursGreaterThanShouldReturnList() throws Exception {
        when(orderEmployeeService.getByPlannedhoursGreaterThan(PLANNED_HOURS_1)).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + "/planned-hours/greater-than")
                .param("plannedhours", PLANNED_HOURS_1.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByPlannedhoursLessThanShouldReturnList() throws Exception {
        when(orderEmployeeService.getByPlannedhoursLessThan(PLANNED_HOURS_2)).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + "/planned-hours/less-than")
                .param("plannedhours", PLANNED_HOURS_2.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getByPlannedhoursBetweenShouldReturnList() throws Exception {
        BigDecimal minHours = new BigDecimal("150.00");
        BigDecimal maxHours = new BigDecimal("170.00");
        when(orderEmployeeService.getByPlannedhoursBetween(minHours, maxHours)).thenReturn(List.of(responseDTO1));

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
        when(orderEmployeeService.getWithMinimumRateAndHours(minRate, minHours)).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + "/minimum-rate-hours")
                .param("minRate", minRate.toString())
                .param("minHours", minHours.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== GET OPERATIONS BY QUALIFICATION OR TITLE CONTAINING KEYWORD =====

    @Test
    void getByQualificationOrTitleContainingShouldReturnList() throws Exception {
        when(orderEmployeeService.getByQualificationOrTitleContaining(KEYWORD)).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + "/search/" + KEYWORD))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    // ===== ORDERING OPERATIONS TESTS =====

    @Test
    void getAllOrderByHourlyrateAscShouldReturnList() throws Exception {
        when(orderEmployeeService.getAllOrderByHourlyrateAsc()).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + "/ordered/hourly-rate-asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getAllOrderByHourlyrateDescShouldReturnList() throws Exception {
        when(orderEmployeeService.getAllOrderByHourlyrateDesc()).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + "/ordered/hourly-rate-desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getAllOrderByPlannedhoursAscShouldReturnList() throws Exception {
        when(orderEmployeeService.getAllOrderByPlannedhoursAsc()).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + "/ordered/planned-hours-asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getAllOrderByPlannedhoursDescShouldReturnList() throws Exception {
        when(orderEmployeeService.getAllOrderByPlannedhoursDesc()).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + "/ordered/planned-hours-desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getAllOrderByQualificationkmuiAscShouldReturnList() throws Exception {
        when(orderEmployeeService.getAllOrderByQualificationkmuiAsc()).thenReturn(List.of(responseDTO1));

        mockMvc.perform(get(baseUri + "/ordered/qualification-kmui-asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(QUALIFICATION_K_MUI_0).value(QUALIFICATION_K_MUI_1));
    }

    @Test
    void getAllOrderByTitleAscShouldReturnList() throws Exception {
        when(orderEmployeeService.getAllOrderByTitleAsc()).thenReturn(List.of(responseDTO1));

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

}