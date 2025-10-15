package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.Customer;
import com.iws_manager.iws_manager_api.models.ContactPerson;

public interface CustomerService {
    Customer create(Customer customer);
    Optional<Customer> findById(Long id);
    List<Customer> findAll();
    Customer update(Long id, Customer customerDetails);
    void delete(Long id);
    List<ContactPerson> findContactsByCustomerId(Long customerId);

    Long getNextCustomerNo();
}
