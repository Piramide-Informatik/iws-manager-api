package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;


@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByUuid(String uuid);
}