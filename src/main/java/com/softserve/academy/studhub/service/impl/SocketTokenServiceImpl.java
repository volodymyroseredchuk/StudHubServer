package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.coders.SocketTokenEncoder;
import com.softserve.academy.studhub.entity.SocketToken;
import com.softserve.academy.studhub.service.SocketTokenService;
import org.apache.commons.lang.RandomStringUtils;

import javax.websocket.EncodeException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class SocketTokenServiceImpl implements SocketTokenService {

    private static List<String> tokenList = new ArrayList<>();
    private static LocalDate LAST_CLEAR_DATE = LocalDate.now();
    private static Period CLEANING_PERIOD = Period.ofDays(15);

    private SocketTokenEncoder encoder = new SocketTokenEncoder();

    @Override
    public boolean checkAccess(String token) {

        for(String t : tokenList) {
            if (t.equals(token)) {
                removeToken(t);
                return true;
            }
        }
        return false;

    }

    @Override
    public String generateToken() throws EncodeException {

        if(comparePeriods(Period.between(LAST_CLEAR_DATE, LocalDate.now()), CLEANING_PERIOD)) {
            tokenList.clear();
        }

        String generatedString = RandomStringUtils.random(20, true, true);
        tokenList.add(generatedString);

        SocketToken socketToken = new SocketToken(generatedString);

        return encoder.encode(socketToken);
    }

    @Override
    public void removeToken(String token) {
        tokenList.remove(token);
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
