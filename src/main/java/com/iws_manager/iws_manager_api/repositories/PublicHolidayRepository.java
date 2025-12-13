package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.PublicHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

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

    // Method to get holidays between dates
    @Query("SELECT p FROM PublicHoliday p WHERE p.date BETWEEN :startDate AND :endDate")
    List<PublicHoliday> findHolidaysBetweenDates(LocalDate startDate, LocalDate endDate);
    
    // Method to get holidays by year
    @Query("SELECT p FROM PublicHoliday p WHERE YEAR(p.date) = :year")
    List<PublicHoliday> findHolidaysByYear(Integer year);

    // Get all holidays with fixed date by day and month
    @Query("SELECT p FROM PublicHoliday p WHERE p.isFixedDate = true AND MONTH(p.date) = :month AND DAY(p.date) = :day")
    List<PublicHoliday> findFixedHolidaysByDayAndMonth(Integer day, Integer month);
    
    // Get all holidays with fixed date
    @Query("SELECT p FROM PublicHoliday p WHERE p.isFixedDate = true")
    List<PublicHoliday> findAllFixedHolidays();

    // Checks if a public holiday exists on a specific date.
    boolean existsByDate(LocalDate date);
    
    // Finds a public holiday by specific date.
    Optional<PublicHoliday> findByDate(LocalDate date);
    
}
