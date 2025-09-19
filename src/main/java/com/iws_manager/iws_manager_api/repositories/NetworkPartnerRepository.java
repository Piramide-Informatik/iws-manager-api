package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.NetworkPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NetworkPartnerRepository extends JpaRepository<NetworkPartner, Long> {
    
    List<NetworkPartner> findAllByOrderByPartnernoAsc();
    
    List<NetworkPartner> findByNetworkId(Long networkId);
    
    List<NetworkPartner> findByPartnerId(Long partnerId);
    
    List<NetworkPartner> findByContactpersonId(Long contactPersonId);
    
    List<NetworkPartner> findByNetworkIdOrderByPartnernoAsc(Long networkId);
    List<NetworkPartner> findByPartnerIdOrderByPartnernoAsc(Long partnerId);
    List<NetworkPartner> findByContactpersonIdOrderByPartnernoAsc(Long contactPersonId);

}