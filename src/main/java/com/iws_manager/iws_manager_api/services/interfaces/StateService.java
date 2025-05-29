package com.iws_manager.iws_manager_api.services.interfaces;

import com.iws_manager.iws_manager_api.models.State;

import java.util.List;
import java.util.Optional;

public interface StateService {
    State create(State state);
    Optional<State> findById(Long id);
    List<State> findAll();
    State update(Long id, State stateDetails);
    void delete(Long id);
}
