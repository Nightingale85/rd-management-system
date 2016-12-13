package com.epam.rd.backend.core.exception;

public class EntityIsAlreadyExistException extends RuntimeException {
    public EntityIsAlreadyExistException(String message) {
        super(message);
    }
}
