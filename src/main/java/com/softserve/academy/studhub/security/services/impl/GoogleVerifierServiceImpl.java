package com.softserve.academy.studhub.security.services.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.constants.ErrorMessage;
import com.softserve.academy.studhub.security.dto.GoogleUserData;
import com.softserve.academy.studhub.security.dto.LoginForm;
import com.softserve.academy.studhub.security.services.GoogleVerifierService;
import com.softserve.academy.studhub.service.RoleService;
import com.softserve.academy.studhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleVerifierServiceImpl implements GoogleVerifierService {

    private final GoogleIdTokenVerifier verifier;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    @Autowired
    public GoogleVerifierServiceImpl(
            @Value("${studhub.google.clientId}") String clientId,
            UserService userService,
            RoleService roleService,
            PasswordEncoder encoder
    ) {
        verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();
        this.userService = userService;
        this.roleService = roleService;
        this.encoder = encoder;
    }



    @Override
    public boolean isValidToken(String clientIdToken) {
        if (clientIdToken != null) {
            GoogleIdToken idToken;
            try {
                idToken = verifier.verify(clientIdToken);
                if (idToken != null) {
                    return true;
                } else {
                    throw new IllegalArgumentException(ErrorMessage.WRONG_GOOGLE_DATA);
                }
            } catch (GeneralSecurityException | IOException e) {
                throw new IllegalArgumentException(ErrorMessage.WRONG_GOOGLE_DATA);
            }
        } else {
            throw new IllegalArgumentException("Cannot check validity of an empty client token.");
        }
    }

    @Override
    public LoginForm authenticateUser(GoogleUserData userData) throws IllegalArgumentException {
        if (userData != null) {
            isValidToken(userData.getIdToken());
            LoginForm form = new LoginForm();

            if (!userService.existsByEmail(userData.getEmail())) {
                User addUser = userData.toUser(roleService);
                addUser.setPassword(encoder.encode(userData.getId()));
                addUser.setGooglePassword(encoder.encode(userData.getId()));
                userService.add(addUser);
                form.setUsername(userData.getEmail());
                form.setPassword(userData.getId());
            } else {
                User foundUser = userService.findByEmail(userData.getEmail());
                if (foundUser.getGooglePassword() == null) {
                    foundUser.setGooglePassword(encoder.encode(userData.getId()));
                    userService.update(foundUser);
                }

                form.setUsername(foundUser.getUsername());
                form.setPassword(userData.getId());
            }

            return form;

        } else {
            throw new IllegalArgumentException("Cannot authenticate an empty user data.");
        }

    }

}
