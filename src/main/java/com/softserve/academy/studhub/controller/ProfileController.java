package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.UserDto;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private UserService userService;

    @GetMapping("/my")
    public ResponseEntity<UserDto> gerCurrentUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return new ResponseEntity<UserDto>(userService.findByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/foreign/{id}")
    public ResponseEntity<UserDto> getForeignUser(@PathVariable Integer id) {

        return new ResponseEntity<UserDto>(userService.get(id), HttpStatus.OK);
    }

}
