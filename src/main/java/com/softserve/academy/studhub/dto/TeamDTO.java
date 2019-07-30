package com.softserve.academy.studhub.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TeamDTO {

    private Integer id;

    private String title;

    private String description;

    private UserForListDTO user;

    private LocalDateTime creationDate;

    private LocalDateTime modifiedDate;

    private List<UserForListDTO> userList;

    private Boolean isPublic;
}
