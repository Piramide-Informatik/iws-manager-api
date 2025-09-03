package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.HolidayYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HolidayYearRepository extends JpaRepository<HolidayYear, Long> {
    List<HolidayYear> findByPublicHoliday_Id(Long publicHolidayId);
}
