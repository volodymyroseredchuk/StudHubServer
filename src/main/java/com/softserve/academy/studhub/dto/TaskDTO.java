package com.softserve.academy.studhub.dto;

import com.softserve.academy.studhub.entity.Tag;
import com.softserve.academy.studhub.entity.enums.TaskStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class TaskDTO {

    private Integer id;

    private String title;

    private String body;

    private LocalDateTime creationDate;

    private LocalDateTime modifiedDate;

    private LocalDateTime deadlineDate;

    private Integer expectedPrice;

    private UserGeneralInfoDTO user;

    private TaskStatus status;

    private Set<Tag> tagList;
}
