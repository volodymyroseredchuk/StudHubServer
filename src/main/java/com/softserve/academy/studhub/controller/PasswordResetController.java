package com.softserve.academy.studhub.controller;


import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.dto.PasswordResetDto;
import com.softserve.academy.studhub.security.model.PasswordResetToken;
import com.softserve.academy.studhub.security.repository.PasswordResetTokenRepository;
import com.softserve.academy.studhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/reset-password")
public class PasswordResetController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordResetTokenRepository tokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity<String> handlePasswordReset(@Valid @RequestBody PasswordResetDto form,
                                                      BindingResult result,
                                                      RedirectAttributes redirectAttributes) {

        if (result.hasErrors()){
            redirectAttributes.addFlashAttribute(BindingResult.class.getName() + ".passwordResetForm", result);
            redirectAttributes.addFlashAttribute("passwordResetForm", form);
            return new ResponseEntity<String>("Fail -> reset password has errors!",
                    HttpStatus.BAD_REQUEST);
        }

        PasswordResetToken token = tokenRepository.findByToken(form.getToken());
        User user = token.getUser();
        String updatedPassword = passwordEncoder.encode(form.getPassword());
        userService.updatePassword(updatedPassword, user.getId());
        tokenRepository.delete(token);

        return new ResponseEntity<String>(" reset password success",
                HttpStatus.OK);
    }

}
