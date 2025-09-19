package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.VatRate;
import com.iws_manager.iws_manager_api.models.Vat;
import com.iws_manager.iws_manager_api.services.interfaces.VatRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VatRateControllerTest {

    @Mock
    private VatRateService vatRateService;

    @InjectMocks
    private VatRateController vatRateController;

    private VatRate testVatRate;
    private Vat testVat = new Vat();;

    @BeforeEach
    void setUp() {
        testVat.setId(1L);
        
        testVatRate = new VatRate();
        testVatRate.setId(1L);
        testVatRate.setFromdate(LocalDate.of(2024, 1, 1));
        testVatRate.setRate(new BigDecimal("19.00"));
        testVatRate.setVat(testVat);
    }

    @Test
    void createVatRateShouldReturnCreated() {
        when(vatRateService.create(any(VatRate.class))).thenReturn(testVatRate);

        ResponseEntity<?> response = vatRateController.createVatRate(testVatRate);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testVatRate, response.getBody());
        verify(vatRateService, times(1)).create(testVatRate);
    }

    @Test
    void getVatRateByIdShouldReturnVatRate() {
        when(vatRateService.findById(1L)).thenReturn(Optional.of(testVatRate));

        ResponseEntity<VatRate> response = vatRateController.getVatRateById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testVatRate, response.getBody());
    }

    @Test
    void getVatRateByIdShouldReturnNotFound() {
        when(vatRateService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<VatRate> response = vatRateController.getVatRateById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllVatRatesShouldReturnAllVatRates() {
        List<VatRate> vatRates = Arrays.asList(testVatRate, new VatRate());
        when(vatRateService.findAll()).thenReturn(vatRates);

        ResponseEntity<List<VatRate>> response = vatRateController.getAllVatRates();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void updateVatRateShouldReturnUpdatedVatRate() {
        VatRate updatedDetails = new VatRate();
        updatedDetails.setRate(new BigDecimal("21.00"));
        updatedDetails.setFromdate(LocalDate.of(2024, 6, 1));
        
        when(vatRateService.update(1L, updatedDetails)).thenReturn(testVatRate);

        ResponseEntity<VatRate> response = vatRateController.updateVatRate(1L, updatedDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testVatRate, response.getBody());
    }

    @Test
    void updateVatRateShouldReturnNotFound() {
        VatRate updatedDetails = new VatRate();
        when(vatRateService.update(1L, updatedDetails)).thenThrow(new RuntimeException());

        ResponseEntity<VatRate> response = vatRateController.updateVatRate(1L, updatedDetails);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteVatRateShouldReturnNoContent() {
        doNothing().when(vatRateService).delete(1L);

        ResponseEntity<Void> response = vatRateController.deleteVatRate(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(vatRateService, times(1)).delete(1L);
    }

    @Test
    void getVatRatesByVatIdShouldReturnVatRates() {
        List<VatRate> vatRates = Arrays.asList(testVatRate);
        when(vatRateService.getByVatId(1L)).thenReturn(vatRates);

        ResponseEntity<List<VatRate>> response = vatRateController.getVatRatesByVatId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(vatRates, response.getBody());
        verify(vatRateService, times(1)).getByVatId(1L);
    }

    @Test
    void getVatRatesByVatIdShouldReturnBadRequest() {
        when(vatRateService.getByVatId(-1L)).thenThrow(new IllegalArgumentException());

        ResponseEntity<List<VatRate>> response = vatRateController.getVatRatesByVatId(-1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(vatRateService, times(1)).getByVatId(-1L);
    }

    @Test
    void getAllVatRatesShouldReturnEmptyList() {
        when(vatRateService.findAll()).thenReturn(Arrays.asList());

        ResponseEntity<List<VatRate>> response = vatRateController.getAllVatRates();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void getVatRatesByVatIdShouldReturnEmptyList() {
        when(vatRateService.getByVatId(1L)).thenReturn(Arrays.asList());

        ResponseEntity<List<VatRate>> response = vatRateController.getVatRatesByVatId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void constructorShouldInitializeServiceDependency() {
        assertNotNull(vatRateController);
        assertNotNull(vatRateService);
    }
}