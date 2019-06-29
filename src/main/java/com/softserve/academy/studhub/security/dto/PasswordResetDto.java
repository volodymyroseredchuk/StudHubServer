package com.softserve.academy.studhub.security.dto;

import com.softserve.academy.studhub.constants.ValidationConstants;
import com.softserve.academy.studhub.constraint.FieldMatch;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
public class PasswordResetDto {

    @NotBlank(message = ValidationConstants.EMPTY_PASSWORD)
    @Size(min = ValidationConstants.PASSWORD_MIN_LENGTH, max = ValidationConstants.PASSWORD_MAX_LENGTH,
            message = ValidationConstants.INVALID_PASSWORD_LENGTH)
    private String password;

    @NotBlank(message = ValidationConstants.EMPTY_PASSWORD)
    @Size(min = ValidationConstants.PASSWORD_MIN_LENGTH, max = ValidationConstants.PASSWORD_MAX_LENGTH,
            message = ValidationConstants.INVALID_PASSWORD_LENGTH)
    private String confirmPassword;

    @NotBlank(message = ValidationConstants.EMPTY_TOKEN)
    private String token;
}
