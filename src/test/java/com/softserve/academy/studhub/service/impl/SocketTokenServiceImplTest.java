package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.service.SocketTokenService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class SocketTokenServiceImplTest {

    private SocketTokenService tokenService = new SocketTokenServiceImpl();

    @After
    public void finalize() {
        tokenService = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkAccessNull() {
        tokenService.checkAccess(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void generateTokenNull() {
        tokenService.generateToken(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeTokenNull() {
        tokenService.removeToken(null);
    }
}