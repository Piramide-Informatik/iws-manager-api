package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.ContactPerson;
import com.iws_manager.iws_manager_api.repositories.ContactPersonRepository;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ContactPersonServiceImplTest {

    @Mock
    private ContactPersonRepository contactPersonRepository;

    @InjectMocks
    private ContactPersonServiceImpl contactPersonService;

    private ContactPerson testContactPerson;

    @BeforeEach
    public void setUp() {
        testContactPerson = new ContactPerson();
        testContactPerson.setId(1L);
        testContactPerson.setFirstName("Test ContactPerson");
        testContactPerson.setForInvoicing(1);
    }

    @Test
    public void createShouldSaveAndReturnContactPerson() {
        when(contactPersonRepository.save(any(ContactPerson.class))).thenReturn(testContactPerson);

        ContactPerson result = contactPersonService.create(testContactPerson);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(contactPersonRepository, times(1)).save(testContactPerson);
    }

    @Test
    public void createShouldThrowExceptionWhenContactPersonIsNull() {
        assertThrows(IllegalArgumentException.class, () -> contactPersonService.create(null));
    }

    @Test
    public void findByIdShouldReturnContactPersonWhenExists() {
        when(contactPersonRepository.findById(1L)).thenReturn(Optional.of(testContactPerson));

        Optional<ContactPerson> result = contactPersonService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test ContactPerson", result.get().getFirstName());
    }

    @Test
    public void findAllShouldReturnAllContactPersons() {
        when(contactPersonRepository.findAll()).thenReturn(List.of(testContactPerson, new ContactPerson()));

        List<ContactPerson> result = contactPersonService.findAll();

        assertEquals(2, result.size());
        verify(contactPersonRepository, times(1)).findAll();
    }

    @Test
    public void updateShouldUpdateContactPerson() {
        ContactPerson updatedDetails = new ContactPerson();
        updatedDetails.setFirstName("Updated Name");

        when(contactPersonRepository.findById(1L)).thenReturn(Optional.of(testContactPerson));
        when(contactPersonRepository.save(any(ContactPerson.class))).thenReturn(testContactPerson);

        ContactPerson result = contactPersonService.update(1L, updatedDetails);

        assertEquals("Updated Name", result.getFirstName());
        verify(contactPersonRepository, times(1)).save(testContactPerson);
    }

    @Test
    public void deleteShouldDeleteContactPerson() {
        when(contactPersonRepository.existsById(1L)).thenReturn(true);
        
        contactPersonService.delete(1L);
        
        verify(contactPersonRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteShouldThrowExceptionWhenContactPersonNotExists() {
        when(contactPersonRepository.existsById(1L)).thenReturn(false);
        
        assertThrows(EntityNotFoundException.class, () -> contactPersonService.delete(1L));
    }
}