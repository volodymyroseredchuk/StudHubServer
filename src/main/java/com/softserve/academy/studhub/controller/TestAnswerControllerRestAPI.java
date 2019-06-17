package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.AnswerDTO;
import com.softserve.academy.studhub.entity.Answer;
import com.softserve.academy.studhub.repository.AnswerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class TestAnswerControllerRestAPI {

    private AnswerRepository answerRepository;

    private final ModelMapper modelMapper;

    @GetMapping("/api/test/getAllAnswers/{questionId}")
    public List<AnswerDTO> getAllAnswers(@PathVariable Integer questionId){
        List<Answer> answers = answerRepository.findByQuestionIdOrderByCreationDateAsc(questionId);
        List<AnswerDTO> answerDTOS = answers.stream()
                .map(answer -> modelMapper.map(answer, AnswerDTO.class))
                .collect(Collectors.toList());

        return answerDTOS;
    }
}