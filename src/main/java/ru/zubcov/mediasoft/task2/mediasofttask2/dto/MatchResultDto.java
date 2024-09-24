package ru.zubcov.mediasoft.task2.mediasofttask2.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MatchResultDto {

    private Long id;
    private String season;
    private LocalDate matchDate;
    private TeamDto homeTeam;
    private TeamDto guestTeam;
    private short homeTeamGoals;
    private short guestTeamGoals;
}
