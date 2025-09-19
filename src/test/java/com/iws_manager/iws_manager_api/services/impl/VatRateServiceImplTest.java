package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.VatRate;
import com.iws_manager.iws_manager_api.models.Vat;
import com.iws_manager.iws_manager_api.repositories.VatRateRepository;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class VatRateServiceImplTest {

    @Mock
    private VatRateRepository vatRateRepository;

    @InjectMocks
    private VatRateServiceImpl vatRateService;

    private VatRate testVatRate;
    private Vat testVat;

    @BeforeEach
    public void setUp() {
        testVat = new Vat();
        testVat.setId(1L);
        
        testVatRate = new VatRate();
        testVatRate.setId(1L);
        testVatRate.setFromdate(LocalDate.of(2024, 1, 1));
        testVatRate.setRate(new BigDecimal("19.00"));
        testVatRate.setVat(testVat);
    }

    // Test para create()
    @Test
    public void createShouldSaveAndReturnVatRate() {
        when(vatRateRepository.save(any(VatRate.class))).thenReturn(testVatRate);

        VatRate result = vatRateService.create(testVatRate);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(new BigDecimal("19.00"), result.getRate());
        verify(vatRateRepository, times(1)).save(testVatRate);
    }

    @Test
    public void createShouldThrowExceptionWhenVatRateIsNull() {
        assertThrows(IllegalArgumentException.class, () -> vatRateService.create(null));
        verify(vatRateRepository, never()).save(any());
    }

    // Test para findById()
    @Test
    public void findByIdShouldReturnVatRateWhenExists() {
        when(vatRateRepository.findById(1L)).thenReturn(Optional.of(testVatRate));

        Optional<VatRate> result = vatRateService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("19.00"), result.get().getRate());
        verify(vatRateRepository, times(1)).findById(1L);
    }

    @Test
    public void findByIdShouldReturnEmptyWhenNotExists() {
        when(vatRateRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<VatRate> result = vatRateService.findById(1L);

        assertFalse(result.isPresent());
        verify(vatRateRepository, times(1)).findById(1L);
    }

    @Test
    public void findByIdShouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> vatRateService.findById(null));
        verify(vatRateRepository, never()).findById(any());
    }

    // Test para findAll()
    @Test
    public void findAllShouldReturnAllVatRatesOrderedByFromdate() {
        VatRate secondVatRate = new VatRate();
        secondVatRate.setId(2L);
        secondVatRate.setFromdate(LocalDate.of(2023, 1, 1));
        
        when(vatRateRepository.findAllByOrderByFromdateAsc())
            .thenReturn(List.of(secondVatRate, testVatRate));

        List<VatRate> result = vatRateService.findAll();

        assertEquals(2, result.size());
        verify(vatRateRepository, times(1)).findAllByOrderByFromdateAsc();
        verify(vatRateRepository, never()).findAll();
    }

    @Test
    public void findAllShouldReturnEmptyListWhenNoVatRates() {
        when(vatRateRepository.findAllByOrderByFromdateAsc()).thenReturn(List.of());

        List<VatRate> result = vatRateService.findAll();

        assertTrue(result.isEmpty());
        verify(vatRateRepository, times(1)).findAllByOrderByFromdateAsc();
    }

    // Test para update()
    @Test
    public void updateShouldUpdateVatRate() {
        VatRate updatedDetails = new VatRate();
        updatedDetails.setFromdate(LocalDate.of(2024, 6, 1));
        updatedDetails.setRate(new BigDecimal("21.00"));
        
        Vat newVat = new Vat();
        newVat.setId(2L);
        updatedDetails.setVat(newVat);

        when(vatRateRepository.findById(1L)).thenReturn(Optional.of(testVatRate));
        when(vatRateRepository.save(any(VatRate.class))).thenReturn(testVatRate);

        VatRate result = vatRateService.update(1L, updatedDetails);

        assertNotNull(result);
        assertEquals(LocalDate.of(2024, 6, 1), result.getFromdate());
        assertEquals(new BigDecimal("21.00"), result.getRate());
        assertEquals(newVat, result.getVat());
        verify(vatRateRepository, times(1)).save(testVatRate);
    }

    @Test
    public void updateShouldThrowExceptionWhenVatRateNotFound() {
        VatRate updatedDetails = new VatRate();
        when(vatRateRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> vatRateService.update(1L, updatedDetails));
        verify(vatRateRepository, never()).save(any());
    }

    @Test
    public void updateShouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> vatRateService.update(null, new VatRate()));
        verify(vatRateRepository, never()).findById(any());
    }

    @Test
    public void updateShouldThrowExceptionWhenDetailsIsNull() {
        assertThrows(IllegalArgumentException.class, () -> vatRateService.update(1L, null));
        verify(vatRateRepository, never()).findById(any());
    }

    @Test
    public void updateShouldThrowExceptionWhenBothParametersAreNull() {
        assertThrows(IllegalArgumentException.class, () -> vatRateService.update(null, null));
        verify(vatRateRepository, never()).findById(any());
    }

    // Test para delete()
    @Test
    public void deleteShouldDeleteVatRate() {
        when(vatRateRepository.existsById(1L)).thenReturn(true);
        
        vatRateService.delete(1L);
        
        verify(vatRateRepository, times(1)).existsById(1L);
        verify(vatRateRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteShouldThrowExceptionWhenVatRateNotExists() {
        when(vatRateRepository.existsById(1L)).thenReturn(false);
        
        assertThrows(EntityNotFoundException.class, () -> vatRateService.delete(1L));
        verify(vatRateRepository, times(1)).existsById(1L);
        verify(vatRateRepository, never()).deleteById(any());
    }

    @Test
    public void deleteShouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> vatRateService.delete(null));
        verify(vatRateRepository, never()).existsById(any());
        verify(vatRateRepository, never()).deleteById(any());
    }

    // Test para getByVatId()
    @Test
    public void getByVatIdShouldReturnVatRatesOrderedByFromdate() {
        when(vatRateRepository.findByVatIdOrderByFromdateAsc(1L))
            .thenReturn(List.of(testVatRate));

        List<VatRate> result = vatRateService.getByVatId(1L);

        assertEquals(1, result.size());
        verify(vatRateRepository, times(1)).findByVatIdOrderByFromdateAsc(1L);
    }

    @Test
    public void getByVatIdShouldReturnEmptyListWhenNoResults() {
        when(vatRateRepository.findByVatIdOrderByFromdateAsc(1L)).thenReturn(List.of());

        List<VatRate> result = vatRateService.getByVatId(1L);

        assertTrue(result.isEmpty());
        verify(vatRateRepository, times(1)).findByVatIdOrderByFromdateAsc(1L);
    }

    @Test
    public void getByVatIdShouldThrowExceptionWhenVatIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> vatRateService.getByVatId(null));
        verify(vatRateRepository, never()).findByVatIdOrderByFromdateAsc(any());
    }

    // Test para verificar que se usan los métodos ordenados del repositorio
    @Test
    public void shouldUseOrderedRepositoryMethods() {
        when(vatRateRepository.findAllByOrderByFromdateAsc()).thenReturn(List.of(testVatRate));
        when(vatRateRepository.findByVatIdOrderByFromdateAsc(1L)).thenReturn(List.of(testVatRate));

        vatRateService.findAll();
        vatRateService.getByVatId(1L);

        verify(vatRateRepository, times(1)).findAllByOrderByFromdateAsc();
        verify(vatRateRepository, times(1)).findByVatIdOrderByFromdateAsc(1L);
        verify(vatRateRepository, never()).findAll();
        verify(vatRateRepository, never()).findByVatId(any());
    }

    // Test para verificar el constructor con dependencias
    @Test
    public void shouldInitializeWithRepositoryDependency() {
        assertNotNull(vatRateService);
        assertNotNull(vatRateRepository);
    }

    // Test para verificar el comportamiento con valores null en campos
    @Test
    public void updateShouldHandleNullFieldsCorrectly() {
        VatRate updatedDetails = new VatRate();
        updatedDetails.setFromdate(null);
        updatedDetails.setRate(null);
        updatedDetails.setVat(null);

        when(vatRateRepository.findById(1L)).thenReturn(Optional.of(testVatRate));
        when(vatRateRepository.save(any(VatRate.class))).thenReturn(testVatRate);

        VatRate result = vatRateService.update(1L, updatedDetails);

        assertNotNull(result);
        assertNull(result.getFromdate());
        assertNull(result.getRate());
        assertNull(result.getVat());
        verify(vatRateRepository, times(1)).save(testVatRate);
    }
}