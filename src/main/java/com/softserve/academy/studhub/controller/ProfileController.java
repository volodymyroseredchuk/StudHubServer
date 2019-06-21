package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.UserDTO;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @GetMapping("/my")
    public ResponseEntity<UserDTO> gerCurrentUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = ((UserDetails) principal).getUsername();

        return new ResponseEntity<>(modelMapper.
            map(userService.findByUsername(username), UserDTO.class), HttpStatus.OK);
    }

    @GetMapping("/foreign/{id}")
    public ResponseEntity<UserDTO> getForeignUser(@PathVariable Integer id) {

        return new ResponseEntity<>(modelMapper.
            map(userService.get(id), UserDTO.class), HttpStatus.OK);
    }

}
