package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.Invoice;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    Invoice create(Invoice invoice);
    Optional<Invoice> findById(Long id);
    List<Invoice> findAll();
    Invoice update(Long id, Invoice invoiceDetails);
    void delete(Long id);

    List<Invoice> getAllByOrderByInvoiceNoAsc();
    List<Invoice> getAllByOrderByInvoiceDateAsc();
    List<Invoice> getAllByOrderByInvoiceTitleAsc();

    // PROPERTIES - Campos básicos
    List<Invoice> getByComment(String comment);
    List<Invoice> getByInvoiceNo(Integer invoiceNo);
    List<Invoice> getByInvoiceTitle(String invoiceTitle);
    List<Invoice> getByIsCancellation(Short isCancellation);
    List<Invoice> getByNote(String note);
    List<Invoice> getByTaxRate(BigDecimal taxRate);

    // PROPERTIES - Campos de monto
    List<Invoice> getByAmountGross(BigDecimal amountGross);
    List<Invoice> getByAmountNet(BigDecimal amountNet);
    List<Invoice> getByAmountOpen(BigDecimal amountOpen);
    List<Invoice> getByAmountPaid(BigDecimal amountPaid);
    List<Invoice> getByAmountTax(BigDecimal amountTax);

    // PROPERTIES - Campos de fecha
    List<Invoice> getByInvoiceDate(LocalDate invoiceDate);
    List<Invoice> getByPayDeadline(LocalDate payDeadline);
    List<Invoice> getByPaymentDate(LocalDate paymentDate);

    // PROPERTIES - Relaciones (IDs)
    List<Invoice> getByBillerId(Long billerId);
    List<Invoice> getByCancelledInvoiceId(Long cancelledInvoiceId);
    List<Invoice> getByCustomerId(Long customerId);
    List<Invoice> getByInvoiceTypeId(Long invoiceTypeId);
    List<Invoice> getByNetworkId(Long networkId);
    List<Invoice> getByOrderId(Long orderId);
    List<Invoice> getByPayConditionId(Long payConditionId);
    List<Invoice> getByVatId(Long vatId);

    // HELPERS - Rangos para montos
    List<Invoice> getByAmountGrossLessThanEqual(BigDecimal amount);
    List<Invoice> getByAmountGrossGreaterThanEqual(BigDecimal amount);
    List<Invoice> getByAmountGrossBetween(BigDecimal startAmount, BigDecimal endAmount);

    List<Invoice> getByAmountNetLessThanEqual(BigDecimal amount);
    List<Invoice> getByAmountNetGreaterThanEqual(BigDecimal amount);
    List<Invoice> getByAmountNetBetween(BigDecimal startAmount, BigDecimal endAmount);

    List<Invoice> getByAmountOpenLessThanEqual(BigDecimal amount);
    List<Invoice> getByAmountOpenGreaterThanEqual(BigDecimal amount);
    List<Invoice> getByAmountOpenBetween(BigDecimal startAmount, BigDecimal endAmount);

    List<Invoice> getByAmountPaidLessThanEqual(BigDecimal amount);
    List<Invoice> getByAmountPaidGreaterThanEqual(BigDecimal amount);
    List<Invoice> getByAmountPaidBetween(BigDecimal startAmount, BigDecimal endAmount);

    List<Invoice> getByAmountTaxLessThanEqual(BigDecimal amount);
    List<Invoice> getByAmountTaxGreaterThanEqual(BigDecimal amount);
    List<Invoice> getByAmountTaxBetween(BigDecimal startAmount, BigDecimal endAmount);

    // HELPERS - Rangos para invoiceNo
    List<Invoice> getByInvoiceNoLessThanEqual(Integer invoiceNo);
    List<Invoice> getByInvoiceNoGreaterThanEqual(Integer invoiceNo);
    List<Invoice> getByInvoiceNoBetween(Integer startInvoiceNo, Integer endInvoiceNo);

    // HELPERS - Rangos para invoiceDate
    List<Invoice> getByInvoiceDateLessThanEqual(LocalDate invoiceDate);
    List<Invoice> getByInvoiceDateGreaterThanEqual(LocalDate invoiceDate);
    List<Invoice> getByInvoiceDateBetween(LocalDate startDate, LocalDate endDate);

    // HELPERS - Rangos para taxRate
    List<Invoice> getByTaxRateLessThanEqual(BigDecimal taxRate);
    List<Invoice> getByTaxRateGreaterThanEqual(BigDecimal taxRate);
    List<Invoice> getByTaxRateBetween(BigDecimal startTaxRate, BigDecimal endTaxRate);

    // HELPERS - Rangos para fechas
    List<Invoice> getByPayDeadlineLessThanEqual(LocalDate date);
    List<Invoice> getByPayDeadlineGreaterThanEqual(LocalDate date);
    List<Invoice> getByPayDeadlineBetween(LocalDate startDate, LocalDate endDate);

    List<Invoice> getByPaymentDateLessThanEqual(LocalDate date);
    List<Invoice> getByPaymentDateGreaterThanEqual(LocalDate date);
    List<Invoice> getByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

    // HELPERS - Búsqueda por texto (case insensitive)
    List<Invoice> getByCommentContainingIgnoreCase(String comment);
    List<Invoice> getByInvoiceTitleContainingIgnoreCase(String invoiceTitle);
    List<Invoice> getByNoteContainingIgnoreCase(String note);

    List<Invoice> getByCustomerIdOrderByInvoiceNoAsc(Long customerId);

}