package com.softserve.academy.studhub.service.impl;


import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.entity.enums.RoleName;
import com.softserve.academy.studhub.repository.UserRepository;
import com.softserve.academy.studhub.service.RoleService;
import com.softserve.academy.studhub.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public User findByUserName(String userName) {
        Optional<User> result = userRepository.findByUsername(userName);

        return result.orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + userName));
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

        Optional<User> user = userRepository.findById(id);

        return user.orElseThrow(() -> new UsernameNotFoundException("No user found with id: " + id));

    }

    @Override
    public User findByEmail(String email) {

        Optional<User> user = userRepository.findByEmail(email);

        return user.orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));
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
