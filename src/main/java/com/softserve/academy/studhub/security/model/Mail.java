package com.softserve.academy.studhub.security.model;

import lombok.Data;

import java.util.Map;

@Data
public class Mail {

    private String from;

    private String to;

    private String subject;

    private Map<String, Object> model;
}
