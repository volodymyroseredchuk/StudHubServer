package com.softserve.academy.studhub.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.graph.Graph;
import com.softserve.academy.studhub.coders.SocketTokenEncoder;
import com.softserve.academy.studhub.entity.SocketToken;
import com.softserve.academy.studhub.service.SocketTokenService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;
import javax.websocket.EncodeException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class SocketTokenServiceImpl implements SocketTokenService {

    private SocketTokenEncoder encoder = new SocketTokenEncoder();

    private static LoadingCache<String, Integer> tokenIdMap = CacheBuilder.newBuilder()
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

        try {
            Integer id = tokenIdMap.asMap().get(token);
            removeToken(token);
            return id;
        } catch (NullPointerException e) {
            return 0;
        }

    }

    @Override
    public String generateToken(Integer id) throws EncodeException {

        String generatedString = RandomStringUtils.random(20, true, true);
        tokenIdMap.put(generatedString, id);

        SocketToken socketToken = new SocketToken(generatedString);

        return encoder.encode(socketToken);
    }

    @Override
    public void removeToken(String token) {
        tokenIdMap.asMap().remove(token);
    }

}
