package com.softserve.academy.studhub.security.services.impl;

import com.softserve.academy.studhub.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

public class FacebookUserDetailService implements SocialUserDetailsService {


    private UserDetailsServiceImpl userDetailsService;
    private UserRepository repository;

    public FacebookUserDetailService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException{
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
        return new SocialUser(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

    public String execute(Connection<?> connection) {
        UserProfile profile = connection.fetchUserProfile();

        //repository.save(profile);
        return null;
    }
}
