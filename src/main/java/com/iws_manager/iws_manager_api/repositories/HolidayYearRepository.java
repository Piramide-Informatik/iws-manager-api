package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.HolidayYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

public interface HolidayYearRepository extends JpaRepository<HolidayYear, Long> {
    @EntityGraph(attributePaths = { "publicHoliday" })
    List<HolidayYear> findByPublicHoliday_Id(Long publicHolidayId);
}
