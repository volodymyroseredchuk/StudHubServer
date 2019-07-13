package com.softserve.academy.studhub.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ProposalDTO {

    private Integer id;

    private String body;

    private Integer price;

    private Integer daysCount;

    private LocalDateTime creationDate;

    private UserGeneralInfoDTO user;
}
