package com.softserve.academy.studhub.service.impl;

import com.softserve.academy.studhub.dto.AnswerDTO;
import com.softserve.academy.studhub.entity.Answer;
import com.softserve.academy.studhub.repository.AnswerRepository;
import com.softserve.academy.studhub.service.AnswerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private AnswerRepository answerRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<Answer> findAllAnswersByQuestionId(Integer questionId) {
        List<Answer> answers = answerRepository.findByQuestionIdOrderByCreationDateAsc(questionId);


        return answers;
    }
}
