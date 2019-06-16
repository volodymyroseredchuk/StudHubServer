package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.AnswerDTO;
import com.softserve.academy.studhub.entity.Answer;
import com.softserve.academy.studhub.repository.AnswerRepository;
import com.softserve.academy.studhub.service.AnswerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
public class AnswerController {

    private AnswerService answerService;

    private ModelMapper modelMapper;

    @GetMapping("/question/{questionId}/answer")
    public List<AnswerDTO> getAnswersByQuestionId(@PathVariable Integer questionId){
        List<Answer> answers = answerService.findAllAnswersByQuestionId(questionId);
        List<AnswerDTO> answerDTOS = answers.stream()
                .map(answer -> modelMapper.map(answer, AnswerDTO.class))
                .collect(Collectors.toList());

        return answerDTOS;

    }


}
