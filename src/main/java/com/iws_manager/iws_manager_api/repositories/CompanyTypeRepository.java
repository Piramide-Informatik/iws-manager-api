package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.CompanyType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;


@Repository
public interface CompanyTypeRepository extends JpaRepository<CompanyType, Integer> {
    Optional<CompanyType> findByUuid(String uuid);
}