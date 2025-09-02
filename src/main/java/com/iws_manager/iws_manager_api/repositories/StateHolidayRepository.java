package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.StateHoliday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateHolidayRepository extends JpaRepository<StateHoliday, Long> {
    List<StateHoliday> findByPublicHoliday_Id(Long publicHolidayId);
    void deleteByPublicHoliday_Id(Long publicHolidayId);
}
