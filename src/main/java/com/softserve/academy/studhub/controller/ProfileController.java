package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.UserDTO;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
    @GetMapping("/my")
    public ResponseEntity<UserDTO> gerCurrentUser(Principal principal) {

        String username = principal.getName();

        return new ResponseEntity<>(modelMapper.
                map(userService.findByUsername(username), UserDTO.class), HttpStatus.OK);
    }

    @CrossOrigin(exposedHeaders = "Access-Control-Allow-Origin")
    @GetMapping("/foreign/{id}")
    public ResponseEntity<UserDTO> getForeignUser(@PathVariable Integer id) {

        return new ResponseEntity<>(modelMapper.
                map(userService.findById(id), UserDTO.class), HttpStatus.OK);
    }

}
