package com.softserve.academy.studhub.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {

    private Integer id;

    private String body;

    private LocalDateTime creationDate;

    private LocalDateTime modifiedDate;

    private UserGeneralInfoDTO user;
}
