package com.iws_manager.iws_manager_api.services.interfaces;

import java.time.LocalDate;

import com.iws_manager.iws_manager_api.models.HolidayYear;

import java.util.List;
import java.util.Optional;

public interface HolidayYearService {
    HolidayYear create(HolidayYear holidayYear);
    Optional<HolidayYear> findById(Long id);
    List<HolidayYear> getAll();
    HolidayYear update(Long id, HolidayYear holidayYearDetails);
    void delete(Long id);

    List<HolidayYear> getByPublicHolidayId(Long publicHolidayId);
    HolidayYear createNextYear(Long publicHolidayId);
    boolean existsByYearAndPublicHoliday(LocalDate year, Long publicHolidayId);
    List<HolidayYear> getByYear(LocalDate year);    
    List<HolidayYear> getAllOrderByYearAsc();
    List<HolidayYear> getByPublicHolidayIdOrderByYearAsc(Long publicHolidayId);
}
