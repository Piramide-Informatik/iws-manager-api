package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.Employee;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @EntityGraph(attributePaths = {"customer", "customer.branch", "customer.companytype", "customer.country", "customer.state", "qualificationFZ", "salutation", "title", "employeeCategory"})
    List<Employee> findAllByOrderByEmployeenoAsc();

    @EntityGraph(attributePaths = {"customer", "customer.branch", "customer.companytype", "customer.country", "customer.state", "qualificationFZ", "salutation", "title", "employeeCategory"})
    List<Employee> findByLastname(String lastname);
    @EntityGraph(attributePaths = {"customer", "customer.branch", "customer.companytype", "customer.country", "customer.state", "qualificationFZ", "salutation", "title", "employeeCategory"})
    Employee findByEmail(String email);
    @EntityGraph(attributePaths = {"customer", "customer.branch", "customer.companytype", "customer.country", "customer.state", "qualificationFZ", "salutation", "title", "employeeCategory"})
    List<Employee> findByTitleId(Long titleId);
    @EntityGraph(attributePaths = {"customer", "customer.branch", "customer.companytype", "customer.country", "customer.state", "qualificationFZ", "salutation", "title", "employeeCategory"})
    List<Employee> findBySalutationId(Long salutationId);
    @EntityGraph(attributePaths = {"customer", "customer.branch", "customer.companytype", "customer.country", "customer.state", "qualificationFZ", "salutation", "title", "employeeCategory"})
    List<Employee> findByQualificationFZId(Long qualificationFZId);
    @EntityGraph(attributePaths = {"customer", "customer.branch", "customer.companytype", "customer.country", "customer.state", "qualificationFZ", "salutation", "title", "employeeCategory"})
    List<Employee> findByEmployeeCategoryId(Long employeeCategoryId);
    //find by customer Id
    @EntityGraph(attributePaths = {"customer", "customer.branch", "customer.companytype", "customer.country", "customer.state", "qualificationFZ", "salutation", "title", "employeeCategory"})
    List<Employee> findByCustomerIdOrderByEmployeenoAsc(Long customerId);
}