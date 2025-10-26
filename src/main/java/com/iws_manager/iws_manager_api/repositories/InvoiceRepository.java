package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findAllByOrderByInvoiceNoAsc();
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findAllByOrderByInvoiceDateAsc();
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findAllByOrderByInvoiceTitleAsc();

    // PROPERTIES - Campos básicos
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByComment(String comment);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByInvoiceNo(Integer invoiceNo);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByInvoiceTitle(String invoiceTitle);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByIsCancellation(Short isCancellation);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByNote(String note);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByTaxRate(BigDecimal taxRate);

    // PROPERTIES - Campos de monto
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountGross(BigDecimal amountGross);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountNet(BigDecimal amountNet);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountOpen(BigDecimal amountOpen);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountPaid(BigDecimal amountPaid);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountTax(BigDecimal amountTax);

    // PROPERTIES - Campos de fecha
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByInvoiceDate(LocalDate invoiceDate);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByPayDeadline(LocalDate payDeadline);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByPaymentDate(LocalDate paymentDate);

    // PROPERTIES - Relaciones (IDs)
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByBillerId(Long billerId);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByCancelledInvoiceId(Long cancelledInvoiceId);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByCustomerId(Long customerId);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByInvoiceTypeId(Long invoiceTypeId);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByNetworkId(Long networkId);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByOrderId(Long orderId);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByPayConditionId(Long payConditionId);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByVatId(Long vatId);

    // HELPERS - Rangos para montos
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountGrossLessThanEqual(BigDecimal amount);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountGrossGreaterThanEqual(BigDecimal amount);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountGrossBetween(BigDecimal startAmount, BigDecimal endAmount);

    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountNetLessThanEqual(BigDecimal amount);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountNetGreaterThanEqual(BigDecimal amount);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountNetBetween(BigDecimal startAmount, BigDecimal endAmount);

    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountOpenLessThanEqual(BigDecimal amount);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountOpenGreaterThanEqual(BigDecimal amount);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountOpenBetween(BigDecimal startAmount, BigDecimal endAmount);

    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountPaidLessThanEqual(BigDecimal amount);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountPaidGreaterThanEqual(BigDecimal amount);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountPaidBetween(BigDecimal startAmount, BigDecimal endAmount);

    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountTaxLessThanEqual(BigDecimal amount);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountTaxGreaterThanEqual(BigDecimal amount);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByAmountTaxBetween(BigDecimal startAmount, BigDecimal endAmount);

    // HELPERS - Rangos para invoiceNo
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByInvoiceNoLessThanEqual(Integer invoiceNo);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByInvoiceNoGreaterThanEqual(Integer invoiceNo);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByInvoiceNoBetween(Integer startInvoiceNo, Integer endInvoiceNo);

    // HELPERS - Rangos para invoiceDate
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByInvoiceDateLessThanEqual(LocalDate invoiceDate);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByInvoiceDateGreaterThanEqual(LocalDate invoiceDate);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByInvoiceDateBetween(LocalDate startDate, LocalDate endDate);

    // HELPERS - Rangos para taxRate
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByTaxRateLessThanEqual(BigDecimal taxRate);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByTaxRateGreaterThanEqual(BigDecimal taxRate);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByTaxRateBetween(BigDecimal startTaxRate, BigDecimal endTaxRate);

    // HELPERS - Rangos para fechas
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByPayDeadlineLessThanEqual(LocalDate date);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByPayDeadlineGreaterThanEqual(LocalDate date);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByPayDeadlineBetween(LocalDate startDate, LocalDate endDate);

    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByPaymentDateLessThanEqual(LocalDate date);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByPaymentDateGreaterThanEqual(LocalDate date);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

    // HELPERS - Búsqueda por texto (case insensitive)
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByCommentContainingIgnoreCase(String comment);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByInvoiceTitleContainingIgnoreCase(String invoiceTitle);
    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByNoteContainingIgnoreCase(String note);

    @EntityGraph(attributePaths = { "biller", "cancelledInvoice", "customer", "invoiceType", "network", "order", "payCondition", "vat" })
    List<Invoice> findByCustomerIdOrderByInvoiceNoAsc(Long customerId);
}