package com.softserve.academy.studhub.security.services.impl;

import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor
public class GoogleAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        if (auth == null) {
            throw new BadCredentialsException("Cannot authenticate an empty authentication.");
        }

        String username = auth.getName();
        String password = auth.getCredentials()
                .toString();

        User user = userService.findByUsername(username);
        String hashedPass = user.getGooglePassword();

        if (hashedPass == null) {
            return null;
        } else if (BCrypt.checkpw(password, hashedPass)) {
            return new UsernamePasswordAuthenticationToken
                    (UserPrinciple.build(user), password, Collections.emptyList());
        } else {
            throw new
                    BadCredentialsException("External system authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
