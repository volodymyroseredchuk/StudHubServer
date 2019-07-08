package com.softserve.academy.studhub.dto;

import com.softserve.academy.studhub.entity.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class QuestionForListDTO {

    private Integer id;
    private String title;
    private LocalDate creationDate;
    private LocalDate modifiedDate;
    private UserGeneralInfoDTO user;
    private Set<Tag> tagList;
}
