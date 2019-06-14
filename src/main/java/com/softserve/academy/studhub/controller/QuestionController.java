package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.Tag;
import com.softserve.academy.studhub.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/question")
public class QuestionController {

    private IQuestionService questionService;

    @Autowired
    public QuestionController(IQuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public List<Question> getAllQuestions() {

        return questionService.sortByAge();
    }

    @GetMapping("/{id}")
    public Question showQuestionPage(@PathVariable Integer id) {
        return questionService.findById(id);
    }

    @GetMapping("/tagged")
    public List<Question> getAllSortByTags(@RequestBody List<Tag> tags) {
        return questionService.sortByTag(tags);
    }

    @PutMapping("/{id}/edit")
    public Question editQuestion(@PathVariable Integer id, @RequestBody Question question) {

        return questionService.update(id, question);
    }

    @PostMapping("/create")
    public Question createQuestion(@RequestBody Question question) {

        return questionService.save(question);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteQuestion(@PathVariable Integer id) {

        questionService.deleteById(id);
    }


}
