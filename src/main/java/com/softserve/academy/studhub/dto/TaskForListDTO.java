package com.softserve.academy.studhub.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TaskForListDTO {

    private Integer id;

    private String title;

    private LocalDateTime creationDate;

    private LocalDateTime deadlineDate;

    private Integer expectedPrice;

    private UserGeneralInfoDTO user;
}
