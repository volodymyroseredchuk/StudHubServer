package com.softserve.academy.studhub.security.services;

import com.softserve.academy.studhub.security.dto.GoogleUserData;
import com.softserve.academy.studhub.security.dto.LoginForm;

public interface GoogleVerifierService {

    boolean isValidToken(String clientIdToken);
    LoginForm authenticateUser(GoogleUserData userData) throws IllegalArgumentException;

}
