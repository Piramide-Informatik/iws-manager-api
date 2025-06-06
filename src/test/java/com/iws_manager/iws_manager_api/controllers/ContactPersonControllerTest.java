package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.ContactPerson;
import com.iws_manager.iws_manager_api.services.interfaces.ContactPersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactPersonControllerTest {

    @Mock
    private ContactPersonService contactPersonService;

    @InjectMocks
    private ContactPersonController contactPersonController;

    private ContactPerson testContactPerson;

    @BeforeEach
    void setUp() {
        testContactPerson = new ContactPerson();
        testContactPerson.setId(1L);
        testContactPerson.setFirstName("Test ContactPerson");
    }

    @Test
    void createContactPersonShouldReturnCreated() {
        when(contactPersonService.create(any(ContactPerson.class))).thenReturn(testContactPerson);

        ResponseEntity<?> response = contactPersonController.createContactPerson(testContactPerson);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testContactPerson, response.getBody());
        verify(contactPersonService, times(1)).create(testContactPerson);
    }

    @Test
    void getContactPersonByIdShouldReturnContactPerson() {
        when(contactPersonService.findById(1L)).thenReturn(Optional.of(testContactPerson));

        ResponseEntity<ContactPerson> response = contactPersonController.getContactPersonById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testContactPerson, response.getBody());
    }

    @Test
    void getContactPersonByIdShouldReturnNotFound() {
        when(contactPersonService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<ContactPerson> response = contactPersonController.getContactPersonById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllContactPersonsShouldReturnAllContactPersons() {
        List<ContactPerson> contactPersons = Arrays.asList(testContactPerson, new ContactPerson());
        when(contactPersonService.findAll()).thenReturn(contactPersons);

        ResponseEntity<List<ContactPerson>> response = contactPersonController.getAllContactPersons();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void updateContactPersonShouldReturnUpdatedContactPerson() {
        ContactPerson updatedDetails = new ContactPerson();
        updatedDetails.setFirstName("Updated Name");
        
        when(contactPersonService.update(1L, updatedDetails)).thenReturn(testContactPerson);

        ResponseEntity<ContactPerson> response = contactPersonController.updateContactPerson(1L, updatedDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testContactPerson, response.getBody());
    }

    @Test
    void updateContactPersonShouldReturnNotFound() {
        ContactPerson updatedDetails = new ContactPerson();
        when(contactPersonService.update(1L, updatedDetails)).thenThrow(new RuntimeException());

        ResponseEntity<ContactPerson> response = contactPersonController.updateContactPerson(1L, updatedDetails);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteContactPersonShouldReturnNoContent() {
        doNothing().when(contactPersonService).delete(1L);

        ResponseEntity<Void> response = contactPersonController.deleteContactPerson(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(contactPersonService, times(1)).delete(1L);
    }
}