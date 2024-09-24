package ru.zubcov.mediasoft.task2.mediasofttask2.dto;

import lombok.Data;

@Data
public class SeasonTeamResult {

    private String teamName;
    private int numberOfGames;
    private int points;

    public void addMatch(int homeScore, int guestScore) {
        numberOfGames++;

        if (homeScore > guestScore) {
            points += 3;
        } else if (homeScore == guestScore) {
            points += 1;
        }
    }

}
