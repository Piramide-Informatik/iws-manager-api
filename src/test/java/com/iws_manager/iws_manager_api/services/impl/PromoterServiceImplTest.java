package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.Country;
import com.iws_manager.iws_manager_api.models.Promoter;
import com.iws_manager.iws_manager_api.repositories.PromoterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PromoterServiceImplTest {

    private static final String CITY_TEST = "CityX";
    private static final String PROJ_TEST = "ProjectX";
    private static final String NAME1_TEST = "Name1";
    private static final String NAME2_TEST = "Name2";
    private static final String STREET_TEST = "StreetX";

    @Mock
    private PromoterRepository promoterRepository;

    @InjectMocks
    private PromoterServiceImpl promoterService;

    private Promoter promoter;

    @BeforeEach
    void setUp() {
        promoter = new Promoter();
        promoter.setCity(CITY_TEST);
        promoter.setCountry(new Country());
        promoter.setProjectPromoter(PROJ_TEST);
        promoter.setPromoterName1(NAME1_TEST);
        promoter.setPromoterName2(NAME2_TEST);
        promoter.setPromoterNo("123");
        promoter.setStreet(STREET_TEST);
        promoter.setZipCode("0001");
    }

    @Test
    void createShouldSavePromoter() {
        when(promoterRepository.save(promoter)).thenReturn(promoter);

        Promoter result = promoterService.create(promoter);

        assertEquals(promoter, result);
        verify(promoterRepository).save(promoter);
    }

    @Test
    void createShouldThrowWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> promoterService.create(null));
    }

    @Test
    void findByIdShouldReturnPromoter() {
        when(promoterRepository.findById(1L)).thenReturn(Optional.of(promoter));

        Optional<Promoter> result = promoterService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(promoter, result.get());
    }

    @Test
    void findByIdShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> promoterService.findById(null));
    }

    @Test
    void findAllShouldReturnList() {
        when(promoterRepository.findAllByOrderByPromoterNoAsc()).thenReturn(List.of(promoter));

        List<Promoter> result = promoterService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    void updateShouldModifyAndSavePromoter() {
        Promoter updated = new Promoter();
        updated.setCity("NewCity");

        when(promoterRepository.findById(1L)).thenReturn(Optional.of(promoter));
        when(promoterRepository.save(any(Promoter.class))).thenReturn(updated);

        Promoter result = promoterService.update(1L, updated);

        assertEquals("NewCity", result.getCity());
        verify(promoterRepository).save(any(Promoter.class));
    }

    @Test
    void updateShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> promoterService.update(null, promoter));
    }

    @Test
    void updateShouldThrowWhenDetailsNull() {
        assertThrows(IllegalArgumentException.class, () -> promoterService.update(1L, null));
    }

    @Test
    void updateShouldThrowWhenNotFound() {
        when(promoterRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> promoterService.update(99L, promoter));
    }

    @Test
    void deleteShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> promoterService.delete(null));
    }

    @Test
    void getByCityShouldReturnList() {
        when(promoterRepository.findByCity(CITY_TEST)).thenReturn(List.of(promoter));

        List<Promoter> result = promoterService.getByCity(CITY_TEST);

        assertEquals(1, result.size());
    }

    @Test
    void getByCountryidShouldReturnList() {
        when(promoterRepository.findByCountryId(1L)).thenReturn(List.of(promoter));

        List<Promoter> result = promoterService.getByCountryId(1L);

        assertEquals(1, result.size());
    }

    @Test
    void getByProjectPromoterShouldReturnList() {
        when(promoterRepository.findByProjectPromoter(PROJ_TEST)).thenReturn(List.of(promoter));

        List<Promoter> result = promoterService.getByProjectPromoter(PROJ_TEST);

        assertEquals(1, result.size());
    }

    @Test
    void getByPromoterName1ShouldReturnList() {
        when(promoterRepository.findByPromoterName1(NAME1_TEST)).thenReturn(List.of(promoter));

        List<Promoter> result = promoterService.getByPromoterName1(NAME1_TEST);

        assertEquals(1, result.size());
    }

    @Test
    void getByPromoterName2ShouldReturnList() {
        when(promoterRepository.findByPromoterName2(NAME2_TEST)).thenReturn(List.of(promoter));

        List<Promoter> result = promoterService.getByPromoterName2(NAME2_TEST);

        assertEquals(1, result.size());
    }

    @Test
    void getByPromoterNoShouldReturnList() {
        when(promoterRepository.findByPromoterNo("123")).thenReturn(List.of(promoter));

        List<Promoter> result = promoterService.getByPromoterNo("123");

        assertEquals(1, result.size());
    }

    @Test
    void getByStreetShouldReturnList() {
        when(promoterRepository.findByStreet(STREET_TEST)).thenReturn(List.of(promoter));

        List<Promoter> result = promoterService.getByStreet(STREET_TEST);

        assertEquals(1, result.size());
    }

    @Test
    void getByZipCodeShouldReturnList() {
        when(promoterRepository.findByZipCode("0001")).thenReturn(List.of(promoter));

        List<Promoter> result = promoterService.getByZipCode("0001");

        assertEquals(1, result.size());
    }

    @Test
    void getByPromoterName1OrPromoterName2ShouldReturnList() {
        when(promoterRepository.findByPromoterName1OrPromoterName2(NAME1_TEST, NAME2_TEST))
                .thenReturn(List.of(promoter));

        List<Promoter> result = promoterService.getByPromoterName1OrPromoterName2(NAME1_TEST, NAME2_TEST);

        assertEquals(1, result.size());
    }
}
