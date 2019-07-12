package com.softserve.academy.studhub.service;

import com.softserve.academy.studhub.dto.AnswerApproveDTO;
import com.softserve.academy.studhub.dto.AnswerCreateDTO;
import com.softserve.academy.studhub.entity.Answer;

import java.util.List;

public interface AnswerService {
    List<Answer> findAllByQuestionId(Integer questionId);

    Answer save(AnswerCreateDTO answerCreateDTO, Integer questionId, String username);

    Answer findById(Integer answerId);

    Boolean deleteById(Integer answerId);

    AnswerApproveDTO approve (AnswerApproveDTO answerApproveDTO);

}
