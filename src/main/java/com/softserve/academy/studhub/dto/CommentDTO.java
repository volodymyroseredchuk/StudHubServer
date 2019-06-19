package com.softserve.academy.studhub.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.softserve.academy.studhub.entity.Answer;
import com.softserve.academy.studhub.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
