package com.softserve.academy.studhub.security.services.impl;

import com.softserve.academy.studhub.exceptions.ExpiredTokenException;
import com.softserve.academy.studhub.security.entity.PasswordResetToken;
import com.softserve.academy.studhub.security.repository.PasswordResetTokenRepository;
import com.softserve.academy.studhub.security.services.PasswordResetTokenService;
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
public class PasswordResetTokenServiceImplTest {

    @Mock
    private PasswordResetTokenRepository passwordResetTokenRepository;

    private PasswordResetTokenService passwordResetTokenService;

    @Before
    public void init() {
        passwordResetTokenService = new PasswordResetTokenServiceImpl(passwordResetTokenRepository);
    }

    @Test
    public void findByValidToken() {

        String token = "validToken";

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryDate(30);

        when(passwordResetTokenRepository.findByToken(token)).thenReturn(Optional.of(passwordResetToken));

        Assert.assertEquals(passwordResetToken, passwordResetTokenService.findByValidToken(token));
    }

    @Test(expected = ExpiredTokenException.class)
    public void findByInvalidValidToken() {

        passwordResetTokenService.findByValidToken(null);
    }

    @Test(expected = ExpiredTokenException.class)
    public void findByExpiredValidToken() {

        String token = "validToken";

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryDate(-5);

        when(passwordResetTokenRepository.findByToken(token)).thenReturn(Optional.of(passwordResetToken));

        passwordResetTokenService.findByValidToken(token);
    }

}