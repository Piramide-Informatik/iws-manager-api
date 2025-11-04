package com.iws_manager.iws_manager_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iws_manager.iws_manager_api.models.User;
import com.iws_manager.iws_manager_api.services.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private static final String FIRST_USERNAME = "rigo123";
    private static final String SECOND_USERNAME = "4ndr3s";
    private MockMvc mockMvc;
    private String uri = "/api/v1/users";
    private String user = "$.username";

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user1 = new User();
        user1.setId(1L);
        user1.setUsername(FIRST_USERNAME);

        user2 = new User();
        user2.setId(2L);
        user2.setUsername(SECOND_USERNAME);
    }

    @Test
    void createUserShouldReturnCreatedUser() throws Exception {
        given(userService.create(any(User.class))).willReturn(user1);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(user).value(FIRST_USERNAME));
    }

    @Test
    void getUserByIdShouldReturnUser() throws Exception {
        given(userService.findById(1L)).willReturn(Optional.of(user1));

        mockMvc.perform(get(uri+"/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath(user).value(FIRST_USERNAME));
    }

    @Test
    void getUserByIdShouldReturnNotFound() throws Exception {
        given(userService.findById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get(uri+"/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllUserShouldReturnAllUser() throws Exception {
        List<User> users = Arrays.asList(user1,user2);
        given(userService.findAll()).willReturn(users);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].username").value(FIRST_USERNAME))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].username").value(SECOND_USERNAME));
    }

    @Test
    void updateUserShouldReturnConflictWhenUsernameExists() throws Exception {
        given(userService.update(anyLong(), any(User.class)))
                .willThrow(new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists"));

        mockMvc.perform(put(uri + "/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isConflict());
    }

    @Test
    void updateUserShouldReturnNotFound() throws Exception {
        given(userService.update(anyLong(), any(User.class)))
                .willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        mockMvc.perform(put(uri + "/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUserShouldReturnNoContent() throws Exception {
        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete(uri+"/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void createUserShouldReturnCreatedContractor() throws Exception {
        User validUser = new User();
        validUser.setUsername(FIRST_USERNAME);

        when(userService.create(any(User.class))).thenReturn(validUser);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(user).value(FIRST_USERNAME));
    }
}
