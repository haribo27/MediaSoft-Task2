package ru.zubcov.mediasoft.task2.mediasofttask2.model;

import lombok.Data;

import java.time.Instant;

@Data
public class ErrorResponse {

    private int status;
    private String message;
    private Instant timestamp;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = Instant.now();
    }
}
