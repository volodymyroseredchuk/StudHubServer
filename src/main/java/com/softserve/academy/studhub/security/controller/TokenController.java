package com.softserve.academy.studhub.security.controller;

import com.softserve.academy.studhub.security.dto.JwtResponse;
import com.softserve.academy.studhub.security.dto.MessageResponse;
import com.softserve.academy.studhub.security.jwt.JwtProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class TokenController {

    private final JwtProvider jwtProvider;

    public TokenController(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accessToken = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);

        return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
    }

    @PostMapping("/verify")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> verifyAccessToken() {

        return ResponseEntity.ok(new MessageResponse("token is valid"));
    }
}
