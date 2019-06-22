package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.User;

import java.util.List;

public interface UserService {

    User add(User user);

    User findByUsername(String userName);

    User findById(Integer id);

    User update(User user);

    void delete(Integer id);

    List<User> getAll();

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User findByEmail(String email);

    void updatePassword(String password, Integer userId);

    boolean isUserPrivileged(Integer userId);

}
