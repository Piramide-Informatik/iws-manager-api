package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.TeamIws;
import com.iws_manager.iws_manager_api.repositories.TeamIwsRepository;
import com.iws_manager.iws_manager_api.services.interfaces.TeamIwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeamIwsServiceImpl implements TeamIwsService {
    private final TeamIwsRepository teamIwsRepository;

    @Autowired
    public TeamIwsServiceImpl(TeamIwsRepository teamIwsRepository) {
        this.teamIwsRepository = teamIwsRepository;
    }

    @Override
    public TeamIws create(TeamIws teamIws) {
        if (teamIws == null) {
            throw new IllegalArgumentException("teamIws cannot be null");
        }
        return teamIwsRepository.save(teamIws);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TeamIws> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return teamIwsRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamIws> findAll() {
        return teamIwsRepository.findAllByOrderByNameAsc();
    }

    @Override
    public TeamIws update(Long id, TeamIws teamIwsDetails) {
        if (id == null || teamIwsDetails == null) {
            throw new IllegalArgumentException("ID and teamIws details cannot be null");
        }
        return teamIwsRepository.findById(id)
                .map(existingTeamIws -> {
                    existingTeamIws.setName(teamIwsDetails.getName());
                    existingTeamIws.setTeamLeader(teamIwsDetails.getTeamLeader());
                    return teamIwsRepository.save(existingTeamIws);
                })
                .orElseThrow(() -> new RuntimeException("TeamIws not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        teamIwsRepository.deleteById(id);
    }
}
