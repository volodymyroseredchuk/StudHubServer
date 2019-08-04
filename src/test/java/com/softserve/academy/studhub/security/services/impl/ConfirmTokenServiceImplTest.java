package com.softserve.academy.studhub.security.services.impl;

import com.softserve.academy.studhub.exceptions.ExpiredTokenException;
import com.softserve.academy.studhub.security.entity.ConfirmToken;
import com.softserve.academy.studhub.security.repository.ConfirmTokenRepository;
import com.softserve.academy.studhub.security.services.ConfirmTokenService;
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
public class ConfirmTokenServiceImplTest {

    @Mock
    private ConfirmTokenRepository confirmTokenRepository;

    private ConfirmTokenService confirmTokenService;

    @Before
    public void init() {
        confirmTokenService = new ConfirmTokenServiceImpl(confirmTokenRepository);
    }

    @Test
    public void findByValidToken() {

        String token = "validToken";

        ConfirmToken confirmToken = new ConfirmToken();
        confirmToken.setToken(token);
        confirmToken.setExpiryDate(30);

        when(confirmTokenRepository.findByToken(token)).thenReturn(Optional.of(confirmToken));

        Assert.assertEquals(confirmToken, confirmTokenService.findByValidToken(token));
    }

    @Test(expected = ExpiredTokenException.class)
    public void findByInvalidValidToken() {

        confirmTokenService.findByValidToken(null);
    }

    @Test(expected = ExpiredTokenException.class)
    public void findByExpiredValidToken() {

        String token = "validToken";

        ConfirmToken confirmToken = new ConfirmToken();
        confirmToken.setToken(token);
        confirmToken.setExpiryDate(-5);

        when(confirmTokenRepository.findByToken(token)).thenReturn(Optional.of(confirmToken));

        confirmTokenService.findByValidToken(token);
    }

}