package com.iws_manager.iws_manager_api.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iws_manager.iws_manager_api.models.Invoice;
import com.iws_manager.iws_manager_api.repositories.InvoiceRepository;
import com.iws_manager.iws_manager_api.services.interfaces.InvoiceService;

/**
 * Implementation of the {@link InvoiceService} interface for managing Invoice entities.
 * Provides CRUD operations and business logic for Invoice management.
 * 
 * <p>This service implementation is transactional by default, with read-only operations
 * optimized for database performance.</p>
 */
@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    
    /**
     * Constructs a new InvoiceService with the required repository dependency.
     * 
     * @param invoiceRepository the repository for Invoice entity operations
     */
    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    /**
     * Creates and persists a new Invoice entity.
     * 
     * @param invoice the Invoice entity to be created
     * @return the persisted Invoice entity with generated ID
     * @throws IllegalArgumentException if the Invoice parameter is null
     */
    @Override
    public Invoice create(Invoice invoice) {
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice cannot be null");
        }
        return invoiceRepository.save(invoice);
    }

    /**
     * Retrieves an Invoice by its unique identifier.
     * 
     * @param id the ID of the Invoice to retrieve
     * @return an Optional containing the found Invoice, or empty if not found
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Invoice> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return invoiceRepository.findById(id);
    }

    /**
     * Retrieves all Invoice entities from the database.
     * 
     * @return a List of all Invoice entities (empty if none found)
     */
    @Override
    @Transactional(readOnly = true)
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    /**
     * Updates an existing Invoice entity.
     * 
     * @param id the ID of the Invoice to update
     * @param invoiceDetails the Invoice object containing updated fields
     * @return the updated Invoice entity
     * @throws RuntimeException if no Invoice exists with the given ID
     * @throws IllegalArgumentException if either parameter is null
     */
    @Override
    public Invoice update(Long id, Invoice invoiceDetails) {
        if (id == null || invoiceDetails == null) {
            throw new IllegalArgumentException("ID and invoice details cannot be null");
        }
        
        return invoiceRepository.findById(id)
                .map(existingInvoice -> {
                    existingInvoice.setAmountGross(invoiceDetails.getAmountGross());
                    existingInvoice.setAmountNet(invoiceDetails.getAmountNet());
                    existingInvoice.setAmountOpen(invoiceDetails.getAmountOpen());
                    existingInvoice.setAmountPaid(invoiceDetails.getAmountPaid());
                    existingInvoice.setAmountTax(invoiceDetails.getAmountTax());
                    existingInvoice.setBiller(invoiceDetails.getBiller());
                    existingInvoice.setCancelledInvoice(invoiceDetails.getCancelledInvoice());
                    existingInvoice.setComment(invoiceDetails.getComment());
                    existingInvoice.setCustomer(invoiceDetails.getCustomer());
                    existingInvoice.setInvoiceDate(invoiceDetails.getInvoiceDate());
                    existingInvoice.setInvoiceNo(invoiceDetails.getInvoiceNo());
                    existingInvoice.setInvoicePdf(invoiceDetails.getInvoicePdf());
                    existingInvoice.setInvoiceTitle(invoiceDetails.getInvoiceTitle());
                    existingInvoice.setInvoiceType(invoiceDetails.getInvoiceType());
                    existingInvoice.setIsCancellation(invoiceDetails.getIsCancellation());
                    existingInvoice.setNetwork(invoiceDetails.getNetwork());
                    existingInvoice.setNote(invoiceDetails.getNote());
                    existingInvoice.setOrder(invoiceDetails.getOrder());
                    existingInvoice.setPayCondition(invoiceDetails.getPayCondition());
                    existingInvoice.setPayDeadline(invoiceDetails.getPayDeadline());
                    existingInvoice.setPaymentDate(invoiceDetails.getPaymentDate());
                    existingInvoice.setTaxRate(invoiceDetails.getTaxRate());
                    existingInvoice.setVat(invoiceDetails.getVat());

                    return invoiceRepository.save(existingInvoice);
                })
                .orElseThrow(() -> new RuntimeException("Invoice not found with id: " + id));
    }

    /**
     * Deletes an Invoice entity by its ID.
     * 
     * @param id the ID of the Invoice to delete
     * @throws IllegalArgumentException if the id parameter is null
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        invoiceRepository.deleteById(id);
    }

    // ORDERING METHODS
    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getAllByOrderByInvoiceNoAsc() {
        return invoiceRepository.findAllByOrderByInvoiceNoAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getAllByOrderByInvoiceDateAsc() {
        return invoiceRepository.findAllByOrderByInvoiceDateAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getAllByOrderByInvoiceTitleAsc() {
        return invoiceRepository.findAllByOrderByInvoiceTitleAsc();
    }

    // PROPERTIES - Campos básicos
    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByComment(String comment) {
        return invoiceRepository.findByComment(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByInvoiceNo(Integer invoiceNo) {
        return invoiceRepository.findByInvoiceNo(invoiceNo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByInvoiceTitle(String invoiceTitle) {
        return invoiceRepository.findByInvoiceTitle(invoiceTitle);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByIsCancellation(Short isCancellation) {
        return invoiceRepository.findByIsCancellation(isCancellation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByNote(String note) {
        return invoiceRepository.findByNote(note);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByTaxRate(BigDecimal taxRate) {
        return invoiceRepository.findByTaxRate(taxRate);
    }

    // PROPERTIES - Campos de monto
    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountGross(BigDecimal amountGross) {
        return invoiceRepository.findByAmountGross(amountGross);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountNet(BigDecimal amountNet) {
        return invoiceRepository.findByAmountNet(amountNet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountOpen(BigDecimal amountOpen) {
        return invoiceRepository.findByAmountOpen(amountOpen);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountPaid(BigDecimal amountPaid) {
        return invoiceRepository.findByAmountPaid(amountPaid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountTax(BigDecimal amountTax) {
        return invoiceRepository.findByAmountTax(amountTax);
    }

    // PROPERTIES - Campos de fecha
    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByInvoiceDate(Integer invoiceDate) {
        return invoiceRepository.findByInvoiceDate(invoiceDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByPayDeadline(LocalDate payDeadline) {
        return invoiceRepository.findByPayDeadline(payDeadline);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByPaymentDate(LocalDate paymentDate) {
        return invoiceRepository.findByPaymentDate(paymentDate);
    }

    // PROPERTIES - Relaciones (IDs)
    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByBillerId(Long billerId) {
        return invoiceRepository.findByBillerId(billerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByCancelledInvoiceId(Long cancelledInvoiceId) {
        return invoiceRepository.findByCancelledInvoiceId(cancelledInvoiceId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByCustomerId(Long customerId) {
        return invoiceRepository.findByCustomerId(customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByInvoiceTypeId(Long invoiceTypeId) {
        return invoiceRepository.findByInvoiceTypeId(invoiceTypeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByNetworkId(Long networkId) {
        return invoiceRepository.findByNetworkId(networkId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByOrderId(Long orderId) {
        return invoiceRepository.findByOrderId(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByPayConditionId(Long payConditionId) {
        return invoiceRepository.findByPayConditionId(payConditionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByVatId(Long vatId) {
        return invoiceRepository.findByVatId(vatId);
    }

    // HELPERS - Rangos para montos
    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountGrossLessThanEqual(BigDecimal amount) {
        return invoiceRepository.findByAmountGrossLessThanEqual(amount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountGrossGreaterThanEqual(BigDecimal amount) {
        return invoiceRepository.findByAmountGrossGreaterThanEqual(amount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountGrossBetween(BigDecimal startAmount, BigDecimal endAmount) {
        return invoiceRepository.findByAmountGrossBetween(startAmount, endAmount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountNetLessThanEqual(BigDecimal amount) {
        return invoiceRepository.findByAmountNetLessThanEqual(amount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountNetGreaterThanEqual(BigDecimal amount) {
        return invoiceRepository.findByAmountNetGreaterThanEqual(amount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountNetBetween(BigDecimal startAmount, BigDecimal endAmount) {
        return invoiceRepository.findByAmountNetBetween(startAmount, endAmount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountOpenLessThanEqual(BigDecimal amount) {
        return invoiceRepository.findByAmountOpenLessThanEqual(amount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountOpenGreaterThanEqual(BigDecimal amount) {
        return invoiceRepository.findByAmountOpenGreaterThanEqual(amount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountOpenBetween(BigDecimal startAmount, BigDecimal endAmount) {
        return invoiceRepository.findByAmountOpenBetween(startAmount, endAmount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountPaidLessThanEqual(BigDecimal amount) {
        return invoiceRepository.findByAmountPaidLessThanEqual(amount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountPaidGreaterThanEqual(BigDecimal amount) {
        return invoiceRepository.findByAmountPaidGreaterThanEqual(amount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountPaidBetween(BigDecimal startAmount, BigDecimal endAmount) {
        return invoiceRepository.findByAmountPaidBetween(startAmount, endAmount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountTaxLessThanEqual(BigDecimal amount) {
        return invoiceRepository.findByAmountTaxLessThanEqual(amount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountTaxGreaterThanEqual(BigDecimal amount) {
        return invoiceRepository.findByAmountTaxGreaterThanEqual(amount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByAmountTaxBetween(BigDecimal startAmount, BigDecimal endAmount) {
        return invoiceRepository.findByAmountTaxBetween(startAmount, endAmount);
    }

    // HELPERS - Rangos para invoiceNo
    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByInvoiceNoLessThanEqual(Integer invoiceNo) {
        return invoiceRepository.findByInvoiceNoLessThanEqual(invoiceNo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByInvoiceNoGreaterThanEqual(Integer invoiceNo) {
        return invoiceRepository.findByInvoiceNoGreaterThanEqual(invoiceNo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByInvoiceNoBetween(Integer startInvoiceNo, Integer endInvoiceNo) {
        return invoiceRepository.findByInvoiceNoBetween(startInvoiceNo, endInvoiceNo);
    }

    // HELPERS - Rangos para invoiceDate
    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByInvoiceDateLessThanEqual(Integer invoiceDate) {
        return invoiceRepository.findByInvoiceDateLessThanEqual(invoiceDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByInvoiceDateGreaterThanEqual(Integer invoiceDate) {
        return invoiceRepository.findByInvoiceDateGreaterThanEqual(invoiceDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByInvoiceDateBetween(Integer startDate, Integer endDate) {
        return invoiceRepository.findByInvoiceDateBetween(startDate, endDate);
    }

    // HELPERS - Rangos para taxRate
    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByTaxRateLessThanEqual(BigDecimal taxRate) {
        return invoiceRepository.findByTaxRateLessThanEqual(taxRate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByTaxRateGreaterThanEqual(BigDecimal taxRate) {
        return invoiceRepository.findByTaxRateGreaterThanEqual(taxRate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByTaxRateBetween(BigDecimal startTaxRate, BigDecimal endTaxRate) {
        return invoiceRepository.findByTaxRateBetween(startTaxRate, endTaxRate);
    }

    // HELPERS - Rangos para fechas
    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByPayDeadlineLessThanEqual(LocalDate date) {
        return invoiceRepository.findByPayDeadlineLessThanEqual(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByPayDeadlineGreaterThanEqual(LocalDate date) {
        return invoiceRepository.findByPayDeadlineGreaterThanEqual(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByPayDeadlineBetween(LocalDate startDate, LocalDate endDate) {
        return invoiceRepository.findByPayDeadlineBetween(startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByPaymentDateLessThanEqual(LocalDate date) {
        return invoiceRepository.findByPaymentDateLessThanEqual(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByPaymentDateGreaterThanEqual(LocalDate date) {
        return invoiceRepository.findByPaymentDateGreaterThanEqual(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByPaymentDateBetween(LocalDate startDate, LocalDate endDate) {
        return invoiceRepository.findByPaymentDateBetween(startDate, endDate);
    }

    // HELPERS - Búsqueda por texto (case insensitive)
    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByCommentContainingIgnoreCase(String comment) {
        return invoiceRepository.findByCommentContainingIgnoreCase(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByInvoiceTitleContainingIgnoreCase(String invoiceTitle) {
        return invoiceRepository.findByInvoiceTitleContainingIgnoreCase(invoiceTitle);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Invoice> getByNoteContainingIgnoreCase(String note) {
        return invoiceRepository.findByNoteContainingIgnoreCase(note);
    }
}