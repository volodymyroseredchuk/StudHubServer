package com.softserve.academy.studhub.security.controller;

import com.softserve.academy.studhub.entity.Role;
import com.softserve.academy.studhub.entity.enums.RoleName;
import com.softserve.academy.studhub.security.dto.*;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.jwt.JwtProvider;
import com.softserve.academy.studhub.security.model.ConfirmToken;
import com.softserve.academy.studhub.security.services.ConfirmTokenService;
import com.softserve.academy.studhub.service.EmailService;
import com.softserve.academy.studhub.service.RoleService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
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
    private final ConfirmTokenService confirmTokenService;
    private final EmailService emailService;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;
    private final ModelMapper modelMapper;

    @PostMapping("/signin")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        if (userService.findByUsername(loginRequest.getUsername()).getIsActivated()) {

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessTokenString = jwtProvider.generateAccessToken(authentication);
            String refreshToken = jwtProvider.generateRefreshToken(authentication);

            return ResponseEntity.ok(new JwtResponse(accessTokenString, refreshToken));
        } else {
            return ResponseEntity.status(403).body(new MessageResponse("Please, activate your account"));
        }

    }

    @PostMapping("/signup")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {

        User user = modelMapper.map(signUpRequest, User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(new HashSet<Role>() {{
            add(roleService.findByName(RoleName.ROLE_USER));
        }});
        userService.add(user);

        ConfirmToken token = new ConfirmToken(user);
        confirmTokenService.save(token);
        emailService.sendConfirmAccountEmail(user, token);

        return ResponseEntity.ok(new MessageResponse("Confirm your account at " + user.getEmail()));
    }

    @PostMapping("/confirm-account")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> confirmAccount(@Valid @RequestBody ConfirmDto form) {

        ConfirmToken token = confirmTokenService.findByToken(form.getToken());
        User user = token.getUser();
        user.setIsActivated(true);
        userService.update(user);
        confirmTokenService.delete(token);

        return ResponseEntity.ok(new MessageResponse("You have been successfully confirmed your account!"));
    }
}