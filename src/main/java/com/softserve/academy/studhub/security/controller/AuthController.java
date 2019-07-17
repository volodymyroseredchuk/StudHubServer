package com.softserve.academy.studhub.security.controller;

import com.softserve.academy.studhub.constants.SuccessMessage;
import com.softserve.academy.studhub.security.dto.*;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.jwt.JwtProvider;
import com.softserve.academy.studhub.security.services.FacebookService;
import com.softserve.academy.studhub.security.services.GoogleVerifierService;
import com.softserve.academy.studhub.security.entity.ConfirmToken;
import com.softserve.academy.studhub.security.services.ConfirmTokenService;
import com.softserve.academy.studhub.service.EmailService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final GoogleVerifierService googleVerifier;
    private final UserService userService;
    private final ConfirmTokenService confirmTokenService;
    private final EmailService emailService;
    private final JwtProvider jwtProvider;
    private FacebookService facebookService;
    private final SignupConverter converter;


    @PostMapping("/signin")
    @PreAuthorize("permitAll()")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        return authenticate(loginRequest);
    }

    @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {

        User user = converter.convertToUser(signUpRequest);
        userService.add(user);

        return ResponseEntity.ok(new MessageResponse(SuccessMessage.USER_REGISTERED + user.getEmail()));
    }

    @PostMapping("/signup/confirm")
    @PreAuthorize("permitAll()")
    public ResponseEntity<MessageResponse> sendConfirmLink(@Valid @RequestBody SignUpForm signUpRequest) {

        User user = userService.findByUsername(signUpRequest.getUsername());

        ConfirmToken token = new ConfirmToken(user);
        confirmTokenService.save(token);

        emailService.sendConfirmAccountEmail(user, token);

        return ResponseEntity.ok(new MessageResponse(SuccessMessage.SENT_CONFIRM_ACC_LINK + user.getEmail()));
    }

    @PostMapping("/signinGoogle")
    @PreAuthorize("permitAll()")
    public ResponseEntity<JwtResponse> authenticateGoogleUser(@Valid @RequestBody GoogleUserData userData) {

        LoginForm form = googleVerifier.authenticateUser(userData);

        return authenticate(form);
    }

    @PostMapping("/signinFacebook")
    @PreAuthorize("permitAll()")
    public ResponseEntity<JwtResponse> authenticateFacebookUser(@Valid @RequestBody FacebookUserData userData) {

        LoginForm form = facebookService.authenticateUser(userData);
        System.out.println(form);
        return authenticate(form);
    }

    @PostMapping("/confirm-account")
    @PreAuthorize("permitAll()")
    public ResponseEntity<MessageResponse> confirmAccount(@Valid @RequestBody ConfirmDto form) {

        ConfirmToken token = confirmTokenService.findByValidToken(form.getToken());

        User user = token.getUser();
        user.setIsActivated(true);
        userService.update(user);
        confirmTokenService.delete(token);

        return ResponseEntity.ok(new MessageResponse(SuccessMessage.CONFIRM_ACC));
    }

    private ResponseEntity<JwtResponse> authenticate(LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        userService.isUserActivated(loginRequest.getUsername());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessTokenString = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);

        return ResponseEntity.ok(new JwtResponse(accessTokenString, refreshToken));
    }

}