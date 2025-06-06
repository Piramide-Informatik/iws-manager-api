package com.iws_manager.iws_manager_api.services.interfaces;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.ContactPerson;

public interface ContactPersonService {
    ContactPerson create(ContactPerson contactPerson);
    Optional<ContactPerson> findById(Long id);
    List<ContactPerson> findAll();
    ContactPerson update(Long id, ContactPerson contactPersonDetails);
    void delete(Long id);
}
