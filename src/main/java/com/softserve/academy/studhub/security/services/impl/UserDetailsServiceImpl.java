package com.softserve.academy.studhub.security.services.impl;

import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.exceptions.NotFoundException;
import com.softserve.academy.studhub.security.entity.UserPrinciple;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findByUsername(username);
        return UserPrinciple.build(user);
    }

    @Transactional
    public UserDetails loadUserById(Integer id) throws NotFoundException {

        User user = userService.findById(id);
        return UserPrinciple.build(user);
    }
}