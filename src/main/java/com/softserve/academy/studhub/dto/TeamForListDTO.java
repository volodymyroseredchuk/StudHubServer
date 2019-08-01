package com.softserve.academy.studhub.dto;

import com.softserve.academy.studhub.entity.Invitation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TeamForListDTO {

    private Integer id;

    private String title;

    private String description;

    private UserGeneralInfoDTO user;

    private LocalDateTime creationDate;

    private LocalDateTime modifiedDate;

    private Boolean isPublic;

    private List<Invitation> invitations;

}
