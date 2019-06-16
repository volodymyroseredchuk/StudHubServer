package com.softserve.academy.studhub.security.services;

public interface AdminService {

    void raiseToModerator(Integer userId);

    void downToUser(Integer moderatorId);
}
