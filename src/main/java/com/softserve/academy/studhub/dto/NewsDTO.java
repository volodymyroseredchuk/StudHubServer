package com.softserve.academy.studhub.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NewsDTO {

    private Integer id;
    private String title;
    private String body;
    private LocalDateTime creationDate;
    private String sourceUrl;
}
