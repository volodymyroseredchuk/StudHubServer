package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.entity.enums.RoleName;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.RoleRepository;
import com.softserve.academy.studhub.service.RoleService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    private RoleService roleService;

    @Before
    public void init() {
        roleService = new RoleServiceImpl(roleRepository);
    }

    @Test
    public void findByName() {

        RoleName roleName = RoleName.ROLE_USER;

        Role role = new Role();
        role.setName(roleName);

        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role));

        Role foundRole = roleService.findByName(roleName);

        Assert.assertEquals(foundRole.getName(), role.getName());
    }

    @Test(expected = NotFoundException.class)
    public void findByInvalidName() {
        roleService.findByName(null);
    }

}