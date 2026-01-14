package com.iws_manager.iws_manager_api.services.V2.impl;

import com.iws_manager.iws_manager_api.dtos.publicholiday.SimpleHolidayDTO;
import com.iws_manager.iws_manager_api.mappers.PublicHolidayMapper;
import com.iws_manager.iws_manager_api.models.PublicHoliday;
import com.iws_manager.iws_manager_api.models.State;
import com.iws_manager.iws_manager_api.models.StateHoliday;
import com.iws_manager.iws_manager_api.repositories.PublicHolidayRepository;
import com.iws_manager.iws_manager_api.repositories.StateHolidayRepository;
import com.iws_manager.iws_manager_api.repositories.StateRepository;
import com.iws_manager.iws_manager_api.services.V2.interfaces.PublicHolidayServiceV2;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service("publicHolidayServiceV2")
@Transactional
public class PublicHolidayServiceV2Impl implements PublicHolidayServiceV2 {

    private final PublicHolidayRepository publicHolidayRepository;
    private final StateRepository stateRepository;
    private final StateHolidayRepository stateHolidayRepository;
    private final com.iws_manager.iws_manager_api.repositories.HolidayYearRepository holidayYearRepository;

    @Autowired
    public PublicHolidayServiceV2Impl(PublicHolidayRepository publicHolidayRepository,
            StateRepository stateRepository,
            StateHolidayRepository stateHolidayRepository,
            com.iws_manager.iws_manager_api.repositories.HolidayYearRepository holidayYearRepository) {
        this.publicHolidayRepository = publicHolidayRepository;
        this.stateRepository = stateRepository;
        this.stateHolidayRepository = stateHolidayRepository;
        this.holidayYearRepository = holidayYearRepository;
    }

    // ========== MÉTODOS V1 (IDÉNTICOS A LA IMPLEMENTACIÓN V1) ==========

