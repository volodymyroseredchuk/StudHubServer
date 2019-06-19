package com.softserve.academy.studhub.dto;

import com.softserve.academy.studhub.entity.Comment;
import com.softserve.academy.studhub.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AnswerDTO {

    private Integer id;

    private String body;

    private Boolean approved;

    private List<CommentDTO> comment;

    private UserGeneralInfoDTO user;

    private LocalDateTime creationDate;

    private Integer rate;
}
