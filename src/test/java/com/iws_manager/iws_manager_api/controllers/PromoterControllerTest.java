package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Promoter;
import com.iws_manager.iws_manager_api.services.interfaces.PromoterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PromoterControllerTest {

    private static final Long ID = 1L;
    private static final String CITY = "La Paz";
    private static final Long COUNTRY_ID = 2L;
    private static final String PROJECT_PROMOTER = "ProjectX";
    private static final String PROMOTER1 = "Juan";
    private static final String PROMOTER2 = "Pedro";
    private static final Integer PROMOTER_NO = 123;
    private static final String STREET = "Main Street";
    private static final String ZIP_CODE = "12345";

    @Mock
    private PromoterService promoterService;

    @InjectMocks
    private PromoterController promoterController;

    private Promoter promoter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        promoter = new Promoter();
        promoter.setId(ID);
        promoter.setPromoterName1(PROMOTER1);
    }

    @Test
    void createReturnsCreatedPromoter() {
        when(promoterService.create(promoter)).thenReturn(promoter);

        ResponseEntity<?> response = promoterController.create(promoter);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(promoter, response.getBody());
        verify(promoterService, times(1)).create(promoter);
    }

    @Test
    void getByIdFound() {
        when(promoterService.findById(ID)).thenReturn(Optional.of(promoter));

        ResponseEntity<Promoter> response = promoterController.getById(ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(promoter, response.getBody());
    }

    @Test
    void getByIdNotFound() {
        when(promoterService.findById(ID)).thenReturn(Optional.empty());

        ResponseEntity<Promoter> response = promoterController.getById(ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getAllReturnsList() {
        List<Promoter> list = Arrays.asList(promoter);
        when(promoterService.findAll()).thenReturn(list);

        ResponseEntity<List<Promoter>> response = promoterController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void updateFound() {
        when(promoterService.update(ID, promoter)).thenReturn(promoter);

        ResponseEntity<Promoter> response = promoterController.update(ID, promoter);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(promoter, response.getBody());
    }

    @Test
    void updateNotFound() {
        when(promoterService.update(ID, promoter)).thenThrow(new RuntimeException());

        ResponseEntity<Promoter> response = promoterController.update(ID, promoter);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteFound() {
        ResponseEntity<Void> response = promoterController.delete(ID);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(promoterService, times(1)).delete(ID);
    }

    @Test
    void getByCityReturnsList() {
        List<Promoter> list = Arrays.asList(promoter);
        when(promoterService.getByCity(CITY)).thenReturn(list);

        ResponseEntity<List<Promoter>> response = promoterController.getByCity(CITY);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByCountryIdReturnsList() {
        List<Promoter> list = Arrays.asList(promoter);
        when(promoterService.getByCountryId(COUNTRY_ID)).thenReturn(list);

        ResponseEntity<List<Promoter>> response = promoterController.getByCountryId(COUNTRY_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByProjectPromoterReturnsList() {
        List<Promoter> list = Arrays.asList(promoter);
        when(promoterService.getByProjectPromoter(PROJECT_PROMOTER)).thenReturn(list);

        ResponseEntity<List<Promoter>> response = promoterController.getByProjectPromoter(PROJECT_PROMOTER);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByPromoterName1ReturnsList() {
        List<Promoter> list = Arrays.asList(promoter);
        when(promoterService.getByPromoterName1(PROMOTER1)).thenReturn(list);

        ResponseEntity<List<Promoter>> response = promoterController.getByPromoterName1(PROMOTER1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByPromoterName2ReturnsList() {
        List<Promoter> list = Arrays.asList(promoter);
        when(promoterService.getByPromoterName2(PROMOTER2)).thenReturn(list);

        ResponseEntity<List<Promoter>> response = promoterController.getByPromoterName2(PROMOTER2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByPromoterNoReturnsList() {
        List<Promoter> list = Arrays.asList(promoter);
        when(promoterService.getByPromoterNo(PROMOTER_NO)).thenReturn(list);

        ResponseEntity<List<Promoter>> response = promoterController.getByPromoterNo(PROMOTER_NO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByStreetReturnsList() {
        List<Promoter> list = Arrays.asList(promoter);
        when(promoterService.getByStreet(STREET)).thenReturn(list);

        ResponseEntity<List<Promoter>> response = promoterController.getByStreet(STREET);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByZipCodeReturnsList() {
        List<Promoter> list = Arrays.asList(promoter);
        when(promoterService.getByZipCode(ZIP_CODE)).thenReturn(list);

        ResponseEntity<List<Promoter>> response = promoterController.getByZipCode(ZIP_CODE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void getByPromoterName1OrPromoterName2ReturnsList() {
        List<Promoter> list = Arrays.asList(promoter);
        when(promoterService.getByPromoterName1OrPromoterName2(PROMOTER1, PROMOTER2)).thenReturn(list);

        ResponseEntity<List<Promoter>> response = promoterController.getByPromoterName1OrPromoterName2(PROMOTER1, PROMOTER2);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }
}
