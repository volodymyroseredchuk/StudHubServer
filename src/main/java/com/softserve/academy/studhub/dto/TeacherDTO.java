package com.softserve.academy.studhub.dto;

import com.softserve.academy.studhub.entity.University;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TeacherDTO {

    private Integer id;

    private LocalDateTime creationDate;

    private LocalDateTime modifiedDate;

    private String firstName;

    private String lastName;

    private String imageUrl;

    private University university;

    private Double mark;
}
