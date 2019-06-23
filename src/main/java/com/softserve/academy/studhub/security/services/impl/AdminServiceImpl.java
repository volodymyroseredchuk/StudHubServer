package com.softserve.academy.studhub.security.services.impl;

import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.entity.enums.RoleName;
import com.softserve.academy.studhub.security.services.AdminService;
import com.softserve.academy.studhub.service.RoleService;
import com.softserve.academy.studhub.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final RoleService roleService;

    public AdminServiceImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void addRole(Integer userId, RoleName roleName) {

        User user = userService.findById(userId);

        if(userService.isUserPrivilegedByRole(userId, roleName) == false){

            Set<Role> roles = user.getRoles();
            Role role = roleService.findByName(roleName);
            roles.add(role);
            user.setRoles(roles);
            userService.update(user);
        } else {
            throw new IllegalArgumentException("This user is already " + roleName);
        }
    }

    @Override
    public void removeRole(Integer userId, RoleName roleName) {

        User user = userService.findById(userId);

        if(userService.isUserPrivilegedByRole(userId, roleName)){

            Set<Role> roles = user.getRoles();
            Role role = roleService.findByName(roleName);
            roles.remove(role);
            user.setRoles(roles);
            userService.update(user);
        } else {
            throw new IllegalArgumentException("This user isn't " + roleName);
        }
    }
}
