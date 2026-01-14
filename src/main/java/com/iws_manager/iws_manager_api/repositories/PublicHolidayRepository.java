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

    @EntityGraph(attributePaths = { "stateHolidays", "holidayYears" })
    @Query("SELECT COALESCE(MAX(p.sequenceNo), 0) FROM PublicHoliday p")
    Long findMaxSequenceNo();

    // Method to get holidays between dates
    @Query("SELECT DISTINCT p FROM PublicHoliday p JOIN p.stateHolidays sh WHERE p.date BETWEEN :startDate AND :endDate AND (:stateId IS NULL OR sh.state.id = :stateId) AND sh.isholiday = true")
    List<PublicHoliday> findHolidaysBetweenDates(LocalDate startDate, LocalDate endDate, Long stateId);

    // Method to get holidays by year
    @Query("SELECT DISTINCT p FROM PublicHoliday p JOIN p.stateHolidays sh WHERE YEAR(p.date) = :year AND (:stateId IS NULL OR sh.state.id = :stateId) AND sh.isholiday = true")
    List<PublicHoliday> findHolidaysByYear(Integer year, Long stateId);

    // Get all holidays with fixed date by day and month
    @Query("SELECT DISTINCT p FROM PublicHoliday p JOIN p.stateHolidays sh WHERE p.isFixedDate = true AND MONTH(p.date) = :month AND DAY(p.date) = :day AND (:stateId IS NULL OR sh.state.id = :stateId) AND sh.isholiday = true")
    List<PublicHoliday> findFixedHolidaysByDayAndMonth(Integer day, Integer month, Long stateId);

    // Get all holidays with fixed date
    @Query("SELECT DISTINCT p FROM PublicHoliday p JOIN p.stateHolidays sh WHERE p.isFixedDate = true AND (:stateId IS NULL OR sh.state.id = :stateId) AND sh.isholiday = true")
    List<PublicHoliday> findAllFixedHolidays(Long stateId);

    // Checks if a public holiday exists on a specific date.
    boolean existsByDate(LocalDate date);

    // Finds a public holiday by specific date.
    Optional<PublicHoliday> findByDate(LocalDate date);

}
