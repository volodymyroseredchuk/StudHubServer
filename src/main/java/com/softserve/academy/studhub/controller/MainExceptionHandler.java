package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.exceptions.ErrorDetails;
import com.softserve.academy.studhub.exceptions.ErrorMessage;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.exceptions.UserAlreadyExistsAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class MainExceptionHandler extends ResponseEntityExceptionHandler {

    // This is for unauthorised access errors
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(
            AccessDeniedException ex) {
        ErrorDetails details = new ErrorDetails(HttpStatus.UNAUTHORIZED,
                ErrorMessage.NOT_AUTHORISED);
        ex.printStackTrace();
        return new ResponseEntity<>(details, HttpStatus.UNAUTHORIZED);
    }

    // Custom response if entity not found
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        ErrorDetails details = new ErrorDetails(HttpStatus.NOT_FOUND, ex.getMessage());
        ex.printStackTrace();
        log.error(ex.getMessage());
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorDetails details = new ErrorDetails(HttpStatus.UNAUTHORIZED, ex.getMessage());
        ex.printStackTrace();
        log.error(ex.getMessage());
        return new ResponseEntity<>(details, HttpStatus.UNAUTHORIZED);
    }

    //This is optional. If no use - delete
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorDetails details = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    // This is for all other errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        ErrorDetails details = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.SERVER_ERROR);
        ex.printStackTrace();
        return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // This one for wrong url params (letter instead of number etc)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleIllegalArgument(MethodArgumentTypeMismatchException ex) {
        ErrorDetails details = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsAuthenticationException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsAuthenticationException(UserAlreadyExistsAuthenticationException ex) {
        ErrorDetails details = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    // This is supposed to catch errors thrown by @Valid, @NotNull etc.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        ErrorDetails details = new ErrorDetails(HttpStatus.BAD_REQUEST, ErrorMessage.BAD_ARGUMENT);
        ex.printStackTrace();
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }
}
