package com.softserve.academy.studhub.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.softserve.academy.studhub.coders.SocketTokenEncoder;
import com.softserve.academy.studhub.entity.SocketToken;
import com.softserve.academy.studhub.service.SocketService;
import com.softserve.academy.studhub.service.SocketTokenService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.EncodeException;
import java.util.concurrent.TimeUnit;

@Service
public class SocketTokenServiceImpl implements SocketTokenService {

    @Autowired
    private SocketService socketService;

    private SocketTokenEncoder encoder = new SocketTokenEncoder();

    private LoadingCache<String, Integer> tokenIdMap = CacheBuilder.newBuilder()
            .maximumSize(100000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Integer>() {
                @Override
                public Integer load(String s) throws Exception {
                    return tokenIdMap.get(s);
                }
            });

    @Override
    public Integer checkAccess(String token) {
        if (token == null) {
            throw new IllegalArgumentException("Cannot check access of an empty token.");
        }

        try {
            Integer id = tokenIdMap.asMap().get(token);
            removeToken(token);
            return id;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public String generateToken(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot generate token for an empty ID.");
        }

        socketService.removeSession(id);
        String generatedString = RandomStringUtils.random(20, true, true);
        tokenIdMap.put(generatedString, id);
        SocketToken socketToken = new SocketToken(generatedString);

        try {
            return encoder.encode(socketToken);
        } catch (EncodeException e) {
            throw new IllegalArgumentException("Could not generate token.");
        }
    }

    @Override
    public void removeToken(String token) {
        if (token == null) {
            throw new IllegalArgumentException("Cannot remove an empty token.");
        }

        tokenIdMap.asMap().remove(token);
    }

}
