package ru.zubcov.mediasoft.task2.mediasofttask2.exception;

public class EntityAlreadyExists extends RuntimeException {
    public EntityAlreadyExists(String message) {
        super(message);
    }
}
