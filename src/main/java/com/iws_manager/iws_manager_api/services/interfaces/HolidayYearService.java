package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.HolidayYear;

import java.util.List;

public interface HolidayYearService {
    List<HolidayYear> getAll();
    List<HolidayYear> getByPublicHolidayId(Long publicHolidayId);
    HolidayYear createNextYear(Long publicHolidayId);

}
