package com.softserve.academy.studhub.service.impl;


import com.softserve.academy.studhub.dto.FreelancerForRatingDTO;
import com.softserve.academy.studhub.entity.Freelancer;
import com.softserve.academy.studhub.entity.Order;
import com.softserve.academy.studhub.repository.FreelancerRepository;
import com.softserve.academy.studhub.service.FreelancerService;
import com.softserve.academy.studhub.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class FreelancerImpl implements FreelancerService {

    private final FreelancerRepository freelancerRepository;
    private final OrderService orderService;

    @Override
    public Freelancer add(Freelancer freelancer, Integer orderId) {

        Order order = orderService.findById(orderId);
        freelancer.setUser(orderService.findById(orderId).getUserExecutor());
        order.setFreelancer(freelancer);

        return freelancerRepository.saveAndFlush(freelancer);
    }

    @Override
    public FreelancerForRatingDTO getRatingByUserUsername(String username) {

        FreelancerForRatingDTO result = new FreelancerForRatingDTO();

        result.setQuality(freelancerRepository.avgQualityByUserUsername(username));
        result.setVelocity(freelancerRepository.avgVelocityByUserUsername(username));
        result.setPrice(freelancerRepository.avgPriceByUserUsername(username));
        result.setContact(freelancerRepository.avgContactByUserUsername(username));

        return result;
    }
}