package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.dto.UserDto;
import com.softserve.academy.studhub.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User add(User user);

    UserDto get(Integer id);

    User update(User user);

    void delete(Integer id);

    List<User> getAll();

    UserDto findByUsername(String username);

}
