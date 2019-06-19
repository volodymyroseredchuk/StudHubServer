package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User add(User user);

    Optional<User> get(Integer id);
    User findByUserName (String userName);

    User update(User user);

    void delete(Integer id);

    List<User> getAll();

}
