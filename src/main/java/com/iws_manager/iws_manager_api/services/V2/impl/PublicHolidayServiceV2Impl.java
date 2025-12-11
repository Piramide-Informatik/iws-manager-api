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
    
    @Autowired
    public PublicHolidayServiceV2Impl(PublicHolidayRepository publicHolidayRepository,
                                      StateRepository stateRepository,
                                      StateHolidayRepository stateHolidayRepository) {
        this.publicHolidayRepository = publicHolidayRepository;
        this.stateRepository = stateRepository;
        this.stateHolidayRepository = stateHolidayRepository;
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
    public List<SimpleHolidayDTO> getSimpleHolidaysWithWeekends(Integer year) {
        if (year == null) {
            year = LocalDate.now().getYear();
        }
        
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        
        return getSimpleHolidaysInRange(startDate, endDate);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SimpleHolidayDTO> getSimpleHolidaysInRange(LocalDate startDate, LocalDate endDate) {
        validateDateRange(startDate, endDate);
        
        Map<LocalDate, String> dbHolidaysMap = getDatabaseHolidaysMap(startDate, endDate);
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
    public List<SimpleHolidayDTO> getDatabaseHolidays(Integer year) {
        if (year == null) {
            year = LocalDate.now().getYear();
        }
        
        List<PublicHoliday> holidays = publicHolidayRepository.findHolidaysByYear(year);
        return PublicHolidayMapper.toSimpleDTOList(holidays);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SimpleHolidayDTO> getDatabaseHolidaysInRange(LocalDate startDate, LocalDate endDate) {
        validateDateRange(startDate, endDate);
        
        List<PublicHoliday> holidays = publicHolidayRepository.findHolidaysBetweenDates(startDate, endDate);
        return PublicHolidayMapper.toSimpleDTOList(holidays);
    }
    
    // ========== MÉTODOS PRIVADOS AUXILIARES ==========
    
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
    private Map<LocalDate, String> getDatabaseHolidaysMap(LocalDate startDate, LocalDate endDate) {
        List<PublicHoliday> holidays = publicHolidayRepository.findHolidaysBetweenDates(startDate, endDate);
        
        return holidays.stream()
            .collect(Collectors.toMap(
                PublicHoliday::getDate,
                PublicHoliday::getName,
                (existing, replacement) -> existing // In case of duplicates, keep the first one
            ));
    }
}