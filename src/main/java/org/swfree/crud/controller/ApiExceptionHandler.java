package org.swfree.crud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.swfree.crud.exception.PlanetNotFoundException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(PlanetNotFoundException.class)
    public ResponseEntity handleNotFound() {
        return new ResponseEntity(NOT_FOUND);
    }


    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleInternalServerError() {
        return new ResponseEntity(INTERNAL_SERVER_ERROR);
    }

}
