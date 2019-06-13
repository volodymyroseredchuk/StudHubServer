package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.entity.Answer;
import com.softserve.academy.studhub.repository.AnswerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TestAnswerControllerRestAPI {

    private AnswerRepository answerRepository;



    @GetMapping("/api/test/getAllAnswers")
    public List<Answer> getAllAnswers(){
        List<Answer> answers = answerRepository.findAll();

        return answers;
    }
}
