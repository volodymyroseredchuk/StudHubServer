package com.softserve.academy.studhub.dto;

import com.softserve.academy.studhub.entity.User;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TeamForListDTO {

    private Integer id;

    private String name;

    private User user;

    private LocalDateTime creationDate;

    private LocalDateTime modifiedDate;
}
