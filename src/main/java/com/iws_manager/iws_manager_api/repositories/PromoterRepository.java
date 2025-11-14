package com.iws_manager.iws_manager_api.repositories;

import java.util.List;

import com.iws_manager.iws_manager_api.models.Promoter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoterRepository extends JpaRepository<Promoter, Long> {
    @EntityGraph(attributePaths = {"country"})
    List<Promoter> findAllByOrderByPromoterNoAsc();
    
    @EntityGraph(attributePaths = {"country"})
    List<Promoter> findByCity(String city);

    @EntityGraph(attributePaths = {"country"})    
    List<Promoter> findByCountryId(Long countryId);

    @EntityGraph(attributePaths = {"country"})
    List<Promoter> findByProjectPromoter(String projectPromoter);

    @EntityGraph(attributePaths = {"country"})
    List<Promoter> findByPromoterName1(String promoterName1);

    @EntityGraph(attributePaths = {"country"})
    List<Promoter> findByPromoterName2(String promoterName2);

    @EntityGraph(attributePaths = {"country"})
    List<Promoter> findByPromoterNo(String promoterNo);

    @EntityGraph(attributePaths = {"country"})
    List<Promoter> findByStreet(String street);

    @EntityGraph(attributePaths = {"country"})
    List<Promoter> findByZipCode(String zipCode);

    @EntityGraph(attributePaths = {"country"})
    List<Promoter> findByPromoterName1OrPromoterName2(String name1, String name2);

    @Query("SELECT COALESCE(MAX(CAST(p.promoterNo AS INTEGER)), 0) FROM Promoter p WHERE p.promoterNo IS NOT NULL")
    Integer findMaxPromoterNo();

    // CREATION - verify if projectPromoter or promoterName1 exist (case-insensitive)
    @Query("SELECT COUNT(p) > 0 FROM Promoter p WHERE UPPER(p.projectPromoter) = UPPER(:projectPromoter) OR UPPER(p.promoterName1) = UPPER(:promoterName1)")
    boolean existsByProjectPromoterOrPromoterName1IgnoreCase(@Param("projectPromoter") String projectPromoter, 
                                                           @Param("promoterName1") String promoterName1);
    
    // UPDATING - verify if projectPromoter or promoterName1 exist with exception of the same item (case-insensitive)
    @Query("SELECT COUNT(p) > 0 FROM Promoter p WHERE (UPPER(p.projectPromoter) = UPPER(:projectPromoter) OR UPPER(p.promoterName1) = UPPER(:promoterName1)) AND p.id != :excludeId")
    boolean existsByProjectPromoterOrPromoterName1IgnoreCaseAndIdNot(@Param("projectPromoter") String projectPromoter, 
                                                                   @Param("promoterName1") String promoterName1, 
                                                                   @Param("excludeId") Long excludeId);
}