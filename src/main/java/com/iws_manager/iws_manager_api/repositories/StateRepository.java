package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.State;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;


@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
    Optional<State> findByUuid(String uuid);
}