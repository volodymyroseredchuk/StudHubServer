package com.softserve.academy.studhub.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {

    private Integer id;

    private Integer payment;

    private Integer formulation;

    private Integer clarity;

    private Integer contact;
}
