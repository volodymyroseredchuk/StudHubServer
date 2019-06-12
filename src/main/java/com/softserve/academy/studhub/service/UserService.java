package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User add(User user);

    User get(Integer id);

    User update(User user);

    void delete(Integer id);

    List<User> getAll();

    User findByUsername(String username);

}
