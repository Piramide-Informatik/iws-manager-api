package com.iws_manager.iws_manager_api.services.v2.interfaces;

import com.iws_manager.iws_manager_api.dtos.publicholiday.SimpleHolidayDTO;
import com.iws_manager.iws_manager_api.models.PublicHoliday;
import com.iws_manager.iws_manager_api.models.State;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PublicHolidayServiceV2 {
    
   // ========== V1 ==========
    PublicHoliday create(PublicHoliday publicHoliday);
    Optional<PublicHoliday> findById(Long id);
    List<PublicHoliday> findAll();
    PublicHoliday update(Long id, PublicHoliday publicHolidayDetails);
    void delete(Long id);

    List<State> getStatesWithSelection(Long publicHolidayId);
    void saveStateSelections(Long publicHolidayId, List<Long> selectedStateIds);

    List<PublicHoliday> findAllByOrderBySequenceNo();
    List<PublicHoliday> findAllByOrderBySequenceNoDesc();
    Long getNextSequenceNo();

    // ========== V2 ==========
    List<SimpleHolidayDTO> getSimpleHolidaysWithWeekends(Integer year);
    List<SimpleHolidayDTO> getSimpleHolidaysInRange(LocalDate startDate, LocalDate endDate);
    List<SimpleHolidayDTO> getWeekendsOnly(Integer year);
    List<SimpleHolidayDTO> getWeekendsInRange(LocalDate startDate, LocalDate endDate);
    List<SimpleHolidayDTO> getDatabaseHolidays(Integer year);
    List<SimpleHolidayDTO> getDatabaseHolidaysInRange(LocalDate startDate, LocalDate endDate);
}