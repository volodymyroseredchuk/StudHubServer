package com.softserve.academy.studhub.security.dto;

import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.entity.enums.RoleName;
import com.softserve.academy.studhub.service.RoleService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@AllArgsConstructor
public class SignupConverter {

    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    public User convertToUser(SignUpForm signUpForm) {

        User user = modelMapper.map(signUpForm, User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCookiesCount(1000);
        user.setRoles(new HashSet<Role>() {{
            add(roleService.findByName(RoleName.ROLE_USER));
        }});
        return user;
    }
}
