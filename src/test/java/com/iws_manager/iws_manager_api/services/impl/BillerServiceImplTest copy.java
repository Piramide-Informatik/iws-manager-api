package com.iws_manager.iws_manager_api.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.iws_manager.iws_manager_api.models.Network;
import com.iws_manager.iws_manager_api.repositories.NetworkRepository;
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

@ExtendWith(MockitoExtension.class)
@DisplayName("Network Service Implementation Tests")
class NetworkServiceImplTest {

    @Mock
    private NetworkRepository networkRepository;

    @InjectMocks
    private NetworkServiceImpl networkService;

    private Network sampleNetwork;
    private String networkName = "Network 1";

    @BeforeEach
    void setUp() {
        sampleNetwork = new Network();
        sampleNetwork.setId(1L);
        sampleNetwork.setName(networkName);
    }

    @Test
    @DisplayName("Should save Network successfully")
    void createShouldReturnSavedNetwork() {
        // Arrange
        when(networkRepository.save(any(Network.class))).thenReturn(sampleNetwork);

        // Act
        Network result = networkService.create(sampleNetwork);

        // Assert
        assertNotNull(result);
        assertEquals(networkName, result.getName());
        verify(networkRepository, times(1)).save(any(Network.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null Network")
    void createShouldThrowExceptionWhenNetworkIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> networkService.create(null));
        verify(networkRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find Network by ID")
    void findByIdShouldReturnNetworkWhenExists() {
        // Arrange
        when(networkRepository.findById(1L)).thenReturn(Optional.of(sampleNetwork));

        // Act
        Optional<Network> result = networkService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(networkName, result.get().getName());
        verify(networkRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when Network not found")
    void findByIdShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(networkRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<Network> result = networkService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(networkRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should throw exception when finding with null ID")
    void findByIdShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> networkService.findById(null));
        verify(networkRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Should return all Networks")
    void findAllShouldReturnAllNetworks() {
        // Arrange
        Network network2 = new Network();
        network2.setId(2L);
        network2.setName("New Network");
        
        when(networkRepository.findAllByOrderByNameAsc()).thenReturn(Arrays.asList(sampleNetwork, network2));

        // Act
        List<Network> result = networkService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(networkRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Should update network successfully")
    void updateShouldReturnUpdatedNetwork() {
        // Arrange
        Network updatedDetails = new Network();
        updatedDetails.setName("Network Updated");

        when(networkRepository.findById(1L)).thenReturn(Optional.of(sampleNetwork));
        when(networkRepository.save(any(Network.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Network result = networkService.update(1L, updatedDetails);

        // Assert
        assertEquals("Network Updated", result.getName());
        verify(networkRepository, times(1)).findById(1L);
        verify(networkRepository, times(1)).save(any(Network.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent network")
    void updateShouldThrowExceptionWhenNetworkNotFound() {
        // Arrange
        when(networkRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, 
            () -> networkService.update(99L, new Network()));
        verify(networkRepository, never()).save(any());
    }

    @Test
    public void updateShouldThrowExceptionWhenOptimisticLockingFails() {
        // Setup
        Long networkId = 1L;
        Network currentNetwork = new Network();
        currentNetwork.setId(networkId);
        currentNetwork.setName(networkName);
        currentNetwork.setVersion(2L); // Current version in DB
        
        Network outdatedNetwork = new Network();
        outdatedNetwork.setId(networkId);
        outdatedNetwork.setName("Doctor");
        outdatedNetwork.setVersion(1L); // Outdated version

        when(networkRepository.findById(networkId)).thenReturn(Optional.of(currentNetwork));
        when(networkRepository.save(any(Network.class)))
            .thenThrow(new ObjectOptimisticLockingFailureException("Concurrent modification detected", 
                    new ObjectOptimisticLockingFailureException(Network.class, networkId)));

        // Execution and verification
        Exception exception = assertThrows(RuntimeException.class, () -> networkService.update(networkId, outdatedNetwork));

        assertNotNull(exception, "An exception should have been thrown");
        
        // Verify if it's the direct exception or wrapped
        if (!(exception instanceof ObjectOptimisticLockingFailureException)) {
            assertNotNull(exception.getCause(), "The exception should have a cause");
            assertTrue(exception.getCause() instanceof ObjectOptimisticLockingFailureException, 
                    "The cause should be ObjectOptimisticLockingFailureException");
        }
        
        // Verify repository interactions
        verify(networkRepository).findById(networkId);
        verify(networkRepository).save(any(Network.class));
    }
}
