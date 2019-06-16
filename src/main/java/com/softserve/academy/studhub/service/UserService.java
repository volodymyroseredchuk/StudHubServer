package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.dto.UserDTO;
import com.softserve.academy.studhub.entity.User;

import java.util.List;

public interface UserService {

    UserDTO add(User user);

    UserDTO get(Integer id);

    UserDTO update(User user);

    void delete(Integer id);

    List<UserDTO> getAll();

    UserDTO findByUsername(String username);

}
