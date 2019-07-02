package com.softserve.academy.studhub.security.services;

import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.dto.LoginForm;

public interface FacebookService {
    String facebookLogin();

    String getFacebookAccessToken(String code);

    LoginForm getFacebookUserProfile(String accessToken);
}
