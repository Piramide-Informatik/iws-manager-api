package com.iws_manager.iws_manager_api.repositories;

import com.iws_manager.iws_manager_api.models.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;


@Repository
public interface TitleRepository extends JpaRepository<Title, Integer> {
    Optional<Title> findByUuid(String uuid);
}