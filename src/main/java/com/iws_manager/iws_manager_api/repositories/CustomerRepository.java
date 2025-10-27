package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.Customer;
import com.iws_manager.iws_manager_api.models.ContactPerson;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @EntityGraph(attributePaths = {"branch", "companytype", "country", "state"})
    List<Customer> findAll();

    @Query("SELECT cp FROM ContactPerson cp " +
           "LEFT JOIN FETCH cp.salutation " +
           "LEFT JOIN FETCH cp.title " +
           "WHERE cp.customer.id = :customerId " +
           "ORDER BY cp.lastName ASC, cp.firstName ASC")
    List<ContactPerson> findByCustomerId(@Param("customerId") Long customerId);

    @EntityGraph(attributePaths = {"branch", "companytype", "country", "state"})
    Optional<Customer> findById(Long id);

    @Query("SELECT MAX(c.customerno) FROM Customer c")
    Long findMaxCustomerNo();
}