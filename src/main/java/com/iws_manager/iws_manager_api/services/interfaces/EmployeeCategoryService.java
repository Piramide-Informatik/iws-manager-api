package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.EmployeeCategory;

import java.util.List;
import java.util.Optional;

public interface EmployeeCategoryService {
    EmployeeCategory create(EmployeeCategory category);
    Optional<EmployeeCategory> findById(Long id);
    List<EmployeeCategory> findAll();
    EmployeeCategory update(Long id, EmployeeCategory categoryDetails);
    void delete(Long id);
}
