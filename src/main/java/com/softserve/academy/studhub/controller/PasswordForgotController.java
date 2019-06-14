package com.softserve.academy.studhub.controller;


import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.dto.PasswordForgotDto;
import com.softserve.academy.studhub.security.model.Mail;
import com.softserve.academy.studhub.security.model.PasswordResetToken;
import com.softserve.academy.studhub.security.repository.PasswordResetTokenRepository;
import com.softserve.academy.studhub.service.EmailService;
import com.softserve.academy.studhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PasswordForgotController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<String> processForgotPasswordForm(@Valid @RequestBody PasswordForgotDto form,
                                                            HttpServletRequest request) {


        User user = userService.findByEmail(form.getEmail());
        if (user == null) {
            return new ResponseEntity<String>("Fail -> no user with this email!",
                    HttpStatus.BAD_REQUEST);
        }

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUser(user);
        token.setExpiryDate(30);
        tokenRepository.save(token);

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

        return new ResponseEntity<String>("Success -> success reset",
                HttpStatus.OK);

    }

}
