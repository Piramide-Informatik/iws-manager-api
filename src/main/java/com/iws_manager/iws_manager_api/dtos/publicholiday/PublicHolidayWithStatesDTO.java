package com.iws_manager.iws_manager_api.dtos.publicholiday;

import com.iws_manager.iws_manager_api.dtos.shared.BasicReferenceDTO;
import java.time.LocalDate;
import java.util.List;

public record PublicHolidayWithStatesDTO(
    Long id,
    LocalDate date,
    String name,
    Boolean isFixedDate,
    Integer sequenceNo,
    Integer version,
    List<BasicReferenceDTO> selectedStates
) {}