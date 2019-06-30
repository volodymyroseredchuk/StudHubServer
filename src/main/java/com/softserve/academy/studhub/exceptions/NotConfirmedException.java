package com.softserve.academy.studhub.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotConfirmedException extends RuntimeException {

    public NotConfirmedException(String message) {
        super(message);
    }
}

