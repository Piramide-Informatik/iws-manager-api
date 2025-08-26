package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Invoice;
import com.iws_manager.iws_manager_api.services.interfaces.InvoiceService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Invoice invoice){
        Invoice createdInvoice = invoiceService.create(invoice);
        return new ResponseEntity<>(createdInvoice, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getById(@PathVariable Long id){
        return invoiceService.findById(id)
                .map(invoice -> new ResponseEntity<>(invoice, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> getAll(){
        List<Invoice> invoices = invoiceService.findAll();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> update(@PathVariable Long id, @RequestBody Invoice invoiceDetails){
        try {
            Invoice updatedInvoice = invoiceService.update(id, invoiceDetails);
            return new ResponseEntity<>(updatedInvoice, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            invoiceService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ORDERING METHODS
    @GetMapping("/sort-by-invoiceno")
    public ResponseEntity<List<Invoice>> getAllSortedByInvoiceNo() {
        List<Invoice> invoices = invoiceService.getAllByOrderByInvoiceNoAsc();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/sort-by-invoicedate")
    public ResponseEntity<List<Invoice>> getAllSortedByInvoiceDate() {
        List<Invoice> invoices = invoiceService.getAllByOrderByInvoiceDateAsc();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/sort-by-invoicetitle")
    public ResponseEntity<List<Invoice>> getAllSortedByInvoiceTitle() {
        List<Invoice> invoices = invoiceService.getAllByOrderByInvoiceTitleAsc();
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    // PROPERTIES - Campos básicos
    @GetMapping("/by-comment/{comment}")
    public ResponseEntity<List<Invoice>> getByComment(@PathVariable String comment) {
        List<Invoice> invoices = invoiceService.getByComment(comment);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-invoiceno/{invoiceNo}")
    public ResponseEntity<List<Invoice>> getByInvoiceNo(@PathVariable Integer invoiceNo) {
        List<Invoice> invoices = invoiceService.getByInvoiceNo(invoiceNo);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-invoicetitle/{invoiceTitle}")
    public ResponseEntity<List<Invoice>> getByInvoiceTitle(@PathVariable String invoiceTitle) {
        List<Invoice> invoices = invoiceService.getByInvoiceTitle(invoiceTitle);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-cancellation/{isCancellation}")
    public ResponseEntity<List<Invoice>> getByIsCancellation(@PathVariable Short isCancellation) {
        List<Invoice> invoices = invoiceService.getByIsCancellation(isCancellation);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-note/{note}")
    public ResponseEntity<List<Invoice>> getByNote(@PathVariable String note) {
        List<Invoice> invoices = invoiceService.getByNote(note);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-taxrate/{taxRate}")
    public ResponseEntity<List<Invoice>> getByTaxRate(@PathVariable BigDecimal taxRate) {
        List<Invoice> invoices = invoiceService.getByTaxRate(taxRate);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    // PROPERTIES - Campos de monto
    @GetMapping("/by-amountgross/{amountGross}")
    public ResponseEntity<List<Invoice>> getByAmountGross(@PathVariable BigDecimal amountGross) {
        List<Invoice> invoices = invoiceService.getByAmountGross(amountGross);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-amountnet/{amountNet}")
    public ResponseEntity<List<Invoice>> getByAmountNet(@PathVariable BigDecimal amountNet) {
        List<Invoice> invoices = invoiceService.getByAmountNet(amountNet);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-amountopen/{amountOpen}")
    public ResponseEntity<List<Invoice>> getByAmountOpen(@PathVariable BigDecimal amountOpen) {
        List<Invoice> invoices = invoiceService.getByAmountOpen(amountOpen);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-amountpaid/{amountPaid}")
    public ResponseEntity<List<Invoice>> getByAmountPaid(@PathVariable BigDecimal amountPaid) {
        List<Invoice> invoices = invoiceService.getByAmountPaid(amountPaid);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-amounttax/{amountTax}")
    public ResponseEntity<List<Invoice>> getByAmountTax(@PathVariable BigDecimal amountTax) {
        List<Invoice> invoices = invoiceService.getByAmountTax(amountTax);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    // PROPERTIES - Campos de fecha
    @GetMapping("/by-invoicedate/{invoiceDate}")
    public ResponseEntity<List<Invoice>> getByInvoiceDate(@PathVariable Integer invoiceDate) {
        List<Invoice> invoices = invoiceService.getByInvoiceDate(invoiceDate);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-paydeadline/{payDeadline}")
    public ResponseEntity<List<Invoice>> getByPayDeadline(@PathVariable LocalDate payDeadline) {
        List<Invoice> invoices = invoiceService.getByPayDeadline(payDeadline);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-paymentdate/{paymentDate}")
    public ResponseEntity<List<Invoice>> getByPaymentDate(@PathVariable LocalDate paymentDate) {
        List<Invoice> invoices = invoiceService.getByPaymentDate(paymentDate);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    // PROPERTIES - Relaciones (IDs)
    @GetMapping("/by-biller/{billerId}")
    public ResponseEntity<List<Invoice>> getByBillerId(@PathVariable Long billerId) {
        List<Invoice> invoices = invoiceService.getByBillerId(billerId);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-cancelledinvoice/{cancelledInvoiceId}")
    public ResponseEntity<List<Invoice>> getByCancelledInvoiceId(@PathVariable Long cancelledInvoiceId) {
        List<Invoice> invoices = invoiceService.getByCancelledInvoiceId(cancelledInvoiceId);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-customer/{customerId}")
    public ResponseEntity<List<Invoice>> getByCustomerId(@PathVariable Long customerId) {
        List<Invoice> invoices = invoiceService.getByCustomerId(customerId);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-invoicetype/{invoiceTypeId}")
    public ResponseEntity<List<Invoice>> getByInvoiceTypeId(@PathVariable Long invoiceTypeId) {
        List<Invoice> invoices = invoiceService.getByInvoiceTypeId(invoiceTypeId);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-network/{networkId}")
    public ResponseEntity<List<Invoice>> getByNetworkId(@PathVariable Long networkId) {
        List<Invoice> invoices = invoiceService.getByNetworkId(networkId);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-order/{orderId}")
    public ResponseEntity<List<Invoice>> getByOrderId(@PathVariable Long orderId) {
        List<Invoice> invoices = invoiceService.getByOrderId(orderId);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-paycondition/{payConditionId}")
    public ResponseEntity<List<Invoice>> getByPayConditionId(@PathVariable Long payConditionId) {
        List<Invoice> invoices = invoiceService.getByPayConditionId(payConditionId);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-vat/{vatId}")
    public ResponseEntity<List<Invoice>> getByVatId(@PathVariable Long vatId) {
        List<Invoice> invoices = invoiceService.getByVatId(vatId);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    // HELPERS - Rangos para montos
    @GetMapping("/by-amountgross-less-than/{amount}")
    public ResponseEntity<List<Invoice>> getByAmountGrossLessThanEqual(@PathVariable BigDecimal amount) {
        List<Invoice> invoices = invoiceService.getByAmountGrossLessThanEqual(amount);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-amountgross-greater-than/{amount}")
    public ResponseEntity<List<Invoice>> getByAmountGrossGreaterThanEqual(@PathVariable BigDecimal amount) {
        List<Invoice> invoices = invoiceService.getByAmountGrossGreaterThanEqual(amount);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-amountgross-between")
    public ResponseEntity<?> getByAmountGrossBetween(
            @RequestParam("startAmount") BigDecimal startAmount,
            @RequestParam("endAmount") BigDecimal endAmount) {
        
        try {
            if (startAmount == null || endAmount == null) {
                throw new IllegalArgumentException("Both amounts are required");
            }
            if (startAmount.compareTo(endAmount) > 0) {
                throw new IllegalArgumentException("Start amount cannot be greater than end amount");
            }

            List<Invoice> invoices = invoiceService.getByAmountGrossBetween(startAmount, endAmount);
            return ResponseEntity.ok(invoices);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    "error", "Invalid parameters",
                    "message", ex.getMessage()
                )
            );
        }
    }

    // HELPERS - Rangos para invoiceNo
    @GetMapping("/by-invoiceno-less-than/{invoiceNo}")
    public ResponseEntity<List<Invoice>> getByInvoiceNoLessThanEqual(@PathVariable Integer invoiceNo) {
        List<Invoice> invoices = invoiceService.getByInvoiceNoLessThanEqual(invoiceNo);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-invoiceno-greater-than/{invoiceNo}")
    public ResponseEntity<List<Invoice>> getByInvoiceNoGreaterThanEqual(@PathVariable Integer invoiceNo) {
        List<Invoice> invoices = invoiceService.getByInvoiceNoGreaterThanEqual(invoiceNo);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-invoiceno-between")
    public ResponseEntity<?> getByInvoiceNoBetween(
            @RequestParam("startInvoiceNo") Integer startInvoiceNo,
            @RequestParam("endInvoiceNo") Integer endInvoiceNo) {
        
        try {
            if (startInvoiceNo == null || endInvoiceNo == null) {
                throw new IllegalArgumentException("Both invoice numbers are required");
            }
            if (startInvoiceNo > endInvoiceNo) {
                throw new IllegalArgumentException("Start invoice number cannot be greater than end invoice number");
            }

            List<Invoice> invoices = invoiceService.getByInvoiceNoBetween(startInvoiceNo, endInvoiceNo);
            return ResponseEntity.ok(invoices);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    "error", "Invalid parameters",
                    "message", ex.getMessage()
                )
            );
        }
    }

    // HELPERS - Rangos para fechas
    @GetMapping("/by-paydeadline-range")
    public ResponseEntity<List<Invoice>> getByPayDeadlineBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        
        if (start == null || end == null) {
            throw new IllegalArgumentException("Both start and end dates are required");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        List<Invoice> invoices = invoiceService.getByPayDeadlineBetween(start, end);
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/by-paymentdate-range")
    public ResponseEntity<List<Invoice>> getByPaymentDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        
        if (start == null || end == null) {
            throw new IllegalArgumentException("Both start and end dates are required");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        List<Invoice> invoices = invoiceService.getByPaymentDateBetween(start, end);
        return ResponseEntity.ok(invoices);
    }

    // HELPERS - Búsqueda por texto (case insensitive)
    @GetMapping("/search-comment/{comment}")
    public ResponseEntity<List<Invoice>> searchByComment(@PathVariable String comment) {
        List<Invoice> invoices = invoiceService.getByCommentContainingIgnoreCase(comment);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/search-invoicetitle/{invoiceTitle}")
    public ResponseEntity<List<Invoice>> searchByInvoiceTitle(@PathVariable String invoiceTitle) {
        List<Invoice> invoices = invoiceService.getByInvoiceTitleContainingIgnoreCase(invoiceTitle);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/search-note/{note}")
    public ResponseEntity<List<Invoice>> searchByNote(@PathVariable String note) {
        List<Invoice> invoices = invoiceService.getByNoteContainingIgnoreCase(note);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    // HELPERS - Rangos para taxRate
    @GetMapping("/by-taxrate-less-than/{taxRate}")
    public ResponseEntity<List<Invoice>> getByTaxRateLessThanEqual(@PathVariable BigDecimal taxRate) {
        List<Invoice> invoices = invoiceService.getByTaxRateLessThanEqual(taxRate);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-taxrate-greater-than/{taxRate}")
    public ResponseEntity<List<Invoice>> getByTaxRateGreaterThanEqual(@PathVariable BigDecimal taxRate) {
        List<Invoice> invoices = invoiceService.getByTaxRateGreaterThanEqual(taxRate);
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/by-taxrate-between")
    public ResponseEntity<?> getByTaxRateBetween(
            @RequestParam("startTaxRate") BigDecimal startTaxRate,
            @RequestParam("endTaxRate") BigDecimal endTaxRate) {
        
        try {
            if (startTaxRate == null || endTaxRate == null) {
                throw new IllegalArgumentException("Both tax rates are required");
            }
            if (startTaxRate.compareTo(endTaxRate) > 0) {
                throw new IllegalArgumentException("Start tax rate cannot be greater than end tax rate");
            }

            List<Invoice> invoices = invoiceService.getByTaxRateBetween(startTaxRate, endTaxRate);
            return ResponseEntity.ok(invoices);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                Map.of(
                    "error", "Invalid parameters",
                    "message", ex.getMessage()
                )
            );
        }
    }
}