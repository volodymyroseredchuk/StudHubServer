package com.softserve.academy.studhub.dto;

import com.softserve.academy.studhub.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TeamDTO {

    private Integer id;

    private String name;

    private UserGeneralInfoDTO user;

    private LocalDateTime creationDate;

    private LocalDateTime modifiedDate;

    private List<UserGeneralInfoDTO> userList;
}
