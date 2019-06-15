package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.dto.AnswerDTO;

import java.util.List;

public interface AnswerService {
    List<AnswerDTO> findAllAnswersByQuestionId(Integer questionId);
}
