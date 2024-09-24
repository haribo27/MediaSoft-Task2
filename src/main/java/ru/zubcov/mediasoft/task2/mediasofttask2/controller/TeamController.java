package ru.zubcov.mediasoft.task2.mediasofttask2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.NewTeamRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.TeamDto;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.UpdateTeamRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.mapper.TeamMapper;
import ru.zubcov.mediasoft.task2.mediasofttask2.model.Team;
import ru.zubcov.mediasoft.task2.mediasofttask2.service.TeamService;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
@Validated
public class TeamController {

    private final TeamService teamService;
    private final TeamMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeamDto createTeam(@RequestBody @Valid NewTeamRequest request) {
        Team team = teamService.createTeam(request);
        return mapper.mapTeamDto(team);
    }

    @PatchMapping("/{teamId}")
    public TeamDto updateTeam(@RequestBody UpdateTeamRequest request,
                              @PathVariable("teamId") Long teamId) {
        Team team = teamService.updateTeam(request, teamId);
        return mapper.mapTeamDto(team);
    }

    @DeleteMapping("/{teamId}")
    public void deleteTeam(@PathVariable("teamId") Long teamId) {
        teamService.deleteTeam(teamId);
    }

    @GetMapping("/{teamId}")
    @ResponseStatus(HttpStatus.OK)
    public TeamDto getTeamById(@PathVariable("teamId") Long teamId) {
        Team team = teamService.findById(teamId);
        return mapper.mapTeamDto(team);
    }
}
