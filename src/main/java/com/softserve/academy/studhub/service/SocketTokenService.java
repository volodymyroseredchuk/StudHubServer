package com.softserve.academy.studhub.service;

import javax.websocket.EncodeException;

public interface SocketTokenService {

    boolean checkAccess(String token);
    String generateToken() throws EncodeException;
    void removeToken(String token);

}
