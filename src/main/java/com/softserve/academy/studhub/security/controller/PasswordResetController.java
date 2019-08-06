package com.softserve.academy.studhub.security.controller;


import com.softserve.academy.studhub.constants.SuccessMessage;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.dto.MessageResponse;
import com.softserve.academy.studhub.security.dto.PasswordForgotDto;
import com.softserve.academy.studhub.security.dto.PasswordResetDto;
import com.softserve.academy.studhub.security.entity.PasswordResetToken;
import com.softserve.academy.studhub.security.services.PasswordResetTokenService;
import com.softserve.academy.studhub.service.EmailService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
public class PasswordResetController {

    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @PostMapping("/forgot-password")
    @PreAuthorize("permitAll()")
    public void processForgotPasswordForm(@Valid @RequestBody PasswordForgotDto form) {

        userService.findByEmail(form.getEmail());
    }

    @PostMapping("/forgot-password/confirm")
    @PreAuthorize("permitAll()")
    public ResponseEntity<MessageResponse> sendForgotPasswordLink(@Valid @RequestBody PasswordForgotDto form) {

        User user = userService.findByEmail(form.getEmail());
        PasswordResetToken token = new PasswordResetToken(user);
        passwordResetTokenService.save(token);

        emailService.sendResetPasswordEmail(token);

        return ResponseEntity.ok(new MessageResponse(SuccessMessage.SENT_RESET_PASSWORD_LINK + user.getEmail()));
    }

    @PostMapping("/reset-password")
    @Transactional
    @PreAuthorize("permitAll()")
    public ResponseEntity<MessageResponse> handlePasswordReset(@Valid @RequestBody PasswordResetDto form) {

        PasswordResetToken token = passwordResetTokenService.findByValidToken(form.getToken());

        User user = token.getUser();

        String updatedPassword = passwordEncoder.encode(form.getPassword());
        userService.updatePassword(updatedPassword, user.getId());
        passwordResetTokenService.delete(token);

        return ResponseEntity.ok(new MessageResponse(SuccessMessage.RESET_PASSWORD));
    }

}
