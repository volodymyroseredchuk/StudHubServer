package com.softserve.academy.studhub.security.dto;

import com.softserve.academy.studhub.constants.ValidationConstants;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginForm {

    @NotBlank(message = ValidationConstants.EMPTY_USERNAME)
    @Size(min = ValidationConstants.USERNAME_MIN_LENGTH, max = ValidationConstants.USERNAME_MAX_LENGTH,
            message = ValidationConstants.INVALID_USERNAME_LENGTH)
    private String username;

    @NotBlank(message = ValidationConstants.EMPTY_PASSWORD)
    @Size(min = ValidationConstants.PASSWORD_MIN_LENGTH, max = ValidationConstants.PASSWORD_MAX_LENGTH,
            message = ValidationConstants.INVALID_PASSWORD_LENGTH)
    private String password;
}