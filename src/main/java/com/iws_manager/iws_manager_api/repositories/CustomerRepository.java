package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUuid(String uuid);
}