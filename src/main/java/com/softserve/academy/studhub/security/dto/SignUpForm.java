package com.softserve.academy.studhub.security.dto;

import com.softserve.academy.studhub.constants.ValidationConstants;
import com.softserve.academy.studhub.entity.University;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class SignUpForm {

    @NotBlank(message = ValidationConstants.EMPTY_FIRSTNAME)
    @Size(min = ValidationConstants.FIRSTNAME_MIN_LENGTH, max = ValidationConstants.FIRSTNAME_MAX_LENGTH,
            message = ValidationConstants.INVALID_FIRSTNAME_LENGTH)
    private String firstName;

    @NotBlank(message = ValidationConstants.EMPTY_LASTNAME)
    @Size(min = ValidationConstants.LASTNAME_MIN_LENGTH, max = ValidationConstants.LASTNAME_MAX_LENGTH,
            message = ValidationConstants.INVALID_LASTNAME_LENGTH)
    private String lastName;

    @NotBlank(message = ValidationConstants.EMPTY_USERNAME)
    @Size(min = ValidationConstants.USERNAME_MIN_LENGTH, max = ValidationConstants.USERNAME_MAX_LENGTH,
            message = ValidationConstants.INVALID_USERNAME_LENGTH)
    private String username;

    @NotBlank(message = ValidationConstants.EMPTY_EMAIL)
    @Email(message = ValidationConstants.INVALID_EMAIL)
    @Size(min = ValidationConstants.EMAIL_MIN_LENGTH, max = ValidationConstants.EMAIL_MAX_LENGTH,
            message = ValidationConstants.INVALID_EMAIL_LENGTH)
    private String email;

    @NotBlank(message = ValidationConstants.EMPTY_PASSWORD)
    @Size(min = ValidationConstants.PASSWORD_MIN_LENGTH, max = ValidationConstants.PASSWORD_MAX_LENGTH,
            message = ValidationConstants.INVALID_PASSWORD_LENGTH)
    @Size(min=6, max = 16)
    private String password;

    private University university;
}