package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.NetworkPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NetworkPartnerRepository extends JpaRepository<NetworkPartner, Long> {
    @EntityGraph(attributePaths = {"contact", "partner", "partner.branch", "partner.companytype", "partner.country", "partner.state", "network"})
    List<NetworkPartner> findAllByOrderByPartnernoAsc();

    @EntityGraph(attributePaths = {"contact", "partner", "partner.branch", "partner.companytype", "partner.country", "partner.state", "network"})
    List<NetworkPartner> findByNetworkIdOrderByPartnernoAsc(Long networkId);

    @EntityGraph(attributePaths = {"contact", "partner", "partner.branch", "partner.companytype", "partner.country", "partner.state", "network"})
    List<NetworkPartner> findByPartnerIdOrderByPartnernoAsc(Long partnerId);

    @EntityGraph(attributePaths = {"contact", "partner", "partner.branch", "partner.companytype", "partner.country", "partner.state", "network"})
    List<NetworkPartner> findByContactIdOrderByPartnernoAsc(Long contactPersonId);

}