package com.softserve.academy.studhub.controller;

import com.softserve.academy.studhub.dto.QuestionDTO;
import com.softserve.academy.studhub.dto.QuestionForListDTO;
import com.softserve.academy.studhub.dto.QuestionPaginatedDTO;
import com.softserve.academy.studhub.entity.Question;
import com.softserve.academy.studhub.service.IQuestionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    @PreAuthorize("permitAll()")
    public ResponseEntity<QuestionPaginatedDTO> getAllQuestions(Pageable pageable) {

        Page<Question> questionPage = questionService.findAllSortedByAge(pageable);

        List<QuestionForListDTO> questionForListDTOs = questionPage.getContent().stream()
            .map(question -> modelMapper.map(question, QuestionForListDTO.class))
            .collect(Collectors.toList());

        return ResponseEntity.ok().body(new QuestionPaginatedDTO(questionForListDTOs, questionPage.getTotalElements()));
    }


    @GetMapping("/{questionId}")
    @PreAuthorize("permitAll()")
    public Question getQuestionById(@PathVariable Integer questionId) {

        return questionService.findById(questionId);
    }

    @GetMapping("/search/{keywords}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<QuestionPaginatedDTO> getSearchedByKeywordsQuestions(@PathVariable String[] keywords, Pageable pageable) {

        Page<Question> questionPage = questionService.searchByKeywords(keywords, pageable);

        List<QuestionForListDTO> questionForListDTOs = questionPage.getContent().stream()
            .map(question -> modelMapper.map(question, QuestionForListDTO.class))
            .collect(Collectors.toList());

        return ResponseEntity.ok().body(new QuestionPaginatedDTO(questionForListDTOs, questionPage.getTotalElements()));
    }


    @GetMapping("/tagged/{tags}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<QuestionPaginatedDTO> getSearchedByTagsQuestions(@PathVariable String[] tags, Pageable pageable) {

        Page<Question> questionPage = questionService.searchByTags(tags, pageable);

        List<QuestionForListDTO> questionForListDTOs = questionPage.getContent().stream()
            .map(question -> modelMapper.map(question, QuestionForListDTO.class))
            .collect(Collectors.toList());

        return ResponseEntity.ok().body(new QuestionPaginatedDTO(questionForListDTOs, questionPage.getTotalElements()));
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<QuestionDTO> createQuestion(@Valid @RequestBody QuestionDTO questionDto, Principal principal) {

        Question result = questionService.save(modelMapper.map(questionDto, Question.class), principal);
        return ResponseEntity.ok(modelMapper.map(result, QuestionDTO.class));
    }

    @PutMapping("/{questionId}")
    @PreAuthorize("isAuthenticated() and @questionServiceImpl.findById(#questionId).getUser().getUsername() == principal.username")
    public ResponseEntity<QuestionDTO> editQuestion(@PathVariable Integer questionId, @RequestBody QuestionDTO questionDto) {

        Question result = questionService.update(questionId, modelMapper.map(questionDto, Question.class));
        return ResponseEntity.ok(modelMapper.map(result, QuestionDTO.class));
    }


    @DeleteMapping("/{questionId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or @questionServiceImpl.findById(#questionId).getUser().getUsername()== principal.username")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer questionId) {

        return ResponseEntity.ok(questionService.deleteById(questionId));
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<QuestionForListDTO>> getAllQuestionsByCurrentUser(@PathVariable String username) {

        return ResponseEntity.ok(questionService.findQuestionByUserUsernameOrderByCreationDateDesc(username).
                stream().map(question -> modelMapper.map(question, QuestionForListDTO.class)).
                collect(Collectors.toList()));
    }

}
