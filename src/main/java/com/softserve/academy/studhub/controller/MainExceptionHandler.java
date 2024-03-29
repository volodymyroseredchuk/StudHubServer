package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.exceptions.*;
import com.softserve.academy.studhub.constants.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
@Slf4j
public class MainExceptionHandler extends ResponseEntityExceptionHandler {

    // This is for unauthorised access errors
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(
            AccessDeniedException ex) {
        ErrorDetails details = new ErrorDetails(HttpStatus.UNAUTHORIZED,
                ErrorMessage.NOT_AUTHORISED);
        logInfo(ex);
        log.error(ex.getMessage());
        return new ResponseEntity<>(details, HttpStatus.UNAUTHORIZED);
    }

    // Custom response if entity not found
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
        ErrorDetails details = new ErrorDetails(HttpStatus.NOT_FOUND, ex.getMessage());
        logInfo(ex);
        log.error(ex.getMessage());
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {
        ErrorDetails details = new ErrorDetails(HttpStatus.UNAUTHORIZED, ex.getMessage());
        logInfo(ex);
        log.error(ex.getMessage());
        return new ResponseEntity<>(details, HttpStatus.UNAUTHORIZED);
    }

    //This is optional. If no use - delete
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorDetails details = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getMessage());
        logInfo(ex);
        log.error(ex.getMessage());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    /*// This is for all other errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        ErrorDetails details = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessage.SERVER_ERROR);
        logInfo(ex);
        log.error(ex.getMessage());
        return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
    }*/

    // This one for wrong url params (letter instead of number etc)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleIllegalArgument(MethodArgumentTypeMismatchException ex) {
        ErrorDetails details = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getMessage());
        logInfo(ex);
        log.error(ex.getMessage());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExistsAuthenticationException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsAuthenticationException(UserAlreadyExistsAuthenticationException ex) {
        ErrorDetails details = new ErrorDetails(HttpStatus.BAD_REQUEST, ex.getMessage());
        logInfo(ex);
        log.error(ex.getMessage());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<Object> handleExpiredTokenException(ExpiredTokenException ex) {
        ErrorDetails details = new ErrorDetails(HttpStatus.GONE, ex.getMessage());
        logInfo(ex);
        log.error(ex.getMessage());
        return new ResponseEntity<>(details, HttpStatus.GONE);
    }

    @ExceptionHandler(NotConfirmedException.class)
    public ResponseEntity<Object> handleNotConfirmedException(NotConfirmedException ex) {
        ErrorDetails details = new ErrorDetails(HttpStatus.FORBIDDEN, ex.getMessage());
        logInfo(ex);
        log.error(ex.getMessage());
        return new ResponseEntity<>(details, HttpStatus.FORBIDDEN);
    }

    // This is supposed to catch errors thrown by @Valid, @NotNull etc.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        ErrorDetails details = new ErrorDetails(HttpStatus.BAD_REQUEST, ErrorMessage.BAD_ARGUMENT);
        logInfo(ex);
        log.error(ex.getMessage());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<Object> handleMethodNotAllowed (MethodNotAllowedException ex, HttpServletRequest request) {
        ErrorDetails details = new ErrorDetails(HttpStatus.METHOD_NOT_ALLOWED,  request.getMethod() + ErrorMessage.WRONG_METHOD);
        logInfo(ex);
        log.error(ex.getMessage());
        return new ResponseEntity<>(details, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status,
                                                                         WebRequest request) {

        ErrorDetails details = new ErrorDetails(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
        logInfo(ex);
        log.error(ex.getMessage());
        return new ResponseEntity<>(details, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        ErrorDetails details = new ErrorDetails(HttpStatus.BAD_REQUEST, ErrorMessage.BAD_ARGUMENT);
        logInfo(ex);
        log.error(ex.getMessage());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    private void logInfo(Exception ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        String stackTrace = sw.toString();
        log.debug(stackTrace);
    }

}
