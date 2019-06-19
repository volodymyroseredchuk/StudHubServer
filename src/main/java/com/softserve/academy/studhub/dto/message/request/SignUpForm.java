package com.softserve.academy.studhub.dto.message.request;

import com.softserve.academy.studhub.entity.University;
import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class SignUpForm {

    private String firstName;

    private String lastName;

    private String username;

    private String email;
    
    @Size(min=6, max = 16)
    private String password;

    private String imageUrl;

    private LocalDate creationDate;

    private University university;
}