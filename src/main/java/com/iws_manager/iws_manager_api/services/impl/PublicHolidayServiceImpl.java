package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.PublicHoliday;
import com.iws_manager.iws_manager_api.repositories.PublicHolidayRepository;
import com.iws_manager.iws_manager_api.services.interfaces.PublicHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PublicHolidayServiceImpl implements PublicHolidayService {
    private final PublicHolidayRepository publicHolidayRepository;

    @Autowired
    public PublicHolidayServiceImpl(PublicHolidayRepository publicHolidayRepository) {
        this.publicHolidayRepository = publicHolidayRepository;
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
        return publicHolidayRepository.findAll();
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
                    exisitingPublicHoliday.setStates(publicHolidayDetails.getStates());
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
}
