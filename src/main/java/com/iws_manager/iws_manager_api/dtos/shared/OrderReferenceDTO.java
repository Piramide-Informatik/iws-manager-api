package com.iws_manager.iws_manager_api.dtos.shared;

public record OrderReferenceDTO(
    Long id,
    String acronym,
    String orderLabel,
    Integer orderNo,
    String orderTitle,
    ProjectReferenceDTO project
) {}
