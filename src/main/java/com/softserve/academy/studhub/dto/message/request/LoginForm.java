package com.softserve.academy.studhub.dto.message.request;

import lombok.Data;

@Data
public class LoginForm {

    private String username;

    private String password;
}