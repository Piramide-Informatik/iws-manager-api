package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.Vat;

public interface VatService {
    Vat create(Vat vat);
    Optional<Vat> findById(Long id);
    List<Vat> findAll();
    Vat update(Long id, Vat vatDetails);
    void delete(Long id);
}