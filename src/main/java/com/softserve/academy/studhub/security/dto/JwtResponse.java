package com.softserve.academy.studhub.security.dto;

import lombok.Data;

@Data
public class JwtResponse {

    private String accessToken;

    private String refreshToken;

    private String type = "Bearer";

    public JwtResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}