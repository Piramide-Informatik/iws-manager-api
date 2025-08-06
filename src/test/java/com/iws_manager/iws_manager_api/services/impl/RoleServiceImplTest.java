package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.Role;
import com.iws_manager.iws_manager_api.models.Title;
import com.iws_manager.iws_manager_api.repositories.RoleRepository;
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
@DisplayName("Role Service Implementation Tests")
public class RoleServiceImplTest {
    private static final String FIRST_ROLE = "admin";
    private static final String SECOND_ROLE = "finanza";

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role sampleRole;

    @BeforeEach
    void setUp(){
        sampleRole = new Role();
        sampleRole.setId(1L);
        sampleRole.setName(FIRST_ROLE);
    }

    @Test
    @DisplayName("Should save role successfully")
    void createShouldReturnSavedRole() {
        when(roleRepository.save(any(Role.class))).thenReturn(sampleRole);

        Role result = roleService.create(sampleRole);

        assertNotNull(result);
        assertEquals(FIRST_ROLE, result.getName());
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null role")
    void createShouldThrowExceptionWhenRoleIsNull() {
        assertThrows(IllegalArgumentException.class, () -> roleService.create(null));
        verify(roleRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find role by ID")
    void findByIdShouldReturnRoleWhenExists() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(sampleRole));

        Optional<Role> result = roleService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(FIRST_ROLE,result.get().getName());
        verify(roleRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when role not found")
    void findByIdShouldReturnEmptyWhenNotFound() {
        when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Role> result = roleService.findById(99L);

        assertFalse(result.isPresent());
        verify(roleRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should throw exception when finding with null ID")
    void findByIdShouldThrowExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> roleService.findById(null));
        verify(roleRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Should return all roles")
    void findAllShouldReturnAllRoles() {
        Role role2 = new Role();
        role2.setId(2L);
        role2.setName(SECOND_ROLE);

        when(roleRepository.findAll()).thenReturn(Arrays.asList(sampleRole, role2));

        List<Role> result = roleService.findAll();

        assertEquals(2, result.size());
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should update user successfully")
    void updateShouldReturnUpdatedUser() {
        Role roleDetails = new Role();
        roleDetails.setName("admin updated");

        when(roleRepository.findById(1L)).thenReturn(Optional.of(sampleRole));
        when(roleRepository.save(any(Role.class))).thenAnswer(inv -> inv.getArgument(0));

        Role result = roleService.update(1L, roleDetails);

        assertEquals("admin updated", result.getName());
        verify(roleRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent role")
    void updateShouldThrowExceptionWhenRoleNotFound() {
        when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> roleService.update(99L, new Role()));
        verify(roleRepository, never()).save(any());
    }

    @Test
    public void updateShouldThrowExceptionWhenOptimisticLockingFails() {
        Long roleId = 1L;
        Role currentRole = new Role();
        currentRole.setId(roleId);
        currentRole.setName(FIRST_ROLE);
        currentRole.setVersion(2L);

        Role outdatedRole = new Role();
        outdatedRole.setId(roleId);
        outdatedRole.setName(SECOND_ROLE);
        outdatedRole.setVersion(1L);

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(currentRole));
        when(roleRepository.save(any(Role.class)))
                .thenThrow(new ObjectOptimisticLockingFailureException("Concurrent modification detected",
                         new ObjectOptimisticLockingFailureException(Role.class, roleId)));

        Exception exception = assertThrows(RuntimeException.class, () ->
           roleService.update(roleId, outdatedRole)
        );

        assertNotNull(exception, "An exception should have been thrown");

        // Verify if it's the direct exception or wrapped
        if (!(exception instanceof ObjectOptimisticLockingFailureException)) {
            assertNotNull(exception.getCause(), "The exception should have a cause");
            assertTrue(exception.getCause() instanceof ObjectOptimisticLockingFailureException,
                    "The cause should be ObjectOptimisticLockingFailureException");
        }

        // Verify repository interactions
        verify(roleRepository).findById(roleId);
        verify(roleRepository).save(any(Role.class));
    }
}
