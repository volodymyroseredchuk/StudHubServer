package com.softserve.academy.studhub.security.services.impl;

import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.dto.LoginForm;
import com.softserve.academy.studhub.security.services.FacebookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

@Service
public class FacebookServiceImpl implements FacebookService {

    @Value("${GOOGLE_CLIENT_ID}")
    private String facebookId;
    @Value("${FACEBOOK_CLIENT_ID}")
    private String facebookSecret;

    private FacebookConnectionFactory createFacebookConnection(){
        return  new FacebookConnectionFactory(facebookId, facebookSecret);
    }

    @Override
    public String facebookLogin() {
        OAuth2Parameters parameters = new OAuth2Parameters();
        parameters.setRedirectUri("http://localhost:8080/facebook");
        parameters.setScope("public_profile, email");
        return createFacebookConnection().getOAuthOperations().buildAuthenticateUrl(parameters);
    }

    @Override
    public String getFacebookAccessToken(String code) {
        return createFacebookConnection().getOAuthOperations()
                .exchangeForAccess(code, "http://localhost:8080/facebook", null)
                .getAccessToken();
    }

    @Override
    public LoginForm getFacebookUserProfile(String accessToken) {
        Facebook facebook = new FacebookTemplate(accessToken);
        String [] fields = {"username", "password"};
        return facebook.fetchObject("me", LoginForm.class, fields);
    }
}
