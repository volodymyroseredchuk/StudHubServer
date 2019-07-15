package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Freelancer;

import java.security.Principal;

public interface FreelancerService {

    Freelancer add(Freelancer freelancer, Principal principal);
}
