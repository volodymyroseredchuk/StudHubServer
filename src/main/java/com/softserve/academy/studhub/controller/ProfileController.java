package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.FeedbackDTO;
import com.softserve.academy.studhub.dto.PrivilegeDTO;
import com.softserve.academy.studhub.dto.QuestionForListDTO;
import com.softserve.academy.studhub.dto.UserDTO;
import com.softserve.academy.studhub.entity.Privilege;
import com.softserve.academy.studhub.entity.Role;
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

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> gerCurrentUser(Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username);
        UserDTO userDTO = modelMapper.
                map(user, UserDTO.class);
        Set<PrivilegeDTO> privileges = new HashSet<>();
        for (Role role :
             user.getRoles()) {
            privileges.addAll(role.getPrivileges().stream().map(
                    privilege -> modelMapper.map(privilege, PrivilegeDTO.class)
            ).collect(Collectors.toList()));
        }
        userDTO.setPrivileges(privileges);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
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

        return new ResponseEntity<>(modelMapper.
                map(userService.update(modelMapper.map(updatedUser, User.class)), UserDTO.class), HttpStatus.OK);
    }

}
