package ru.zubcov.mediasoft.task2.mediasofttask2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.MatchResultDto;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.NewMatchResultRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.UpdateMatchResultRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.mapper.MatchResultMapper;
import ru.zubcov.mediasoft.task2.mediasofttask2.model.MatchResult;
import ru.zubcov.mediasoft.task2.mediasofttask2.service.MatchResultService;

@RestController
@RequestMapping("/results")
@Validated
@RequiredArgsConstructor
public class MatchResultController {

    private final MatchResultService matchResultService;
    private final MatchResultMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MatchResultDto createMatchResult(@RequestBody @Valid NewMatchResultRequest request) {
        MatchResult matchResult = matchResultService.createMatchResult(request);
        return mapper.mapToMatchResultDto(matchResult);
    }

    @PatchMapping("/{matchResultId}")
    public MatchResultDto updateMatchResult(@RequestBody @Valid UpdateMatchResultRequest request,
                                            @PathVariable("matchResultId") Long matchResultId) {
        MatchResult matchResult = matchResultService.updateMatchResult(request, matchResultId);
        return mapper.mapToMatchResultDto(matchResult);
    }

    @GetMapping("/{matchResultId}")
    public MatchResultDto getMatchResultById(@PathVariable("matchResultId") Long matchResultId) {
        MatchResult matchResult = matchResultService.getMatchResultById(matchResultId);
        return mapper.mapToMatchResultDto(matchResult);
    }

    @DeleteMapping("/{matchResultId}")
    public void deleteMatchResultById(@PathVariable("matchResultId") Long matchResultId) {
        matchResultService.deleteMatchResultById(matchResultId);
    }
}
