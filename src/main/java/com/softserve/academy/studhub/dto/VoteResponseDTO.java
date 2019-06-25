package com.softserve.academy.studhub.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VoteResponseDTO {
    private Integer id;
    private Integer value;
    private Integer answerId;
    private Integer feedbackId;
}
