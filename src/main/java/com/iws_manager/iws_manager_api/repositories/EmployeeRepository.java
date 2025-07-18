package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByOrderByEmployeenoAsc();
    
    List<Employee> findByLastname(String lastname);
    Employee findByEmail(String email);
    List<Employee> findByTitleId(Long titleId);
    List<Employee> findBySalutationId(Long salutationId);
    List<Employee> findByQualificationFZId(Long qualificationFZId);
    //find by customer Id 
    List<Employee> findByCustomerIdOrderByEmployeenoAsc(Long customerId);
}