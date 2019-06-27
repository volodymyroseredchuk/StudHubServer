package com.softserve.academy.studhub.security.services.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.softserve.academy.studhub.exceptions.ErrorMessage;
import com.softserve.academy.studhub.security.services.GoogleVerifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleVerifierServiceImpl implements GoogleVerifierService {

    private GoogleIdTokenVerifier verifier;

    @Autowired
    public GoogleVerifierServiceImpl(
            @Value("${studhub.google.clientId}") String clientId
    ) {
        verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();
    }



    @Override
    public boolean isValidToken(String clientIdToken) {
        GoogleIdToken idToken;
        try {
            idToken = verifier.verify(clientIdToken);
            if (idToken != null) {
                return true;
            } else {
                throw new IllegalArgumentException(ErrorMessage.WRONG_GOOGLE_ID_TOKEN);
            }
        } catch (GeneralSecurityException | IOException e) {
            throw new IllegalArgumentException(ErrorMessage.WRONG_GOOGLE_ID_TOKEN);
        }
    }

}
