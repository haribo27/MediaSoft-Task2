package ru.zubcov.mediasoft.task2.mediasofttask2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zubcov.mediasoft.task2.mediasofttask2.model.Team;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findTeamByName(String name);
}
