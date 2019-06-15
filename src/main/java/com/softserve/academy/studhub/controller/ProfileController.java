package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.repository.UserRepository;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private UserRepository userRepository;

    private UserService userService;

    @GetMapping("/my")
    public Object gerCurrentUser() {

        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/foreign/{id}")
    public User getForeignUser(@PathVariable Integer id) {

        return userService.get(id);
    }

}
