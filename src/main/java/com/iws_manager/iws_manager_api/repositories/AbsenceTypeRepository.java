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

    // CREATION - verify if name and label exist
    @Query("SELECT COUNT(a) > 0 FROM AbsenceType a WHERE a.name = :name OR a.label = :label")
    boolean existsByNameOrLabel(@Param("name") String name, @Param("label") String label);
    
    // UPDATING - verify if name and label exists with exception of the same item
    @Query("SELECT COUNT(a) > 0 FROM AbsenceType a WHERE (a.name = :name OR a.label = :label) AND a.id != :excludeId")
    boolean existsByNameOrLabelAndIdNot(
        @Param("name") String name, 
        @Param("label") String label, 
        @Param("excludeId") Long excludeId
    );

}
