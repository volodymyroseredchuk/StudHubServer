package com.softserve.academy.studhub.service.impl;


import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.entity.enums.RoleName;
import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.exceptions.NotConfirmedException;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.exceptions.UserAlreadyExistsAuthenticationException;
import com.softserve.academy.studhub.repository.UserRepository;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User add(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsAuthenticationException(ErrorMessage.USER_ALREADY_EXISTS_BY_EMAIL);
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsAuthenticationException(ErrorMessage.USER_ALREADY_EXISTS_BY_USERNAME);
        }

        user.setCreationDate(LocalDate.now());
        return userRepository.save(user);
    }

    @Override
    public User update(User updatedUser) {

        User updatable = findByUsername(updatedUser.getUsername());

        updatable.setFirstName(updatedUser.getFirstName());
        updatable.setLastName(updatedUser.getLastName());
        updatable.setEmailSubscription(updatedUser.getEmailSubscription());
        updatable.setImageUrl(updatedUser.getImageUrl());
        updatable.setUniversity(updatedUser.getUniversity());

        return userRepository.saveAndFlush(updatable);
    }

    @Override
    public void delete(Integer id) {

        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id) throws NotFoundException {

        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_ID + id));
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(ErrorMessage.USER_NOT_FOUND_BY_USERNAME + username));
    }

    @Override
    public User findByEmail(String email) throws NotFoundException {

        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException(ErrorMessage.USER_NOT_FOUND_BY_EMAIL + email));
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
    public boolean isUserPrivilegedByRole(Integer userId, RoleName roleName) {

        User user = findById(userId);
        Set<Role> roles = user.getRoles();

        for (Role role : roles) {

            if (role.getName().equals(roleName)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return findByUsername(currentPrincipalName);
    }

    @Override
    public void isUserActivated(String username) {

        if (!findByUsername(username).getIsActivated()) {
            throw new NotConfirmedException(ErrorMessage.ASK_TO_CONFIRM_ACC);
        }
    }

}