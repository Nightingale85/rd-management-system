package com.epam.rd.backend.web.controller;

import com.epam.rd.backend.core.exception.EntityDoesNotExistException;
import com.epam.rd.backend.core.exception.EntityIsAlreadyExistException;
import com.epam.rd.dto.ExceptionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);

    @ExceptionHandler(EntityDoesNotExistException.class)
    public ResponseEntity<ExceptionDto> handleEntityDoesNotExistException(EntityDoesNotExistException e) {
        LOGGER.error(e.getMessage());
        return ResponseEntity
                .status(NOT_FOUND)
                .body(new ExceptionDto(e.getMessage(), e.getClass()));
    }

    @ExceptionHandler(EntityIsAlreadyExistException.class)
    public ResponseEntity<ExceptionDto> handleEntityIsAlreadyExistException(EntityIsAlreadyExistException e) {
        LOGGER.error(e.getMessage());
        return ResponseEntity
                .status(CONFLICT)
                .body(new ExceptionDto(e.getMessage(), e.getClass()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleException(Exception e) {
        LOGGER.error(e.getMessage());
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new ExceptionDto(e.getMessage(), e.getClass()));
    }
}
