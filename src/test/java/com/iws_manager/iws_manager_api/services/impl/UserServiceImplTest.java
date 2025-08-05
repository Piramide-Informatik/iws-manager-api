package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.User;
import com.iws_manager.iws_manager_api.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Service Implementation Tests")
public class UserServiceImplTest {
    private PasswordEncoder passwordEncoder;
    private static final String FIRSTNAME_ROGER = "Roger";
    private static final String USERNAME_ROGER = "the_roger";
    private static final String USERNAME_ANDRES = "andy123";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;
    private User sampleUser;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserServiceImpl(userRepository, passwordEncoder);

        sampleUser=new User();
        sampleUser.setId(1L);
        sampleUser.setFirstName(FIRSTNAME_ROGER);
        sampleUser.setLastName("Lopez");
        sampleUser.setUsername(USERNAME_ROGER);
        sampleUser.setEmail("roger@mail.com");
        sampleUser.setPassword("roger123");
    }

    @Test
    @DisplayName("Should save user successfully with encrypted password")
    void creatShouldReturnSavedUser(){
        // Simulates saving what is passed (do not overwrite the password here)
     when(userRepository.save(any(User.class))).thenReturn(sampleUser).thenAnswer(invocation -> invocation.getArgument(0));

     User result = userService.create(sampleUser);

     assertNotNull(result);
     assertEquals(FIRSTNAME_ROGER, result.getFirstName());
     assertEquals("Lopez", result.getLastName());
     assertEquals(USERNAME_ROGER, result.getUsername());
     assertEquals("roger@mail.com", result.getEmail());
     // Verify that it has been encrypted correctly
     assertTrue(passwordEncoder.matches("roger123", result.getPassword()));

     verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null user")
    void createShouldThrowExceptionWhenUserIsNull() {
        assertThrows(IllegalArgumentException.class, () -> userService.create(null));
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find user by ID")
    void findByIdShouldReturnUserWhen(){
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        Optional<User> result = userService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(USERNAME_ROGER, result.get().getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return all user")
    void findAllShouldReturnAllUser() {
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername(USERNAME_ANDRES);

        when(userRepository.findAll()).thenReturn(Arrays.asList(sampleUser,user2));

        List<User> result = userService.findAll();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();

    }

    @Test
    @DisplayName("Should update user successfully including encrypted password")
    void updateShouldReturnUpdatedUser() {
        User updatedDetails = new User();
        updatedDetails.setUsername(USERNAME_ROGER+" Updated");
        updatedDetails.setPassword("newPassword123");

        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User result = userService.update(1L, updatedDetails);
        assertTrue(passwordEncoder.matches("newPassword123", result.getPassword()));

        assertEquals("the_roger Updated", result.getUsername());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent user")
    void updateShouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> userService.update(99L, new User()));
        verify(userRepository, never()).save(any());
    }

    @Test
    public void updateShouldThrowExceptioWhenOptimisticLockingFails() {
        Long userId = 1L;
        User currentUser = new User();
        currentUser.setId(userId);
        currentUser.setUsername(USERNAME_ROGER);
        currentUser.setVersion(2L);
        currentUser.setPassword("testRoger123");

        User outUser = new User();
        outUser.setId(userId);
        outUser.setUsername(USERNAME_ANDRES);
        outUser.setVersion(1L);
        outUser.setPassword("someNewPassword123");

        when(userRepository.findById(userId)).thenReturn(Optional.of(currentUser));
        when(userRepository.save(any(User.class)))
                .thenThrow(new ObjectOptimisticLockingFailureException("Concurrent modification detected",
                        new ObjectOptimisticLockingFailureException(User.class, userId)));
        Exception exception = assertThrows(RuntimeException.class, () -> userService.update(userId, outUser));

        assertNotNull(exception, "An exception should have been thrown");

        if (!(exception instanceof  ObjectOptimisticLockingFailureException)) {
            assertNotNull(exception.getCause(), "The exception should have a cause");
            assertTrue(exception.getCause() instanceof  ObjectOptimisticLockingFailureException,
                    "The cause should be ObjectOptimisticLockingFailureException");
        }

        verify(userRepository).findById(userId);
        verify(userRepository).save(any(User.class));
    }
}
