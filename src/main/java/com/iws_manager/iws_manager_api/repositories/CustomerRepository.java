package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.Customer;
import com.iws_manager.iws_manager_api.models.ContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT cp FROM ContactPerson cp " +
           "LEFT JOIN FETCH cp.salutation " +
           "LEFT JOIN FETCH cp.title " +
           "WHERE cp.customer.id = :customerId " +
           "ORDER BY cp.lastName ASC, cp.firstName ASC")
    List<ContactPerson> findByCustomerId(@Param("customerId") Long customerId);
}