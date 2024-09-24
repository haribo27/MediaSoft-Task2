package ru.zubcov.mediasoft.task2.mediasofttask2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.zubcov.mediasoft.task2.mediasofttask2.model.MatchResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MatchResultRepository extends JpaRepository<MatchResult, Long> {

    @Query("select mr " +
            "from MatchResult mr " +
            "JOIN FETCH mr.guestTeam " +
            "JOIN FETCH mr.homeTeam " +
            "where mr.id = ?1 ")
    Optional<MatchResult> findByIdWithTeams(Long matchResultId);

    @Query("select mr " +
            "FROM MatchResult mr " +
            "JOIN FETCH mr.guestTeam " +
            "JOIN FETCH mr.homeTeam " +
            "WHERE mr.matchDate <= ?1 " +
            "AND mr.season = ?2 ")
    List<MatchResult> findAllByMatchDateLessThanEqualAndBySeason(LocalDate date, String season);
}
