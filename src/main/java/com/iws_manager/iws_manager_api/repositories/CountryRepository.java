package com.iws_manager.iws_manager_api.repositories;

import java.util.List;

import com.iws_manager.iws_manager_api.models.Country;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    List<Country> findAllByOrderByNameAsc();    
}