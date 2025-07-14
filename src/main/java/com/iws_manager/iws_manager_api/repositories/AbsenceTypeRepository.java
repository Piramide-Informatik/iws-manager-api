package com.iws_manager.iws_manager_api.repositories;

import java.util.List;
import com.iws_manager.iws_manager_api.models.AbsenceType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AbsenceTypeRepository extends JpaRepository<AbsenceType,Long> {
    List<AbsenceType> findAllByOrderByNameAsc();

}
