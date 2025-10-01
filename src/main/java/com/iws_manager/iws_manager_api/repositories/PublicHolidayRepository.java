package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.PublicHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, Long> {
    List<PublicHoliday> findAllByOrderByNameAsc();
    List<PublicHoliday> findAllByOrderBySequenceNoAsc();
    List<PublicHoliday> findAllByOrderBySequenceNoDesc();

    @Query("SELECT COALESCE(MAX(p.sequenceNo), 0) FROM PublicHoliday p")
    Integer findMaxSequenceNo();
}
