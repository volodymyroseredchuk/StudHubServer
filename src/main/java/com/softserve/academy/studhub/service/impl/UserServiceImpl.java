package com.softserve.academy.studhub.service.impl;


import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.entity.enums.RoleName;
import com.softserve.academy.studhub.exceptions.ErrorMessage;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.repository.UserRepository;
import com.softserve.academy.studhub.service.RoleService;
import com.softserve.academy.studhub.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public User add(User user) {

        return userRepository.saveAndFlush(user);
    }

    @Override
    public User update(User user) {

        return userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(Integer id) {

        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAll() {

        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id) {

        return userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + id));
    }

    @Override
    public User findByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow(() ->
                new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_USERNAME + username));
    }

    @Override
    public User findByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_EMAIL + email));
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void updatePassword(String password, Integer userId) {
        userRepository.updatePassword(password, userId);
    }

    @Override
    public boolean isUserPrivileged(Integer userId) {

        User user = findById(userId);
        Set<Role> roles = user.getRoles();

        for (Role role : roles) {

            if (role.getName().equals(RoleName.ROLE_MODERATOR)) {
                return true;
            }
        }

        return false;
    }
}
