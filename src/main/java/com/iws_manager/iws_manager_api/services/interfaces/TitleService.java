package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.Title;

public interface TitleService {
    Title create(Title title);
    Optional<Title> findById(Long id);
    List<Title> findAll();
    Title update(Long id, Title titleDetails);
    void delete(Long id);
}
