package com.iws_manager.iws_manager_api.repositories;

import java.time.LocalDate;

import com.iws_manager.iws_manager_api.models.HolidayYear;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface HolidayYearRepository extends JpaRepository<HolidayYear, Long> {

    @EntityGraph(attributePaths = {"publicHoliday"})
    List<HolidayYear> findAll();

    @EntityGraph(attributePaths = {"publicHoliday"})
    Optional<HolidayYear> findById(Long id);
    
    @EntityGraph(attributePaths = {"publicHoliday"})
    List<HolidayYear> findByPublicHolidayId(Long publicHolidayId);

    @EntityGraph(attributePaths = {"publicHoliday"})
    boolean existsByYearAndPublicHolidayId(LocalDate year, Long publicHolidayId);

    @EntityGraph(attributePaths = {"publicHoliday"})
    List<HolidayYear> findByYear(LocalDate year);
}
