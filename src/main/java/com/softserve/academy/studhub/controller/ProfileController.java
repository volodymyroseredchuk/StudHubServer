package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.UserDTO;

import com.softserve.academy.studhub.entity.Privilege;
import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.dto.*;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @GetMapping("/current")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> getCurrentUser(Principal principal) {

        User user = userService.findByUsername(principal.getName());
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        Set<PrivilegeDTO> privileges = new HashSet<>();
        for (Role role :
                user.getRoles()) {
            privileges.addAll(role.getPrivileges().stream().map(
                    privilege -> modelMapper.map(privilege, PrivilegeDTO.class)
            ).collect(Collectors.toList()));
        }
        userDTO.setPrivileges(privileges);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/{username}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {

        return ResponseEntity.ok(modelMapper.
                map(userService.findByUsername(username), UserDTO.class));
    }

    @PostMapping("/update")
    @PreAuthorize("isAuthenticated() and #updatedUserDTO.getUsername() == principal.username")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO updatedUserDTO) {

        User updatedUser = modelMapper.map(updatedUserDTO, User.class);

        return ResponseEntity.ok(modelMapper.
                map(userService.update(updatedUser), UserDTO.class));
    }
}
