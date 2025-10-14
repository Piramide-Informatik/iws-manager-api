package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.ContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContactPersonRepository extends JpaRepository<ContactPerson, Long> {
    List<ContactPerson> findAllByOrderByLastNameAsc();
    List<ContactPerson> findByCustomerIdOrderByLastNameAsc(Long customerId);
}