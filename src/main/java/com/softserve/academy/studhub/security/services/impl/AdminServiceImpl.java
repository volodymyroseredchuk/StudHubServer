package com.softserve.academy.studhub.security.services.impl;

import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.entity.enums.RoleName;
import com.softserve.academy.studhub.security.services.AdminService;
import com.softserve.academy.studhub.service.RoleService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final RoleService roleService;

    @Override
    public User addRole(Integer userId, RoleName roleName) {

        if (userService.isUserPrivilegedByRole(userId, roleName)) {
            throw new IllegalArgumentException(ErrorMessage.USER_IS_ALREADY + roleName);
        }

        User user = userService.findById(userId);
        Set<Role> roles = user.getRoles();
        Role role = roleService.findByName(roleName);
        roles.add(role);
        user.setRoles(roles);

        return userService.update(user);
    }

    @Override
    public User removeRole(Integer userId, RoleName roleName) {

        if (!userService.isUserPrivilegedByRole(userId, roleName)) {
            throw new IllegalArgumentException(ErrorMessage.USER_IS_NOT + roleName);
        }

        User user = userService.findById(userId);
        Set<Role> roles = user.getRoles();
        Role role = roleService.findByName(roleName);
        roles.remove(role);
        user.setRoles(roles);

        return userService.update(user);
    }
}
