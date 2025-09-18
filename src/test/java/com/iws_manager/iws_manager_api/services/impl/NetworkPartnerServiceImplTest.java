package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import com.iws_manager.iws_manager_api.models.NetworkPartner;
import com.iws_manager.iws_manager_api.repositories.NetworkPartnerRepository;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NetworkPartnerServiceImplTest {

    @Mock
    private NetworkPartnerRepository networkPartnerRepository;

    @InjectMocks
    private NetworkPartnerServiceImpl networkPartnerService;

    private NetworkPartner testNetworkPartner;

    @BeforeEach
    public void setUp() {
        testNetworkPartner = new NetworkPartner();
        testNetworkPartner.setId(1L);
        testNetworkPartner.setComment("Test Comment");
        testNetworkPartner.setPartnerno(123);
    }

    @Test
    public void createShouldSaveAndReturnNetworkPartner() {
        when(networkPartnerRepository.save(any(NetworkPartner.class))).thenReturn(testNetworkPartner);

        NetworkPartner result = networkPartnerService.create(testNetworkPartner);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(networkPartnerRepository, times(1)).save(testNetworkPartner);
    }

    @Test
    public void createShouldThrowExceptionWhenNetworkPartnerIsNull() {
        assertThrows(IllegalArgumentException.class, () -> networkPartnerService.create(null));
    }

    @Test
    public void findByIdShouldReturnNetworkPartnerWhenExists() {
        when(networkPartnerRepository.findById(1L)).thenReturn(Optional.of(testNetworkPartner));

        Optional<NetworkPartner> result = networkPartnerService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Test Comment", result.get().getComment());
    }

    @Test
    public void findByIdShouldReturnEmptyWhenNotExists() {
        when(networkPartnerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<NetworkPartner> result = networkPartnerService.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    public void findByIdShouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> networkPartnerService.findById(null));
    }

    @Test
    public void findAllShouldReturnAllNetworkPartnersOrderedByPartnerno() {
        NetworkPartner secondPartner = new NetworkPartner();
        secondPartner.setId(2L);
        secondPartner.setPartnerno(100);
        
        when(networkPartnerRepository.findAllByOrderByPartnernoAsc())
            .thenReturn(List.of(secondPartner, testNetworkPartner));

        List<NetworkPartner> result = networkPartnerService.findAll();

        assertEquals(2, result.size());
        verify(networkPartnerRepository, times(1)).findAllByOrderByPartnernoAsc();
    }

    @Test
    public void updateShouldUpdateNetworkPartner() {
        NetworkPartner updatedDetails = new NetworkPartner();
        updatedDetails.setComment("Updated Comment");
        updatedDetails.setPartnerno(456);

        when(networkPartnerRepository.findById(1L)).thenReturn(Optional.of(testNetworkPartner));
        when(networkPartnerRepository.save(any(NetworkPartner.class))).thenReturn(testNetworkPartner);

        NetworkPartner result = networkPartnerService.update(1L, updatedDetails);

        assertEquals("Updated Comment", result.getComment());
        assertEquals(456, result.getPartnerno());
        verify(networkPartnerRepository, times(1)).save(testNetworkPartner);
    }

    @Test
    public void updateShouldThrowExceptionWhenNetworkPartnerNotFound() {
        NetworkPartner updatedDetails = new NetworkPartner();
        when(networkPartnerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> networkPartnerService.update(1L, updatedDetails));
    }

    @Test
    public void updateShouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> networkPartnerService.update(null, new NetworkPartner()));
    }

    @Test
    public void updateShouldThrowExceptionWhenDetailsIsNull() {
        assertThrows(IllegalArgumentException.class, () -> networkPartnerService.update(1L, null));
    }

    @Test
    public void deleteShouldDeleteNetworkPartner() {
        when(networkPartnerRepository.existsById(1L)).thenReturn(true);
        
        networkPartnerService.delete(1L);
        
        verify(networkPartnerRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteShouldThrowExceptionWhenNetworkPartnerNotExists() {
        when(networkPartnerRepository.existsById(1L)).thenReturn(false);
        
        assertThrows(EntityNotFoundException.class, () -> networkPartnerService.delete(1L));
    }

    @Test
    public void deleteShouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> networkPartnerService.delete(null));
    }

    @Test
    public void findByNetworkIdShouldReturnNetworkPartnersOrdered() {
        when(networkPartnerRepository.findByNetworkIdOrderByPartnernoAsc(1L))
            .thenReturn(List.of(testNetworkPartner));

        List<NetworkPartner> result = networkPartnerService.findByNetworkId(1L);

        assertEquals(1, result.size());
        verify(networkPartnerRepository, times(1)).findByNetworkIdOrderByPartnernoAsc(1L);
    }

    @Test
    public void findByNetworkIdShouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> networkPartnerService.findByNetworkId(null));
    }

    @Test
    public void findByPartnerIdShouldReturnNetworkPartnersOrdered() {
        when(networkPartnerRepository.findByPartnerIdOrderByPartnernoAsc(1L))
            .thenReturn(List.of(testNetworkPartner));

        List<NetworkPartner> result = networkPartnerService.findByPartnerId(1L);

        assertEquals(1, result.size());
        verify(networkPartnerRepository, times(1)).findByPartnerIdOrderByPartnernoAsc(1L);
    }

    @Test
    public void findByPartnerIdShouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> networkPartnerService.findByPartnerId(null));
    }

    @Test
    public void findByContactIdShouldReturnNetworkPartnersOrdered() {
        when(networkPartnerRepository.findByContactIdOrderByPartnernoAsc(1L))
            .thenReturn(List.of(testNetworkPartner));

        List<NetworkPartner> result = networkPartnerService.findByContactId(1L);

        assertEquals(1, result.size());
        verify(networkPartnerRepository, times(1)).findByContactIdOrderByPartnernoAsc(1L);
    }

    @Test
    public void findByContactIdShouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> networkPartnerService.findByContactId(null));
    }

    @Test
    public void findAllShouldReturnEmptyListWhenNoNetworkPartners() {
        when(networkPartnerRepository.findAllByOrderByPartnernoAsc()).thenReturn(List.of());

        List<NetworkPartner> result = networkPartnerService.findAll();

        assertTrue(result.isEmpty());
        verify(networkPartnerRepository, times(1)).findAllByOrderByPartnernoAsc();
    }

    @Test
    public void findByNetworkIdShouldReturnEmptyListWhenNoResults() {
        when(networkPartnerRepository.findByNetworkIdOrderByPartnernoAsc(1L)).thenReturn(List.of());

        List<NetworkPartner> result = networkPartnerService.findByNetworkId(1L);

        assertTrue(result.isEmpty());
        verify(networkPartnerRepository, times(1)).findByNetworkIdOrderByPartnernoAsc(1L);
    }
}