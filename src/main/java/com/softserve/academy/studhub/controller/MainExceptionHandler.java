package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.exceptions.ErrorDetails;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MainExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(
            AccessDeniedException ex, WebRequest request) {
        ErrorDetails details = new ErrorDetails(HttpStatus.UNAUTHORIZED,
                "You don't have rights to access this resource", ex);
        return new ResponseEntity<>(details, details.getStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetails> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorDetails details = new ErrorDetails(HttpStatus.NOT_FOUND,
                ex.getMessage(), ex);
        return new ResponseEntity<>(details, details.getStatus());
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        ErrorDetails details = new ErrorDetails(status,
                "One or more of posted parameters is not valid", ex);
        return new ResponseEntity<>(details, details.getStatus());
    }
}
