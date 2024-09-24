package ru.zubcov.mediasoft.task2.mediasofttask2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.NewMatchResultRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.UpdateMatchResultRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.exception.EntityNotFound;
import ru.zubcov.mediasoft.task2.mediasofttask2.exception.EqualsTeamException;
import ru.zubcov.mediasoft.task2.mediasofttask2.mapper.MatchResultMapper;
import ru.zubcov.mediasoft.task2.mediasofttask2.model.MatchResult;
import ru.zubcov.mediasoft.task2.mediasofttask2.model.Team;
import ru.zubcov.mediasoft.task2.mediasofttask2.repository.MatchResultRepository;
import ru.zubcov.mediasoft.task2.mediasofttask2.service.MatchResultServiceImpl;
import ru.zubcov.mediasoft.task2.mediasofttask2.service.TeamService;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MatchResultServiceImplTest {

    @Mock
    private MatchResultRepository matchResultRepository;

    @Mock
    private TeamService teamService;

    @Mock
    private MatchResultMapper mapper;

    @InjectMocks
    private MatchResultServiceImpl matchResultService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateMatchResultSuccessfully() {
        NewMatchResultRequest request = new NewMatchResultRequest();
        request.setSeason("2024");
        request.setMatchDate(LocalDate.now());
        request.setGuestTeam_id(1L);
        request.setHomeTeam_id(2L);
        request.setGuestTeamGoals((short) 3);
        request.setHomeTeamGoals((short) 2);
        MatchResult matchResult = new MatchResult();
        Team homeTeam = new Team();
        homeTeam.setId(1L);
        Team guestTeam = new Team();
        guestTeam.setId(2L);

        when(teamService.findById(request.getHomeTeam_id())).thenReturn(homeTeam);
        when(teamService.findById(request.getGuestTeam_id())).thenReturn(guestTeam);
        when(mapper.mapMatchResult(request)).thenReturn(matchResult);
        when(matchResultRepository.save(any(MatchResult.class))).thenReturn(matchResult);

        MatchResult result = matchResultService.createMatchResult(request);

        assertNotNull(result);
        verify(matchResultRepository, times(1)).save(any(MatchResult.class));
    }

    @Test
    void shouldThrowEqualsTeamExceptionWhenTeamsAreEqualInCreate() {
        NewMatchResultRequest request = new NewMatchResultRequest();
        request.setSeason("2024");
        request.setMatchDate(LocalDate.now());
        request.setGuestTeam_id(1L);
        request.setHomeTeam_id(2L);
        request.setGuestTeamGoals((short) 3);
        request.setHomeTeamGoals((short) 2);
        Team team = new Team();
        team.setId(1L);

        when(teamService.findById(request.getHomeTeam_id())).thenReturn(team);
        when(teamService.findById(request.getGuestTeam_id())).thenReturn(team);

        assertThrows(EqualsTeamException.class, () -> matchResultService.createMatchResult(request));
        verify(matchResultRepository, never()).save(any(MatchResult.class));
    }

    @Test
    void shouldUpdateMatchResultSuccessfully() {
        long matchResultId = 1L;
        UpdateMatchResultRequest request = new UpdateMatchResultRequest();
        request.setSeason("2024");
        request.setMatchDate(LocalDate.now());
        request.setGuestTeam_id(1L);
        request.setHomeTeam_id(2L);
        request.setGuestTeamGoals((short) 3);
        request.setHomeTeamGoals((short) 2);
        MatchResult matchResult = new MatchResult();
        Team homeTeam = new Team();
        homeTeam.setId(1L);
        Team guestTeam = new Team();
        guestTeam.setId(2L);
        matchResult.setHomeTeam(homeTeam);
        matchResult.setGuestTeam(guestTeam);

        when(matchResultRepository.findByIdWithTeams(matchResultId)).thenReturn(Optional.of(matchResult));
        when(teamService.findById(request.getHomeTeam_id())).thenReturn(homeTeam);
        when(teamService.findById(request.getGuestTeam_id())).thenReturn(guestTeam);
        when(matchResultRepository.save(matchResult)).thenReturn(matchResult);

        MatchResult result = matchResultService.updateMatchResult(request, matchResultId);

        assertNotNull(result);
        verify(mapper, times(1)).updateMatchResult(request, matchResult);
        verify(matchResultRepository, times(1)).save(matchResult);
    }

    @Test
    void shouldThrowEntityNotFoundWhenUpdatingNonExistentMatchResult() {
        long matchResultId = 1L;
        UpdateMatchResultRequest request = new UpdateMatchResultRequest();
        request.setSeason("2024");
        request.setMatchDate(LocalDate.now());
        request.setGuestTeam_id(1L);
        request.setHomeTeam_id(2L);
        request.setGuestTeamGoals((short) 3);
        request.setHomeTeamGoals((short) 2);

        when(matchResultRepository.findByIdWithTeams(matchResultId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFound.class, () -> matchResultService.updateMatchResult(request, matchResultId));
        verify(matchResultRepository, never()).save(any(MatchResult.class));
    }

    @Test
    void shouldThrowEqualsTeamExceptionWhenTeamsAreEqualInUpdate() {
        long matchResultId = 1L;
        UpdateMatchResultRequest request = new UpdateMatchResultRequest();
        request.setSeason("2024");
        request.setMatchDate(LocalDate.now());
        request.setGuestTeam_id(1L);
        request.setHomeTeam_id(1L);
        request.setGuestTeamGoals((short) 3);
        request.setHomeTeamGoals((short) 2);
        MatchResult matchResult = new MatchResult();
        Team team = new Team();
        team.setId(1L);
        matchResult.setHomeTeam(team);
        matchResult.setGuestTeam(team);

        when(matchResultRepository.findByIdWithTeams(matchResultId)).thenReturn(Optional.of(matchResult));

        assertThrows(EqualsTeamException.class, () -> matchResultService.updateMatchResult(request, matchResultId));
        verify(matchResultRepository, never()).save(any(MatchResult.class));
    }

    @Test
    void shouldFindMatchResultByIdSuccessfully() {
        long matchResultId = 1L;
        MatchResult matchResult = new MatchResult();
        matchResult.setId(matchResultId);

        when(matchResultRepository.findById(matchResultId)).thenReturn(Optional.of(matchResult));

        MatchResult result = matchResultService.getMatchResultById(matchResultId);

        assertNotNull(result);
        assertEquals(matchResultId, result.getId());
        verify(matchResultRepository, times(1)).findById(matchResultId);
    }

    @Test
    void shouldThrowEntityNotFoundWhenMatchResultNotFoundById() {
        long matchResultId = 1L;

        when(matchResultRepository.findById(matchResultId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFound.class, () -> matchResultService.getMatchResultById(matchResultId));
        verify(matchResultRepository, times(1)).findById(matchResultId);
    }

    @Test
    void shouldDeleteMatchResultSuccessfully() {
        long matchResultId = 1L;
        MatchResult matchResult = new MatchResult();
        matchResult.setId(matchResultId);

        when(matchResultRepository.findById(matchResultId)).thenReturn(Optional.of(matchResult));

        matchResultService.deleteMatchResultById(matchResultId);

        verify(matchResultRepository, times(1)).deleteById(matchResultId);
    }
    @Test
    void shouldThrowEntityNotFoundWhenDeletingNonExistentMatchResult() {
        long matchResultId = 1L;

        when(matchResultRepository.findById(matchResultId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFound.class, () -> matchResultService.deleteMatchResultById(matchResultId));
        verify(matchResultRepository, never()).deleteById(matchResultId);
    }

    @Test
    void shouldGetMatchResultsByDateAndSeasonSuccessfully() {
        LocalDate date = LocalDate.now();
        String season = "23/24";
        List<MatchResult> matchResults = List.of(new MatchResult(), new MatchResult());

        when(matchResultRepository.findAllByMatchDateLessThanEqualAndBySeason(date, season)).thenReturn(matchResults);

        List<MatchResult> result = matchResultService.getMatchResultsByDateAndSeason(date, season);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(matchResultRepository, times(1)).findAllByMatchDateLessThanEqualAndBySeason(date, season);  // Проверяем вызов с обоими параметрами
    }

}