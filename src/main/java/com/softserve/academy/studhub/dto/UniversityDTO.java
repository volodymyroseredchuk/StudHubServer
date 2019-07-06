package com.softserve.academy.studhub.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UniversityDTO {
    private Integer id;

    private LocalDateTime creationDate;

    private LocalDateTime modifiedDate;

    private String name;

    private String city;

    private String imageUrl;

    private Double mark;
}
