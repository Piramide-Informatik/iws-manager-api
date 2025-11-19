package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.dtos.employee.*;
import java.util.List;
import java.util.Optional;

public interface EmployeeServiceV2 {

    // Basic CRUD operations with DTOs
    EmployeeDTO create(EmployeeInputDTO dto);
    Optional<EmployeeDetailDTO> findById(Long id);
    List<EmployeeDTO> findAll();
    EmployeeDTO update(Long id, EmployeeInputDTO dto);
    void delete(Long id);

    // Query methods with DTOs
    List<EmployeeDTO> findByLastname(String lastname);
    Optional<EmployeeDetailDTO> findByEmail(String email);
    List<EmployeeDTO> findByTitleId(Long titleId);
    List<EmployeeDTO> findBySalutationId(Long salutationId);
    List<EmployeeDTO> findByQualificationFZId(Long qualificationFZId);
    List<EmployeeDTO> findByCustomerId(Long customerId);
    List<EmployeeDTO> findByEmployeeCategoryId(Long employeeCategoryId);
}