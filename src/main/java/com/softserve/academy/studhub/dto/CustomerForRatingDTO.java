package com.softserve.academy.studhub.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerForRatingDTO {

    private Double payment;

    private Double formulation;

    private Double clarity;

    private Double contact;
}
