package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.InvoiceType;
import com.iws_manager.iws_manager_api.services.interfaces.InvoiceTypeService;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class InvoiceTypeControllerTest {

    // Constants for reusable values
    private static final String BASE_URL = "/api/v1/invoice-types";
    private static final String INVOICE_TYPE_NAME = "Direct Billing";
    private static final String NAME_JSON_PATH = "$.name";
    private static final String ERROR_JSON_PATH = "$.error";
    private static final String ID = "/{id}";
    private static final long VALID_ID = 1L;
    private static final long INVALID_ID = 99L;

    private MockMvc mockMvc;

    @Mock
    private InvoiceTypeService invoiceTypeService;

    @InjectMocks
    private InvoiceTypeController invoiceTypeController;

    private InvoiceType validInvoiceType;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(invoiceTypeController).build();
        validInvoiceType = createTestInvoiceType(VALID_ID, INVOICE_TYPE_NAME);
    }

    private InvoiceType createTestInvoiceType(Long id, String name) {
        InvoiceType invoiceType = new InvoiceType();
        invoiceType.setId(id);
        invoiceType.setName(name);
        return invoiceType;
    }

    private String buildInvoiceTypeJson(String name) {
        return String.format("""
            {
                "name": "%s"
            }
            """, 
            name != null ? name : "");
    }

    // ------------------- CREATE TESTS -------------------
    @Test
    void createInvoiceTypeShouldReturnCreatedWhenValidInput() throws Exception {
        when(invoiceTypeService.create(any(InvoiceType.class))).thenReturn(validInvoiceType);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildInvoiceTypeJson(INVOICE_TYPE_NAME)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(NAME_JSON_PATH).value(INVOICE_TYPE_NAME));
    }

    @Test
    void createInvoiceTypeShouldReturnBadRequestWhenMissingName() throws Exception {
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildInvoiceTypeJson(null)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ERROR_JSON_PATH).value("Name is required"));
    }

    @Test
    void createInvoiceTypeShouldReturnBadRequestWhenEmptyName() throws Exception {
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildInvoiceTypeJson("   ")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ERROR_JSON_PATH).value("Name is required"));
    }

    // ------------------- GET BY ID TESTS -------------------
    @Test
    void getInvoiceTypeByIdShouldReturnInvoiceTypeWhenValidId() throws Exception {
        when(invoiceTypeService.findById(VALID_ID)).thenReturn(Optional.of(validInvoiceType));

        mockMvc.perform(get(BASE_URL + ID, VALID_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(NAME_JSON_PATH).value(INVOICE_TYPE_NAME));
    }

    @Test
    void getInvoiceTypeByIdShouldReturnNotFoundWhenInvalidId() throws Exception {
        when(invoiceTypeService.findById(INVALID_ID)).thenReturn(Optional.empty());

        mockMvc.perform(get(BASE_URL + ID, INVALID_ID))
                .andExpect(status().isNotFound());
    }

    // ------------------- GET ALL TESTS -------------------
    @Test
    void getAllInvoiceTypesShouldReturnAllInvoiceTypes() throws Exception {
        InvoiceType invoiceType2 = createTestInvoiceType(2L, "Network Billing");
        when(invoiceTypeService.findAll()).thenReturn(Arrays.asList(validInvoiceType, invoiceType2));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0]" + NAME_JSON_PATH.substring(1)).value(INVOICE_TYPE_NAME))
                .andExpect(jsonPath("$[1]" + NAME_JSON_PATH.substring(1)).value("Network Billing"));
    }

    @Test
    void getAllInvoiceTypesShouldReturnEmptyListWhenNoInvoiceTypes() throws Exception {
        when(invoiceTypeService.findAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // ------------------- UPDATE TESTS -------------------
    @Test
    void updateInvoiceTypeShouldReturnUpdatedInvoiceTypeWhenValidInput() throws Exception {
        when(invoiceTypeService.update(eq(VALID_ID), any(InvoiceType.class))).thenReturn(validInvoiceType);

        mockMvc.perform(put(BASE_URL + ID, VALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildInvoiceTypeJson("Updated Billing")))
                .andExpect(status().isOk())
                .andExpect(jsonPath(NAME_JSON_PATH).value(INVOICE_TYPE_NAME));
    }

    @Test
    void updateInvoiceTypeShouldReturnNotFoundWhenInvalidId() throws Exception {
        when(invoiceTypeService.update(eq(INVALID_ID), any(InvoiceType.class)))
            .thenThrow(new RuntimeException("InvoiceType not found"));

        mockMvc.perform(put(BASE_URL + ID, INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildInvoiceTypeJson("Updated Billing")))
                .andExpect(status().isNotFound());
    }

    // ------------------- DELETE TESTS -------------------
    @Test
    void deleteInvoiceTypeShouldReturnNoContentWhenValidId() throws Exception {
        mockMvc.perform(delete(BASE_URL + ID, VALID_ID))
                .andExpect(status().isNoContent());

        verify(invoiceTypeService, times(1)).delete(VALID_ID);
    }

    @Test
    void getAllInvoiceTypesShouldReturnOrderedList() throws Exception {
        InvoiceType typeA = createTestInvoiceType(1L, "A Type");
        InvoiceType typeB = createTestInvoiceType(2L, "B Type");
        InvoiceType typeC = createTestInvoiceType(3L, "C Type");
        
        when(invoiceTypeService.findAll()).thenReturn(Arrays.asList(typeA, typeB, typeC));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value("A Type"))
                .andExpect(jsonPath("$[1].name").value("B Type"))
                .andExpect(jsonPath("$[2].name").value("C Type"));
    }
}