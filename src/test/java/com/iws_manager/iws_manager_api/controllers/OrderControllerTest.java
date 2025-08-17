package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.Order;
import com.iws_manager.iws_manager_api.services.interfaces.OrderService;

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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    // Base URI and Paths
    private static final String BASE_URI = "/api/v1/orders";
    private static final String ID_PATH = "/{id}";  // Compliant path is annotated
    private static final String BY_ORDER_VALUE_BETWEEN_PATH = "/by-order-value-between";
    private static final String BY_APPROVAL_DATE_RANGE_PATH = "/by-approval-date-range";
    private static final String WITHOUT_APPROVAL_DATE_PATH = "/without-approval-date";

    // Test Data Constants
    private static final String ORDER_TITLE = "Test Order";
    private static final String SECOND_ORDER_TITLE = "Second Order";
    private static final String UPDATED_ORDER_TITLE = "Updated Order";
    private static final String NON_EXISTENT_ORDER = "NON_EXISTENT";
    private static final BigDecimal ORDER_VALUE_1000 = new BigDecimal("1000.00");
    private static final BigDecimal ORDER_VALUE_2000 = new BigDecimal("2000.00");
    private static final String JSON_TITLE_PATH = "$.orderTitle";
    private static final String JSON_ID_PATH = "$.id";
    private static final String JSON_ERROR_PATH = "$.error";
    private static final String JSON_MESSAGE_PATH = "$.message";
    private static final String ERROR_MESSAGE_RANGE = "Start value cannot be greater than end value";

    // Test IDs
    private static final Long ORDER_ID_1 = 1L;
    private static final Long ORDER_ID_2 = 2L;
    private static final Long ORDER_ID_NOT_FOUND = 99L;
    private static final Long CUSTOMER_ID = 10L;
    private static final Long NON_EXISTENT_ID = 999L;

    // Test Dates
    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate TOMORROW = TODAY.plusDays(1);
    private static final LocalDate START_DATE = LocalDate.of(2023, 1, 1);
    private static final LocalDate END_DATE = LocalDate.of(2023, 12, 31);

    private static final String ID_0 = "$[0].id";

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Order order1;
    private Order order2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        order1 = new Order();
        order1.setId(ORDER_ID_1);
        order1.setOrderTitle(ORDER_TITLE);
        order1.setOrderValue(ORDER_VALUE_1000);
        order1.setOrderDate(TODAY);

        order2 = new Order();
        order2.setId(ORDER_ID_2);
        order2.setOrderTitle(SECOND_ORDER_TITLE);
        order2.setOrderValue(ORDER_VALUE_2000);
        order2.setOrderDate(TOMORROW);
    }

    @Test
    void createOrderShouldReturnCreatedStatus() throws Exception {
        Order inputOrder = new Order();
        inputOrder.setOrderTitle(ORDER_TITLE);
        inputOrder.setOrderValue(ORDER_VALUE_1000);

        given(orderService.create(any(Order.class))).willReturn(order1);

        mockMvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputOrder)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(JSON_ID_PATH).value(ORDER_ID_1))
                .andExpect(jsonPath(JSON_TITLE_PATH).value(ORDER_TITLE));
    }

    @Test
    void getOrderByIdShouldReturnOrder() throws Exception {
        given(orderService.findById(ORDER_ID_1)).willReturn(Optional.of(order1));

        mockMvc.perform(get(BASE_URI + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID_PATH).value(ORDER_ID_1))
                .andExpect(jsonPath(JSON_TITLE_PATH).value(ORDER_TITLE));
    }

    @Test
    void getOrderByIdShouldReturnNotFound() throws Exception {
        given(orderService.findById(ORDER_ID_NOT_FOUND)).willReturn(Optional.empty());

        mockMvc.perform(get(BASE_URI + ID_PATH, ORDER_ID_NOT_FOUND))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllOrdersShouldReturnAll() throws Exception {
        given(orderService.findAll()).willReturn(Arrays.asList(order1, order2));

        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_0).value(ORDER_ID_1))
                .andExpect(jsonPath("$[0].orderTitle").value(ORDER_TITLE))
                .andExpect(jsonPath("$[1].id").value(ORDER_ID_2))
                .andExpect(jsonPath("$[1].orderTitle").value(SECOND_ORDER_TITLE));
    }

    @Test
    void updateOrderShouldReturnUpdatedOrder() throws Exception {
        Order updated = new Order();
        updated.setOrderTitle(UPDATED_ORDER_TITLE);

        given(orderService.update(ORDER_ID_1, updated)).willReturn(updated);

        mockMvc.perform(put(BASE_URI + ID_PATH, ORDER_ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_TITLE_PATH).value(UPDATED_ORDER_TITLE));
    }

    @Test
    void deleteOrderShouldReturnNoContent() throws Exception {
        doNothing().when(orderService).delete(ORDER_ID_1);

        mockMvc.perform(delete(BASE_URI + ID_PATH, ORDER_ID_1))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteOrderShouldReturnNotFound() throws Exception {
        doThrow(new RuntimeException("Not found")).when(orderService).delete(NON_EXISTENT_ID);

        mockMvc.perform(delete(BASE_URI + ID_PATH, NON_EXISTENT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void getByCustomerIdShouldReturnList() throws Exception {
        given(orderService.getByCustomerId(CUSTOMER_ID)).willReturn(Collections.singletonList(order1));

        mockMvc.perform(get(BASE_URI + "/by-customer/" + CUSTOMER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_0).value(ORDER_ID_1));
    }

    @Test
    void getByOrderValueShouldReturnList() throws Exception {
        given(orderService.getByOrderValue(ORDER_VALUE_1000))
            .willReturn(Collections.singletonList(order1));

        mockMvc.perform(get(BASE_URI + "/by-ordervalue/" + ORDER_VALUE_1000))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderTitle").value(ORDER_TITLE));
    }

    @Test
    void getByOrderValueBetweenShouldReturnList() throws Exception {
        given(orderService.getByOrderValueBetween(ORDER_VALUE_1000, ORDER_VALUE_2000))
            .willReturn(Collections.singletonList(order1));

        mockMvc.perform(get(BASE_URI + BY_ORDER_VALUE_BETWEEN_PATH)
                .param("startValue", ORDER_VALUE_1000.toString())
                .param("endValue", ORDER_VALUE_2000.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_0).value(ORDER_ID_1));
    }

    @Test
    void getByOrderValueBetweenInvalidRangeShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(BASE_URI + BY_ORDER_VALUE_BETWEEN_PATH)
                .param("startValue", ORDER_VALUE_2000.toString())
                .param("endValue", ORDER_VALUE_1000.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(JSON_ERROR_PATH).exists())
                .andExpect(jsonPath(JSON_MESSAGE_PATH).value(ERROR_MESSAGE_RANGE));
    }

    @Test
    void getByApprovalDateBetweenShouldReturnList() throws Exception {
        given(orderService.getByApprovalDateBetween(START_DATE, END_DATE))
            .willReturn(Collections.singletonList(order1));

        mockMvc.perform(get(BASE_URI + BY_APPROVAL_DATE_RANGE_PATH)
                .param("start", START_DATE.toString())
                .param("end", END_DATE.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_0).value(ORDER_ID_1));
    }

    @Test
    void getByApprovalDateIsNullShouldReturnList() throws Exception {
        given(orderService.getByApprovalDateIsNull())
            .willReturn(Collections.singletonList(order1));

        mockMvc.perform(get(BASE_URI + WITHOUT_APPROVAL_DATE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_0).value(ORDER_ID_1));
    }

    @Test
    void getOrdersByCustomerSortedByTitleShouldReturnSorted() throws Exception {
        given(orderService.getByCustomerIdOrderByOrderTitleAsc(CUSTOMER_ID))
            .willReturn(Collections.singletonList(order1));

        mockMvc.perform(get(BASE_URI + "/customer/{customerId}/sort-by-title", CUSTOMER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_0).value(ORDER_ID_1));
    }

    @Test
    void getAllOrdersWhenEmptyShouldReturnEmptyList() throws Exception {
        given(orderService.findAll()).willReturn(Collections.emptyList());

        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getByNonExistentPropertyShouldReturnEmptyList() throws Exception {
        given(orderService.getByOrderTitle(NON_EXISTENT_ORDER))
            .willReturn(Collections.emptyList());

        mockMvc.perform(get(BASE_URI + "/by-ordertitle/" + NON_EXISTENT_ORDER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}