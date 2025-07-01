package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.Employee;

public interface EmployeeService {

    Employee create(Employee employee);
    Optional<Employee> findById(Long id);
    List<Employee> findAll();
    Employee update(Long id, Employee employee);
    void delete(Long id);

    List<Employee> findByLastname(String lastname);
    Optional<Employee> findByEmail(String email);
    List<Employee> findByTitleId(Long titleId);
    List<Employee> findBySalutationId(Long salutationId);
    List<Employee> findByQualificationFZId(Long qualificationFZId);
    List<Employee> findByCustomerId(Long customerId);
}
