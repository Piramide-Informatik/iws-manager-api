package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.PublicHoliday;
import com.iws_manager.iws_manager_api.models.State;
import com.iws_manager.iws_manager_api.models.StateHoliday;
import com.iws_manager.iws_manager_api.repositories.HolidayYearRepository;
import com.iws_manager.iws_manager_api.repositories.PublicHolidayRepository;
import com.iws_manager.iws_manager_api.repositories.StateHolidayRepository;
import com.iws_manager.iws_manager_api.repositories.StateRepository;
import com.iws_manager.iws_manager_api.services.interfaces.PublicHolidayService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PublicHolidayServiceImpl implements PublicHolidayService {
    private final PublicHolidayRepository publicHolidayRepository;
    private final StateRepository stateRepository;
    private final StateHolidayRepository stateHolidayRepository;
    @Autowired
    public PublicHolidayServiceImpl(PublicHolidayRepository publicHolidayRepository, StateRepository stateRepository, StateHolidayRepository stateHolidayRepository) {
        this.publicHolidayRepository = publicHolidayRepository;
        this.stateRepository = stateRepository;
        this.stateHolidayRepository = stateHolidayRepository;
    }

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
                .map(exisitingPublicHoliday -> {
                    exisitingPublicHoliday.setName(publicHolidayDetails.getName());
                    exisitingPublicHoliday.setDate(publicHolidayDetails.getDate());
                    exisitingPublicHoliday.setIsFixedDate(publicHolidayDetails.getIsFixedDate());
                    exisitingPublicHoliday.setSequenceNo(publicHolidayDetails.getSequenceNo());
                    return publicHolidayRepository.save(exisitingPublicHoliday);
                }).orElseThrow(()-> new RuntimeException("PublicHoliday not found with id: "+ id));
    }

    @Override
    public void delete(Long id) {
        if (id == null){
            throw new IllegalArgumentException("Id cannot be null");
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

}
