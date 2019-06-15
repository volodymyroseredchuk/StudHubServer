package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.AnswerDTO;
import com.softserve.academy.studhub.service.AnswerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class AnswerController {
    private AnswerService answerService;

    @GetMapping("/question/{questionId}/answer")
    public List<AnswerDTO> getAnswersByQuestionId(@PathVariable Integer questionId){
        return answerService.findAllAnswersByQuestionId(questionId);

    }
}
