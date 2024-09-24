package ru.zubcov.mediasoft.task2.mediasofttask2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.NewMatchResultRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.UpdateMatchResultRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.exception.EntityNotFound;
import ru.zubcov.mediasoft.task2.mediasofttask2.exception.EqualsTeamException;
import ru.zubcov.mediasoft.task2.mediasofttask2.mapper.MatchResultMapper;
import ru.zubcov.mediasoft.task2.mediasofttask2.model.MatchResult;
import ru.zubcov.mediasoft.task2.mediasofttask2.model.Team;
import ru.zubcov.mediasoft.task2.mediasofttask2.repository.MatchResultRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchResultServiceImpl implements MatchResultService {

    private final MatchResultRepository matchResultRepository;
    private final TeamService teamService;
    private final MatchResultMapper mapper;

    @Override
    @Transactional
    public MatchResult createMatchResult(NewMatchResultRequest request) {
        log.info("Creating new Match result");
        MatchResult matchResult = mapper.mapMatchResult(request);
        Team hometeam = teamService.findById(request.getHomeTeam_id());
        Team guestTeam = teamService.findById(request.getGuestTeam_id());
        checkIsTeamsDifferent(hometeam, guestTeam);
        matchResult.setHomeTeam(hometeam);
        log.info("Set to match result home team {}", hometeam);
        matchResult.setGuestTeam(guestTeam);
        log.info("Set to match result guest team {}", guestTeam);
        matchResult = matchResultRepository.save(matchResult);
        log.info("Match result saved success");
        return matchResult;
    }

    @Override
    @Transactional
    public MatchResult updateMatchResult(UpdateMatchResultRequest request, Long matchResultId) {
        log.info("Updating match result with id: {}", matchResultId);
        MatchResult matchResult = matchResultRepository.findByIdWithTeams(matchResultId)
                .orElseThrow(() -> new EntityNotFound("Match result is not found"));
        if (request.getGuestTeam_id() != null && !matchResult.getGuestTeam().getId().equals(request.getGuestTeam_id())) {
            Team guestTeam = teamService.findById(request.getGuestTeam_id());
            log.info("Change guest team from team id: {} to team id: {}",
                    matchResult.getGuestTeam().getId(), guestTeam.getId());
            matchResult.setGuestTeam(guestTeam);
        }
        if (request.getHomeTeam_id() != null && !matchResult.getHomeTeam().getId().equals(request.getHomeTeam_id())) {
            Team homeTeam = teamService.findById(request.getHomeTeam_id());
            log.info("Change home team id: {} to team id: {}",
                    matchResult.getHomeTeam().getId(), homeTeam.getId());
            matchResult.setHomeTeam(homeTeam);
        }
        checkIsTeamsDifferent(matchResult.getHomeTeam(), matchResult.getGuestTeam());
        mapper.updateMatchResult(request, matchResult);
        log.info("Match result updated success");
        return matchResultRepository.save(matchResult);
    }

    @Override
    public MatchResult getMatchResultById(Long matchResultId) {
        log.info("Getting match result by id: {}", matchResultId);
        return matchResultRepository.findById(matchResultId)
                .orElseThrow(() -> new EntityNotFound("MatchResult is not found"));
    }

    @Override
    @Transactional
    public void deleteMatchResultById(Long matchResultId) {
        log.info("Trying to delete match result by id: {}", matchResultId);
        matchResultRepository.findById(matchResultId)
                .orElseThrow(() -> new EntityNotFound("MatchResult is not found"));
        matchResultRepository.deleteById(matchResultId);
        log.info("Match result deleted success");
    }

    @Override
    public List<MatchResult> getMatchResultsByDateAndSeason(LocalDate date, String season) {
        log.info("Getting season results by date {} and season {}", date, season);
        return matchResultRepository.findAllByMatchDateLessThanEqualAndBySeason(date, season);
    }

    private void checkIsTeamsDifferent(Team homeTeam, Team guestTeam) {
        log.info("Check team is different");
        if (Objects.equals(homeTeam.getId(), guestTeam.getId())) {
            throw new EqualsTeamException("Teams cant be equals in match");
        }
    }
}
