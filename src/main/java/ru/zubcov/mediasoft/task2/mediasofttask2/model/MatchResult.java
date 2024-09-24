package ru.zubcov.mediasoft.task2.mediasofttask2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "match_results")
public class MatchResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "season", nullable = false)
    private String season;
    @Column(name = "match_date", nullable = false)
    private LocalDate matchDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id", nullable = false)
    private Team homeTeam;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_team_id", nullable = false)
    private Team guestTeam;
    @Column(name = "home_team_goals", nullable = false)
    private short homeTeamGoals;
    @Column(name = "guest_team_goals")
    private short guestTeamGoals;
}
