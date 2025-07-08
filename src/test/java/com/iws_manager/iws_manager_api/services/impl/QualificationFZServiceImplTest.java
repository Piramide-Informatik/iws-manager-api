package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.QualificationFZ;
import com.iws_manager.iws_manager_api.repositories.QualificationFZRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QualificationFZServiceImplTest {

     private static final String QUALIFICATION = "Wissenschaftler";

    @Mock
    private QualificationFZRepository qualificationFZRepository;

    @InjectMocks
    private QualificationFZServiceImpl qualificationFZService;

    @Test
    void createshouldSaveQualificationSuccessfully() {
        // Arrange
        QualificationFZ input = new QualificationFZ();
        input.setQualification(QUALIFICATION);
        
        QualificationFZ saved = new QualificationFZ();
        saved.setId(1L);
        saved.setQualification(QUALIFICATION);
        
        when(qualificationFZRepository.save(input)).thenReturn(saved);

        // Act
        QualificationFZ result = qualificationFZService.create(input);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(QUALIFICATION, result.getQualification());
        verify(qualificationFZRepository).save(input);
    }

    @Test
    void createshouldThrowWhenQualificationIsEmpty() {
        // Arrange
        QualificationFZ input = new QualificationFZ();
        input.setQualification("  ");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> qualificationFZService.create(input));
        verifyNoInteractions(qualificationFZRepository);
    }

    @Test
    void findByIdshouldReturnQualificationWhenExists() {
        // Arrange
        QualificationFZ expected = new QualificationFZ();
        expected.setId(1L);
        
        when(qualificationFZRepository.findById(1L)).thenReturn(Optional.of(expected));

        // Act
        Optional<QualificationFZ> result = qualificationFZService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(qualificationFZRepository).findById(1L);
    }

    @Test
    void findByIdshouldReturnEmptyWhenNotExists() {
        // Arrange
        when(qualificationFZRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<QualificationFZ> result = qualificationFZService.findById(1L);

        // Assert
        assertTrue(result.isEmpty());
        verify(qualificationFZRepository).findById(1L);
    }

    @Test
    void findByIdshouldThrowWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> qualificationFZService.findById(null));
        verifyNoInteractions(qualificationFZRepository);
    }

    @Test
    void findAllshouldReturnOrderedQualifications() {
        // Arrange
        QualificationFZ q1 = new QualificationFZ();
        q1.setQualification("Ärzte");
        
        QualificationFZ q2 = new QualificationFZ();
        q2.setQualification("Techniker");
        
        when(qualificationFZRepository.findAllByOrderByQualificationAsc()).thenReturn(List.of(q1, q2));

        // Act
        List<QualificationFZ> result = qualificationFZService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Ärzte", result.get(0).getQualification());
        assertEquals("Techniker", result.get(1).getQualification());
        verify(qualificationFZRepository).findAllByOrderByQualificationAsc();
    }

    @Test
    void updateshouldUpdateExistingQualification() {
        // Arrange
        QualificationFZ existing = new QualificationFZ();
        existing.setId(1L);
        existing.setQualification("Old");
        
        QualificationFZ updates = new QualificationFZ();
        updates.setQualification("New");
        
        when(qualificationFZRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(qualificationFZRepository.save(existing)).thenReturn(existing);

        // Act
        QualificationFZ result = qualificationFZService.update(1L, updates);

        // Assert
        assertEquals("New", result.getQualification());
        verify(qualificationFZRepository).findById(1L);
        verify(qualificationFZRepository).save(existing);
    }

    @Test
    void updateshouldThrowWhenQualificationNotFound() {
        // Arrange
        when(qualificationFZRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> qualificationFZService.update(1L, new QualificationFZ()));

        verify(qualificationFZRepository).findById(1L);
        verify(qualificationFZRepository, never()).save(any());
    }

    @Test
    void updateshouldThrowWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> qualificationFZService.update(null, new QualificationFZ()));
        verifyNoInteractions(qualificationFZRepository);
    }

    @Test
    void updateshouldThrowWhenDetailsIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> qualificationFZService.update(1L, null));
        verifyNoInteractions(qualificationFZRepository);
    }

   @Test
    void updateshouldUpdateQualificationSuccessfully() {
        // Arrange
        Long id = 1L;
        QualificationFZ existing = new QualificationFZ();
        existing.setId(id);
        existing.setQualification("Old Qualification");
        
        QualificationFZ updates = new QualificationFZ();
        updates.setQualification("New Qualification");
        
        when(qualificationFZRepository.findById(id)).thenReturn(Optional.of(existing));
        when(qualificationFZRepository.save(existing)).thenReturn(existing);

        // Act
        QualificationFZ result = qualificationFZService.update(id, updates);

        // Assert
        assertNotNull(result);
        assertEquals("New Qualification", result.getQualification());
        verify(qualificationFZRepository).findById(id);
        verify(qualificationFZRepository).save(existing);
    }

    @Test
    void deleteshouldDeleteExistingQualification() {
        // Arrange
        when(qualificationFZRepository.existsById(1L)).thenReturn(true);

        // Act
        qualificationFZService.delete(1L);

        // Assert
        verify(qualificationFZRepository).existsById(1L);
        verify(qualificationFZRepository).deleteById(1L);
    }

    @Test
    void deleteshouldThrowWhenQualificationNotFound() {
        // Arrange
        when(qualificationFZRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> qualificationFZService.delete(1L));
        verify(qualificationFZRepository).existsById(1L);
        verify(qualificationFZRepository, never()).deleteById(any());
    }

    @Test
    void deleteshouldThrowWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> qualificationFZService.delete(null));
        verifyNoInteractions(qualificationFZRepository);
    }
}