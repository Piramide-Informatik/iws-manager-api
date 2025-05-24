package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.CompanyType;

public interface CompanyTypeService {
    CompanyType create(CompanyType companyType);
    Optional<CompanyType> findById(Long id);
    List<CompanyType> findAll();
    CompanyType update(Long id, CompanyType companyTypeDetails);
    void delete(Long id);
}
