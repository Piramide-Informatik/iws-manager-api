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

    @Mock
    private PromoterRepository promoterRepository;

    @InjectMocks
    private PromoterServiceImpl promoterService;

    private Promoter promoter;

    @BeforeEach
    void setUp() {
        promoter = new Promoter();
        promoter.setCity("CityX");
        promoter.setCountry(new Country());
        promoter.setProjectPromoter("ProjectX");
        promoter.setPromoterName1("Name1");
        promoter.setPromoterName2("Name2");
        promoter.setPromoterNo("123");
        promoter.setStreet("StreetX");
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
        when(promoterRepository.findAllByOrderByProjectPromoterAsc()).thenReturn(List.of(promoter));

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
    void deleteShouldCallRepository() {
        promoterService.delete(1L);
        verify(promoterRepository).deleteById(1L);
    }

    @Test
    void deleteShouldThrowWhenIdNull() {
        assertThrows(IllegalArgumentException.class, () -> promoterService.delete(null));
    }

    @Test
    void getByCityShouldReturnList() {
        when(promoterRepository.findByCity("CityX")).thenReturn(List.of(promoter));

        List<Promoter> result = promoterService.getByCity("CityX");

        assertEquals(1, result.size());
    }

    @Test
    void getByCountryidShouldReturnList() {
        when(promoterRepository.findByCountryid(1L)).thenReturn(List.of(promoter));

        List<Promoter> result = promoterService.getByCountryid(1L);

        assertEquals(1, result.size());
    }

    @Test
    void getByProjectPromoterShouldReturnList() {
        when(promoterRepository.findByProjectPromoter("ProjectX")).thenReturn(List.of(promoter));

        List<Promoter> result = promoterService.getByProjectPromoter("ProjectX");

        assertEquals(1, result.size());
    }

    @Test
    void getByPromoterName1ShouldReturnList() {
        when(promoterRepository.findByPromoterName1("Name1")).thenReturn(List.of(promoter));

        List<Promoter> result = promoterService.getByPromoterName1("Name1");

        assertEquals(1, result.size());
    }

    @Test
    void getByPromoterName2ShouldReturnList() {
        when(promoterRepository.findByPromoterName2("Name2")).thenReturn(List.of(promoter));

        List<Promoter> result = promoterService.getByPromoterName2("Name2");

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
        when(promoterRepository.findByStreet("StreetX")).thenReturn(List.of(promoter));

        List<Promoter> result = promoterService.getByStreet("StreetX");

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
        when(promoterRepository.findByPromoterName1OrPromoterName2("Name1", "Name2"))
                .thenReturn(List.of(promoter));

        List<Promoter> result = promoterService.getByPromoterName1OrPromoterName2("Name1", "Name2");

        assertEquals(1, result.size());
    }
}
