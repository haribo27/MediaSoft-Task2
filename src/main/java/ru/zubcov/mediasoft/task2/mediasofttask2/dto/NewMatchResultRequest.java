package ru.zubcov.mediasoft.task2.mediasofttask2.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class NewMatchResultRequest {

    @NotBlank
    @Size(max = 5)
    @Pattern(regexp = "\\d{2}/\\d{2}", message = "Сезон должен соответствовать формату 'YY/YY'")
    private String season;
    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @NotNull
    private LocalDate matchDate;
    @NotNull
    @Positive
    private Long homeTeam_id;
    @NotNull
    @Positive
    private Long guestTeam_id;
    @NotNull
    @Min(0)
    private Short homeTeamGoals;
    @NotNull
    @Min(0)
    private Short guestTeamGoals;
}
