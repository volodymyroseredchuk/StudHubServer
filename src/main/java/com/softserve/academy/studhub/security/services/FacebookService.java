package com.softserve.academy.studhub.security.services;

import com.softserve.academy.studhub.security.dto.FacebookData;
import com.softserve.academy.studhub.security.dto.FacebookUserData;
import com.softserve.academy.studhub.security.dto.LoginForm;

public interface FacebookService {

    FacebookData verifyFacebookToken(String accessToken);

    LoginForm authenticateUser(FacebookUserData facebookUserData) throws IllegalArgumentException;
}
