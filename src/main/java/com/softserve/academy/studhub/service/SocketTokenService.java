package com.softserve.academy.studhub.service;

import javax.websocket.EncodeException;

public interface SocketTokenService {

    Integer checkAccess(String token);
    String generateToken(Integer id) throws EncodeException;
    void removeToken(String token);

}
