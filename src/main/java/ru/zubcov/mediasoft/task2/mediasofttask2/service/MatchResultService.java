package ru.zubcov.mediasoft.task2.mediasofttask2.service;

import ru.zubcov.mediasoft.task2.mediasofttask2.dto.NewMatchResultRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.UpdateMatchResultRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.model.MatchResult;

import java.time.LocalDate;
import java.util.List;

public interface MatchResultService {

    MatchResult createMatchResult(NewMatchResultRequest request);

    MatchResult updateMatchResult(UpdateMatchResultRequest request, Long matchResultId);

    MatchResult getMatchResultById(Long matchResultId);

    void deleteMatchResultById(Long matchResultId);

    List<MatchResult> getMatchResultsByDateAndSeason(LocalDate date, String season);
}
