package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.Network;

public interface NetworkService {
    Network create(Network network);
    Optional<Network> findById(Long id);
    List<Network> findAll();
    Network update(Long id, Network networkDetails);
    void delete(Long id);
}
