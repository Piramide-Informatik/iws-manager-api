package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iws_manager.iws_manager_api.models.Invoice;
import com.iws_manager.iws_manager_api.repositories.InvoiceRepository;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceImplTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    private Invoice testInvoice;

    @BeforeEach
    void setUp() {
        testInvoice = new Invoice();
        testInvoice.setId(1L);
        testInvoice.setInvoiceNo(1001);
        testInvoice.setInvoiceTitle("Test Invoice");
        testInvoice.setAmountGross(new BigDecimal("1000.00"));
        testInvoice.setAmountNet(new BigDecimal("800.00"));
        testInvoice.setComment("Test comment");
        testInvoice.setIsCancellation((short) 0);
        testInvoice.setTaxRate(new BigDecimal("19.00"));
    }

    @Test
    void testCreate() {
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(testInvoice);

        Invoice result = invoiceService.create(testInvoice);

        assertNotNull(result);
        assertEquals(testInvoice.getId(), result.getId());
        verify(invoiceRepository, times(1)).save(testInvoice);
    }

    @Test
    void testCreateNullInvoiceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> invoiceService.create(null));
    }

    @Test
    void testFindById() {
        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(testInvoice));

        Optional<Invoice> result = invoiceService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(testInvoice.getId(), result.get().getId());
        verify(invoiceRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(invoiceRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Invoice> result = invoiceService.findById(1L);

        assertFalse(result.isPresent());
        verify(invoiceRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNullIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> invoiceService.findById(null));
    }

    @Test
    void testFindAll() {
        List<Invoice> invoices = Arrays.asList(testInvoice);
        when(invoiceRepository.findAll()).thenReturn(invoices);

        List<Invoice> result = invoiceService.findAll();

        assertEquals(1, result.size());
        assertEquals(testInvoice.getId(), result.get(0).getId());
        verify(invoiceRepository, times(1)).findAll();
    }

    @Test
    void testUpdate() {
        Invoice updatedDetails = new Invoice();
        updatedDetails.setInvoiceNo(1002);
        updatedDetails.setInvoiceTitle("Updated Invoice");
        updatedDetails.setAmountGross(new BigDecimal("1200.00"));

        when(invoiceRepository.findById(1L)).thenReturn(Optional.of(testInvoice));
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(testInvoice);

        Invoice result = invoiceService.update(1L, updatedDetails);

        assertNotNull(result);
        verify(invoiceRepository, times(1)).findById(1L);
        verify(invoiceRepository, times(1)).save(testInvoice);
    }

    @Test
    void testUpdateNotFound() {
        Invoice updatedDetails = new Invoice();
        when(invoiceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> invoiceService.update(1L, updatedDetails));
        verify(invoiceRepository, times(1)).findById(1L);
        verify(invoiceRepository, never()).save(any());
    }

    @Test
    void testUpdateNullParametersThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> invoiceService.update(null, new Invoice()));
        assertThrows(IllegalArgumentException.class, () -> invoiceService.update(1L, null));
    }

    @Test
    void testDelete() {
        doNothing().when(invoiceRepository).deleteById(1L);

        invoiceService.delete(1L);

        verify(invoiceRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNullIdThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> invoiceService.delete(null));
    }

    @Test
    void testGetAllByOrderByInvoiceNoAsc() {
        List<Invoice> invoices = Arrays.asList(testInvoice);
        when(invoiceRepository.findAllByOrderByInvoiceNoAsc()).thenReturn(invoices);

        List<Invoice> result = invoiceService.getAllByOrderByInvoiceNoAsc();

        assertEquals(1, result.size());
        verify(invoiceRepository, times(1)).findAllByOrderByInvoiceNoAsc();
    }

    @Test
    void testGetByInvoiceNo() {
        List<Invoice> invoices = Arrays.asList(testInvoice);
        when(invoiceRepository.findByInvoiceNo(1001)).thenReturn(invoices);

        List<Invoice> result = invoiceService.getByInvoiceNo(1001);

        assertEquals(1, result.size());
        verify(invoiceRepository, times(1)).findByInvoiceNo(1001);
    }

    @Test
    void testGetByInvoiceTitle() {
        List<Invoice> invoices = Arrays.asList(testInvoice);
        when(invoiceRepository.findByInvoiceTitle("Test Invoice")).thenReturn(invoices);

        List<Invoice> result = invoiceService.getByInvoiceTitle("Test Invoice");

        assertEquals(1, result.size());
        verify(invoiceRepository, times(1)).findByInvoiceTitle("Test Invoice");
    }

    @Test
    void testGetByIsCancellation() {
        List<Invoice> invoices = Arrays.asList(testInvoice);
        when(invoiceRepository.findByIsCancellation((short) 0)).thenReturn(invoices);

        List<Invoice> result = invoiceService.getByIsCancellation((short) 0);

        assertEquals(1, result.size());
        verify(invoiceRepository, times(1)).findByIsCancellation((short) 0);
    }

    @Test
    void testGetByAmountGross() {
        List<Invoice> invoices = Arrays.asList(testInvoice);
        BigDecimal amount = new BigDecimal("1000.00");
        when(invoiceRepository.findByAmountGross(amount)).thenReturn(invoices);

        List<Invoice> result = invoiceService.getByAmountGross(amount);

        assertEquals(1, result.size());
        verify(invoiceRepository, times(1)).findByAmountGross(amount);
    }

    @Test
    void testGetByAmountNet() {
        List<Invoice> invoices = Arrays.asList(testInvoice);
        BigDecimal amount = new BigDecimal("800.00");
        when(invoiceRepository.findByAmountNet(amount)).thenReturn(invoices);

        List<Invoice> result = invoiceService.getByAmountNet(amount);

        assertEquals(1, result.size());
        verify(invoiceRepository, times(1)).findByAmountNet(amount);
    }

    @Test
    void testGetByCustomerId() {
        List<Invoice> invoices = Arrays.asList(testInvoice);
        when(invoiceRepository.findByCustomerId(1L)).thenReturn(invoices);

        List<Invoice> result = invoiceService.getByCustomerId(1L);

        assertEquals(1, result.size());
        verify(invoiceRepository, times(1)).findByCustomerId(1L);
    }

    @Test
    void testGetByAmountGrossBetween() {
        List<Invoice> invoices = Arrays.asList(testInvoice);
        BigDecimal start = new BigDecimal("500.00");
        BigDecimal end = new BigDecimal("1500.00");
        when(invoiceRepository.findByAmountGrossBetween(start, end)).thenReturn(invoices);

        List<Invoice> result = invoiceService.getByAmountGrossBetween(start, end);

        assertEquals(1, result.size());
        verify(invoiceRepository, times(1)).findByAmountGrossBetween(start, end);
    }

    @Test
    void testGetByInvoiceNoBetween() {
        List<Invoice> invoices = Arrays.asList(testInvoice);
        when(invoiceRepository.findByInvoiceNoBetween(1000, 2000)).thenReturn(invoices);

        List<Invoice> result = invoiceService.getByInvoiceNoBetween(1000, 2000);

        assertEquals(1, result.size());
        verify(invoiceRepository, times(1)).findByInvoiceNoBetween(1000, 2000);
    }

    @Test
    void testGetByPayDeadlineBetween() {
        List<Invoice> invoices = Arrays.asList(testInvoice);
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate end = LocalDate.of(2024, 12, 31);
        when(invoiceRepository.findByPayDeadlineBetween(start, end)).thenReturn(invoices);

        List<Invoice> result = invoiceService.getByPayDeadlineBetween(start, end);

        assertEquals(1, result.size());
        verify(invoiceRepository, times(1)).findByPayDeadlineBetween(start, end);
    }

    @Test
    void testGetByCommentContainingIgnoreCase() {
        List<Invoice> invoices = Arrays.asList(testInvoice);
        when(invoiceRepository.findByCommentContainingIgnoreCase("test")).thenReturn(invoices);

        List<Invoice> result = invoiceService.getByCommentContainingIgnoreCase("test");

        assertEquals(1, result.size());
        verify(invoiceRepository, times(1)).findByCommentContainingIgnoreCase("test");
    }

    @Test
    void testGetByInvoiceTitleContainingIgnoreCase() {
        List<Invoice> invoices = Arrays.asList(testInvoice);
        when(invoiceRepository.findByInvoiceTitleContainingIgnoreCase("invoice")).thenReturn(invoices);

        List<Invoice> result = invoiceService.getByInvoiceTitleContainingIgnoreCase("invoice");

        assertEquals(1, result.size());
        verify(invoiceRepository, times(1)).findByInvoiceTitleContainingIgnoreCase("invoice");
    }

    @Test
    void testGetByTaxRateBetween() {
        List<Invoice> invoices = Arrays.asList(testInvoice);
        BigDecimal start = new BigDecimal("10.00");
        BigDecimal end = new BigDecimal("20.00");
        when(invoiceRepository.findByTaxRateBetween(start, end)).thenReturn(invoices);

        List<Invoice> result = invoiceService.getByTaxRateBetween(start, end);

        assertEquals(1, result.size());
        verify(invoiceRepository, times(1)).findByTaxRateBetween(start, end);
    }
}