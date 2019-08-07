package com.softserve.academy.studhub.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UniversityDTO {

    private Integer id;

    private String name;

    private String city;

    private String imageUrl;

    private Double mark;
}
