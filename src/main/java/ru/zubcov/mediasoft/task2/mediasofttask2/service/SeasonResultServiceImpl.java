package ru.zubcov.mediasoft.task2.mediasofttask2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.SeasonTeamResult;
import ru.zubcov.mediasoft.task2.mediasofttask2.model.MatchResult;
import ru.zubcov.mediasoft.task2.mediasofttask2.model.Team;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SeasonResultServiceImpl implements SeasonResultService {

    private final MatchResultService matchResultService;

    @Override
    public List<SeasonTeamResult> getSeasonResult(LocalDate date, String season) {
        List<MatchResult> matchResults = matchResultService.getMatchResultsByDateAndSeason(date, season);

        Map<Team, SeasonTeamResult> teamResult = new HashMap<>();

        for (MatchResult matchResult : matchResults) {
            Team homeTeam = matchResult.getHomeTeam();
            Team guestTeam = matchResult.getGuestTeam();
            int homeScore = matchResult.getHomeTeamGoals();
            int guestScore = matchResult.getGuestTeamGoals();

            teamResult.putIfAbsent(homeTeam, new SeasonTeamResult());
            SeasonTeamResult homeResult = teamResult.get(homeTeam);
            if (homeResult.getTeamName() == null) {
                homeResult.setTeamName(homeTeam.getName());
            }
            homeResult.addMatch(homeScore, guestScore);

            teamResult.putIfAbsent(guestTeam, new SeasonTeamResult());
            SeasonTeamResult guestResult = teamResult.get(guestTeam);
            if (guestResult.getTeamName() == null) {
                guestResult.setTeamName(guestTeam.getName());
            }
            guestResult.addMatch(guestScore, homeScore);
        }

        return new ArrayList<>(teamResult.values()
                .stream()
                .sorted(Comparator.comparingInt(SeasonTeamResult::getPoints)
                        .reversed())
                .toList());
    }

}
