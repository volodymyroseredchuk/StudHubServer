package com.softserve.academy.studhub.security.dto;

import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.entity.enums.RoleName;
import com.softserve.academy.studhub.service.RoleService;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;

@Data
@Getter
@Setter
public class FacebookData {

    private String email;

    private String first_name;

    private String id;

    private String last_name;

    private String name;

    public User toUser (RoleService roleService) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(id);
        user.setFirstName(first_name);
        user.setLastName(last_name);
        user.setUsername(name);
        user.setCreationDate(LocalDate.now());
        user.setRoles(new HashSet<Role>(){{
            add(roleService.findByName(RoleName.ROLE_USER));
        }});
        return user;
    }

}
