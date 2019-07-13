package com.softserve.academy.studhub.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TeamForListDTO {

    private Integer id;

    private String title;

    private UserGeneralInfoDTO user;

    private LocalDateTime creationDate;

    private LocalDateTime modifiedDate;
}
