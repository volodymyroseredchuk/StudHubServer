package com.softserve.academy.studhub.dto;

import com.softserve.academy.studhub.entity.Tag;
import com.softserve.academy.studhub.entity.Team;
import com.softserve.academy.studhub.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class QuestionDTO {

    private Integer id;
    private String title;
    private String body;
    private LocalDate creationDate;
    private LocalDate modifiedDate;
    private UserGeneralInfoDTO user;
    private List<AnswerDTO> answerList;
    private Set<Tag> tagList;

}
