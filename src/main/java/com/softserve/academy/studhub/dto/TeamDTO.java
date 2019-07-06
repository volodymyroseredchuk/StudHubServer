package com.softserve.academy.studhub.dto;

import com.softserve.academy.studhub.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TeamDTO {

    private Integer id;

    private String name;

    private User user;

    private LocalDateTime creationDate;

    private LocalDateTime modifiedDate;

    private List<User> userList;
}
