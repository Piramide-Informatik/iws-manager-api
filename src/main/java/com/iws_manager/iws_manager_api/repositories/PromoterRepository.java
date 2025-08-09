package com.iws_manager.iws_manager_api.repositories;

import java.util.List;

import com.iws_manager.iws_manager_api.models.Promoter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoterRepository extends JpaRepository<Promoter, Long> {
    
    List<Promoter> findAllByOrderByProjectPromoterAsc();
    
    List<Promoter> findByCity(String city);
    List<Promoter> findByCountryid(Long countryId);
    List<Promoter> findByProjectPromoter(String projectPromoter);
    List<Promoter> findByPromoterName1(String promoterName1);
    List<Promoter> findByPromoterName2(String promoterName2);
    List<Promoter> findByPromoterNo(String promoterNo);
    List<Promoter> findByStreet(String street);
    List<Promoter> findByZipCode(String zipCode);

    List<Promoter> findByPromoterName1OrPromoterName2(String name1, String name2);
}