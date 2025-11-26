package com.iws_manager.iws_manager_api.dtos.shared;

/**
 * DTO reutilizable para referencias básicas a entidades con versionado.
 * Usado en DTOs de entrada (POST/PUT) para establecer relaciones.
 */
public record BasicReferenceDTO(
        Long id,
        Integer version) {
}