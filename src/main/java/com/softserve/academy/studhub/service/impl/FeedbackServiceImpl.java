package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Feedback;
import com.softserve.academy.studhub.repository.FeedbackRepository;
import com.softserve.academy.studhub.service.FeedbackService;
import com.softserve.academy.studhub.service.UserService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private FeedbackRepository feedbackRepository;
    private UserService userService;


    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, UserService userService) {
        this.feedbackRepository = feedbackRepository;
        this.userService = userService;
    }

    @Override
    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

//    @Override
//    public List<Feedback> sortByAge() {
//        return feedbackRepository.findAllByOrderByCreationDateAsc();
//    }


    @Override
    public List<Feedback> findByTeacherId(Integer teacherId) {
        return feedbackRepository.findByTeacherId(teacherId);
    }

    @Override
    public List<Feedback> findByUniversityId(Integer universityId) {
        return feedbackRepository.findByUniversityId(universityId);
    }

    @Override
    public Feedback save(Feedback feedback) {
//        feedback.setUser(userService.getCurrentUser());
//        feedback.setRate(feedback.getRate());
        return feedbackRepository.saveAndFlush(feedback);
    }

}
