package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.Promoter;

public interface PromoterService {
    Promoter create(Promoter promoter);
    Optional<Promoter> findById(Long id);
    List<Promoter> findAll();
    Promoter update(Long id, Promoter promoterDetails);
    void delete(Long id);

    List<Promoter> getByCity(String city);
    List<Promoter> getByCountryId(Long countryId);
    List<Promoter> getByProjectPromoter(String projectPromoter);
    List<Promoter> getByPromoterName1(String promoterName1);
    List<Promoter> getByPromoterName2(String promoterName2);
    List<Promoter> getByPromoterNo(String promoterNo);
    List<Promoter> getByStreet(String street);
    List<Promoter> getByZipCode(String zipCode);

    List<Promoter> getByPromoterName1OrPromoterName2(String name1, String name2);
}
