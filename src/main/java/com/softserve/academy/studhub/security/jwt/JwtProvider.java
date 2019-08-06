package com.softserve.academy.studhub.security.jwt;

import com.softserve.academy.studhub.security.constants.JwtConstants;
import com.softserve.academy.studhub.security.entity.UserPrinciple;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtProvider {

    @Value("${studhub.app.jwtSecret}")
    private String jwtSecret;

    @Value("${studhub.app.accessTokenExpiration}")
    private int accessTokenExpiration;

    @Value("${studhub.app.refreshTokenExpiration}")
    private int refreshTokenExpiration;

    public String generateAccessToken(Authentication authentication) {

        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrincipal.getId().toString()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + accessTokenExpiration*1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {

        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getId().toString()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + refreshTokenExpiration*1000))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public boolean validateJwtToken(String authToken) {

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error(JwtConstants.INVALID_JWT_SIGNATURE, e.getMessage());
        } catch (MalformedJwtException e) {
            log.error(JwtConstants.INVALID_JWT_TOKEN, e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error(JwtConstants.JWT_EXPIRED, e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error(JwtConstants.UNSUPPORTED_JWT, e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error(JwtConstants.JWT_CLAIM_IS_EMPTY, e.getMessage());
        }

        return false;
    }

    public Integer getIdFromJwtToken(String token) {

        return Integer.valueOf(Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody().getSubject());
    }
}