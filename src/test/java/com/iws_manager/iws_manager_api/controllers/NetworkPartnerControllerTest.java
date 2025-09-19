package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.NetworkPartner;
import com.iws_manager.iws_manager_api.services.interfaces.NetworkPartnerService;
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
class NetworkPartnerControllerTest {

    @Mock
    private NetworkPartnerService networkPartnerService;

    @InjectMocks
    private NetworkPartnerController networkPartnerController;

    private NetworkPartner testNetworkPartner;

    @BeforeEach
    void setUp() {
        testNetworkPartner = new NetworkPartner();
        testNetworkPartner.setId(1L);
        testNetworkPartner.setComment("Test Network Partner");
        testNetworkPartner.setPartnerno(123);
    }

    @Test
    void createNetworkPartnerShouldReturnCreated() {
        when(networkPartnerService.create(any(NetworkPartner.class))).thenReturn(testNetworkPartner);

        ResponseEntity<?> response = networkPartnerController.createNetworkPartner(testNetworkPartner);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(testNetworkPartner, response.getBody());
        verify(networkPartnerService, times(1)).create(testNetworkPartner);
    }

    @Test
    void getNetworkPartnerByIdShouldReturnNetworkPartner() {
        when(networkPartnerService.findById(1L)).thenReturn(Optional.of(testNetworkPartner));

        ResponseEntity<NetworkPartner> response = networkPartnerController.getNetworkPartnerById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testNetworkPartner, response.getBody());
    }

    @Test
    void getNetworkPartnerByIdShouldReturnNotFound() {
        when(networkPartnerService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<NetworkPartner> response = networkPartnerController.getNetworkPartnerById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllNetworkPartnersShouldReturnAllNetworkPartners() {
        List<NetworkPartner> networkPartners = Arrays.asList(testNetworkPartner, new NetworkPartner());
        when(networkPartnerService.findAll()).thenReturn(networkPartners);

        ResponseEntity<List<NetworkPartner>> response = networkPartnerController.getAllNetworkPartners();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void updateNetworkPartnerShouldReturnUpdatedNetworkPartner() {
        NetworkPartner updatedDetails = new NetworkPartner();
        updatedDetails.setComment("Updated Comment");
        
        when(networkPartnerService.update(1L, updatedDetails)).thenReturn(testNetworkPartner);

        ResponseEntity<NetworkPartner> response = networkPartnerController.updateNetworkPartner(1L, updatedDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testNetworkPartner, response.getBody());
    }

    @Test
    void updateNetworkPartnerShouldReturnNotFound() {
        NetworkPartner updatedDetails = new NetworkPartner();
        when(networkPartnerService.update(1L, updatedDetails)).thenThrow(new RuntimeException());

        ResponseEntity<NetworkPartner> response = networkPartnerController.updateNetworkPartner(1L, updatedDetails);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteNetworkPartnerShouldReturnNoContent() {
        doNothing().when(networkPartnerService).delete(1L);

        ResponseEntity<Void> response = networkPartnerController.deleteNetworkPartner(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(networkPartnerService, times(1)).delete(1L);
    }

    @Test
    void getNetworkPartnersByNetworkIdShouldReturnNetworkPartners() {
        List<NetworkPartner> networkPartners = Arrays.asList(testNetworkPartner);
        when(networkPartnerService.findByNetworkId(1L)).thenReturn(networkPartners);

        ResponseEntity<List<NetworkPartner>> response = networkPartnerController.getNetworkPartnersByNetworkId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(networkPartners, response.getBody());
        verify(networkPartnerService, times(1)).findByNetworkId(1L);
    }

    @Test
    void getNetworkPartnersByNetworkIdShouldReturnBadRequest() {
        when(networkPartnerService.findByNetworkId(-1L)).thenThrow(new IllegalArgumentException());

        ResponseEntity<List<NetworkPartner>> response = networkPartnerController.getNetworkPartnersByNetworkId(-1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(networkPartnerService, times(1)).findByNetworkId(-1L);
    }

    @Test
    void getNetworkPartnersByPartnerIdShouldReturnNetworkPartners() {
        List<NetworkPartner> networkPartners = Arrays.asList(testNetworkPartner);
        when(networkPartnerService.findByPartnerId(1L)).thenReturn(networkPartners);

        ResponseEntity<List<NetworkPartner>> response = networkPartnerController.getNetworkPartnersByPartnerId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(networkPartners, response.getBody());
        verify(networkPartnerService, times(1)).findByPartnerId(1L);
    }

    @Test
    void getNetworkPartnersByPartnerIdShouldReturnBadRequest() {
        when(networkPartnerService.findByPartnerId(-1L)).thenThrow(new IllegalArgumentException());

        ResponseEntity<List<NetworkPartner>> response = networkPartnerController.getNetworkPartnersByPartnerId(-1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(networkPartnerService, times(1)).findByPartnerId(-1L);
    }

    @Test
    void getNetworkPartnersByContactIdShouldReturnNetworkPartners() {
        List<NetworkPartner> networkPartners = Arrays.asList(testNetworkPartner);
        when(networkPartnerService.findByContactId(1L)).thenReturn(networkPartners);

        ResponseEntity<List<NetworkPartner>> response = networkPartnerController.getNetworkPartnersByContactId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(networkPartners, response.getBody());
        verify(networkPartnerService, times(1)).findByContactId(1L);
    }

    @Test
    void getNetworkPartnersByContactIdShouldReturnBadRequest() {
        when(networkPartnerService.findByContactId(-1L)).thenThrow(new IllegalArgumentException());

        ResponseEntity<List<NetworkPartner>> response = networkPartnerController.getNetworkPartnersByContactId(-1L);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(networkPartnerService, times(1)).findByContactId(-1L);
    }
}