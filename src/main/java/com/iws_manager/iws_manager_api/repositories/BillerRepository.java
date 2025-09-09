package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.Biller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BillerRepository extends JpaRepository<Biller, Long> {
    List<Biller> findAllByOrderByNameAsc();
}