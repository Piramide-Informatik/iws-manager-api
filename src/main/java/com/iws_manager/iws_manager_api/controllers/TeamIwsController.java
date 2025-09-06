package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.TeamIws;
import com.iws_manager.iws_manager_api.services.interfaces.TeamIwsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/teams-iws")
public class TeamIwsController {
    private final TeamIwsService teamIwsService;

    @Autowired
    public TeamIwsController(TeamIwsService teamIwsService) {
        this.teamIwsService = teamIwsService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createTeamIws(@RequestBody TeamIws teamIws) {

        if (teamIws.getName() == null || teamIws.getName().trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Name is required");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        TeamIws createdState = teamIwsService.create(teamIws);
        return new ResponseEntity<>(createdState, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamIws> getTeamIwsById(@PathVariable Long id) {
        return teamIwsService.findById(id)
                .map(teamIws -> new ResponseEntity<>(teamIws, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<TeamIws>> getAllTeamsIws() {
        List<TeamIws> teamIws = teamIwsService.findAll();
        return new ResponseEntity<>(teamIws, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamIws> updateTeamIws(
            @PathVariable Long id,
            @RequestBody TeamIws teamIwsDetails) {
        try {
            TeamIws updatedTeamIws = teamIwsService.update(id, teamIwsDetails);
            return new ResponseEntity<>(updatedTeamIws, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteTeamIws(@PathVariable Long id) {
        teamIwsService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
