package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.*;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.service.FeedbackService;
import com.softserve.academy.studhub.service.IQuestionService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @GetMapping
    @PreAuthorize("permitAll()")
    public List<UserForListDTO> getAllUsers() {

        List<UserForListDTO> userForListDTOS = userService.findAll().stream()
                .map(user -> modelMapper.map(user, UserForListDTO.class))
                .collect(Collectors.toList());

        return userForListDTOS;
    }

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> gerCurrentUser(Principal principal) {

        String username = principal.getName();

        return new ResponseEntity<>(modelMapper.
                map(userService.findByUsername(username), UserDTO.class), HttpStatus.OK);
    }

    @GetMapping("/foreign/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<UserDTO> getForeignUser(@PathVariable Integer id) {

        return new ResponseEntity<>(modelMapper.
                map(userService.findById(id), UserDTO.class), HttpStatus.OK);
    }

    @PostMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO updatedUser) {

        User user = modelMapper.map(updatedUser, User.class);

        return new ResponseEntity<>(modelMapper.
                map(userService.update(user), UserDTO.class), HttpStatus.OK);
    }

}
