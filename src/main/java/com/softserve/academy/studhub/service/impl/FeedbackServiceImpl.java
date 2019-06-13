package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.entity.Feedback;
import com.softserve.academy.studhub.repository.FeedbackRepository;
import com.softserve.academy.studhub.service.FeedbackService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public List<Feedback> findByTeacherId(Integer teacherId) {
        return feedbackRepository.findByTeacherId(teacherId);
//        List<Feedback> list = new ArrayList<>();
//        list.add(new Feedback(2, "lll", null, null, null, null, null));
//        return list;
    }

    @Override
    public List<Feedback> findByUniversityId(Integer universityId) {
        return feedbackRepository.findByUniversityId(universityId);
    }

    @Override
    public Feedback save(Feedback feedback) {
        return feedbackRepository.saveAndFlush(feedback);
    }

}
