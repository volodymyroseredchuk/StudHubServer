package com.softserve.academy.studhub.service.impl;


import com.softserve.academy.studhub.entity.Freelancer;
import com.softserve.academy.studhub.repository.FreelancerRepository;
import com.softserve.academy.studhub.service.FreelancerService;
import com.softserve.academy.studhub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@AllArgsConstructor
public class FreelancerImpl implements FreelancerService {

    private final FreelancerRepository freelancerRepository;
    private final UserService userService;

    @Override
    public Freelancer add(Freelancer freelancer, Principal principal) {

        freelancer.setUser(userService.findByUsername(principal.getName()));
        return freelancerRepository.saveAndFlush(freelancer);
    }
}