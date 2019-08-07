package com.softserve.academy.studhub.dto;

import com.softserve.academy.studhub.constants.ValidationConstants;
import com.softserve.academy.studhub.entity.Team;
import com.softserve.academy.studhub.entity.University;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"firstName", "lastName", "university", "imageUrl", "privileges", "emailSubscription"})
public class UserDTO {

    private Integer id;

    @NotBlank(message = ValidationConstants.EMPTY_FIRSTNAME)
    @Size(min = ValidationConstants.FIRSTNAME_MIN_LENGTH, max = ValidationConstants.FIRSTNAME_MAX_LENGTH,
            message = ValidationConstants.INVALID_FIRSTNAME_LENGTH)
    private String firstName;

    @NotBlank(message = ValidationConstants.EMPTY_LASTNAME)
    @Size(min = ValidationConstants.LASTNAME_MIN_LENGTH, max = ValidationConstants.LASTNAME_MAX_LENGTH,
            message = ValidationConstants.INVALID_LASTNAME_LENGTH)
    private String lastName;

    private String email;

    private String username;

    private LocalDate creationDate;

    private University university;

    private String imageUrl;

    private Set<PrivilegeDTO> privileges = new HashSet<>();

    private Boolean emailSubscription;

    private Integer cookiesCount;

    private List<Team> teamList;
}
