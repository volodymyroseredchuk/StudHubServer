package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.dto.FreelancerForRatingDTO;
import com.softserve.academy.studhub.entity.Freelancer;

public interface FreelancerService {

    Freelancer add(Freelancer freelancer, Integer orderId);

    FreelancerForRatingDTO getRatingByUserUsername(String username);
}
