package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.UserDTO;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> gerCurrentUser(Principal principal) {

        String username = principal.getName();

        return ResponseEntity.ok(modelMapper.
                map(userService.findByUsername(username), UserDTO.class));
    }

    @GetMapping("/foreign/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<UserDTO> getForeignUser(@PathVariable Integer id) {

        return ResponseEntity.ok(modelMapper.
                map(userService.findById(id), UserDTO.class));
    }

    @PostMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO updatedUserDTO) {

        User updatedUser = modelMapper.map(updatedUserDTO, User.class);

        return ResponseEntity.ok(modelMapper.
                map(userService.update(updatedUser), UserDTO.class));
    }

}
