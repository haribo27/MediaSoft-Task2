package ru.zubcov.mediasoft.task2.mediasofttask2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.NewTeamRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.UpdateTeamRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.exception.EntityAlreadyExists;
import ru.zubcov.mediasoft.task2.mediasofttask2.exception.EntityNotFound;
import ru.zubcov.mediasoft.task2.mediasofttask2.mapper.TeamMapper;
import ru.zubcov.mediasoft.task2.mediasofttask2.model.Team;
import ru.zubcov.mediasoft.task2.mediasofttask2.repository.TeamRepository;
import ru.zubcov.mediasoft.task2.mediasofttask2.service.TeamServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamMapper teamMapper;

    @InjectMocks
    private TeamServiceImpl teamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateTeamSuccessfully() {
        NewTeamRequest request = new NewTeamRequest();
        request.setName("Team A");
        Team team = new Team();
        team.setName("Team A");

        when(teamRepository.findTeamByName(request.getName())).thenReturn(Optional.empty());
        when(teamMapper.mapToTeam(request)).thenReturn(team);
        when(teamRepository.save(any(Team.class))).thenReturn(team);

        Team result = teamService.createTeam(request);

        assertNotNull(result);
        assertEquals("Team A", result.getName());
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    @Test
    void shouldThrowEntityAlreadyExistsWhenTeamExists() {
        NewTeamRequest request = new NewTeamRequest();
        request.setName("Team A");

        when(teamRepository.findTeamByName(request.getName())).thenReturn(Optional.of(new Team()));

        assertThrows(EntityAlreadyExists.class, () -> teamService.createTeam(request));

        verify(teamRepository, never()).save(any(Team.class));
    }


    @Test
    void shouldThrowExceptionWhenUpdatingToExistingTeamName() {
        long teamId = 1L;
        UpdateTeamRequest request = new UpdateTeamRequest();
        request.setName("Existing Team");

        Team existingTeam = new Team();
        existingTeam.setId(teamId);
        existingTeam.setName("Old Team");

        Team anotherTeam = new Team();
        anotherTeam.setId(2L);
        anotherTeam.setName("Existing Team");

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(existingTeam));
        when(teamRepository.findTeamByName(request.getName())).thenReturn(Optional.of(anotherTeam));

        Exception exception = assertThrows(EntityAlreadyExists.class, () -> {
            teamService.updateTeam(request, teamId);
        });
        assertEquals("Team with this name already exists", exception.getMessage());
    }




    @Test
    void shouldThrowEntityNotFoundWhenUpdatingNonExistentTeam() {
        long teamId = 1L;
        UpdateTeamRequest request = new UpdateTeamRequest();
        request.setName("Updated Team");

        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFound.class, () -> teamService.updateTeam(request, teamId));

        verify(teamRepository, never()).save(any(Team.class));
    }

    @Test
    void shouldDeleteTeamSuccessfully() {
        long teamId = 1L;
        Team existingTeam = new Team();
        existingTeam.setId(teamId);

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(existingTeam));

        teamService.deleteTeam(teamId);

        verify(teamRepository, times(1)).deleteById(teamId);
    }

    @Test
    void shouldThrowEntityNotFoundWhenDeletingNonExistentTeam() {
        long teamId = 1L;

        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFound.class, () -> teamService.deleteTeam(teamId));

        verify(teamRepository, never()).deleteById(teamId);
    }

    @Test
    void shouldFindTeamByIdSuccessfully() {
        long teamId = 1L;
        Team existingTeam = new Team();
        existingTeam.setId(teamId);

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(existingTeam));

        Team result = teamService.findById(teamId);

        assertNotNull(result);
        assertEquals(teamId, result.getId());
        verify(teamRepository, times(1)).findById(teamId);
    }

    @Test
    void shouldThrowEntityNotFoundWhenTeamNotFoundById() {
        long teamId = 1L;

        when(teamRepository.findById(teamId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFound.class, () -> teamService.findById(teamId));

        verify(teamRepository, times(1)).findById(teamId);
    }
}
