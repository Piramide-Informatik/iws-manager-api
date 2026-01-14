package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.PublicHoliday;
import com.iws_manager.iws_manager_api.repositories.PublicHolidayRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PublicHoliday Service Implementation Tests")
public class PublicHolidayServiceImplTest {
    private static final String NAME_INDAY = "Independence Day";
    private static final String NAME_COCHR = "Corpus Christi";

    @Mock
    private PublicHolidayRepository publicHolidayRepository;

    @InjectMocks
    private PublicHolidayServiceV2Impl publicHolidayService;
    private PublicHoliday samplePublicHoliday;

    @BeforeEach
    void setUp() {
        samplePublicHoliday = new PublicHoliday();
        samplePublicHoliday.setId(1L);
        samplePublicHoliday.setName(NAME_INDAY);
    }

    @Test
    @DisplayName("Should save publicholiday successfully")
    void creatShouldReturnSavedPublicHoliday() {
        when(publicHolidayRepository.save(any(PublicHoliday.class))).thenReturn(samplePublicHoliday);

        PublicHoliday result = publicHolidayService.create(samplePublicHoliday);

        assertNotNull(result);
        assertEquals(NAME_INDAY, result.getName());
        verify(publicHolidayRepository, times(1)).save(any(PublicHoliday.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null publicholiday")
    void createShouldThrowExceptionWhenPublicHolidayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> publicHolidayService.create(null));
        verify(publicHolidayRepository, never()).save(any());

    }

    @Test
    @DisplayName("Should find PublicHoliday by ID")
    void findByIdShouldReturnPublicHolidayWhen() {
        when(publicHolidayRepository.findById(1L)).thenReturn(Optional.of(samplePublicHoliday));

        Optional<PublicHoliday> result = publicHolidayService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(NAME_INDAY, result.get().getName());
        verify(publicHolidayRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return all PublicHoliday")
    void findAllShouldReturnAllPublicHoliday() {
        PublicHoliday publicHoliday2 = new PublicHoliday();
        publicHoliday2.setId(2L);
        publicHoliday2.setName(NAME_INDAY);

        when(publicHolidayRepository.findAllByOrderByNameAsc())
                .thenReturn(Arrays.asList(samplePublicHoliday, publicHoliday2));

        List<PublicHoliday> result = publicHolidayService.findAll();

        assertEquals(2, result.size());
        verify(publicHolidayRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Should update PublicHoliday successfully")
    void updateShouldReturnUpdatedPublicHoliday() {
        PublicHoliday updatedDetails = new PublicHoliday();
        updatedDetails.setName(NAME_INDAY + " Updated");

        when(publicHolidayRepository.findById(1L)).thenReturn(Optional.of(samplePublicHoliday));
        when(publicHolidayRepository.save(any(PublicHoliday.class))).thenAnswer(inv -> inv.getArgument(0));

        PublicHoliday result = publicHolidayService.update(1L, updatedDetails);

        assertEquals("Independence Day Updated", result.getName());
        verify(publicHolidayRepository, times(1)).findById(1L);
        verify(publicHolidayRepository, times(1)).save(any(PublicHoliday.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent PublicHoliday")
    void updateShouldThrowExceptionWhenPublicHolidayNotFound() {
        when(publicHolidayRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> publicHolidayService.update(99L, new PublicHoliday()));
        verify(publicHolidayRepository, never()).save(any());
    }

    @Test
    public void updateShouldThrowExceptioWhenOptimisticLockingFails() {
        Long publicHolidayId = 1L;
        PublicHoliday currentPublicHoliday = new PublicHoliday();
        currentPublicHoliday.setId(publicHolidayId);
        currentPublicHoliday.setName(NAME_INDAY);
        currentPublicHoliday.setVersion(2);

        PublicHoliday outPublicHoliday = new PublicHoliday();
        outPublicHoliday.setId(publicHolidayId);
        outPublicHoliday.setName(NAME_COCHR);
        outPublicHoliday.setVersion(1);

        when(publicHolidayRepository.findById(publicHolidayId)).thenReturn(Optional.of(currentPublicHoliday));
        when(publicHolidayRepository.save(any(PublicHoliday.class)))
                .thenThrow(new ObjectOptimisticLockingFailureException("Concurrent modification detected",
                        new ObjectOptimisticLockingFailureException(PublicHoliday.class, publicHolidayId)));

        Exception exception = assertThrows(RuntimeException.class,
                () -> publicHolidayService.update(publicHolidayId, outPublicHoliday));

        assertNotNull(exception, "An exception should have been thrown");

        if (!(exception instanceof ObjectOptimisticLockingFailureException)) {
            assertNotNull(exception.getCause(), "The exception should have a cause");
            assertTrue(exception.getCause() instanceof ObjectOptimisticLockingFailureException,
                    "The cause should be ObjectOptimisticLockingFailureException");
        }

        verify(publicHolidayRepository).findById(publicHolidayId);
        verify(publicHolidayRepository).save(any(PublicHoliday.class));
    }

}