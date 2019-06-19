package com.softserve.academy.studhub.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorDetails {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private int errorCode;
    private String errorDetail;
    private String message;
    private Throwable cause;


    public ErrorDetails(HttpStatus status, String message, Throwable ex) {
        this.timestamp = LocalDateTime.now();
        this.errorCode = status.value();
        this.errorDetail = status.getReasonPhrase();
        this.message = message;
        this.cause = ex;

    }


}
