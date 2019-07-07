package com.softserve.academy.studhub.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserForListDTO {

    private Integer id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String imageUrl;
}
