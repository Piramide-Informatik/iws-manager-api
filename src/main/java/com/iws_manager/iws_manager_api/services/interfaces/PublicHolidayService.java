package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.PublicHoliday;

import java.util.List;
import java.util.Optional;

public interface PublicHolidayService {
    PublicHoliday create(PublicHoliday publicHoliday);
    Optional<PublicHoliday> findById(Long id);
    List<PublicHoliday> findAll();
    PublicHoliday update(Long id, PublicHoliday publicHolidayDetails);
    void delete(Long id);
}
