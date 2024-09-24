package ru.zubcov.mediasoft.task2.mediasofttask2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewTeamRequest {

    @NotBlank
    @Size(max = 60)
    private String name;
}
