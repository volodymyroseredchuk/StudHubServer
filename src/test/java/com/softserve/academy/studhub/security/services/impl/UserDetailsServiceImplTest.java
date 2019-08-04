package com.softserve.academy.studhub.security.services.impl;

import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.entity.UserPrinciple;
import com.softserve.academy.studhub.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserService userService;

    private UserDetailsServiceImpl userDetailsService;

    @Before
    public void init() {
        userDetailsService = new UserDetailsServiceImpl(userService);
    }

    @Test
    public void loadUserByUsername() {

        String username = "tarasgl";
        User user = new User();
        user.setUsername(username);

        UserDetails userDetails = UserPrinciple.build(user);

        when(userService.findByUsername(username)).thenReturn(user);

        Assert.assertEquals(userDetailsService.loadUserByUsername(username), userDetails);
    }

    @Test
    public void loadUserById() {

        Integer id = 1;
        User user = new User();
        user.setId(id);

        UserDetails userDetails = UserPrinciple.build(user);

        when(userService.findById(id)).thenReturn(user);

        Assert.assertEquals(userDetailsService.loadUserById(id), userDetails);
    }

}