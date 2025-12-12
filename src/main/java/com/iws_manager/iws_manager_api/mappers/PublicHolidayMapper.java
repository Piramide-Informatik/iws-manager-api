package com.iws_manager.iws_manager_api.mappers;

import com.iws_manager.iws_manager_api.dtos.publicholiday.PublicHolidayDTO;
import com.iws_manager.iws_manager_api.dtos.publicholiday.PublicHolidayRequestDTO;
import com.iws_manager.iws_manager_api.dtos.publicholiday.PublicHolidayWithStatesDTO;
import com.iws_manager.iws_manager_api.dtos.publicholiday.SimpleHolidayDTO;
import com.iws_manager.iws_manager_api.models.PublicHoliday;
import com.iws_manager.iws_manager_api.models.State;

import java.util.List;
import java.util.stream.Collectors;

public class PublicHolidayMapper {
    
    private PublicHolidayMapper() {
    }
    
    // ========== TO DTO ==========
    
    /**
     * Converts an entity PublicHoliday to PublicHolidayDTO
     */
    public static PublicHolidayDTO toDTO(PublicHoliday entity) {
        if (entity == null) {
            return null;
        }
        
        return new PublicHolidayDTO(
            entity.getId(),
            entity.getDate(),
            entity.getName(),
            entity.getIsFixedDate(),
            entity.getSequenceNo(),
            entity.getVersion()
        );
    }
    
    /**
     * Converts a list of PublicHoliday entities to a list of PublicHolidayDTO
     */
    public static List<PublicHolidayDTO> toDTOList(List<PublicHoliday> entities) {
        if (entities == null) {
            return List.of();
        }
        
        return entities.stream()
            .map(PublicHolidayMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Converts an entity PublicHoliday to SimpleHolidayDTO
     */
    public static SimpleHolidayDTO toSimpleDTO(PublicHoliday entity) {
        if (entity == null) {
            return null;
        }
        
        return SimpleHolidayDTO.fromHoliday(
            entity.getName(),
            entity.getDate()
        );
    }
    
    /**
     * Converts a list of PublicHoliday entities to a list of SimpleHolidayDTO
     */
    public static List<SimpleHolidayDTO> toSimpleDTOList(List<PublicHoliday> entities) {
        if (entities == null) {
            return List.of();
        }
        
        return entities.stream()
            .map(PublicHolidayMapper::toSimpleDTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Converts an entity PublicHoliday with states to a DTO with states
     */
    public static PublicHolidayWithStatesDTO toWithStatesDTO(PublicHoliday entity, List<State> states) {
        if (entity == null) {
            return null;
        }
        
        // IMPORTANT: Assuming that State.isSelected() returns boolean (not Boolean)
        // If it returns Boolean, change to: .filter(state -> Boolean.TRUE.equals(state.isSelected()))
        var stateRefs = states.stream()
            .filter(State::isSelected)
            .map(state -> new com.iws_manager.iws_manager_api.dtos.shared.BasicReferenceDTO(
                state.getId(), 
                state.getVersion()
            ))
            .collect(Collectors.toList());
        
        return new PublicHolidayWithStatesDTO(
            entity.getId(),
            entity.getDate(),
            entity.getName(),
            entity.getIsFixedDate(),
            entity.getSequenceNo(),
            entity.getVersion(),
            stateRefs
        );
    }
    
    // ========== TO ENTITY ==========
    
    /**
     * Converts a PublicHolidayRequestDTO to a PublicHoliday entity
     */
    public static PublicHoliday toEntity(PublicHolidayRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        PublicHoliday entity = new PublicHoliday();
        entity.setDate(dto.date());
        entity.setName(dto.name());
        entity.setIsFixedDate(dto.isFixedDate());
        entity.setSequenceNo(dto.sequenceNo());
        
        return entity;
    }
    
    /**
     * Updates an existing PublicHoliday entity with data from a PublicHolidayRequestDTO
     */
    public static void updateEntity(PublicHoliday entity, PublicHolidayRequestDTO dto) {
        if (entity == null || dto == null) {
            return;
        }
        
        // Just update if the value is not null
        if (dto.date() != null) {
            entity.setDate(dto.date());
        }
        if (dto.name() != null) {
            entity.setName(dto.name());
        }
        if (dto.isFixedDate() != null) {
            entity.setIsFixedDate(dto.isFixedDate());
        }
        if (dto.sequenceNo() != null) {
            entity.setSequenceNo(dto.sequenceNo());
        }
    }
    
    /**
     * Creates a new PublicHoliday entity from a PublicHolidayRequestDTO
     * Assigns default values if they are null
     */
    public static PublicHoliday toEntityWithDefaults(PublicHolidayRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        PublicHoliday entity = new PublicHoliday();
        
        // Required fields (according to your business logic)
        entity.setDate(dto.date());
        entity.setName(dto.name());
        
        // Default values
        entity.setIsFixedDate(dto.isFixedDate() != null ? dto.isFixedDate() : false);
        entity.setSequenceNo(dto.sequenceNo() != null ? dto.sequenceNo() : 0);
        
        return entity;
    }
    
    // ========== Special methods to get SimpleHolidayDTO including Saturdays and Sundays ==========
    
    /**
     * Creates a SimpleHolidayDTO for Saturday
     */
    public static SimpleHolidayDTO createSaturdayDTO(java.time.LocalDate date) {
        return SimpleHolidayDTO.forSaturday(date);
    }
    
    /**
     * Creates a SimpleHolidayDTO for Sunday
     */
    public static SimpleHolidayDTO createSundayDTO(java.time.LocalDate date) {
        return SimpleHolidayDTO.forSunday(date);
    }
    
    /**
     * Creates a SimpleHolidayDTO for a holiday
     */
    public static SimpleHolidayDTO createHolidayDTO(String name, java.time.LocalDate date) {
        return SimpleHolidayDTO.fromHoliday(name, date);
    }
}