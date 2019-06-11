package com.softserve.academy.studhub.dto.message.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class SignUpForm {

    @NotBlank
    @Size(min = 3, max = 16)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 16)
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 16)
    private String username;

    @NotBlank
    @Size(max = 60)
    @Email
    private String email;
    
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 16)
    private String password;

    private String imageUrl;

}