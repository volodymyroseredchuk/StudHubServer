package com.softserve.academy.studhub.security.services.impl;

import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.entity.enums.RoleName;
import com.softserve.academy.studhub.repository.UserRepository;
import com.softserve.academy.studhub.security.services.AdminService;
import com.softserve.academy.studhub.service.RoleService;
import com.softserve.academy.studhub.service.UserService;
import com.softserve.academy.studhub.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    private UserService userService;

    private AdminService adminService;

    private User user;

    @Before
    public void init() {

        userService = new UserServiceImpl(userRepository);
        adminService = new AdminServiceImpl(userService, roleService);

        user = new User();
        user.setFirstName("Rosty");
        user.setLastName("Hlynka");
        user.setUsername("Jarvizz");
        user.setPassword("123123");
        user.setEmail("test.test");
        user.setEmailSubscription(true);
        user.setIsActivated(true);
        user.setRoles(new HashSet<>());
    }

    @Test
    public void addRole() {

        Integer userId = 1;
        String username = "Jarvizz";
        RoleName roleName = RoleName.ROLE_MODERATOR;

        Role role = new Role();
        role.setName(roleName);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(roleService.findByName(roleName)).thenReturn(role);
        when(userRepository.saveAndFlush(any())).thenReturn(user);

        Assert.assertEquals(user, adminService.addRole(userId, roleName));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addInvalidRole() {

        Integer userId = 1;
        RoleName roleName = RoleName.ROLE_MODERATOR;

        Set<Role> roles = new HashSet<>();

        Role role = new Role();
        role.setName(roleName);
        roles.add(role);

        user.setRoles(roles);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        adminService.addRole(userId, roleName);
    }

    @Test
    public void removeRole() {

        Integer userId = 1;
        String username = "Jarvizz";

        RoleName roleName = RoleName.ROLE_MODERATOR;
        Role role = new Role();
        role.setName(roleName);

        Set<Role> roles = new HashSet<Role>() {{
            add(role);
        }};

        user.setRoles(roles);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(roleService.findByName(roleName)).thenReturn(role);
        when(userRepository.saveAndFlush(any())).thenReturn(user);

        User u = adminService.removeRole(userId, roleName);

        Assert.assertFalse(u.getRoles().contains(role));
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeInvalidRole() {

        Integer userId = 1;
        RoleName roleName = RoleName.ROLE_MODERATOR;

        Set<Role> roles = new HashSet<>();

        Role role = new Role();
        role.setName(roleName);
        roles.add(role);

        user.setRoles(roles);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        adminService.removeRole(userId, RoleName.ROLE_ADMIN);
    }
}