package com.iws_manager.iws_manager_api.dtos.shared;

public record ProjectReferenceDTO(
    Long id,
    String projectName,
    String projectLabel,
    String title,
    Integer version
) {}