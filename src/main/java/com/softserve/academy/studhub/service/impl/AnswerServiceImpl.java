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
    public List<AnswerDTO> findAllAnswersByQuestionId(Integer questionId) {
        List<Answer> answers = answerRepository.findByQuestionIdOrderByCreationDateAsc(questionId);
        List<AnswerDTO> answerDTOS = answers.stream()
                .map(answer -> modelMapper.map(answer, AnswerDTO.class))
                .collect(Collectors.toList());

        return answerDTOS;
    }
}
