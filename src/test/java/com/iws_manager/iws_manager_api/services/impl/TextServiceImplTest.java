package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.iws_manager.iws_manager_api.models.Text;
import com.iws_manager.iws_manager_api.repositories.TextRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class TextServiceImplTest {

    @Mock
    private TextRepository textRepository;

    @InjectMocks
    private TextServiceImpl textService;

    private Text text;
    private Text text2;

    @BeforeEach
    void setUp() {
        text = new Text();
        text.setId(1L);
        text.setLabel("Welcome Message");
        text.setContent("Welcome to our application!");

        text2 = new Text();
        text2.setId(2L);
        text2.setLabel("Farewell Message");
        text2.setContent("Thank you for using our service!");
    }

    @Test
    void testCreateSuccess() {
        // Arrange
        when(textRepository.save(any(Text.class))).thenReturn(text);

        // Act
        Text result = textService.create(text);

        // Assert
        assertNotNull(result);
        assertEquals(text.getId(), result.getId());
        assertEquals(text.getLabel(), result.getLabel());
        assertEquals(text.getContent(), result.getContent());
        verify(textRepository, times(1)).save(text);
    }

    @Test
    void testCreateWithNullTextThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> textService.create(null));
        
        assertEquals("Text cannot be null", exception.getMessage());
        verify(textRepository, never()).save(any());
    }

    @Test
    void testFindByIdSuccess() {
        // Arrange
        when(textRepository.findById(1L)).thenReturn(Optional.of(text));

        // Act
        Optional<Text> result = textService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(text.getId(), result.get().getId());
        assertEquals(text.getLabel(), result.get().getLabel());
        assertEquals(text.getContent(), result.get().getContent());
        verify(textRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdWithNullIdThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> textService.findById(null));
        
        assertEquals("ID cannot be null", exception.getMessage());
        verify(textRepository, never()).findById(any());
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange
        when(textRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<Text> result = textService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(textRepository, times(1)).findById(99L);
    }

    @Test
    void testFindAllSuccess() {
        // Arrange
        List<Text> textList = Arrays.asList(text, text2);
        when(textRepository.findAllByOrderByLabelAsc()).thenReturn(textList);

        // Act
        List<Text> result = textService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Welcome Message", result.get(0).getLabel());
        assertEquals("Farewell Message", result.get(1).getLabel());
        verify(textRepository, times(1)).findAllByOrderByLabelAsc();
    }

    @Test
    void testFindAllEmpty() {
        // Arrange
        when(textRepository.findAllByOrderByLabelAsc()).thenReturn(Arrays.asList());

        // Act
        List<Text> result = textService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(textRepository, times(1)).findAllByOrderByLabelAsc();
    }

    @Test
    void testUpdateSuccess() {
        // Arrange
        Text updatedText = new Text();
        updatedText.setLabel("Updated Welcome");
        updatedText.setContent("Updated welcome message!");

        when(textRepository.findById(1L)).thenReturn(Optional.of(text));
        when(textRepository.save(any(Text.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Text result = textService.update(1L, updatedText);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Welcome", result.getLabel());
        assertEquals("Updated welcome message!", result.getContent());
        assertEquals(1L, result.getId()); // ID should remain the same
        verify(textRepository, times(1)).findById(1L);
        verify(textRepository, times(1)).save(text);
    }

    @Test
    void testUpdateWithNullIdThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> textService.update(null, text));
        
        assertEquals("ID and Text details cannot be null", exception.getMessage());
        verify(textRepository, never()).findById(any());
        verify(textRepository, never()).save(any());
    }

    @Test
    void testUpdateWithNullTextDetailsThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> textService.update(1L, null));
        
        assertEquals("ID and Text details cannot be null", exception.getMessage());
        verify(textRepository, never()).findById(any());
        verify(textRepository, never()).save(any());
    }

    @Test
    void testUpdateNotFoundThrowsException() {
        // Arrange
        when(textRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> textService.update(99L, text));
        
        assertEquals("Text not found with id: 99", exception.getMessage());
        verify(textRepository, times(1)).findById(99L);
        verify(textRepository, never()).save(any());
    }

    @Test
    void testDeleteSuccess() {
        // Arrange
        when(textRepository.existsById(1L)).thenReturn(true);
        doNothing().when(textRepository).deleteById(1L);

        // Act
        textService.delete(1L);

        // Assert
        verify(textRepository, times(1)).existsById(1L);
        verify(textRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteWithNullIdThrowsException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> textService.delete(null));
        
        assertEquals("ID cannot be null", exception.getMessage());
        verify(textRepository, never()).existsById(any());
        verify(textRepository, never()).deleteById(any());
    }

    @Test
    void testDeleteNotFoundThrowsException() {
        // Arrange
        when(textRepository.existsById(99L)).thenReturn(false);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, 
            () -> textService.delete(99L));
        
        assertEquals("Text not found with id: 99", exception.getMessage());
        verify(textRepository, times(1)).existsById(99L);
        verify(textRepository, never()).deleteById(any());
    }

    @Test
    void testConstructorInjection() {
        // Arrange
        TextRepository testRepository = mock(TextRepository.class);
        
        // Act
        TextServiceImpl testService = new TextServiceImpl(testRepository);
        
        // Assert
        assertNotNull(testService);
        when(testRepository.findAllByOrderByLabelAsc()).thenReturn(Arrays.asList(text));
        List<Text> result = testService.findAll();
        
        assertEquals(1, result.size());
        verify(testRepository, times(1)).findAllByOrderByLabelAsc();
    }

    @Test
    void testUpdateVerifyFieldUpdate() {
        // Arrange
        Text updatedText = new Text();
        updatedText.setLabel("Completely New Label");
        updatedText.setContent("Completely new content for the text template.");

        when(textRepository.findById(1L)).thenReturn(Optional.of(text));
        when(textRepository.save(any(Text.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Text result = textService.update(1L, updatedText);

        // Assert
        assertEquals("Completely New Label", result.getLabel());
        assertEquals("Completely new content for the text template.", result.getContent());
        assertEquals(1L, result.getId());
        verify(textRepository, times(1)).save(text);
    }

    @Test
    void testFindAllOrderVerification() {
        // Arrange
        List<Text> textList = Arrays.asList(text2, text);
        when(textRepository.findAllByOrderByLabelAsc()).thenReturn(Arrays.asList(text, text2));

        // Act
        List<Text> result = textService.findAll();

        // Assert
        assertEquals("Welcome Message", result.get(0).getLabel()); 
        assertEquals("Farewell Message", result.get(1).getLabel());
        verify(textRepository, times(1)).findAllByOrderByLabelAsc();
    }

    @Test
    void testUpdatePartialUpdate() {
        // Arrange
        Text updatedText = new Text();
        updatedText.setLabel("Updated Label Only"); 

        when(textRepository.findById(1L)).thenReturn(Optional.of(text));
        when(textRepository.save(any(Text.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Text result = textService.update(1L, updatedText);

        // Assert
        assertEquals("Updated Label Only", result.getLabel());
        assertNull(result.getContent()); 
        verify(textRepository, times(1)).save(text);
    }
}