package ru.zubcov.mediasoft.task2.mediasofttask2.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UpdateMatchResultRequest {

    @Size(max = 5)
    private String season;
    @DateTimeFormat
    private LocalDate matchDate;
    @Positive
    private Long homeTeam_id;
    @Positive
    private Long guestTeam_id;
    @Min(0)
    private Short homeTeamGoals;
    @Min(0)
    private Short guestTeamGoals;
}
