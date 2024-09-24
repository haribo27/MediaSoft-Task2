package ru.zubcov.mediasoft.task2.mediasofttask2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.NewTeamRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.dto.UpdateTeamRequest;
import ru.zubcov.mediasoft.task2.mediasofttask2.exception.EntityAlreadyExists;
import ru.zubcov.mediasoft.task2.mediasofttask2.exception.EntityNotFound;
import ru.zubcov.mediasoft.task2.mediasofttask2.mapper.TeamMapper;
import ru.zubcov.mediasoft.task2.mediasofttask2.model.Team;
import ru.zubcov.mediasoft.task2.mediasofttask2.repository.TeamRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    @Override
    @Transactional
    public Team createTeam(NewTeamRequest request) {
        log.info("Creating new team {}", request.getName());
        if (teamRepository.findTeamByName(request.getName()).isPresent()) {
            log.info("Team is already exists");
            throw new EntityAlreadyExists("Team already exists");
        }
        Team team = teamMapper.mapToTeam(request);
        log.info("Team is created success {}", team);
        return teamRepository.save(team);
    }

    @Override
    @Transactional
    public Team updateTeam(UpdateTeamRequest request, Long teamId) {
        if (findByName(request.getName()) != null) {
            throw new EntityAlreadyExists("Team with this name already exists");
        }
        log.info("Updating team with id: {}", teamId);
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFound("Team not found"));
        teamMapper.updateTeam(request, team);
        team.setId(teamId);
        log.info("Team updated success");
        return teamRepository.save(team);
    }

    @Override
    @Transactional
    public void deleteTeam(Long teamId) {
        log.info("Deleting team with id: {}", teamId);
        teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFound("Team not found"));
        teamRepository.deleteById(teamId);
        log.info("Team with id: {} is deleted", teamId);
    }

    @Override
    public Team findById(Long teamId) {
        log.info("Trying to find team with id: {}", teamId);
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFound("Team not found"));
    }

    @Override
    public Team findByName(String name) {
        return teamRepository.findTeamByName(name)
                .orElseThrow(() -> new EntityNotFound("Team with name " + name + " not found"));
    }
}
