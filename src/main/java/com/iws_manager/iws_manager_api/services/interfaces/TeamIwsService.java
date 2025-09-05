package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.TeamIws;

import java.util.List;
import java.util.Optional;

public interface TeamIwsService {
    TeamIws create(TeamIws teamIws);
    Optional<TeamIws> findById(Long id);
    List<TeamIws> findAll();
    TeamIws update(Long id, TeamIws teamIwsDetails);
    void delete(Long id);
}
