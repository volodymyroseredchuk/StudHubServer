package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.entity.enums.RoleName;

import java.util.List;

public interface UserService {

    User add(User user);

    User update(User user);

    void delete(Integer id);

    List<User> findAll();

    User findById(Integer id);

    User findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    User findByEmail(String email);

    void updatePassword(String password, Integer userId);

    boolean isUserPrivilegedByRole(Integer userId, RoleName roleName);

    User getCurrentUser();

    void isUserActivated(String username);
}