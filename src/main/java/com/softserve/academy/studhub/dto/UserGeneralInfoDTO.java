package com.softserve.academy.studhub.dto;

import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.entity.University;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserGeneralInfoDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    private String username;

    private LocalDate creationDate;

    private University university;

    private String imageUrl;

    private Set<Role> roles;
}
