package com.softserve.academy.studhub.security.controller;

import com.softserve.academy.studhub.constants.SuccessMessage;
import com.softserve.academy.studhub.security.dto.JwtResponse;
import com.softserve.academy.studhub.security.dto.MessageResponse;
import com.softserve.academy.studhub.security.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
@AllArgsConstructor
public class TokenController {

    private final JwtProvider jwtProvider;

    @PostMapping("/refresh")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> refreshAccessToken() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accessToken = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);

        return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
    }

    @PostMapping("/verify")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> verifyAccessToken() {

        return ResponseEntity.ok(new MessageResponse(SuccessMessage.VALID_TOKEN));
    }
}
