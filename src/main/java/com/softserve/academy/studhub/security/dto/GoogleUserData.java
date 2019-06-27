package com.softserve.academy.studhub.security.dto;

import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.entity.enums.RoleName;
import com.softserve.academy.studhub.service.RoleService;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;

@Data
public class GoogleUserData {

    private String authToken;

    private String email;

    private String firstName;

    private String id;

    private String idToken;

    private String lastName;

    private String name;

    private String photoUrl;

    private String provider;


    public User toUser (RoleService roleService) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setCreationDate(LocalDate.now());
        user.setImageUrl(photoUrl);
        user.setUsername(email);
        user.setRoles(new HashSet<Role>(){{
            add(roleService.findByName(RoleName.ROLE_USER));
        }});
        return user;
    }

}
