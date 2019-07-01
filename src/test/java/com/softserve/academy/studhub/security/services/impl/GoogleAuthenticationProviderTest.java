package com.softserve.academy.studhub.security.services.impl;

import com.softserve.academy.studhub.entity.SocketToken;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GoogleAuthenticationProviderTest {

    @Mock
    UserService userService;

    @InjectMocks
    GoogleAuthenticationProvider provider = new GoogleAuthenticationProvider(userService);

    // TODO: FIX
    @Test
    public void authenticate() {
        /*String username = "Zap";
        String password = "123";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        Authentication authentication = new UsernamePasswordAuthenticationToken
                (UserPrinciple.build(user), password, Collections.emptyList());

        when(userService.findByUsername(username)).thenReturn(user);

        Assert.assertEquals(provider.authenticate(authentication), authentication);*/
    }

    @Test(expected = BadCredentialsException.class)
    public void authenticateNull() {
        provider.authenticate(null);
    }

    @Test
    public void supportsPositive() {
        Assert.assertTrue(provider.supports(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void supportsNegative() {
        Assert.assertFalse(provider.supports(SocketToken.class));
    }

}