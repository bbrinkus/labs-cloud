package com.brinkus.labs.cloud.service.rest;

import com.brinkus.labs.cloud.service.type.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleGeneralException(Exception e) {
        ErrorMessage message = new ErrorMessage(e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(Exception e) {
        ErrorMessage message = new ErrorMessage(e.getClass().getSimpleName(), e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

}