    @Override
    public PublicHoliday create(PublicHoliday publicHoliday) {
        if (publicHoliday == null) {
            throw new IllegalArgumentException("PublicHoliday cannot be null");
        }
        return publicHolidayRepository.save(publicHoliday);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PublicHoliday> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        return publicHolidayRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicHoliday> findAll() {
        return publicHolidayRepository.findAllByOrderByNameAsc();
    }

    @Override
    public PublicHoliday update(Long id, PublicHoliday publicHolidayDetails) {
        if (id == null || publicHolidayDetails == null) {
            throw new IllegalArgumentException("Id and Details cannot be null");
        }
        return publicHolidayRepository.findById(id)
                .map(existingPublicHoliday -> {
                    existingPublicHoliday.setName(publicHolidayDetails.getName());
                    existingPublicHoliday.setDate(publicHolidayDetails.getDate());
                    existingPublicHoliday.setIsFixedDate(publicHolidayDetails.getIsFixedDate());
                    existingPublicHoliday.setSequenceNo(publicHolidayDetails.getSequenceNo());
                    return publicHolidayRepository.save(existingPublicHoliday);
                }).orElseThrow(() -> new RuntimeException("PublicHoliday not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        if (!publicHolidayRepository.existsById(id)) {
            throw new EntityNotFoundException("PublicHoliday not found with id: " + id);
        }

        publicHolidayRepository.deleteById(id);
    }

    @Override
    public List<State> getStatesWithSelection(Long publicHolidayId) {
        List<State> allStates = stateRepository.findAllByOrderByNameAsc();
        List<StateHoliday> links = stateHolidayRepository.findByPublicHoliday_Id(publicHolidayId);
        for (State st : allStates) {
            boolean selected = links.stream()
                    .anyMatch(sh -> sh.getState().getId().equals(st.getId())
                            && Boolean.TRUE.equals(sh.getIsholiday()));
            st.setSelected(selected);
        }
        return allStates;
    }

    @Override
    @Transactional
    public void saveStateSelections(Long publicHolidayId, List<Long> selectedStateIds) {
        stateHolidayRepository.deleteByPublicHoliday_Id(publicHolidayId);

        PublicHoliday ph = publicHolidayRepository.findById(publicHolidayId)
                .orElseThrow(() -> new EntityNotFoundException("PublicHoliday not found"));

        List<StateHoliday> newSelections = selectedStateIds.stream().map(stateId -> {
            StateHoliday sh = new StateHoliday();
            sh.setPublicHoliday(ph);

            State st = stateRepository.findById(stateId)
                    .orElseThrow(() -> new EntityNotFoundException("State not found: " + stateId));
            sh.setState(st);

            sh.setIsholiday(true);
            return sh;
        }).toList();

        stateHolidayRepository.saveAll(newSelections);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicHoliday> findAllByOrderBySequenceNo() {
        return publicHolidayRepository.findAllByOrderBySequenceNoAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicHoliday> findAllByOrderBySequenceNoDesc() {
        return publicHolidayRepository.findAllByOrderBySequenceNoDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getNextSequenceNo() {
        Long maxSequenceNo = publicHolidayRepository.findMaxSequenceNo();
        return (maxSequenceNo != null ? maxSequenceNo + 1 : 1);
    }

    // ========== MÉTODOS V2 (NUEVOS) ==========

    @Override
    @Transactional(readOnly = true)
    public List<SimpleHolidayDTO> getSimpleHolidaysWithWeekends(Integer year, Long stateId) {
        if (year == null) {
            year = LocalDate.now().getYear();
        }

        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        return getSimpleHolidaysInRange(startDate, endDate, stateId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimpleHolidayDTO> getSimpleHolidaysInRange(LocalDate startDate, LocalDate endDate, Long stateId) {
        validateDateRange(startDate, endDate);

        Map<LocalDate, String> dbHolidaysMap = getDatabaseHolidaysMap(startDate, endDate, stateId);
        List<SimpleHolidayDTO> result = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            String holidayName = dbHolidaysMap.get(currentDate);
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

            if (holidayName != null) {
                // Hay un feriado en esta fecha
                result.add(PublicHolidayMapper.createHolidayDTO(holidayName, currentDate));
            } else if (dayOfWeek == DayOfWeek.SATURDAY) {
                // Es sábado sin feriado
                result.add(PublicHolidayMapper.createSaturdayDTO(currentDate));
            } else if (dayOfWeek == DayOfWeek.SUNDAY) {
                // Es domingo sin feriado
                result.add(PublicHolidayMapper.createSundayDTO(currentDate));
            }
            // Los días laborables normales (lunes a viernes sin feriado) no se incluyen

            currentDate = currentDate.plusDays(1);
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimpleHolidayDTO> getWeekendsOnly(Integer year) {
        if (year == null) {
            year = LocalDate.now().getYear();
        }

        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        return getWeekendsInRange(startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimpleHolidayDTO> getWeekendsInRange(LocalDate startDate, LocalDate endDate) {
        validateDateRange(startDate, endDate);

        List<SimpleHolidayDTO> result = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

            if (dayOfWeek == DayOfWeek.SATURDAY) {
                result.add(PublicHolidayMapper.createSaturdayDTO(currentDate));
            } else if (dayOfWeek == DayOfWeek.SUNDAY) {
                result.add(PublicHolidayMapper.createSundayDTO(currentDate));
            }

            currentDate = currentDate.plusDays(1);
        }

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimpleHolidayDTO> getDatabaseHolidays(Integer year, Long stateId) {
        if (year == null) {
            year = LocalDate.now().getYear();
        }

        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);

        return getDatabaseHolidaysInRange(startDate, endDate, stateId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SimpleHolidayDTO> getDatabaseHolidaysInRange(LocalDate startDate, LocalDate endDate, Long stateId) {
        validateDateRange(startDate, endDate);

        Map<LocalDate, String> holidaysMap = getDatabaseHolidaysMap(startDate, endDate, stateId);

        return holidaysMap.entrySet().stream()
                .map(entry -> PublicHolidayMapper.createHolidayDTO(entry.getValue(), entry.getKey()))
                .sorted(Comparator.comparing(SimpleHolidayDTO::date))
                .collect(Collectors.toList());
    }

    /**
     * Validates that start date is not after end date
     */
    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
    }

    /**
     * Gets database holidays as a Map for faster lookup
     */
    private Map<LocalDate, String> getDatabaseHolidaysMap(LocalDate startDate, LocalDate endDate, Long stateId) {
        Map<LocalDate, String> holidaysMap = new HashMap<>();

        // 1. From PublicHoliday (exact date)
        List<PublicHoliday> exactDateHolidays = publicHolidayRepository.findHolidaysBetweenDates(startDate, endDate,
                stateId);
        exactDateHolidays.forEach(holiday -> holidaysMap.put(holiday.getDate(), holiday.getName()));

        // 2. From HolidayYear (no fix dates)
        List<com.iws_manager.iws_manager_api.models.HolidayYear> yearHolidays = holidayYearRepository
                .findHolidaysBetweenDates(startDate, endDate, stateId);
        yearHolidays.forEach(hy -> holidaysMap.put(hy.getDate(), hy.getPublicHoliday().getName()));

        // 3. From Fixed holidays
        addFixedHolidaysToMap(holidaysMap, startDate, endDate, stateId);

        return holidaysMap;
    }

    /**
     * Adds fixed holidays to the map for the given date range
     */
    private void addFixedHolidaysToMap(Map<LocalDate, String> holidaysMap, LocalDate startDate, LocalDate endDate,
            Long stateId) {
        List<PublicHoliday> fixedHolidays = publicHolidayRepository.findAllFixedHolidays(stateId);

        int startYear = startDate.getYear();
        int endYear = endDate.getYear();

        for (int year = startYear; year <= endYear; year++) {
            for (PublicHoliday fixedHoliday : fixedHolidays) {
                LocalDate originalDate = fixedHoliday.getDate();
                LocalDate holidayDate = LocalDate.of(year, originalDate.getMonth(), originalDate.getDayOfMonth());

                if (!holidayDate.isBefore(startDate) && !holidayDate.isAfter(endDate)) {
                    holidaysMap.putIfAbsent(holidayDate, fixedHoliday.getName());
                }
            }
        }
    }
}