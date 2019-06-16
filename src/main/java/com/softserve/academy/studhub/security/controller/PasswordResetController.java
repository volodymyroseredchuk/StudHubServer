package com.softserve.academy.studhub.security.controller;


import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.dto.PasswordResetDto;
import com.softserve.academy.studhub.security.model.PasswordResetToken;
import com.softserve.academy.studhub.security.services.PasswordResetTokenService;
import com.softserve.academy.studhub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/reset-password")
public class PasswordResetController {

    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetController(UserService userService, PasswordResetTokenService passwordResetTokenService,
                                   PasswordEncoder passwordEncoder) {

        this.userService = userService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> handlePasswordReset(@Valid @RequestBody PasswordResetDto form) {

        PasswordResetToken token = passwordResetTokenService.findByToken(form.getToken());
        User user = token.getUser();
        String updatedPassword = passwordEncoder.encode(form.getPassword());
        userService.updatePassword(updatedPassword, user.getId());
        passwordResetTokenService.delete(token);

        return new ResponseEntity<String>("reset password success",
                HttpStatus.OK);
    }

}
