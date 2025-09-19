package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.VatRate;

public interface VatRateService {
    VatRate create(VatRate vatRate);
    Optional<VatRate> findById(Long id);
    List<VatRate> findAll();
    VatRate update(Long id, VatRate vatRateDetails);
    void delete(Long id);
    
    List<VatRate> getByVatId(Long vatId);
}