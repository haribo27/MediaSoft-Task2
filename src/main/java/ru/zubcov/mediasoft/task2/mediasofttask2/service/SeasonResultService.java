package ru.zubcov.mediasoft.task2.mediasofttask2.service;

import ru.zubcov.mediasoft.task2.mediasofttask2.dto.SeasonTeamResult;

import java.time.LocalDate;
import java.util.List;

public interface SeasonResultService {

    List<SeasonTeamResult> getSeasonResult(LocalDate date, String season);
}
