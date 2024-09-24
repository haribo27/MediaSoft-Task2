package ru.zubcov.mediasoft.task2.mediasofttask2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.SeasonTeamResult;
import ru.zubcov.mediasoft.task2.mediasofttask2.service.SeasonResultService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("season_result")
@RequiredArgsConstructor
public class SeasonResultController {

    private final SeasonResultService seasonResultService;

    @GetMapping
    public List<SeasonTeamResult> getSeasonResultsByDate(@RequestParam(name = "date") LocalDate date,
                                                         @RequestParam(name = "season", defaultValue = "23/24") String season) {
        return seasonResultService.getSeasonResult(date, season);
    }
}
