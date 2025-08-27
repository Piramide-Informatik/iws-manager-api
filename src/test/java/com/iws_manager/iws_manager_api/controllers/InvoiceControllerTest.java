package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.Invoice;
import com.iws_manager.iws_manager_api.services.interfaces.InvoiceService;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class InvoiceControllerTest {

    // Base URI and Paths
    private static final String BASE_URI = "/api/v1/invoices";
    private static final String ID_PATH_1 = "/1";
    private static final String BY_AMOUNT_GROSS_BETWEEN_PATH = "/by-amountgross-between";
    private static final String BY_INVOICE_NO_BETWEEN_PATH = "/by-invoiceno-between";
    private static final String BY_PAY_DEADLINE_RANGE_PATH = "/by-paydeadline-range";
    private static final String BY_TAX_RATE_BETWEEN_PATH = "/by-taxrate-between";

    // Test Data Constants
    private static final String INVOICE_TITLE = "Test Invoice";
    private static final String SECOND_INVOICE_TITLE = "Second Invoice";
    private static final String UPDATED_INVOICE_TITLE = "Updated Invoice";
    private static final String NON_EXISTENT_INVOICE = "NON_EXISTENT";
    private static final BigDecimal AMOUNT_1000 = new BigDecimal("1000.00");
    private static final BigDecimal AMOUNT_2000 = new BigDecimal("2000.00");
    private static final BigDecimal TAX_RATE_19 = new BigDecimal("19.00");
    private static final BigDecimal TAX_RATE_7 = new BigDecimal("7.00");
    private static final String JSON_TITLE_PATH = "$.invoiceTitle";
    private static final String JSON_ID_PATH = "$.id";
    private static final String JSON_ERROR_PATH = "$.error";
    private static final String JSON_MESSAGE_PATH = "$.message";
    private static final String ERROR_MESSAGE_RANGE = "Start amount cannot be greater than end amount";

    // Test IDs
    private static final Long INVOICE_ID_1 = 1L;
    private static final Long INVOICE_ID_2 = 2L;
    private static final Long INVOICE_ID_NOT_FOUND = 99L;
    private static final Long CUSTOMER_ID = 10L;
    private static final Long NON_EXISTENT_ID = 999L;

    // Test Dates
    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate TOMORROW = TODAY.plusDays(1);
    private static final LocalDate START_DATE = LocalDate.of(2024, 1, 1);
    private static final LocalDate END_DATE = LocalDate.of(2024, 12, 31);

    // Test Numbers
    private static final Integer INVOICE_NO_1001 = 1001;
    private static final Integer INVOICE_NO_1002 = 1002;
    private static final Integer INVOICE_NO_START = 1000;
    private static final Integer INVOICE_NO_END = 2000;

    private static final String ID_0 = "$[0].id";

    private MockMvc mockMvc;

    @Mock
    private InvoiceService invoiceService;

    @InjectMocks
    private InvoiceController invoiceController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Invoice invoice1;
    private Invoice invoice2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(invoiceController).build();

        invoice1 = new Invoice();
        invoice1.setId(INVOICE_ID_1);
        invoice1.setInvoiceTitle(INVOICE_TITLE);
        invoice1.setInvoiceNo(INVOICE_NO_1001);
        invoice1.setAmountGross(AMOUNT_1000);
        invoice1.setTaxRate(TAX_RATE_19);
        invoice1.setPayDeadline(TODAY);

        invoice2 = new Invoice();
        invoice2.setId(INVOICE_ID_2);
        invoice2.setInvoiceTitle(SECOND_INVOICE_TITLE);
        invoice2.setInvoiceNo(INVOICE_NO_1002);
        invoice2.setAmountGross(AMOUNT_2000);
        invoice2.setTaxRate(TAX_RATE_7);
        invoice2.setPayDeadline(TOMORROW);
    }

    @Test
    void createInvoiceShouldReturnCreatedStatus() throws Exception {
        Invoice inputInvoice = new Invoice();
        inputInvoice.setInvoiceTitle(INVOICE_TITLE);
        inputInvoice.setAmountGross(AMOUNT_1000);

        given(invoiceService.create(any(Invoice.class))).willReturn(invoice1);

        mockMvc.perform(post(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputInvoice)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(JSON_ID_PATH).value(INVOICE_ID_1))
                .andExpect(jsonPath(JSON_TITLE_PATH).value(INVOICE_TITLE));
    }

    @Test
    void getInvoiceByIdShouldReturnInvoice() throws Exception {
        given(invoiceService.findById(INVOICE_ID_1)).willReturn(Optional.of(invoice1));

        mockMvc.perform(get(BASE_URI + ID_PATH_1))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_ID_PATH).value(INVOICE_ID_1))
                .andExpect(jsonPath(JSON_TITLE_PATH).value(INVOICE_TITLE));
    }

    @Test
    void getInvoiceByIdShouldReturnNotFound() throws Exception {
        given(invoiceService.findById(INVOICE_ID_NOT_FOUND)).willReturn(Optional.empty());

        mockMvc.perform(get(BASE_URI + "/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllInvoicesShouldReturnAll() throws Exception {
        given(invoiceService.findAll()).willReturn(Arrays.asList(invoice1, invoice2));

        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_0).value(INVOICE_ID_1))
                .andExpect(jsonPath("$[0].invoiceTitle").value(INVOICE_TITLE))
                .andExpect(jsonPath("$[1].id").value(INVOICE_ID_2))
                .andExpect(jsonPath("$[1].invoiceTitle").value(SECOND_INVOICE_TITLE));
    }

    @Test
    void updateInvoiceShouldReturnUpdatedInvoice() throws Exception {
        Invoice updated = new Invoice();
        updated.setInvoiceTitle(UPDATED_INVOICE_TITLE);

        given(invoiceService.update(INVOICE_ID_1, updated)).willReturn(updated);

        mockMvc.perform(put(BASE_URI + ID_PATH_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_TITLE_PATH).value(UPDATED_INVOICE_TITLE));
    }

    @Test
    void deleteInvoiceShouldReturnNoContent() throws Exception {
        doNothing().when(invoiceService).delete(INVOICE_ID_1);

        mockMvc.perform(delete(BASE_URI + ID_PATH_1))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteInvoiceShouldReturnNotFound() throws Exception {
        doThrow(new RuntimeException("Not found")).when(invoiceService).delete(NON_EXISTENT_ID);

        mockMvc.perform(delete(BASE_URI + "/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getByCustomerIdShouldReturnList() throws Exception {
        given(invoiceService.getByCustomerId(CUSTOMER_ID)).willReturn(Collections.singletonList(invoice1));

        mockMvc.perform(get(BASE_URI + "/by-customer/" + CUSTOMER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_0).value(INVOICE_ID_1));
    }

    @Test
    void getByInvoiceNoShouldReturnList() throws Exception {
        given(invoiceService.getByInvoiceNo(INVOICE_NO_1001))
            .willReturn(Collections.singletonList(invoice1));

        mockMvc.perform(get(BASE_URI + "/by-invoiceno/" + INVOICE_NO_1001))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].invoiceTitle").value(INVOICE_TITLE));
    }

    @Test
    void getByAmountGrossShouldReturnList() throws Exception {
        given(invoiceService.getByAmountGross(AMOUNT_1000))
            .willReturn(Collections.singletonList(invoice1));

        mockMvc.perform(get(BASE_URI + "/by-amountgross/" + AMOUNT_1000))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_0).value(INVOICE_ID_1));
    }

    @Test
    void getByAmountGrossBetweenShouldReturnList() throws Exception {
        given(invoiceService.getByAmountGrossBetween(AMOUNT_1000, AMOUNT_2000))
            .willReturn(Collections.singletonList(invoice1));

        mockMvc.perform(get(BASE_URI + BY_AMOUNT_GROSS_BETWEEN_PATH)
                .param("startAmount", AMOUNT_1000.toString())
                .param("endAmount", AMOUNT_2000.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_0).value(INVOICE_ID_1));
    }

    @Test
    void getByAmountGrossBetweenInvalidRangeShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get(BASE_URI + BY_AMOUNT_GROSS_BETWEEN_PATH)
                .param("startAmount", AMOUNT_2000.toString())
                .param("endAmount", AMOUNT_1000.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(JSON_ERROR_PATH).exists())
                .andExpect(jsonPath(JSON_MESSAGE_PATH).value(ERROR_MESSAGE_RANGE));
    }

    @Test
    void getByInvoiceNoBetweenShouldReturnList() throws Exception {
        given(invoiceService.getByInvoiceNoBetween(INVOICE_NO_START, INVOICE_NO_END))
            .willReturn(Collections.singletonList(invoice1));

        mockMvc.perform(get(BASE_URI + BY_INVOICE_NO_BETWEEN_PATH)
                .param("startInvoiceNo", INVOICE_NO_START.toString())
                .param("endInvoiceNo", INVOICE_NO_END.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_0).value(INVOICE_ID_1));
    }

    @Test
    void getByPayDeadlineRangeShouldReturnList() throws Exception {
        given(invoiceService.getByPayDeadlineBetween(START_DATE, END_DATE))
            .willReturn(Collections.singletonList(invoice1));

        mockMvc.perform(get(BASE_URI + BY_PAY_DEADLINE_RANGE_PATH)
                .param("start", START_DATE.toString())
                .param("end", END_DATE.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_0).value(INVOICE_ID_1));
    }

    @Test
    void getByTaxRateBetweenShouldReturnList() throws Exception {
        given(invoiceService.getByTaxRateBetween(TAX_RATE_7, TAX_RATE_19))
            .willReturn(Collections.singletonList(invoice1));

        mockMvc.perform(get(BASE_URI + BY_TAX_RATE_BETWEEN_PATH)
                .param("startTaxRate", TAX_RATE_7.toString())
                .param("endTaxRate", TAX_RATE_19.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_0).value(INVOICE_ID_1));
    }

    @Test
    void searchByCommentShouldReturnList() throws Exception {
        given(invoiceService.getByCommentContainingIgnoreCase("test"))
            .willReturn(Collections.singletonList(invoice1));

        mockMvc.perform(get(BASE_URI + "/search-comment/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_0).value(INVOICE_ID_1));
    }

    @Test
    void getAllSortedByInvoiceNoShouldReturnSorted() throws Exception {
        given(invoiceService.getAllByOrderByInvoiceNoAsc())
            .willReturn(Collections.singletonList(invoice1));

        mockMvc.perform(get(BASE_URI + "/sort-by-invoiceno"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_0).value(INVOICE_ID_1));
    }

    @Test
    void getByIsCancellationShouldReturnList() throws Exception {
        given(invoiceService.getByIsCancellation((short) 0))
            .willReturn(Collections.singletonList(invoice1));

        mockMvc.perform(get(BASE_URI + "/by-cancellation/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_0).value(INVOICE_ID_1));
    }

    @Test
    void getAllInvoicesWhenEmptyShouldReturnEmptyList() throws Exception {
        given(invoiceService.findAll()).willReturn(Collections.emptyList());

        mockMvc.perform(get(BASE_URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getByNonExistentPropertyShouldReturnEmptyList() throws Exception {
        given(invoiceService.getByInvoiceTitle(NON_EXISTENT_INVOICE))
            .willReturn(Collections.emptyList());

        mockMvc.perform(get(BASE_URI + "/by-invoicetitle/" + NON_EXISTENT_INVOICE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getByBillerIdShouldReturnList() throws Exception {
        given(invoiceService.getByBillerId(1L))
            .willReturn(Collections.singletonList(invoice1));

        mockMvc.perform(get(BASE_URI + "/by-biller/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_0).value(INVOICE_ID_1));
    }

    @Test
    void getByInvoiceTypeIdShouldReturnList() throws Exception {
        given(invoiceService.getByInvoiceTypeId(1L))
            .willReturn(Collections.singletonList(invoice1));

        mockMvc.perform(get(BASE_URI + "/by-invoicetype/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(ID_0).value(INVOICE_ID_1));
    }
}