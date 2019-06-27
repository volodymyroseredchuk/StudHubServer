package com.softserve.academy.studhub.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.entity.enums.RoleName;
import com.softserve.academy.studhub.security.dto.*;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.jwt.JwtProvider;
import com.softserve.academy.studhub.security.services.GoogleVerifierService;
import com.softserve.academy.studhub.service.RoleService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleService roleService;
    private final GoogleVerifierService googleVerifier;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;
    private final ModelMapper modelMapper;


    @PostMapping("/signin")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        return authenticate(loginRequest);
    }

    @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {

        User user = modelMapper.map(signUpRequest, User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(new HashSet<Role>(){{
            add(roleService.findByName(RoleName.ROLE_USER));
        }});
        userService.add(user);

        return ResponseEntity.ok(new MessageResponse("User has been successfully registered"));
    }

    @PostMapping("/signinGoogle")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> authenticateGoogleUser(@Valid @RequestBody GoogleUserData userData) {

        googleVerifier.isValidToken(userData.getIdToken());
        User user = userData.toUser(roleService);

        if (!userService.existsByUsername(user.getEmail())) {
            User addUser = userData.toUser(roleService);
            addUser.setPassword(encoder.encode(userData.getId()));
            userService.add(addUser);
        }

        return authenticate(modelMapper.map(user, LoginForm.class));

    }

    private ResponseEntity<?> authenticate(LoginForm form) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        form.getUsername(),
                        form.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessTokenString = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);

        return ResponseEntity.ok(new JwtResponse(accessTokenString, refreshToken));

    }
}