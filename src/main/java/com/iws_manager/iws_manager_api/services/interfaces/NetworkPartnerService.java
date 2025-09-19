package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.NetworkPartner;

public interface NetworkPartnerService {
    NetworkPartner create(NetworkPartner networkPartner);
    Optional<NetworkPartner> findById(Long id);
    List<NetworkPartner> findAll();
    NetworkPartner update(Long id, NetworkPartner networkPartnerDetails);
    void delete(Long id);
    
    List<NetworkPartner> findByNetworkId(Long networkId);
    List<NetworkPartner> findByPartnerId(Long partnerId);
    List<NetworkPartner> findByContactId(Long contactId);
}