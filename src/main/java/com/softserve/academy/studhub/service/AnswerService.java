package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.entity.Answer;

import java.util.List;

public interface AnswerService {
    List<Answer> findAllAnswersByQuestionId(Integer questionId);
}
