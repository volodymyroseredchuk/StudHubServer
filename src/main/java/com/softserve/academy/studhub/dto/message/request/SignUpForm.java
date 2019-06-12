package com.softserve.academy.studhub.dto.message.request;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class SignUpForm {

    private String firstName;

    private String lastName;

    private String username;

    private String email;
    
    private Set<String> role;

    @Size(min=6, max = 16)
    private String password;

    private String imageUrl;
}