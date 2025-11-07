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
import java.util.Optional;
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
    @Transactional
    public HolidayYear create(HolidayYear holidayYear) {
        // Validations
        if (holidayYear.getPublicHoliday() != null && 
            holidayYearRepository.existsByYearAndPublicHolidayId(
                holidayYear.getYear(), holidayYear.getPublicHoliday().getId())) {
            throw new RuntimeException("Holiday year already exists for this public holiday");
        }
        return holidayYearRepository.save(holidayYear);
    }

    @Override
    public List<HolidayYear> getAll() {
        return holidayYearRepository.findAll();
    }

     @Override
    @Transactional(readOnly = true)
    public Optional<HolidayYear> findById(Long id) {
        return holidayYearRepository.findById(id);
    }

    @Override
    @Transactional
    public HolidayYear update(Long id, HolidayYear holidayYearDetails) {
        HolidayYear existingHolidayYear = holidayYearRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Holiday year not found with id: " + id));
        
        // Update fields
        existingHolidayYear.setDate(holidayYearDetails.getDate());
        existingHolidayYear.setWeekday(holidayYearDetails.getWeekday());
        existingHolidayYear.setYear(holidayYearDetails.getYear());
        
        if (holidayYearDetails.getPublicHoliday() != null) {
            PublicHoliday publicHoliday = publicHolidayRepository.findById(
                    holidayYearDetails.getPublicHoliday().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Public holiday not found"));
            existingHolidayYear.setPublicHoliday(publicHoliday);
        }
        
        return holidayYearRepository.save(existingHolidayYear);
    }

    @Override
    public List<HolidayYear> getByPublicHolidayId(Long publicHolidayId) {
        return holidayYearRepository.findByPublicHolidayId(publicHolidayId);
    }

    @Override
    @Transactional
    public HolidayYear createNextYear(Long publicHolidayId) {
        PublicHoliday ph = publicHolidayRepository.findById(publicHolidayId)
                .orElseThrow(() -> new EntityNotFoundException("PublicHoliday not found"));

        // Find the last HolidayYear by that date (or any logic you want)
        List<HolidayYear> existingYears = holidayYearRepository.findByPublicHolidayId(publicHolidayId);

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

    @Override
    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        holidayYearRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByYearAndPublicHoliday(LocalDate year, Long publicHolidayId) {
        return holidayYearRepository.existsByYearAndPublicHolidayId(year, publicHolidayId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HolidayYear> getByYear(LocalDate year) {
        return holidayYearRepository.findByYear(year);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HolidayYear> getAllOrderByYearAsc() {
        return holidayYearRepository.findAllByOrderByYearAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HolidayYear> getByPublicHolidayIdOrderByYearAsc(Long publicHolidayId) {
        return holidayYearRepository.findByPublicHolidayIdOrderByYearAsc(publicHolidayId);
    }
}
