package com.softserve.academy.studhub.security.controller;


import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.dto.MessageResponse;
import com.softserve.academy.studhub.security.dto.PasswordForgotDto;
import com.softserve.academy.studhub.security.model.PasswordResetToken;
import com.softserve.academy.studhub.security.services.PasswordResetTokenService;
import com.softserve.academy.studhub.service.EmailService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/forgot-password")
@AllArgsConstructor
@Slf4j
public class PasswordForgotController {

    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final EmailService emailService;

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> processForgotPasswordForm(@Valid @RequestBody PasswordForgotDto form) {

        User user = userService.findByEmail(form.getEmail());
        PasswordResetToken token = new PasswordResetToken(user);
        passwordResetTokenService.save(token);

        emailService.sendResetPasswordEmail(user, token);

        return ResponseEntity.ok(new MessageResponse("The reset-password link has been sent on your email"));
    }

}
