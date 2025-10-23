package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.ContactPerson;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContactPersonRepository extends JpaRepository<ContactPerson, Long> {
    @EntityGraph(attributePaths = {"customer", "salutation", "title"})
    List<ContactPerson> findAll();

    @EntityGraph(attributePaths = {"customer", "salutation", "title"})
    Optional<ContactPerson> findById(Long id);

    @EntityGraph(attributePaths = {"customer", "salutation", "title"})
    List<ContactPerson> findAllByOrderByLastNameAsc();

    @EntityGraph(attributePaths = {"customer", "salutation", "title"})
    List<ContactPerson> findByCustomerIdOrderByLastNameAsc(Long customerId);
}