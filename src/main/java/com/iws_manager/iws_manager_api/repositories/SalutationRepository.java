package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.Salutation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;


@Repository
public interface SalutationRepository extends JpaRepository<Salutation, Integer> {
    Optional<Salutation> findByUuid(String uuid);
}