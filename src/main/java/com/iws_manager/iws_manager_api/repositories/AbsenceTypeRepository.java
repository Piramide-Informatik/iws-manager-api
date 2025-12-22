package com.iws_manager.iws_manager_api.repositories;

import java.util.List;

import com.iws_manager.iws_manager_api.models.AbsenceType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AbsenceTypeRepository extends JpaRepository<AbsenceType,Long> {
    List<AbsenceType> findAllByOrderByNameAsc();
    List<AbsenceType> findAllByOrderByLabelAsc();

    // CREATION - verify if name and label exist
    boolean existsByName(String name);
    boolean existsByLabel(String label);
    
    // UPDATING - verify if name and label exists with exception of the same item
    boolean existsByNameAndIdNot(String name, Long excludeId);
    boolean existsByLabelAndIdNot(String label, Long excludeId);

}
