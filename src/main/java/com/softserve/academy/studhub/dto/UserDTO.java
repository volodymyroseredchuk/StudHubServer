package com.softserve.academy.studhub.dto;

import com.softserve.academy.studhub.constants.ValidationConstants;
import com.softserve.academy.studhub.entity.Privilege;
import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.entity.University;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDTO {

    @NotBlank(message = ValidationConstants.EMPTY_FIRSTNAME)
    @Size(min = ValidationConstants.FIRSTNAME_MIN_LENGTH, max = ValidationConstants.FIRSTNAME_MAX_LENGTH,
            message = ValidationConstants.INVALID_FIRSTNAME_LENGTH)
    private String firstName;

    @NotBlank(message = ValidationConstants.EMPTY_LASTNAME)
    @Size(min = ValidationConstants.LASTNAME_MIN_LENGTH, max = ValidationConstants.LASTNAME_MAX_LENGTH,
            message = ValidationConstants.INVALID_LASTNAME_LENGTH)
    private String lastName;

    @NotBlank(message = ValidationConstants.EMPTY_EMAIL)
    @Email(message = ValidationConstants.INVALID_EMAIL)
    @Size(min = ValidationConstants.EMAIL_MIN_LENGTH, max = ValidationConstants.EMAIL_MAX_LENGTH,
            message = ValidationConstants.INVALID_EMAIL_LENGTH)
    private String email;

    private String username;

    private LocalDate creationDate;

    private University university;

    private String imageUrl;

    private Set<Role> roles = new HashSet<>();

    private Set<PrivilegeDTO> privileges = new HashSet<>();

}
