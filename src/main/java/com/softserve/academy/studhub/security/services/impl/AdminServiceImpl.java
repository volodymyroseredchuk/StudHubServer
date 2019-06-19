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
    public void raiseToModerator(Integer userId) {

        if(userService.isUserPrivileged(userId) == false){

            User user = userService.findById(userId);
            Set<Role> roles = user.getRoles();
            Role moderatorRole = roleService.findByName(RoleName.ROLE_MODERATOR);
            roles.add(moderatorRole);
            user.setRoles(roles);
            userService.update(user);
        } else {
            throw new IllegalArgumentException("This user is already moderator");
        }
    }

    @Override
    public void downToUser(Integer moderatorId) {

        if(userService.isUserPrivileged(moderatorId)){

            User user = userService.findById(moderatorId);
            Set<Role> roles = user.getRoles();
            Role moderatorRole = roleService.findByName(RoleName.ROLE_MODERATOR);
            roles.remove(moderatorRole);
            user.setRoles(roles);
            userService.update(user);
        } else {
            throw new IllegalArgumentException("This user isn't moderator");
        }
    }
}
