package com.softserve.academy.studhub.dto;

import com.softserve.academy.studhub.entity.Teacher;
import com.softserve.academy.studhub.entity.University;
import com.softserve.academy.studhub.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeedbackDTO {

    private Integer id;

    private String body;

    private Integer mark;

    private Teacher teacher;

    private University university;

    private User user;

    private Integer rate;

}
