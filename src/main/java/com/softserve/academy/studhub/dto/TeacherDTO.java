package com.softserve.academy.studhub.dto;

import com.softserve.academy.studhub.entity.University;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeacherDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    private String imageUrl;

    private University university;

    private Double mark;

}
