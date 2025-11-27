package com.iws_manager.iws_manager_api.dtos.shared;

public record PromoterBasicDTO(
        Long id,
        String promoterName1,
        String promoterName2,
        String promoterNo,
        String city,
        String street,
        String zipCode) {
}