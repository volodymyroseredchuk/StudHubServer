package com.softserve.academy.studhub.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.softserve.academy.studhub.service.SocketTokenService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SocketTokenServiceImplTest {

    @InjectMocks
    private SocketTokenService tokenService = new SocketTokenServiceImpl();

    @Mock
    private LoadingCache<String, Integer> tokenIdMap;

    /*@Before
    public void initialize() {
        tokenService = new SocketTokenServiceImpl();
    }*/

    @After
    public void finalize() {
        tokenService = null;
    }

    @Test
    public void checkAccessPositive() {
        ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("123", 1);
        when(tokenIdMap.asMap()).thenReturn(map);
        Assert.assertEquals(tokenService.checkAccess("123"), new Integer(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkAccessNull() {
        tokenService.checkAccess(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void generateTokenNull() {
        tokenService.generateToken(null);
    }

    @Test
    public void removeTokenPositive() {
        ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("123", 1);
        when(tokenIdMap.asMap()).thenReturn(map);
        tokenService.removeToken("123");
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeTokenNull() {
        tokenService.removeToken(null);
    }
}