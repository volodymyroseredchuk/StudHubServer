package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.QuestionForListDTO;
import com.softserve.academy.studhub.dto.QuestionPaginatedDTO;
import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.entity.Tag;
import com.softserve.academy.studhub.service.IQuestionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/questions")
public class QuestionController {

    private IQuestionService questionService;

    private ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<QuestionPaginatedDTO> getAllQuestions(Pageable pageable) {
        Page<Question> questionPage = questionService.sortByAge(pageable);

        List<QuestionForListDTO> questionForListDTOs = questionPage.getContent().stream()
            .map(question -> modelMapper.map(question, QuestionForListDTO.class))
            .collect(Collectors.toList());

        return ResponseEntity.ok().body(new QuestionPaginatedDTO(questionForListDTOs, questionPage.getTotalElements()));
    }


    @GetMapping("/{id}")
    public Question showQuestionPage(@PathVariable Integer id) {
        return questionService.findById(id);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<QuestionPaginatedDTO> getSearched(@PathVariable String[] keywords, Pageable pageable) {
        Page<Question> questionPage = questionService.search(keywords, pageable);

        List<QuestionForListDTO> questionForListDTOs = questionPage.getContent().stream()
            .map(question -> modelMapper.map(question, QuestionForListDTO.class))
            .collect(Collectors.toList());

        return ResponseEntity.ok().body(new QuestionPaginatedDTO(questionForListDTOs, questionPage.getTotalElements()));
    }


    @GetMapping("/tagged/{tags}")
    public ResponseEntity<QuestionPaginatedDTO> getAllSortByTags(@PathVariable String[] tags, Pageable pageable) {
        Page<Question> questionPage = questionService.sortByTags(tags, pageable);

        List<QuestionForListDTO> questionForListDTOs = questionPage.getContent().stream()
            .map(question -> modelMapper.map(question, QuestionForListDTO.class))
            .collect(Collectors.toList());

        return ResponseEntity.ok().body(new QuestionPaginatedDTO(questionForListDTOs, questionPage.getTotalElements()));
    }

    @PutMapping("/{questionId}/edit")
    //@PreAuthorize("@questionServiceImpl.findById(#questionId).getUser().getUsername() == principal.username")
    public Question editQuestion(@PathVariable Integer questionId, @RequestBody Question question) {

        return questionService.update(questionId, question);
    }

    @PostMapping("/create")
    //@PreAuthorize("hasRole('USER')")
    public Question createQuestion(@Valid @RequestBody Question question) {

        //return questionService.save(question, principal);
        return questionService.saveNoUser(question);
    }

    @DeleteMapping("/{questionId}/delete")
    //@PreAuthorize("hasRole('ADMIN') or @questionServiceImpl.findById(#questionId).getUser().getUsername()== principal.username")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer questionId) {

        return ResponseEntity.ok(questionService.deleteById(questionId));
    }


}
