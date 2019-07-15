package com.softserve.academy.studhub.security.services.impl;

import com.softserve.academy.studhub.entity.User;
import com.softserve.academy.studhub.security.dto.FacebookData;
import com.softserve.academy.studhub.security.dto.FacebookUserData;
import com.softserve.academy.studhub.security.dto.LoginForm;
import com.softserve.academy.studhub.security.services.FacebookService;
import com.softserve.academy.studhub.service.RoleService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FacebookServiceImpl implements FacebookService {

    private UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder encoder;


    @Override
    public FacebookData verifyFacebookToken(String accessToken) {

        Facebook facebook = new FacebookTemplate(accessToken);
        String[] fields = {"email", "first_name", "id", "last_name", "name"};
        FacebookData facebookData = facebook.fetchObject("me", FacebookData.class, fields);

        return facebookData;
    }

    @Override
    public LoginForm authenticateUser(FacebookUserData facebookUserData) throws IllegalArgumentException {

        FacebookData facebookData = verifyFacebookToken(facebookUserData.getAuthToken());
        LoginForm form = new LoginForm();

        if (!userService.existsByEmail(facebookData.getEmail())) {
            User addUser = facebookData.toUser(roleService);
            addUser.setImageUrl(facebookUserData.getPhotoUrl());
            addUser.setPassword(encoder.encode(facebookData.getId()));
            userService.add(addUser);
            System.out.println("in service "+addUser);
            form.setUsername(facebookData.getEmail());
            form.setPassword(facebookData.getId());
        } else {
            User foundUser = userService.findByEmail(facebookData.getEmail());
            foundUser.setIsActivated(true);
            userService.update(foundUser);
            form.setUsername(foundUser.getUsername());
            form.setPassword(facebookData.getId());
        }
        return form;
    }
}
