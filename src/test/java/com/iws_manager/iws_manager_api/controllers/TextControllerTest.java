package com.iws_manager.iws_manager_api.controllers;

import com.iws_manager.iws_manager_api.models.Text;
import com.iws_manager.iws_manager_api.services.interfaces.TextService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.iws_manager.iws_manager_api.exception.GlobalExceptionHandler;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class TextControllerTest {

    // Constants for reusable values
    private static final String BASE_URL = "/api/v1/texts";
    private static final String TEXT_LABEL = "Welcome Message";
    private static final String TEXT_CONTENT = "Welcome to our application!";
    private static final String TEXT_CONTENT2 = "New content";
    private static final String TEXT_LABEL2 = "New Label";
    private static final String TEXT_LABEL3 = "Long Content";
    private static final String LABEL_JSON_PATH = "$.label";
    private static final String CONTENT_JSON_PATH = "$.content";
    private static final String LENGHT_JSON_PATH = "$.length()";
    private static final String ID = "/{id}";
    private static final long VALID_ID = 1L;
    private static final long INVALID_ID = 99L;

    private MockMvc mockMvc;

    @Mock
    private TextService textService;

    @InjectMocks
    private TextController textController;

    private Text validText;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(textController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
        validText = createTestText(VALID_ID, TEXT_LABEL, TEXT_CONTENT);
    }

    private Text createTestText(Long id, String label, String content) {
        Text text = new Text();
        text.setId(id);
        text.setLabel(label);
        text.setContent(content);
        return text;
    }

    private String buildTextJson(String label, String content) {
        return String.format("""
            {
                "label": "%s",
                "content": "%s"
            }
            """, 
            label != null ? label : "",
            content != null ? content : "");
    }

    // ------------------- CREATE TESTS -------------------
    @Test
    void createTextShouldReturnCreatedWhenValidInput() throws Exception {
        when(textService.create(any(Text.class))).thenReturn(validText);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildTextJson(TEXT_LABEL, TEXT_CONTENT)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(LABEL_JSON_PATH).value(TEXT_LABEL))
                .andExpect(jsonPath(CONTENT_JSON_PATH).value(TEXT_CONTENT));
    }


    // ------------------- GET BY ID TESTS -------------------
    @Test
    void getTextByIdShouldReturnTextWhenValidId() throws Exception {
        when(textService.findById(VALID_ID)).thenReturn(Optional.of(validText));

        mockMvc.perform(get(BASE_URL + ID, VALID_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LABEL_JSON_PATH).value(TEXT_LABEL))
                .andExpect(jsonPath(CONTENT_JSON_PATH).value(TEXT_CONTENT));
    }

    @Test
    void getTextByIdShouldReturnNotFoundWhenInvalidId() throws Exception {
        when(textService.findById(INVALID_ID)).thenReturn(Optional.empty());

        mockMvc.perform(get(BASE_URL + ID, INVALID_ID))
                .andExpect(status().isNotFound());
    }

    // ------------------- GET ALL TESTS -------------------
    @Test
    void getAllTextsShouldReturnAllTexts() throws Exception {
        Text text2 = createTestText(2L, "Farewell Message", "Goodbye!");
        when(textService.findAll()).thenReturn(Arrays.asList(validText, text2));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_JSON_PATH).value(2))
                .andExpect(jsonPath("$[0]" + LABEL_JSON_PATH.substring(1)).value(TEXT_LABEL))
                .andExpect(jsonPath("$[0]" + CONTENT_JSON_PATH.substring(1)).value(TEXT_CONTENT))
                .andExpect(jsonPath("$[1]" + LABEL_JSON_PATH.substring(1)).value("Farewell Message"));
    }

    @Test
    void getAllTextsShouldReturnEmptyListWhenNoTexts() throws Exception {
        when(textService.findAll()).thenReturn(Arrays.asList());

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_JSON_PATH).value(0));
    }

    // ------------------- UPDATE TESTS -------------------
    @Test
    void updateTextShouldReturnUpdatedTextWhenValidInput() throws Exception {
        when(textService.update(eq(VALID_ID), any(Text.class))).thenReturn(validText);

        mockMvc.perform(put(BASE_URL + ID, VALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildTextJson("Updated Label", "Updated content")))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LABEL_JSON_PATH).value(TEXT_LABEL))
                .andExpect(jsonPath(CONTENT_JSON_PATH).value(TEXT_CONTENT));
    }

    @Test
    void updateTextShouldReturnNotFoundWhenInvalidId() throws Exception {
        when(textService.update(eq(INVALID_ID), any(Text.class)))
            .thenThrow(new EntityNotFoundException("Text not found with id: " + INVALID_ID));

        mockMvc.perform(put(BASE_URL + ID, INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildTextJson("Updated Label", "Updated content")))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateTextShouldUpdateBothLabelAndContent() throws Exception {
        Text updatedText = createTestText(VALID_ID, TEXT_LABEL2, TEXT_CONTENT2);
        when(textService.update(eq(VALID_ID), any(Text.class))).thenReturn(updatedText);

        mockMvc.perform(put(BASE_URL + ID, VALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildTextJson(TEXT_LABEL2, TEXT_CONTENT2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LABEL_JSON_PATH).value(TEXT_LABEL2))
                .andExpect(jsonPath(CONTENT_JSON_PATH).value(TEXT_CONTENT2));
    }

    // ------------------- DELETE TESTS -------------------
    @Test
    void deleteTextShouldReturnNoContentWhenValidId() throws Exception {
        mockMvc.perform(delete(BASE_URL + ID, VALID_ID))
                .andExpect(status().isNoContent());

        verify(textService, times(1)).delete(VALID_ID);
    }

    // ------------------- ORDERING TESTS -------------------
    @Test
    void getAllTextsShouldReturnOrderedList() throws Exception {
        Text textA = createTestText(1L, "A Message", "Content A");
        Text textB = createTestText(2L, "B Message", "Content B");
        Text textC = createTestText(3L, "C Message", "Content C");
        
        when(textService.findAll()).thenReturn(Arrays.asList(textA, textB, textC));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath(LENGHT_JSON_PATH).value(3))
                .andExpect(jsonPath("$[0].label").value("A Message"))
                .andExpect(jsonPath("$[1].label").value("B Message"))
                .andExpect(jsonPath("$[2].label").value("C Message"));
    }

    @Test
    void createTextShouldAcceptLongContent() throws Exception {
        String longContent = "This is a very long text content that should be properly stored " +
                           "in the TEXT column. The TEXT column type can handle large amounts " +
                           "of text data without issues. This is important for storing " +
                           "templates, messages, and other lengthy content.";
        
        Text textWithLongContent = createTestText(VALID_ID, TEXT_LABEL3, longContent);
        when(textService.create(any(Text.class))).thenReturn(textWithLongContent);

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(buildTextJson(TEXT_LABEL3, longContent)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(LABEL_JSON_PATH).value(TEXT_LABEL3));
    }
}