package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User add(User user);

    Optional<User> get(Integer id);

    User update(User user);

    void delete(Integer id);

    List<User> getAll();

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User findByEmail(String email);

    void updatePassword(String password, Integer userId);

}
