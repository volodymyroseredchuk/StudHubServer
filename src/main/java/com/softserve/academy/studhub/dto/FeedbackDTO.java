package com.softserve.academy.studhub.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeedbackDTO {

    private String body;

    private Integer mark;

    private Integer teacherId;

    private Integer universityId;

    private Integer rate;
}
