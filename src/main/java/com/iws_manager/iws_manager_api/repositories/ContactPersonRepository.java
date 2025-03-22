package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.ContactPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;


@Repository
public interface ContactPersonRepository extends JpaRepository<ContactPerson, Integer> {
    Optional<ContactPerson> findByUuid(String uuid);
}