package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.Text;

public interface TextService {
    Text create(Text text);
    Optional<Text> findById(Long id);
    List<Text> findAll();
    Text update(Long id, Text textDetails);
    void delete(Long id);
}