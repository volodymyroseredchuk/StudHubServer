package com.softserve.academy.studhub.security.controller;


import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.dto.PasswordForgotDto;
import com.softserve.academy.studhub.security.model.Mail;
import com.softserve.academy.studhub.security.model.PasswordResetToken;
import com.softserve.academy.studhub.security.services.PasswordResetTokenService;
import com.softserve.academy.studhub.service.EmailService;
import com.softserve.academy.studhub.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/forgot-password")
@Slf4j
public class PasswordForgotController {

    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final EmailService emailService;

    public PasswordForgotController(UserService userService, PasswordResetTokenService passwordResetTokenService,
                                    EmailService emailService) {

        this.userService = userService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<String> processForgotPasswordForm(@Valid @RequestBody PasswordForgotDto form,
                                                            HttpServletRequest request) {


        try {

            User user = userService.findByEmail(form.getEmail());
            PasswordResetToken token = new PasswordResetToken();
            token.setToken(UUID.randomUUID().toString());
            token.setUser(user);
            token.setExpiryDate(30);
            passwordResetTokenService.save(token);

            Mail mail = new Mail();
            mail.setFrom("no-reply@studhub-supp.com");
            mail.setTo(user.getEmail());
            mail.setSubject("Password reset request");

            Map<String, Object> model = new HashMap<>();
            model.put("token", token);
            model.put("user", user);
            model.put("signature", "https://studhub.com");
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            model.put("resetUrl", url + "/reset-password?token=" + token.getToken());
            mail.setModel(model);
            emailService.sendEmail(mail);

        } catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<String>(e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<String>("Success -> success reset",
                HttpStatus.OK);

    }

}
