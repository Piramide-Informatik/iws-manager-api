package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.HolidayYear;
import com.iws_manager.iws_manager_api.models.PublicHoliday;
import com.iws_manager.iws_manager_api.repositories.HolidayYearRepository;
import com.iws_manager.iws_manager_api.repositories.PublicHolidayRepository;
import com.iws_manager.iws_manager_api.services.interfaces.HolidayYearService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
@Service
@Transactional
public class HolidayYearServiceImpl implements HolidayYearService {
    private final HolidayYearRepository holidayYearRepository;
    private final PublicHolidayRepository publicHolidayRepository;

    @Autowired
    public HolidayYearServiceImpl(HolidayYearRepository holidayYearRepository, PublicHolidayRepository publicHolidayRepository) {
        this.holidayYearRepository = holidayYearRepository;
        this.publicHolidayRepository = publicHolidayRepository;
    }

    @Override
    public List<HolidayYear> getAll() {
        return holidayYearRepository.findAll();
    }

    @Override
    public List<HolidayYear> getByPublicHolidayId(Long publicHolidayId) {
        return holidayYearRepository.findByPublicHoliday_Id(publicHolidayId);
    }

    @Override
    @Transactional
    public HolidayYear createNextYear(Long publicHolidayId) {
        PublicHoliday ph = publicHolidayRepository.findById(publicHolidayId)
                .orElseThrow(() -> new EntityNotFoundException("PublicHoliday not found"));

        // Find the last HolidayYear by that date (or any logic you want)
        List<HolidayYear> existingYears = holidayYearRepository.findByPublicHoliday_Id(publicHolidayId);

        LocalDate lastDate = existingYears.stream()
                .map(HolidayYear::getDate)
                .max(LocalDate::compareTo)
                .orElse(ph.getDate()); // If it doesn't exist, we use the PublicHoliday date

        LocalDate nextYearDate = lastDate.plusYears(1);

        HolidayYear hy = new HolidayYear();
        hy.setDate(nextYearDate);
        hy.setWeekday(nextYearDate.getDayOfWeek().getValue()); // Monday=1 ... Sunday=7
        hy.setYear(nextYearDate);
        hy.setPublicHoliday(ph);

        return holidayYearRepository.save(hy);
    }
}
