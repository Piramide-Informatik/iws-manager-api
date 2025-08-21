package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.ContractStatus;
import com.iws_manager.iws_manager_api.services.interfaces.ContractStatusService;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ContractStatusControllerTest {

    private static final String BASE_URI = "/api/v1/contractstatuses";
    private static final String ID_PATH_1 = "/1";
    private static final String ID_PATH_999 = "/999";
    private static final String BY_CHANCE_GREATER_THAN_PATH = "/by-chance-greater-than/";
    private static final String BY_CHANCE_LESS_THAN_PATH = "/by-chance-less-than/";
    private static final String BY_CHANCE_BETWEEN_PATH = "/by-chance-between/";

    private static final String STATUS_ACTIVE = "Active";
    private static final String STATUS_PENDING = "Pending";
    private static final String STATUS_CLOSED = "Closed";
    private static final String STATUS_REQUIRED_ERROR = "Status is required";
    private static final String CHANCE_NULL_ERROR = "Chance cannot be null";
    private static final String MIN_MAX_NULL_ERROR = "Min and max chance cannot be null";
    private static final String MIN_GREATER_THAN_MAX_ERROR = "Min chance cannot be greater than max chance";

    private static final BigDecimal CHANCE_25 = new BigDecimal("25.00");
    private static final BigDecimal CHANCE_50 = new BigDecimal("50.00");
    private static final BigDecimal CHANCE_75_50 = new BigDecimal("75.50");
    private static final BigDecimal CHANCE_80 = new BigDecimal("80.00");
    private static final BigDecimal CHANCE_100 = new BigDecimal("100.00");
    private static final BigDecimal CHANCE_30 = new BigDecimal("30.00");
    private static final BigDecimal CHANCE_10 = new BigDecimal("10.00");
    private static final BigDecimal CHANCE_20 = new BigDecimal("20.00");

    private static final Long CONTRACT_STATUS_ID_1 = 1L;
    private static final Long CONTRACT_STATUS_ID_2 = 2L;
    private static final Long CONTRACT_STATUS_ID_3 = 3L;
    private static final Long CONTRACT_STATUS_ID_NOT_FOUND = 999L;

    private MockMvc mockMvc;

    @Mock
    private ContractStatusService contractStatusService;

    @InjectMocks
    private ContractStatusController contractStatusController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ContractStatus contractStatus1;
    private ContractStatus contractStatus2;
    private ContractStatus contractStatus3;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(contractStatusController).build();

        contractStatus1 = new ContractStatus();
        contractStatus1.setId(CONTRACT_STATUS_ID_1);
        contractStatus1.setStatus(STATUS_ACTIVE);
        contractStatus1.setChance(CHANCE_75_50);

        contractStatus2 = new ContractStatus();
        contractStatus2.setId(CONTRACT_STATUS_ID_2);
        contractStatus2.setStatus(STATUS_PENDING);
        contractStatus2.setChance(CHANCE_25);

        contractStatus3 = new ContractStatus();
        contractStatus3.setId(CONTRACT_STATUS_ID_3);
        contractStatus3.setStatus(STATUS_CLOSED);
        contractStatus3.setChance(CHANCE_100);
    }

    // CREATE TESTS
    @Test
    void createContractStatusShouldReturnCreatedStatus() throws Exception {
        ContractStatus inputContractStatus = new ContractStatus();
        inputContractStatus.setStatus(STATUS_ACTIVE);
        inputContractStatus.setChance(CHANCE_75_50);

        given(contractStatusService.create(any(ContractStatus.class))).willReturn(contractStatus1);

        mockMvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputContractStatus)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(CONTRACT_STATUS_ID_1))
                .andExpect(jsonPath("$.status").value(STATUS_ACTIVE))
                .andExpect(jsonPath("$.chance").value(75.50));
    }

    @Test
    void createContractStatusWithEmptyStatusShouldReturnBadRequest() throws Exception {
        ContractStatus invalidContractStatus = new ContractStatus();
        invalidContractStatus.setStatus("");
        invalidContractStatus.setChance(CHANCE_75_50);

        mockMvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidContractStatus)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(STATUS_REQUIRED_ERROR));
    }

    @Test
    void createContractStatusWithNullStatusShouldReturnBadRequest() throws Exception {
        ContractStatus invalidContractStatus = new ContractStatus();
        invalidContractStatus.setStatus(null);
        invalidContractStatus.setChance(CHANCE_75_50);

        mockMvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidContractStatus)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(STATUS_REQUIRED_ERROR));
    }

    // GET BY ID TESTS
    @Test
    void getContractStatusByIdWithExistingIdShouldReturnOk() throws Exception {
        given(contractStatusService.findById(CONTRACT_STATUS_ID_1)).willReturn(Optional.of(contractStatus1));

        mockMvc.perform(get(BASE_URI + ID_PATH_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(CONTRACT_STATUS_ID_1))
                .andExpect(jsonPath("$.status").value(STATUS_ACTIVE))
                .andExpect(jsonPath("$.chance").value(75.50));
    }

    @Test
    void getContractStatusByIdWithNonExistingIdShouldReturnNotFound() throws Exception {
        given(contractStatusService.findById(CONTRACT_STATUS_ID_NOT_FOUND)).willReturn(Optional.empty());

        mockMvc.perform(get(BASE_URI + ID_PATH_999))
                .andExpect(status().isNotFound());
    }

    // GET ALL TESTS
    @Test
    void getAllContractStatusesShouldReturnOrderedList() throws Exception {
        List<ContractStatus> contractStatuses = Arrays.asList(contractStatus1, contractStatus2, contractStatus3);
        given(contractStatusService.findAll()).willReturn(contractStatuses);

        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id").value(CONTRACT_STATUS_ID_1))
                .andExpect(jsonPath("$[1].id").value(CONTRACT_STATUS_ID_2))
                .andExpect(jsonPath("$[2].id").value(CONTRACT_STATUS_ID_3));
    }

    @Test
    void getAllContractStatusesWithEmptyListShouldReturnOk() throws Exception {
        given(contractStatusService.findAll()).willReturn(Collections.emptyList());

        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    // UPDATE TESTS
    @Test
    void updateContractStatusWithExistingIdShouldReturnOk() throws Exception {
        ContractStatus updatedDetails = new ContractStatus();
        updatedDetails.setStatus("Updated Status");
        updatedDetails.setChance(CHANCE_80);

        given(contractStatusService.update(eq(CONTRACT_STATUS_ID_1), any(ContractStatus.class)))
                .willReturn(contractStatus1);

        mockMvc.perform(put(BASE_URI + ID_PATH_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(CONTRACT_STATUS_ID_1))
                .andExpect(jsonPath("$.status").value(STATUS_ACTIVE));
    }

    @Test
    void updateContractStatusWithNonExistingIdShouldReturnNotFound() throws Exception {
        ContractStatus updatedDetails = new ContractStatus();
        updatedDetails.setStatus("Updated Status");
        updatedDetails.setChance(CHANCE_80);

        given(contractStatusService.update(eq(CONTRACT_STATUS_ID_NOT_FOUND), any(ContractStatus.class)))
                .willThrow(new RuntimeException("ContractStatus not found"));

        mockMvc.perform(put(BASE_URI + ID_PATH_999)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDetails)))
                .andExpect(status().isNotFound());
    }

    // DELETE TESTS
    @Test
    void deleteContractStatusWithExistingIdShouldReturnNoContent() throws Exception {
        doNothing().when(contractStatusService).delete(CONTRACT_STATUS_ID_1);

        mockMvc.perform(delete(BASE_URI + ID_PATH_1))
                .andExpect(status().isNoContent());
    }

    // GET BY CHANCE GREATER THAN EQUAL TESTS
    @Test
    void getByChanceGreaterThanEqualShouldReturnFilteredList() throws Exception {
        List<ContractStatus> filteredList = Arrays.asList(contractStatus1, contractStatus3);
        given(contractStatusService.getByChanceGreaterThanEqual(CHANCE_50)).willReturn(filteredList);

        mockMvc.perform(get(BASE_URI + BY_CHANCE_GREATER_THAN_PATH + CHANCE_50))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(CONTRACT_STATUS_ID_1))
                .andExpect(jsonPath("$[1].id").value(CONTRACT_STATUS_ID_3));
    }

    @Test
    void getByChanceGreaterThanEqualWithInvalidChanceShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(BASE_URI + BY_CHANCE_GREATER_THAN_PATH + "invalid"))
                .andExpect(status().isBadRequest());
    }

    // GET BY CHANCE LESS THAN EQUAL TESTS
    @Test
    void getByChanceLessThanEqualShouldReturnFilteredList() throws Exception {
        List<ContractStatus> filteredList = Collections.singletonList(contractStatus2);
        given(contractStatusService.getByChanceLessThanEqual(CHANCE_30)).willReturn(filteredList);

        mockMvc.perform(get(BASE_URI + BY_CHANCE_LESS_THAN_PATH + CHANCE_30))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(CONTRACT_STATUS_ID_2));
    }

     @Test
    void getByChanceLessThanEqualWithInvalidChanceShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(BASE_URI + BY_CHANCE_LESS_THAN_PATH + "invalid"))
                .andExpect(status().isBadRequest());
    }

    // GET BY CHANCE BETWEEN TESTS
    @Test
    void getByChanceBetweenShouldReturnFilteredList() throws Exception {
        List<ContractStatus> filteredList = Collections.singletonList(contractStatus1);
        given(contractStatusService.getByChanceBetween(CHANCE_50, CHANCE_80)).willReturn(filteredList);

        mockMvc.perform(get(BASE_URI + BY_CHANCE_BETWEEN_PATH + CHANCE_50 + "/" + CHANCE_80))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(CONTRACT_STATUS_ID_1));
    }

    @Test
    void getByChanceBetweenWithInvalidMinChanceShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(BASE_URI + BY_CHANCE_BETWEEN_PATH + "invalid/80.00"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getByChanceBetweenWithInvalidMaxChanceShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(BASE_URI + BY_CHANCE_BETWEEN_PATH + "50.00/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getByChanceBetweenWithMinGreaterThanMaxShouldReturnBadRequest() throws Exception {
        given(contractStatusService.getByChanceBetween(CHANCE_80, CHANCE_50))
                .willThrow(new IllegalArgumentException(MIN_GREATER_THAN_MAX_ERROR));

        mockMvc.perform(get(BASE_URI + BY_CHANCE_BETWEEN_PATH + CHANCE_80 + "/" + CHANCE_50))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value(MIN_GREATER_THAN_MAX_ERROR));
    }

    @Test
    void getByChanceBetweenWithNoResultsShouldReturnEmptyList() throws Exception {
        given(contractStatusService.getByChanceBetween(CHANCE_10, CHANCE_20))
                .willReturn(Collections.emptyList());

        mockMvc.perform(get(BASE_URI + BY_CHANCE_BETWEEN_PATH + CHANCE_10 + "/" + CHANCE_20))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}