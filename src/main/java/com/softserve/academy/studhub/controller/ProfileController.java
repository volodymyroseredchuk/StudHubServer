package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.repository.UserRepository;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private UserRepository userRepository;

    @GetMapping
    public User gerCurrentUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        User currentUser = userRepository.findByUsername(username)
            .orElseThrow(() ->
                new UsernameNotFoundException("User Not Found with -> username or email : " + username)
            );

        return currentUser;
    }

}
