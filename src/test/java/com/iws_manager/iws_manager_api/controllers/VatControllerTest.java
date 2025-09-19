package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Vat;
import com.iws_manager.iws_manager_api.services.interfaces.VatService;
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
class VatControllerTest {

    // Constants for reusable values
    private static final String BASE_URL = "/api/v1/vats";
    private static final String VAT_LABEL = "Standard VAT";
    private static final String LABEL_JSON_PATH = "$.label";
    private static final String ERROR_JSON_PATH = "$.error";
    private static final String ID = "/{id}";
    private static final long VALID_ID = 1L;
    private static final long INVALID_ID = 99L;

    private MockMvc mockMvc;

    @Mock
    private VatService vatService;

    @InjectMocks
    private VatController vatController;

    private Vat validVat;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(vatController).build();
        validVat = createTestVat(VALID_ID, VAT_LABEL);
    }

    private Vat createTestVat(Long id, String label) {
        Vat vat = new Vat();
        vat.setId(id);
        vat.setLabel(label);
        return vat;
    }

    private String buildVatJson(String label) {
        return String.format("""
            {
                "label": "%s"
            }
            """, 
            label != null ? label : "");
    }

    // ------------------- CREATE TESTS -------------------
    @Test
    void createVatShouldReturnCreatedWhenValidInput() throws Exception {
        when(vatService.create(any(Vat.class))).thenReturn(validVat);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildVatJson(VAT_LABEL)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(LABEL_JSON_PATH).value(VAT_LABEL));
    }

    @Test
    void createVatShouldReturnBadRequestWhenMissingLabel() throws Exception {
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildVatJson(null)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ERROR_JSON_PATH).value("Label is required"));
    }

    @Test
    void createVatShouldReturnBadRequestWhenEmptyLabel() throws Exception {
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildVatJson("   ")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath(ERROR_JSON_PATH).value("Label is required"));
    }

    // ------------------- GET BY ID TESTS -------------------
    @Test
    void getVatByIdShouldReturnVatWhenValidId() throws Exception {
        when(vatService.findById(VALID_ID)).thenReturn(Optional.of(validVat));

        mockMvc.perform(get(BASE_URL + ID, VALID_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LABEL_JSON_PATH).value(VAT_LABEL));
    }

    @Test
    void getVatByIdShouldReturnNotFoundWhenInvalidId() throws Exception {
        when(vatService.findById(INVALID_ID)).thenReturn(Optional.empty());

        mockMvc.perform(get(BASE_URL + ID, INVALID_ID))
                .andExpect(status().isNotFound());
    }

    // ------------------- GET ALL TESTS -------------------
    @Test
    void getAllVatsShouldReturnAllVats() throws Exception {
        Vat vat2 = createTestVat(2L, "Reduced VAT");
        when(vatService.findAll()).thenReturn(Arrays.asList(validVat, vat2));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0]" + LABEL_JSON_PATH.substring(1)).value(VAT_LABEL))
                .andExpect(jsonPath("$[1]" + LABEL_JSON_PATH.substring(1)).value("Reduced VAT"));
    }

    @Test
    void getAllVatsShouldReturnEmptyListWhenNoVats() throws Exception {
        when(vatService.findAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    // ------------------- UPDATE TESTS -------------------
    @Test
    void updateVatShouldReturnUpdatedVatWhenValidInput() throws Exception {
        when(vatService.update(eq(VALID_ID), any(Vat.class))).thenReturn(validVat);

        mockMvc.perform(put(BASE_URL + ID, VALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildVatJson("Updated VAT")))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LABEL_JSON_PATH).value(VAT_LABEL));
    }

    @Test
    void updateVatShouldReturnNotFoundWhenInvalidId() throws Exception {
        when(vatService.update(eq(INVALID_ID), any(Vat.class)))
            .thenThrow(new RuntimeException("VAT not found"));

        mockMvc.perform(put(BASE_URL + ID, INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildVatJson("Updated VAT")))
                .andExpect(status().isNotFound());
    }

    // ------------------- DELETE TESTS -------------------
    @Test
    void deleteVatShouldReturnNoContentWhenValidId() throws Exception {
        mockMvc.perform(delete(BASE_URL + ID, VALID_ID))
                .andExpect(status().isNoContent());

        verify(vatService, times(1)).delete(VALID_ID);
    }
}