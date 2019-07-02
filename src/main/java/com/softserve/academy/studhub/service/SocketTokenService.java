package com.softserve.academy.studhub.service;

public interface SocketTokenService {

    Integer checkAccess(String token);
    String generateToken(Integer id);
    void removeToken(String token);

}
