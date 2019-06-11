package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.Tag;
import com.softserve.academy.studhub.service.IQuestionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    private IQuestionService questionService;

    public QuestionController(IQuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<Question> getAllQuestions(){

        return questionService.sortByAge();
    }

    @GetMapping("/{id}")
    public Question getPositionById(@PathVariable int id){
        return questionService.findById(id);
    }

    @GetMapping
    public List<Question> getAllSortedByTag(@RequestBody List<Tag> tagList){

        return questionService.sortByTag(tagList);
    }




}
