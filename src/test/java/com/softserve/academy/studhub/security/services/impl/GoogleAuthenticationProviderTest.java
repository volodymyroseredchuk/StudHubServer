package com.softserve.academy.studhub.security.services.impl;

import com.softserve.academy.studhub.entity.SocketToken;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoogleAuthenticationProviderTest {

    @Mock
    UserService userService;

    GoogleAuthenticationProvider provider;

    @Before
    public void initialize() {
        provider = new GoogleAuthenticationProvider(userService);
    }

    @Test
    public void authenticate() {
        String username = "Zap";
        String password = "123";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setGooglePassword(BCrypt.hashpw(password, BCrypt.gensalt()));

        Authentication authentication = new UsernamePasswordAuthenticationToken
                (UserPrinciple.build(user), password, Collections.emptyList());

        when(userService.findByUsername(username)).thenReturn(user);

        Assert.assertEquals(provider.authenticate(authentication), authentication);
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