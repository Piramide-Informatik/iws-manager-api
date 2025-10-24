package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @EntityGraph(attributePaths = {"qualificationFZ", "salutation", "title"})
    List<Employee> findAllByOrderByEmployeenoAsc();

    @EntityGraph(attributePaths = {"qualificationFZ", "salutation", "title"})
    List<Employee> findByLastname(String lastname);
    @EntityGraph(attributePaths = {"qualificationFZ", "salutation", "title"})
    Employee findByEmail(String email);
    @EntityGraph(attributePaths = {"qualificationFZ", "salutation", "title"})
    List<Employee> findByTitleId(Long titleId);
    @EntityGraph(attributePaths = {"qualificationFZ", "salutation", "title"})
    List<Employee> findBySalutationId(Long salutationId);
    @EntityGraph(attributePaths = {"qualificationFZ", "salutation", "title"})
    List<Employee> findByQualificationFZId(Long qualificationFZId);
    //find by customer Id
    @EntityGraph(attributePaths = {"qualificationFZ", "salutation", "title"})
    List<Employee> findByCustomerIdOrderByEmployeenoAsc(Long customerId);
}