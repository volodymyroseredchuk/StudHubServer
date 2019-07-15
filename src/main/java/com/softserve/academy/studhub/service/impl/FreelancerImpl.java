package com.softserve.academy.studhub.service.impl;


import com.softserve.academy.studhub.entity.Freelancer;
import com.softserve.academy.studhub.repository.FreelancerRepository;
import com.softserve.academy.studhub.service.FreelancerService;
import com.softserve.academy.studhub.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class FreelancerImpl implements FreelancerService {

    private final FreelancerRepository freelancerRepository;
    private final TaskService taskService;

    @Override
    public Freelancer add(Freelancer freelancer, Integer taskId) {

        freelancer.setUser(taskService.findById(taskId).getUser());
        return freelancerRepository.saveAndFlush(freelancer);
    }
}