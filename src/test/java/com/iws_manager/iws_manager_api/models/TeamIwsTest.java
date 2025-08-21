package com.iws_manager.iws_manager_api.models;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TeamIwsTest {

    @Test
    void testTeamIwsCreation() {
        // Arrange
        String teamName = "Team A";
        EmployeeIws leader = new EmployeeIws();
        leader.setId(100L);
        leader.setFirstname("Juan");
        leader.setLastname("Perez");

        // Act
        TeamIws team = new TeamIws();
        team.setName(teamName);
        team.setTeamLeader(leader);

        // Assert
        assertEquals(teamName, team.getName());
        assertNotNull(team.getTeamLeader());
        assertEquals(100L, team.getTeamLeader().getId());
        assertEquals("Juan", team.getTeamLeader().getFirstname());
    }

    @Test
    void testTeamIwsWithAuditFields() {
        // Arrange
        TeamIws team = new TeamIws();
        team.setName("Team B");
        LocalDateTime now = LocalDateTime.now();

        // Act
        team.setCreatedAt(now);
        team.setUpdatedAt(now);

        // Assert
        assertEquals(now, team.getCreatedAt());
        assertEquals(now, team.getUpdatedAt());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        EmployeeIws leader1 = new EmployeeIws();
        leader1.setId(100L);

        EmployeeIws leader2 = new EmployeeIws();
        leader2.setId(100L);

        TeamIws team1 = new TeamIws();
        team1.setId(1L);
        team1.setName("Team C");
        team1.setTeamLeader(leader1);

        TeamIws team2 = new TeamIws();
        team2.setId(1L);
        team2.setName("Team C");
        team2.setTeamLeader(leader2);

        TeamIws team3 = new TeamIws();
        team3.setId(2L);
        team3.setName("Team D");
        team3.setTeamLeader(leader1);

        assertEquals(team1, team2);
        assertEquals(team1.hashCode(), team2.hashCode());

        assertNotEquals(team1, team3);
        assertNotEquals(team1.hashCode(), team3.hashCode());
    }
}
