package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.ContractOrderCommission;
import com.iws_manager.iws_manager_api.models.BasicContract;
import com.iws_manager.iws_manager_api.services.interfaces.ContractOrderCommissionService;

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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ContractOrderCommissionControllerTest {

    // Base URI and Paths
    private static final String BASE_URI = "/api/v1/contract-order-commissions";
    private static final String ID_PATH_1 = "/1";
    private static final String BY_COMMISSION_BETWEEN_PATH = "/by-commission-between";
    private static final String BY_FROM_ORDER_VALUE_BETWEEN_PATH = "/by-from-order-value-between";
    private static final String BY_MIN_COMMISSION_BETWEEN_PATH = "/by-min-commission-between";

    // Test Data Constants
    private static final BigDecimal COMMISSION_10_50 = new BigDecimal("10.50");
    private static final BigDecimal COMMISSION_15_75 = new BigDecimal("15.75");
    private static final BigDecimal FROM_ORDER_VALUE_1000 = new BigDecimal("1000.00");
    private static final BigDecimal FROM_ORDER_VALUE_2000 = new BigDecimal("2000.00");
    private static final BigDecimal MIN_COMMISSION_50 = new BigDecimal("50.00");
    private static final BigDecimal MIN_COMMISSION_75 = new BigDecimal("75.00");
    private static final String SCOMMISSION_10_50 = "10.50";
    private static final String SCOMMISSION_15_75 = "15.75";
    private static final String MAX_COMM = "maxCommission";
    private static final String MIN_COMM = "minCommission";
    private static final String JSON_COMMISSION_PATH = "$.commission";
    private static final String JSON_FROM_ORDER_VALUE_PATH = "$.fromOrderValue";
    private static final String JSON_MIN_COMMISSION_PATH = "$.minCommission";
    private static final String JSON_COMMISSION_PATH_0 = "$[0].commission";
    private static final String JSON_ID_PATH_0 = "$[0].id";
    private static final String JSON_ID_PATH = "$.id";
    private static final String JSON_ERROR_PATH = "$.error";
    private static final String JSON_MESSAGE_PATH = "$.message";
    private static final String ERROR_MESSAGE_RANGE = "MinCommission must be less than or equal to MaxCommission";

    // Test IDs
    private static final Long COMMISSION_ID_1 = 1L;
    private static final Long COMMISSION_ID_2 = 2L;
    private static final Long BASIC_CONTRACT_ID_1 = 10L;
    private static final Long NON_EXISTENT_ID = 999L;

    private MockMvc mockMvc;

    @Mock
    private ContractOrderCommissionService contractOrderCommissionService;

    @InjectMocks
    private ContractOrderCommissionController contractOrderCommissionController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ContractOrderCommission commission1;
    private ContractOrderCommission commission2;
    private BasicContract basicContract = new BasicContract();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(contractOrderCommissionController).build();

        basicContract.setId(BASIC_CONTRACT_ID_1);

        commission1 = new ContractOrderCommission();
        commission1.setId(COMMISSION_ID_1);
        commission1.setCommission(COMMISSION_10_50);
        commission1.setFromOrderValue(FROM_ORDER_VALUE_1000);
        commission1.setMinCommission(MIN_COMMISSION_50);
        commission1.setBasicContract(basicContract);

        commission2 = new ContractOrderCommission();
        commission2.setId(COMMISSION_ID_2);
        commission2.setCommission(COMMISSION_15_75);
        commission2.setFromOrderValue(FROM_ORDER_VALUE_2000);
        commission2.setMinCommission(MIN_COMMISSION_75);
        commission2.setBasicContract(basicContract);
    }

    @Test
    void createContractOrderCommissionShouldReturnCreatedStatus() throws Exception {
        ContractOrderCommission inputCommission = new ContractOrderCommission();
        inputCommission.setCommission(COMMISSION_10_50);
        inputCommission.setFromOrderValue(FROM_ORDER_VALUE_1000);
        inputCommission.setMinCommission(MIN_COMMISSION_50);

        given(contractOrderCommissionService.create(any(ContractOrderCommission.class))).willReturn(commission1);

        mockMvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputCommission)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(JSON_ID_PATH).value(COMMISSION_ID_1))
                .andExpect(jsonPath(JSON_COMMISSION_PATH).value(10.50))
                .andExpect(jsonPath(JSON_FROM_ORDER_VALUE_PATH).value(1000.00))
                .andExpect(jsonPath(JSON_MIN_COMMISSION_PATH).value(50.00));
    }

    @Test
    void getContractOrderCommissionByIdShouldReturnCommission() throws Exception {
        given(contractOrderCommissionService.findById(COMMISSION_ID_1)).willReturn(Optional.of(commission1));

        mockMvc.perform(get(BASE_URI + ID_PATH_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID_PATH).value(COMMISSION_ID_1))
                .andExpect(jsonPath(JSON_COMMISSION_PATH).value(10.50))
                .andExpect(jsonPath(JSON_FROM_ORDER_VALUE_PATH).value(1000.00))
                .andExpect(jsonPath(JSON_MIN_COMMISSION_PATH).value(50.00));
    }

    @Test
    void getContractOrderCommissionByIdShouldReturnNotFound() throws Exception {
        given(contractOrderCommissionService.findById(NON_EXISTENT_ID)).willReturn(Optional.empty());

        mockMvc.perform(get(BASE_URI + "/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllContractOrderCommissionsShouldReturnAll() throws Exception {
        given(contractOrderCommissionService.findAll()).willReturn(Arrays.asList(commission1, commission2));

        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID_PATH_0).value(COMMISSION_ID_1))
                .andExpect(jsonPath(JSON_COMMISSION_PATH_0).value(10.50))
                .andExpect(jsonPath("$[1].id").value(COMMISSION_ID_2))
                .andExpect(jsonPath("$[1].commission").value(15.75));
    }

    @Test
    void updateContractOrderCommissionShouldReturnUpdatedCommission() throws Exception {
        ContractOrderCommission updated = new ContractOrderCommission();
        updated.setCommission(COMMISSION_15_75);
        updated.setFromOrderValue(FROM_ORDER_VALUE_2000);
        updated.setMinCommission(MIN_COMMISSION_75);

        given(contractOrderCommissionService.update(COMMISSION_ID_1, updated)).willReturn(updated);

        mockMvc.perform(put(BASE_URI + ID_PATH_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_COMMISSION_PATH).value(15.75))
                .andExpect(jsonPath(JSON_FROM_ORDER_VALUE_PATH).value(2000.00))
                .andExpect(jsonPath(JSON_MIN_COMMISSION_PATH).value(75.00));
    }

    @Test
    void deleteContractOrderCommissionShouldReturnNoContent() throws Exception {
        doNothing().when(contractOrderCommissionService).delete(COMMISSION_ID_1);

        mockMvc.perform(delete(BASE_URI + ID_PATH_1))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteContractOrderCommissionShouldReturnNotFound() throws Exception {
        doThrow(new RuntimeException("Not found")).when(contractOrderCommissionService).delete(NON_EXISTENT_ID);

        mockMvc.perform(delete(BASE_URI + "/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getByCommissionShouldReturnList() throws Exception {
        given(contractOrderCommissionService.getByCommission(COMMISSION_10_50))
            .willReturn(Collections.singletonList(commission1));

        mockMvc.perform(get(BASE_URI + "/by-commission/10.50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID_PATH_0).value(COMMISSION_ID_1))
                .andExpect(jsonPath(JSON_COMMISSION_PATH_0).value(10.50));
    }

    @Test
    void getByFromOrderValueShouldReturnList() throws Exception {
        given(contractOrderCommissionService.getByFromOrderValue(FROM_ORDER_VALUE_1000))
            .willReturn(Collections.singletonList(commission1));

        mockMvc.perform(get(BASE_URI + "/by-from-order-value/1000.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID_PATH_0).value(COMMISSION_ID_1))
                .andExpect(jsonPath("$[0].fromOrderValue").value(1000.00));
    }

    @Test
    void getByMinCommissionShouldReturnList() throws Exception {
        given(contractOrderCommissionService.getByMinCommission(MIN_COMMISSION_50))
            .willReturn(Collections.singletonList(commission1));

        mockMvc.perform(get(BASE_URI + "/by-min-commission/50.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID_PATH_0).value(COMMISSION_ID_1))
                .andExpect(jsonPath("$[0].minCommission").value(50.00));
    }

    @Test
    void getByBasicContractIdShouldReturnList() throws Exception {
        given(contractOrderCommissionService.getByBasicContractId(BASIC_CONTRACT_ID_1))
            .willReturn(Collections.singletonList(commission1));

        mockMvc.perform(get(BASE_URI + "/by-basic-contract/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID_PATH_0).value(COMMISSION_ID_1))
                .andExpect(jsonPath("$[0].basicContract.id").value(10));
    }

    @Test
    void getByCommissionGreaterThanEqualShouldReturnList() throws Exception {
        given(contractOrderCommissionService.getByCommissionGreaterThanEqual(COMMISSION_10_50))
            .willReturn(Collections.singletonList(commission1));

        mockMvc.perform(get(BASE_URI + "/by-commission-greater-than/10.50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID_PATH_0).value(COMMISSION_ID_1))
                .andExpect(jsonPath(JSON_COMMISSION_PATH_0).value(10.50));
    }

    @Test
    void getByCommissionLessThanEqualShouldReturnList() throws Exception {
        given(contractOrderCommissionService.getByCommissionLessThanEqual(COMMISSION_15_75))
            .willReturn(Collections.singletonList(commission1));

        mockMvc.perform(get(BASE_URI + "/by-commission-less-than/15.75"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID_PATH_0).value(COMMISSION_ID_1))
                .andExpect(jsonPath(JSON_COMMISSION_PATH_0).value(10.50));
    }

    @Test
    void getByCommissionBetweenShouldReturnList() throws Exception {
        given(contractOrderCommissionService.getByCommissionBetween(COMMISSION_10_50, COMMISSION_15_75))
            .willReturn(Collections.singletonList(commission1));

        mockMvc.perform(get(BASE_URI + BY_COMMISSION_BETWEEN_PATH)
                .param(MIN_COMM, SCOMMISSION_10_50)
                .param(MAX_COMM, SCOMMISSION_15_75))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID_PATH_0).value(COMMISSION_ID_1))
                .andExpect(jsonPath(JSON_COMMISSION_PATH_0).value(10.50));
    }

    @Test
    void getByCommissionBetweenInvalidRangeShouldReturnBadRequest() throws Exception {
        given(contractOrderCommissionService.getByCommissionBetween(COMMISSION_15_75, COMMISSION_10_50))
            .willThrow(new IllegalArgumentException(ERROR_MESSAGE_RANGE));

        mockMvc.perform(get(BASE_URI + BY_COMMISSION_BETWEEN_PATH)
                .param(MIN_COMM, SCOMMISSION_15_75)
                .param(MAX_COMM, SCOMMISSION_10_50))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(JSON_ERROR_PATH).exists())
                .andExpect(jsonPath(JSON_MESSAGE_PATH).value(ERROR_MESSAGE_RANGE));
    }

    @Test
    void getByFromOrderValueBetweenShouldReturnList() throws Exception {
        given(contractOrderCommissionService.getByFromOrderValueBetween(FROM_ORDER_VALUE_1000, FROM_ORDER_VALUE_2000))
            .willReturn(Collections.singletonList(commission1));

        mockMvc.perform(get(BASE_URI + BY_FROM_ORDER_VALUE_BETWEEN_PATH)
                .param("minFromOrderValue", "1000.00")
                .param("maxFromOrderValue", "2000.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID_PATH_0).value(COMMISSION_ID_1))
                .andExpect(jsonPath("$[0].fromOrderValue").value(1000.00));
    }

    @Test
    void getByMinCommissionBetweenShouldReturnList() throws Exception {
        given(contractOrderCommissionService.getByMinCommissionBetween(MIN_COMMISSION_50, MIN_COMMISSION_75))
            .willReturn(Collections.singletonList(commission1));

        mockMvc.perform(get(BASE_URI + BY_MIN_COMMISSION_BETWEEN_PATH)
                .param("minMinCommission", "50.00")
                .param("maxMinCommission", "75.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID_PATH_0).value(COMMISSION_ID_1))
                .andExpect(jsonPath("$[0].minCommission").value(50.00));
    }

    @Test
    void getByBasicContractIdAndCommissionGreaterThanEqualShouldReturnList() throws Exception {
        given(contractOrderCommissionService.getByBasicContractIdAndCommissionGreaterThanEqual(BASIC_CONTRACT_ID_1, COMMISSION_10_50))
            .willReturn(Collections.singletonList(commission1));

        mockMvc.perform(get(BASE_URI + "/by-basic-contract/10/commission-greater-than/10.50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID_PATH_0).value(COMMISSION_ID_1))
                .andExpect(jsonPath(JSON_COMMISSION_PATH_0).value(10.50));
    }

    @Test
    void getByBasicContractIdAndCommissionLessThanEqualShouldReturnList() throws Exception {
        given(contractOrderCommissionService.getByBasicContractIdAndCommissionLessThanEqual(BASIC_CONTRACT_ID_1, COMMISSION_15_75))
            .willReturn(Collections.singletonList(commission1));

        mockMvc.perform(get(BASE_URI + "/by-basic-contract/10/commission-less-than/15.75"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID_PATH_0).value(COMMISSION_ID_1))
                .andExpect(jsonPath(JSON_COMMISSION_PATH_0).value(10.50));
    }

    @Test
    void getAllContractOrderCommissionsWhenEmptyShouldReturnEmptyList() throws Exception {
        given(contractOrderCommissionService.findAll()).willReturn(Collections.emptyList());

        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getByNonExistentCommissionShouldReturnEmptyList() throws Exception {
        given(contractOrderCommissionService.getByCommission(new BigDecimal("99.99")))
            .willReturn(Collections.emptyList());

        mockMvc.perform(get(BASE_URI + "/by-commission/99.99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getByBasicContractIdAndCommissionBetweenShouldReturnList() throws Exception {
        given(contractOrderCommissionService.getByBasicContractIdAndCommissionBetween(BASIC_CONTRACT_ID_1, COMMISSION_10_50, COMMISSION_15_75))
            .willReturn(Collections.singletonList(commission1));

        mockMvc.perform(get(BASE_URI + "/by-basic-contract/10/commission-between")
                .param(MIN_COMM, SCOMMISSION_10_50)
                .param(MAX_COMM, SCOMMISSION_15_75))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID_PATH_0).value(COMMISSION_ID_1))
                .andExpect(jsonPath(JSON_COMMISSION_PATH_0).value(10.50));
    }
}