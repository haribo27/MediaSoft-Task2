package ru.zubcov.mediasoft.task2.mediasofttask2.service;

import ru.zubcov.mediasoft.task2.mediasofttask2.dto.NewTeamRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.UpdateTeamRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.model.Team;

public interface TeamService {

    Team createTeam(NewTeamRequest request);

    Team updateTeam(UpdateTeamRequest request, Long teamId);

    void deleteTeam(Long teamId);

    Team findById(Long teamId);
}
