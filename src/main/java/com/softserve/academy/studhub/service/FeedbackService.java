package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Feedback;

import java.util.List;


public interface FeedbackService {
    Feedback save(Feedback feedback);

    List<Feedback> findByTeacherId(Integer teacherId);

    List<Feedback> findByUniversityId(Integer universityId);

    List<Feedback> findAll();

}
