package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.PublicHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, Long> {
    // @EntityGraph(attributePaths = {"stateHolidays", "holidayYears"})
    List<PublicHoliday> findAllByOrderByNameAsc();

    // @EntityGraph(attributePaths = {"stateHolidays", "holidayYears"})
    List<PublicHoliday> findAllByOrderBySequenceNoAsc();

    // @EntityGraph(attributePaths = {"stateHolidays", "holidayYears"})
    List<PublicHoliday> findAllByOrderBySequenceNoDesc();

    @EntityGraph(attributePaths = {"stateHolidays", "holidayYears"})
    @Query("SELECT COALESCE(MAX(p.sequenceNo), 0) FROM PublicHoliday p")
    Long findMaxSequenceNo();
}
