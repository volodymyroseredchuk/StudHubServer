package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.coders.SocketTokenEncoder;
import com.softserve.academy.studhub.entity.SocketToken;
import com.softserve.academy.studhub.service.SocketTokenService;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.websocket.EncodeException;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

@Service
public class SocketTokenServiceImpl implements SocketTokenService {

    private static Map<String, Integer> tokenIdMap = new HashMap<>();
    private static LocalDate LAST_CLEAR_DATE = LocalDate.now();
    private static Period CLEANING_PERIOD = Period.ofDays(15);

    private SocketTokenEncoder encoder = new SocketTokenEncoder();

    @Override
    public Integer checkAccess(String token) {

        try {
            Integer id = tokenIdMap.get(token);
            removeToken(token);
            return id;
        } catch (NullPointerException e) {
            return 0;
        }

    }

    @Override
    public String generateToken(Integer id) throws EncodeException {

        if(comparePeriods(Period.between(LAST_CLEAR_DATE, LocalDate.now()), CLEANING_PERIOD)) {
            tokenIdMap.clear();
        }

        String generatedString = RandomStringUtils.random(20, true, true);
        tokenIdMap.put(generatedString, id);

        SocketToken socketToken = new SocketToken(generatedString);

        return encoder.encode(socketToken);
    }

    @Override
    public void removeToken(String token) {
        tokenIdMap.remove(token);
    }

    private boolean comparePeriods(Period p1, Period p2) {
        return period2Days(p1) >= period2Days(p2);
    }

    private int period2Days(Period p) {
        if (p == null) {
            return 0;
        }
        return (p.getYears() * 12 + p.getMonths()) * 30 + p.getDays();
    }

}
