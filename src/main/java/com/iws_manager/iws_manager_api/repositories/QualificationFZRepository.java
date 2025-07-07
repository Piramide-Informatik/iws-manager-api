package com.iws_manager.iws_manager_api.repositories;

import java.util.List;
import com.iws_manager.iws_manager_api.models.QualificationFZ;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QualificationFZRepository extends JpaRepository<QualificationFZ, Long> {
    List<QualificationFZ> findAllByOrderByQualificationAsc();
}