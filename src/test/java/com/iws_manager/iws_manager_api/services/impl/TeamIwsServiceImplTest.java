package com.iws_manager.iws_manager_api.services.impl;

import com.iws_manager.iws_manager_api.models.TeamIws;
import com.iws_manager.iws_manager_api.repositories.TeamIwsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("TeamIws Service Implementation Tests")
class TeamIwsServiceImplTest {
    @Mock
    private TeamIwsRepository teamIwsRepository;

    @InjectMocks
    private TeamIwsServiceImpl teamIwsService;

    private TeamIws sampleTeamIws;
    private String teamIwsName = "Team A";

    @BeforeEach
    void setUp() {
        sampleTeamIws = new TeamIws();
        sampleTeamIws.setId(1L);
        sampleTeamIws.setName(teamIwsName);
    }

    @Test
    @DisplayName("Should save TeamIws successfully")
    void createShouldReturnSavedTeamIws() {
        // Arrange
        when(teamIwsRepository.save(any(TeamIws.class))).thenReturn(sampleTeamIws);

        // Act
        TeamIws result = teamIwsService.create(sampleTeamIws);

        // Assert
        assertNotNull(result);
        assertEquals(teamIwsName, result.getName());
        verify(teamIwsRepository, times(1)).save(any(TeamIws.class));
    }

    @Test
    @DisplayName("Should throw exception when creating null TeamIws")
    void createShouldThrowExceptionWhenTeamIwsIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> teamIwsService.create(null));
        verify(teamIwsRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find TeamIws by ID")
    void findByIdShouldReturnTeamIwsWhenExists() {
        // Arrange
        when(teamIwsService.findById(1L)).thenReturn(Optional.of(sampleTeamIws));

        // Act
        Optional<TeamIws> result = teamIwsService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(teamIwsName, result.get().getName());
        verify(teamIwsRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when TeamIws not found")
    void findByIdShouldReturnEmptyWhenNotFound() {
        // Arrange
        when(teamIwsRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<TeamIws> result = teamIwsService.findById(99L);

        // Assert
        assertFalse(result.isPresent());
        verify(teamIwsRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Should throw exception when finding with null ID")
    void findByIdShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> teamIwsService.findById(null));
        verify(teamIwsRepository, never()).findById(any());
    }

    @Test
    @DisplayName("Should return all TeamsIws")
    void findAllShouldReturnAllTeamsIws() {
        // Arrange
        TeamIws teamIws2 = new TeamIws();
        teamIws2.setId(2L);
        teamIws2.setName("Florida");

        when(teamIwsRepository.findAllByOrderByNameAsc()).thenReturn(Arrays.asList(sampleTeamIws, teamIws2));

        // Act
        List<TeamIws> result = teamIwsService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(teamIwsRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Should return all TeamsIws ordered by name")
    void findAllShouldReturnTeamsIwsOrderedByName() {
        // Arrange
        TeamIws teamA = new TeamIws();
        teamA.setName("team A");

        TeamIws teamB = new TeamIws();
        teamB.setName("team B");

        TeamIws teamC = new TeamIws();
        teamC.setName("team C");

        // Mock ordenado alfabéticamente
        when(teamIwsRepository.findAllByOrderByNameAsc())
                .thenReturn(List.of(teamA, teamB, teamC));

        // Act
        List<TeamIws> result = teamIwsService.findAll();

        // Assert
        assertEquals(3, result.size());
        assertEquals("team A", result.get(0).getName());
        assertEquals("team B", result.get(1).getName());
        assertEquals("team C", result.get(2).getName());
        verify(teamIwsRepository, times(1)).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Should update TeamIws successfully")
    void updateShouldReturnUpdatedTeamIws() {
        // Arrange
        TeamIws updatedDetails = new TeamIws();
        updatedDetails.setName("Team A Updated");

        when(teamIwsRepository.findById(1L)).thenReturn(Optional.of(sampleTeamIws));
        when(teamIwsRepository.save(any(TeamIws.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        TeamIws result = teamIwsService.update(1L, updatedDetails);

        // Assert
        assertEquals("Team A Updated", result.getName());
        verify(teamIwsRepository, times(1)).findById(1L);
        verify(teamIwsRepository, times(1)).save(any(TeamIws.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent TeamIws")
    void updateShouldThrowExceptionWhenTeamIwsNotFound() {
        // Arrange
        when(teamIwsRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> teamIwsService.update(99L, new TeamIws()));
        verify(teamIwsRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete TeamIws successfully")
    void deleteShouldExecuteDelete() {
        // Arrange
        doNothing().when(teamIwsRepository).deleteById(1L);

        // Act
        teamIwsService.delete(1L);

        // Assert
        verify(teamIwsRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting with null ID")
    void deleteShouldThrowExceptionWhenIdIsNull() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> teamIwsService.delete(null));
        verify(teamIwsRepository, never()).deleteById(any());
    }
}