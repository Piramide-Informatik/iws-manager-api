package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findAllByOrderByInvoiceNoAsc();
    List<Invoice> findAllByOrderByInvoiceDateAsc();
    List<Invoice> findAllByOrderByInvoiceTitleAsc();

    // PROPERTIES - Campos básicos
    List<Invoice> findByComment(String comment);
    List<Invoice> findByInvoiceNo(Integer invoiceNo);
    List<Invoice> findByInvoiceTitle(String invoiceTitle);
    List<Invoice> findByIsCancellation(Short isCancellation);
    List<Invoice> findByNote(String note);
    List<Invoice> findByTaxRate(BigDecimal taxRate);

    // PROPERTIES - Campos de monto
    List<Invoice> findByAmountGross(BigDecimal amountGross);
    List<Invoice> findByAmountNet(BigDecimal amountNet);
    List<Invoice> findByAmountOpen(BigDecimal amountOpen);
    List<Invoice> findByAmountPaid(BigDecimal amountPaid);
    List<Invoice> findByAmountTax(BigDecimal amountTax);

    // PROPERTIES - Campos de fecha
    List<Invoice> findByInvoiceDate(Integer invoiceDate);
    List<Invoice> findByPayDeadline(LocalDate payDeadline);
    List<Invoice> findByPaymentDate(LocalDate paymentDate);

    // PROPERTIES - Relaciones (IDs)
    List<Invoice> findByBillerId(Long billerId);
    List<Invoice> findByCancelledInvoiceId(Long cancelledInvoiceId);
    List<Invoice> findByCustomerId(Long customerId);
    List<Invoice> findByInvoiceTypeId(Long invoiceTypeId);
    List<Invoice> findByNetworkId(Long networkId);
    List<Invoice> findByOrderId(Long orderId);
    List<Invoice> findByPayConditionId(Long payConditionId);
    List<Invoice> findByVatId(Long vatId);

    // HELPERS - Rangos para montos
    List<Invoice> findByAmountGrossLessThanEqual(BigDecimal amount);
    List<Invoice> findByAmountGrossGreaterThanEqual(BigDecimal amount);
    List<Invoice> findByAmountGrossBetween(BigDecimal startAmount, BigDecimal endAmount);

    List<Invoice> findByAmountNetLessThanEqual(BigDecimal amount);
    List<Invoice> findByAmountNetGreaterThanEqual(BigDecimal amount);
    List<Invoice> findByAmountNetBetween(BigDecimal startAmount, BigDecimal endAmount);

    List<Invoice> findByAmountOpenLessThanEqual(BigDecimal amount);
    List<Invoice> findByAmountOpenGreaterThanEqual(BigDecimal amount);
    List<Invoice> findByAmountOpenBetween(BigDecimal startAmount, BigDecimal endAmount);

    List<Invoice> findByAmountPaidLessThanEqual(BigDecimal amount);
    List<Invoice> findByAmountPaidGreaterThanEqual(BigDecimal amount);
    List<Invoice> findByAmountPaidBetween(BigDecimal startAmount, BigDecimal endAmount);

    List<Invoice> findByAmountTaxLessThanEqual(BigDecimal amount);
    List<Invoice> findByAmountTaxGreaterThanEqual(BigDecimal amount);
    List<Invoice> findByAmountTaxBetween(BigDecimal startAmount, BigDecimal endAmount);

    // HELPERS - Rangos para invoiceNo
    List<Invoice> findByInvoiceNoLessThanEqual(Integer invoiceNo);
    List<Invoice> findByInvoiceNoGreaterThanEqual(Integer invoiceNo);
    List<Invoice> findByInvoiceNoBetween(Integer startInvoiceNo, Integer endInvoiceNo);

    // HELPERS - Rangos para invoiceDate
    List<Invoice> findByInvoiceDateLessThanEqual(Integer invoiceDate);
    List<Invoice> findByInvoiceDateGreaterThanEqual(Integer invoiceDate);
    List<Invoice> findByInvoiceDateBetween(Integer startDate, Integer endDate);

    // HELPERS - Rangos para taxRate
    List<Invoice> findByTaxRateLessThanEqual(BigDecimal taxRate);
    List<Invoice> findByTaxRateGreaterThanEqual(BigDecimal taxRate);
    List<Invoice> findByTaxRateBetween(BigDecimal startTaxRate, BigDecimal endTaxRate);

    // HELPERS - Rangos para fechas
    List<Invoice> findByPayDeadlineLessThanEqual(LocalDate date);
    List<Invoice> findByPayDeadlineGreaterThanEqual(LocalDate date);
    List<Invoice> findByPayDeadlineBetween(LocalDate startDate, LocalDate endDate);

    List<Invoice> findByPaymentDateLessThanEqual(LocalDate date);
    List<Invoice> findByPaymentDateGreaterThanEqual(LocalDate date);
    List<Invoice> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

    // HELPERS - Búsqueda por texto (case insensitive)
    List<Invoice> findByCommentContainingIgnoreCase(String comment);
    List<Invoice> findByInvoiceTitleContainingIgnoreCase(String invoiceTitle);
    List<Invoice> findByNoteContainingIgnoreCase(String note);

    List<Invoice> findByCustomerIdOrderByInvoiceNoAsc(Long customerId);
}