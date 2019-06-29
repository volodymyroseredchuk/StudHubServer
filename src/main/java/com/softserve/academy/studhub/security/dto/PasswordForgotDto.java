package com.softserve.academy.studhub.security.dto;

import com.softserve.academy.studhub.constants.ValidationConstants;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PasswordForgotDto {

    @NotBlank(message = ValidationConstants.EMPTY_EMAIL)
    @Email(message = ValidationConstants.INVALID_EMAIL)
    @Size(min = ValidationConstants.EMAIL_MIN_LENGTH, max = ValidationConstants.EMAIL_MAX_LENGTH,
            message = ValidationConstants.INVALID_EMAIL_LENGTH)
    private String email;
}
