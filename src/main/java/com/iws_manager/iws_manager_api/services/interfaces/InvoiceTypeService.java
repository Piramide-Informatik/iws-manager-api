package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.InvoiceType;

public interface InvoiceTypeService {
    InvoiceType create(InvoiceType invoiceType);
    Optional<InvoiceType> findById(Long id);
    List<InvoiceType> findAll();
    InvoiceType update(Long id, InvoiceType invoiceTypeDetails);
    void delete(Long id);
}